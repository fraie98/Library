import javafx.collections.*;
import javafx.event.*;


public class GestoreOutput {
    public GraphicUserInterface interfaccia;
    private DbBiblioteca database;
    public Logger gestoreLog;
    
    public GestoreOutput(Configurazione userConfig)
    {
        gestoreLog = new Logger(userConfig);
        database = new DbBiblioteca(userConfig);
       
        
        // 01
        ObservableList<Libro> listaLibri = FXCollections.observableArrayList(database.getBooksDisp());
        ObservableList<GenereStat> listaGeneri = FXCollections.observableArrayList(database.getGeneri());
        
        interfaccia = new GraphicUserInterface(listaLibri,listaGeneri,userConfig);
        
        // 02
        interfaccia.gestisciTasti(2);
        interfaccia.disabilitaAzioneRestituzione(true); 
        
        settaEventiOnClick();
    }
    
    private void settaEventiOnClick()
    {
        interfaccia.getPrestito().setOnAction((ActionEvent ev)->{presta();});
        interfaccia.getRestituzione().setOnAction((ActionEvent ev)->{restituisci();});
        interfaccia.getMenu().setOnAction(e->{gestoreSceltaMenu();});
        interfaccia.getCerca().setOnAction((ActionEvent event)->{ricercaTitolo();   gestoreLog.creaEvento("ClickCerca");});
        interfaccia.getAggiorna().setOnAction((ActionEvent eve)->{mostraPrestati(); gestoreLog.creaEvento("ClickAggiornaPrestati");});
        interfaccia.getDataFine().setOnAction((ActionEvent ev)->{gestoreLog.creaEvento("ClickDataFine");});
        interfaccia.getDataInizio().setOnAction((ActionEvent ev)->{gestoreLog.creaEvento("ClickDataInizio");});  
        interfaccia.getUsernameForm().setOnMouseClicked(ev->{gestoreLog.creaEvento("ClickUsernameForm");});
        interfaccia.getCercaTitolo().setOnMouseClicked(ev->{gestoreLog.creaEvento("ClickCampoRicercaTitolo");});
        interfaccia.getRanking().setOnAction(ev->{gestoreLog.creaEvento("ClickRecensione");});
        interfaccia.getTable().setOnMouseClicked(ev->{gestoreLog.creaEvento("ClickRigaTabella");});
    }
    
    // 03
    private void gestoreSceltaMenu()
    {
        if(interfaccia.getMenu().getValue().equals("Prestati"))
        {
            gestoreLog.creaEvento("ClickMenuEntryPrestati");
            interfaccia.gestisciTasti(1);
        }
        else if(interfaccia.getMenu().getValue().equals("Disponibili"))
        {
            gestoreLog.creaEvento("ClickMenuEntryDisponibili");
            interfaccia.gestisciTasti(2);
            // 04
            mostraDisponibili();
        }
        else
        {
            gestoreLog.creaEvento("ClickMenuEntryRicercaPerTitolo");
            interfaccia.gestisciTasti(3);
        }
    }
    
    private void mostraPrestati()
    {
        interfaccia.disabilitaAzionePrestito(true);
        interfaccia.disabilitaAzioneRestituzione(false);
        
        ObservableList<Libro> libri = FXCollections.observableArrayList(
                database.getPrestati(
                        interfaccia.getValueDataInizio(), interfaccia.getValueDataFine(), interfaccia.getValueUsername()
                )
        );
        updateTabella(libri);
    }
    
    private void mostraDisponibili()
    {
        interfaccia.disabilitaAzionePrestito(false);
        interfaccia.disabilitaAzioneRestituzione(true);
        
        ObservableList<Libro> libri = FXCollections.observableArrayList(database.getBooksDisp());
        updateTabella(libri);
    }
    
    private void ricercaTitolo()
    {
       interfaccia.disabilitaAzionePrestito(false);
       interfaccia.disabilitaAzioneRestituzione(false);
       
       ObservableList<Libro> libri = FXCollections.observableArrayList(database.getTitle(interfaccia.getValueCercaTitolo()));
       updateTabella(libri);
    }
    
    
    private void updateTabella(ObservableList<Libro> libri)
    {
        interfaccia.getTable().aggiorna(libri);
    }
    
    private void presta()
    {
        gestoreLog.creaEvento("ClickEffettuaPrestito");
        database.performUpdateQueryPrestito(interfaccia.getIdRowSelected(), interfaccia.getValueUsername());
        
        // 08
        try{
            if(interfaccia.getMenu().getValue().toString().equals("Ricerca Per Titolo"))
                ricercaTitolo();
            else
                mostraDisponibili();
        }
        catch(NullPointerException ex)
        {
            mostraDisponibili();
        }
        
    }
    
    private void restituisci()
    {
        gestoreLog.creaEvento("ClickEffettuaRestituzione");
      
        int recensione = 0;
        String votoScelto = new String();
        
        // 05
        try
        {
            votoScelto = interfaccia.getRanking().getValue().toString();
        }
        catch(NullPointerException ex)
        {
            votoScelto = "0";
        }
        
          
        if(votoScelto.equals("1"))
            recensione = 1;
        else if(votoScelto.equals("2"))
            recensione = 2;
        else if(votoScelto.equals("3"))
            recensione = 3;
        else if(votoScelto.equals("4"))
            recensione = 4;
        else if(votoScelto.equals("5"))
            recensione = 5;
        else
            recensione = 0;
        
        // 06
        interfaccia.getRanking().setValue(null);
            
        database.performQueryRestituzione(interfaccia.getIdRowSelected(),interfaccia.getValueUsername(),recensione);
        
        // 07
        if(interfaccia.getMenu().getValue().toString().equals("Ricerca Per Titolo"))
            ricercaTitolo();
        else
            mostraPrestati();
    }
}

/*  Commenti
    (01)        Inizializzo le Observable List per l'inizializzazione della tabella e del grafico a torta
    (02)        Devo disabilitare l'azione di restituzione perchè di default all'avvio mi appaiono i libri disponibili 
    (03)        In base alla scelta della enty nel menu crea l'evento corrispondente da inviare al logger e chiama gestisciTasti
    (04)        A differenza di Prestiti e Ricerca Per Titolo per l'entry Disponibili non c'è un tasto di aggiornamento, questo viene
                effettuato automaticamente nel momento in cui si clicca sulla entry, ciò è fatto chiamando in questo punto la funzione
                mostraDisponibili
    (05)        E' possibile che l'utente non lasci un voto quindi è assolutamente normale che la chiamata a getRanking().getValue() dia
                null, in questo caso è possibile andare avanti semplicemente considerando come voto recensione 0 (sommare 0 al voto medio senza
                incrementare il numero di recensioni da lo stesso risultato: (x+y)/2 = (x+y+0)/2
    (06)        Reimposto al valore di default la ComboBox
    (07)        Aggiorno la tabella per mostrare subito informazioni aggiornate
    (08)        Nel momento in cui faccio il prestito di un libro aggiorno la tabella, tuttavia per farlo devo sapere se ero in ricercatitolo
                o se in disponibili, tuttavia se faccio un prestito senza cliccare sul menu non risulto in nessuno dei casi precedenti e la chiamata
                interfaccia.getMenu().getValue() da NullPointrException, questo è normale perchè il fatto che sono nei disponibili non deriva dal
                click sul menù ma del fatto che la mostra dei disponibili è di default, quindi per aggiornare devo mostrare i disponibili
*/