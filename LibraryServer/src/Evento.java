
import java.io.*;
import java.sql.*;


public class Evento implements Serializable{
    public String ipClient;
    public String nomeApp;
    public Timestamp currentTs;
    public String label;
    
    public Evento(String ip,String nome, Timestamp corrente, String etichetta)
    {
        ipClient = ip;
        nomeApp = nome;
        currentTs = corrente;
        label = etichetta;
    }
}
