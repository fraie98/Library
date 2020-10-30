import com.thoughtworks.xstream.*;
import java.io.*;
import java.net.*;
import javax.xml.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.xml.sax.*;

public class LibraryServer {

    public static void main(String[] args)
    {
        while(true)
        {
            
            try(    ServerSocket server = new ServerSocket(8080);
                    Socket sck = server.accept();
                    DataInputStream streamDatiIn = new DataInputStream(sck.getInputStream())
                )
                
            {
                XStream objEvento = new XStream();
    
                String str_xml = new String();
                str_xml = streamDatiIn.readUTF();
                
                if(!validaXML(str_xml))
                {
                    System.err.println("XML ricevuto non valido");
                    continue;
                }
                
                Evento ev = (Evento)objEvento.fromXML(str_xml);
                System.out.println("|| Nome_Applicativo: " + ev.nomeApp + " || IP_Client: " + ev.ipClient + " || Timestamp: " + ev.currentTs.toString() + " || Etichetta: " + ev.label + " ||");
                salvaSuFileXML(str_xml);
                
                if(ev.label.equals("Chiusura_App"))
                    break;
            }
            catch (IOException ex)
            {
                System.err.println(" Errore interno - IOException: ");
                ex.printStackTrace();
            }
        }
    
    }
    
    public static void salvaSuFileXML(String record)
    {
        
        record = record.concat(System.getProperty("line.separator"));
       
        // 01
        record = record.concat(System.getProperty("line.separator"));
        
        try(FileOutputStream fileUscita = new FileOutputStream("./myfiles/archivioEventiLog.xml",true); DataOutputStream streamOut = new DataOutputStream(fileUscita);)
        {
            streamOut.writeBytes(record);
        }
        catch(IOException ex)
        {
            System.err.println("Errore nella salvataggio del log su file");
            ex.printStackTrace();
        }        
    }
    
    private static Boolean validaXML(String xml)
    {
        try
        {
            SchemaFactory schemaF = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema s = schemaF.newSchema(new StreamSource(new File("./myfiles/validaRecordXML.xsd")));
            s.newValidator().validate(new StreamSource(new StringReader(xml))); //02
        }
        catch (Exception ex)
        {
           if(ex instanceof SAXException)
           {   
               System.err.println(xml);
               System.err.println("Errore di validazione: " + ex.getMessage());
           }
           else
           {
               System.err.println(ex.getMessage());
               ex.printStackTrace();
           }
           return false;
       }
        
        return true;
    }
}

/* Commenti
    (01)    Metto un nuovo "a capo" per fare in modo che nel file tra un log e l'altro ci sia una riga vuota
    (02)    La validate prende come argomento un oggetto di tipo source, pertanto gli passo un oggetto StreamSource,
            questo oggetto lo inizializzo tramite uno StringReader che è uno stream di caratteri, tale stream di caratteri
            lo costruisco tramite la mia stringa che contiene l'xml da validare*/
