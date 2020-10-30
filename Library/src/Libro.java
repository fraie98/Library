import java.time.*;
import javafx.beans.property.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

public class Libro {
    private SimpleIntegerProperty id;
    private SimpleStringProperty titolo;
    private SimpleStringProperty autore;
    private SimpleStringProperty genere;
    private SimpleStringProperty dataPrestito;
    private SimpleStringProperty dataRestituzione;
    private SimpleStringProperty scadenza;
    private SimpleDoubleProperty votoMedio;
    private Circle cerchio;
    
    public Libro(int idLib, String t, String a, String gen, String pre, String rest, String sca, double voto, int sogliaArancione, int sogliaRosso)
    {
        id = new SimpleIntegerProperty(idLib);
        titolo = new SimpleStringProperty(t);
        autore = new SimpleStringProperty(a);
        genere = new SimpleStringProperty(gen);
        dataPrestito = new SimpleStringProperty(pre);
        dataRestituzione = new SimpleStringProperty(rest);
        scadenza = new SimpleStringProperty(sca);
        voto = Math.floor(voto*100)/100; // 01
        votoMedio = new SimpleDoubleProperty(voto);
        
        // 02
        if(sca!=null && LocalDate.parse(sca).minusWeeks(sogliaRosso).toEpochDay() <= LocalDate.now().toEpochDay())
        {
            cerchio = new Circle(10,10,10,Color.RED);
        }
        else if(sca!=null && LocalDate.parse(sca).minusWeeks(sogliaArancione).toEpochDay() < LocalDate.now().toEpochDay())
        {
            cerchio = new Circle(10,10,10,Color.ORANGE);
        }
        else if(sca!=null && LocalDate.parse(sca).minusWeeks(sogliaArancione).toEpochDay() >= LocalDate.now().toEpochDay())
        {
            cerchio = new Circle(10,10,10,Color.LIGHTGREEN);
        }
        else if(sca==null)
        {
            cerchio = new Circle(10,10,10,Color.TRANSPARENT);
        }
        
    }
    
    public int getId()
    {
        return id.get();
    }
    
    public String getTitolo()
    {
        return titolo.get();
    }
    
    public String getAutore()
    {
        return autore.get();
    }
    
    public String getGenere()
    {
        return genere.get();
    }
    
    public String getDataPrestito()
    {
        return dataPrestito.get();
    }
    
    public String getDataRestituzione()
    {
        return dataRestituzione.get();
    }
    
    public String getScadenza()
    {
        return scadenza.get();
    }
    
    public Double getVotoMedio()
    {
        return votoMedio.get();
    }
    
    public Circle getCerchio()
    {
        return cerchio;
    }
}

/*  Commenti:
    (01)    Arrotondamento a due cifre decimali
    (02)    Per decidere il colore del pallino deve controllare la differenza tra la data di scadenza e la data odierna (per fare la differenza
            tra le due date ottengo il valore in millisecondi dal 1 Gennaio 1970 di ognuno e uso questo per fare la differenza e il confronto)
*/
