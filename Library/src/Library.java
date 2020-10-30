import com.thoughtworks.xstream.*;
import java.io.*;
import java.nio.file.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.stage.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.*;


public class Library extends Application {
    
   private GestoreOutput output;
   private ControlloreCache cache;
   public Configurazione iniConf;
   
    public void start(Stage primaryStage) {
        iniConf = leggiFileConf();
        output = new GestoreOutput(iniConf);
        cache = new ControlloreCache();
   
        
        // 01
        output.gestoreLog.creaEvento("Avvio_App");
        
        // 02
        primaryStage.setOnCloseRequest((WindowEvent we) -> {cache.write(output.interfaccia); output.gestoreLog.creaEvento("Chiusura_App");});
        
        // 03
        cache.read(output.interfaccia);
        
        Scene nuovaScena = new Scene(output.interfaccia.getGroupRoot(), iniConf.dim_X, iniConf.dim_Y);
        
        primaryStage.setTitle("Library");
        primaryStage.getIcons().add(new Image("file: ../../myfiles/icon.png"));
        primaryStage.setScene(nuovaScena);
        primaryStage.show();
        
        
    }
    
    
    private Configurazione leggiFileConf()
    {
        if(!validaXML())
        {
            // 04
            Configurazione userConfig = new Configurazione(10,"Vivaldi","Helvetica",900,950,"#ebf7da","#46a4f2","white",10,1,2,"192.168.1.1","localhost",8080,4);
            return userConfig;
        }
        
        // 05
        XStream confXML = new XStream();
        Configurazione userConfig = new Configurazione();
        try
        {
            String  x = new String(Files.readAllBytes(Paths.get("./myfiles/conf.xml")));
            userConfig = (Configurazione)confXML.fromXML(x);
        }
        catch(IOException ex)
        {
            System.err.println("Errore nella lettura del file di Configurazione");
        }  
        
        return userConfig;
    }
    
    private Boolean validaXML()
    {
        try
        {
            DocumentBuilder docB = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory schemaF = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Document d = docB.parse(new File("./myfiles/conf.xml"));
            Schema s = schemaF.newSchema(new StreamSource(new File("./myfiles/validaConf.xsd")));
            s.newValidator().validate(new DOMSource(d));
        } catch (Exception ex) {
           if(ex instanceof SAXException)
               System.err.println("Errore di validazione: " + ex.getMessage());
           else
               System.err.println(ex.getMessage());
           
           return false;
       }
        
        return true;
    }
}

// Commenti
// (01)     Invio dell'evento di avvio dell'applicazione
// (02)     Setto le azioni da fare alla chiusura dell'applicazione
// (03)     Leggo le info salvate in cache
// (04)     Se la validazione fallisce viene creato un oggetto Configurazione con le impostazioni di default
// (05)     Se la validazione ha avuto successo leggo il file di configurazione e creo l'oggetto Configurazione