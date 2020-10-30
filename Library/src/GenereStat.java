import javafx.beans.property.*;


public class GenereStat {
    private SimpleStringProperty genere;
    private SimpleIntegerProperty prestitiPerGenere;
    
    public GenereStat(String gen, int numPrestiti)
    {
        genere = new SimpleStringProperty(gen);
        prestitiPerGenere = new SimpleIntegerProperty(numPrestiti);   
    }
    
    public String getGenere()
    {
        return genere.get();
    }
    
    public int getPrestitiPerGenere()
    {
        return prestitiPerGenere.get();
    }
}
