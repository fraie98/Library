import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;


public class TabellaLibri extends TableView{
    private int maxRigheDaVisualizzare;
    
    public TabellaLibri(ObservableList<Libro> ol, int quanteRighe)
    {
        maxRigheDaVisualizzare = quanteRighe;
        
        TableColumn colTitolo = new TableColumn("Titolo"); 
        colTitolo.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        
        TableColumn colAutore = new TableColumn("Autore");
        colAutore.setCellValueFactory(new PropertyValueFactory<>("autore"));         
         
        TableColumn colGenere = new TableColumn("Genere");
        colGenere.setCellValueFactory(new PropertyValueFactory<>("genere"));
        
        TableColumn colDataPrestito = new TableColumn("DataPrestito");
        colDataPrestito.setCellValueFactory(new PropertyValueFactory<>("dataPrestito"));
        
        TableColumn colRestituzione = new TableColumn("Restituzione");
        colRestituzione.setCellValueFactory(new PropertyValueFactory<>("dataRestituzione"));
        
        TableColumn colScadenza = new TableColumn("Scadenza");
        colScadenza.setCellValueFactory(new PropertyValueFactory<>("cerchio"));
        
        TableColumn colRecensioni = new TableColumn("VotoMedio");
        colRecensioni.setCellValueFactory(new PropertyValueFactory<>("votoMedio"));
       
        this.aggiorna(ol);

        this.getColumns().addAll(colTitolo,colAutore,colGenere,colDataPrestito,colRestituzione,colScadenza,colRecensioni);
    }  
    
    public void aggiorna(ObservableList<Libro> listaInteraLibri)
    {
        ObservableList<Libro> libri;
        // 01
        if(listaInteraLibri.size()>maxRigheDaVisualizzare)
            libri = FXCollections.observableList(listaInteraLibri.subList(0, maxRigheDaVisualizzare));
        else
            libri = FXCollections.observableList(listaInteraLibri);
        
        this.setItems(libri);
    }
}

/*  Commenti:
    (01)    Operazione necessaria perchè l'utente deve visualizzare sulla tabella solo un numero di righe <= a maxRigheDaVisualizzare
            che è un numero impostato dall'utente nel file di Configurazione XML
*/