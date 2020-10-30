import java.io.*;

public class ControlloreCache {
 
    // 01
    public void write(GraphicUserInterface gui)
    {
        Cache objDaSalvare = new Cache(gui.getValueCercaTitolo(), gui.getValueUsername(), gui.getValueDataInizio(), gui.getValueDataFine(), gui.getIsSelected());
         
        try(FileOutputStream fileBinOut = new FileOutputStream("./myfiles/cache.bin"); ObjectOutputStream streamOut = new ObjectOutputStream(fileBinOut);)
        {
            streamOut.reset();
            streamOut.writeObject(objDaSalvare);
        }
        catch(IOException ex)
        {
            System.err.println("Errore durante la scrittura del file di cache");
        }
    }
    
    // 02
    public void read(GraphicUserInterface gui)
    {
        
        try(FileInputStream fileBinIn = new FileInputStream("./myfiles/cache.bin"); ObjectInputStream streamIn = new ObjectInputStream(fileBinIn);)  
        {
           Cache infoCache = (Cache) streamIn.readObject();
           aggiornaGui(infoCache,gui);
        }
        catch(IOException ex)
        {
            System.err.println("Cache vuota");
        }
        catch(ClassNotFoundException exClass)
        {
            System.err.println("Errore nella lettura del file di cache (exClass): ");
            exClass.printStackTrace();
        } 
    }
    
    private void  aggiornaGui(Cache infoCache, GraphicUserInterface gui)
    {
        // 03
        if(infoCache.cercaTitolo!=null)
            gui.getCercaTitolo().setText(infoCache.cercaTitolo);
        
        if(infoCache.username!=null)
            gui.getUsernameForm().setText(infoCache.username);
        
        if(infoCache.dal!=null && infoCache.al!=null)
        {
            gui.getDataInizio().setValue(infoCache.dal);
            gui.getDataFine().setValue(infoCache.al);
        }
    }
}

/*  Commenti:
    (01)    Serializza in binario sul file l'oggetto Cache
    (02)    Deserializza da binario a oggetto Cache
    (03)    Aggiorna l'interfaccia grafica con le info della cache
*/