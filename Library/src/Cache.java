import java.io.*;
import java.time.*;

public class Cache implements Serializable {
    public String cercaTitolo;
    public String username;
    public LocalDate dal;
    public LocalDate al;
    public Boolean isSelected;
    
    public Cache(String titolo, String user, LocalDate from, LocalDate to, Boolean selezionato)
    {         
        if(titolo.isEmpty())
            cercaTitolo = null;
        else 
            cercaTitolo = titolo;
        
        if(user.isEmpty())
            username = null;
        else
            username = user;
            
        dal = from;
        al = to;
        isSelected = selezionato;
    }
}
