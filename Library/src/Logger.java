import com.thoughtworks.xstream.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.time.*;


public class Logger {
    private String ipClient;
    private String ipServer;
    private int portaServer;
    
    public Logger(Configurazione ini)
    {
        ipClient = ini.ipClient;
        ipServer = ini.ipServerLog;
        portaServer = ini.portaServerLog;
    }
    
    public void creaEvento(String label)
    {
        Evento log = new Evento(ipClient,"Library",Timestamp.from(Instant.now()),label);
        inviaEvento(log);
    }
    
    private void inviaEvento(Evento ev)
    {
        XStream logDaInviare = new XStream();
        String bodyXml = logDaInviare.toXML(ev);
        String strToSend = "<?xml version=\"1.0\"  encoding=\"UTF-8\"?>";
        strToSend = strToSend.concat(bodyXml);

        try(DataOutputStream streamUscita = new DataOutputStream((new Socket(ipServer,portaServer)).getOutputStream()))
        {
            streamUscita.writeUTF(strToSend);
        }
        catch (IOException ex)
        {
            System.err.println(" Errore nell'invio del log in formato XML al server: Attenzione il Server potrebbe essere spento! ");
        }
    }
        
}

