import java.sql.*;
import java.time.*;
import java.util.*;

public class DbBiblioteca {
    private Connection myDB;
    private Configurazione conf;
    
    public DbBiblioteca(Configurazione userConfig)
    {
        conf = userConfig;
        
        try
        {
            myDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
        }
        catch (SQLException ex)
        {
            System.err.println(ex.getMessage());
        }
    }
    
    public ArrayList<Libro> getBooksDisp()
    {
        String queryText = "select * from biblioteca where DataRestituzione is not null and DataPrestito is not null or DataRestituzione is null and DataPrestito is null;";
        return performQuery(queryText);
    }
    
    public ArrayList<Libro> getPrestati(LocalDate from, LocalDate to, String user)
    {
        String queryText = "select * from biblioteca where BeneficiarioPrestito = '" + user + "' and DataPrestito > '" + from + "' and DataPrestito < '" + to + "';";
        return performQuery(queryText);
    }
    
    public ArrayList<Libro> getTitle(String titoloLibro)
    {
        String queryText = "select * from biblioteca where Titolo = '"+ titoloLibro +"';";
        return performQuery(queryText);
    }
    
    // 01
    private ArrayList<Libro> performQuery(String queryText)
    {
        ArrayList<Libro> libriLista = new ArrayList<>();
       
        
        try(Statement query = myDB.createStatement();)
        {
            ResultSet r = query.executeQuery(queryText);
            
            while(r.next())
            {
                libriLista.add(new Libro(r.getInt("IdLibro"),r.getString("Titolo"),r.getString("Autore"),r.getString("Genere"),r.getString("DataPrestito"),r.getString("DataRestituzione"),r.getString("Scadenza"),r.getDouble("VotoMedio"),conf.sogliaArancione,conf.sogliaRosso));            
            }
        }
        catch (SQLException ex)
        {
            System.err.println(ex.getMessage());
        }
        return libriLista;
    }
    
    public void performUpdateQueryPrestito(int id, String beneficiario)
    {
        try(PreparedStatement query = myDB.prepareStatement(
                "update biblioteca set DataPrestito = ?, DataRestituzione=NULL,BeneficiarioPrestito = ?,NumeroDiPrestiti = NumeroDiPrestiti+1, Scadenza=? where IdLibro = ?;");
                )
        {
            LocalDate dataOdierna = LocalDate.now();
            query.setString(1, dataOdierna.toString());
            query.setString(2, beneficiario);
            
            LocalDate scadenza = dataOdierna.plusWeeks(conf.periodoScadenzaStandard);

            query.setString(3, scadenza.toString());
            query.setInt(4, id);
            
            System.out.println("Row affected: " + query.executeUpdate());
        }
        catch (SQLException ex)
        {
            System.err.println(ex.getMessage());
        }
    }
    
   
    private Boolean isFirstReview(int idBook)
    { 
        double review = 0;
        
        try(Statement query = myDB.createStatement();)
        {
            String queryText = "select VotoMedio from Biblioteca where IdLibro = "+idBook+";";
            ResultSet r = query.executeQuery(queryText);
            
            while(r.next())
            {
                review = r.getDouble("VotoMedio");
            }
            
        }
        catch (SQLException ex)
        {
            System.err.println(ex.getMessage());
        }
        
        return review==0;
    }
    
    private Boolean controllaBeneficiario(int idBook, String user)
    {
        String ben = new String();
      
        try(Statement query = myDB.createStatement();)
        {
            String queryText = "select BeneficiarioPrestito from Biblioteca where IdLibro = "+idBook+";";
            ResultSet r = query.executeQuery(queryText);
            
            while(r.next())
            {
                ben = r.getString("BeneficiarioPrestito");
            }
            
        }
        catch (SQLException ex)
        {
            System.err.println(ex.getMessage());
        }
        
        return ben.equals(user);
    }
    
    public void performQueryRestituzione(int id, String user, int voto)
    {
        // 02
        if(!controllaBeneficiario(id,user))
        {
            System.err.println(" Il beneficiario del prestito non corrisponde con l'utente che sta effettuando la restituzione");
            return;
        }
        
        try(PreparedStatement query = myDB.prepareStatement(
                "update biblioteca set DataRestituzione = ?, BeneficiarioPrestito = NULL,Scadenza=NULL, VotoMedio=(VotoMedio+?)/?, NumRecensioni = NumRecensioni + 1 where IdLibro = ?;");
                )
        {
            LocalDate dataOdierna = LocalDate.now();
            query.setString(1, dataOdierna.toString());
            query.setInt(2,voto);
            
            // 03
            if(isFirstReview(id) || voto==0)
            {
                query.setInt(3,1);
            }
            else
            {
                query.setInt(3,2);
            }
                
            query.setInt(4, id);
            System.out.println("Row affected: " + query.executeUpdate());
            
        }
        catch (SQLException ex)
        {
            System.err.println(ex.getMessage());
        }
    }
    
    public ArrayList<GenereStat> getGeneri()
    {
        ArrayList<GenereStat> gen = new ArrayList<>();
        
        try(Statement query = myDB.createStatement();)
        {
            ResultSet r = query.executeQuery("select Genere, sum(NumeroDiPrestiti) as NumeroDiPrestiti from biblioteca group by Genere order by NumeroDiPrestiti DESC;");
            
            while(r.next())
            {
               gen.add(new GenereStat(r.getString("Genere"),r.getInt("NumeroDiPrestiti")));
            }
            
        }
        catch (SQLException ex)
        {
            System.err.println(ex.getMessage());
        }
        return gen;
    }
}

/*  Commenti
    (01)    Funzione che si occupa di effetuare le query di select che prevedono come risposta un ArrayList<Libro>
    (02)    Devo controllare che il beneficiario del prestito e l'utente loggato siano la stessa persona perchè altrimenti
            nel momento in cui faccio una ricerca per titolo (e il libro risultante è un libro prestato) un utente X potrebbe
            restituire il libro prestato a un utente Y
    (03)    Se è la prima recensione devo dividere per 1 perchè non devo fare la media, stessa cosa se il voto è 0
            perchè significa che non è stata fatta alcuna recensione e se dividessi per 2 falsificherei la media.
            In tutti i casi in diversi dai precedenti devo dividere per due, ovviamente con questo modo di calcolare
            la media avranno più peso i voti più recenti
*/