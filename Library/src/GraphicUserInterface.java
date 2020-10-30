import java.time.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;


public class GraphicUserInterface {
    private Label header;
    private Button prestito;
    private Button restituzione;
    private Label user;
    private TextField usernameForm;
    private ComboBox menu;
    private ComboBox voto;
    private DatePicker dataInizio;
    private DatePicker dataFine;
    private TextField inserimentoTitle;
    private Button aggiorna;
    private Button search;
    private TabellaLibri table;
    private TortaGeneri pie;
    private Group radice;
    
    public GraphicUserInterface(ObservableList<Libro> listaLibri, ObservableList<GenereStat> listaGeneri,Configurazione userConfig)
    {
        header = new Label("Library");
        
        user = new Label("Username:");
        usernameForm = new TextField();
        HBox sezUsername = new HBox(20,user,usernameForm);
        
        menu = new ComboBox();
        dataInizio = new DatePicker();
        dataFine = new DatePicker();
        HBox intervallo = new HBox(20,dataInizio,dataFine);  
        inserimentoTitle = new TextField();
        VBox sezioneOpzioni = new VBox(20,intervallo,inserimentoTitle);
        
        aggiorna = new Button("Aggiorna");
        search = new Button("Cerca");
        VBox tastiOpzione = new VBox(20,aggiorna,search);
   
        HBox opzioniTabella = new HBox(20,menu,sezioneOpzioni,tastiOpzione);
        
        table = new TabellaLibri(listaLibri,userConfig.maxRigheDaVisualizzare);
        
        prestito = new Button("Prestito"); 
        restituzione = new Button("Restituzione");
        voto = new ComboBox();
        HBox rest_rate = new HBox(10,restituzione,voto);
        HBox rigaTasti = new HBox(300,prestito,rest_rate);
        
        pie = new TortaGeneri(listaGeneri,userConfig.maxGeneriTorta);
        
        // 01
        VBox mainBox = new VBox(20,header,sezUsername, opzioniTabella,table,rigaTasti,pie);
        // 02
        setMainBox(mainBox, userConfig);
        setBox(sezUsername,tastiOpzione,opzioniTabella,rigaTasti);
        setStyleElements(userConfig);
        
        // 03
        radice = new Group(mainBox);
        radice.setAutoSizeChildren(true);   
    }
    
    private void setMainBox(VBox mainBox, Configurazione userConfig)
    {
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(25));
        mainBox.setPrefSize(userConfig.dim_X, userConfig.dim_Y);
        mainBox.setStyle("-fx-background-color: "+userConfig.backgroundColor+"; -fx-font: 13px '"+userConfig.font+"';");
    }
    
    private void setBox(HBox sezUsername, VBox tastiOpzione, HBox opzioniTabella, HBox rigaTasti)
    {
        sezUsername.setAlignment(Pos.TOP_CENTER);        
        opzioniTabella.setAlignment(Pos.CENTER);
        tastiOpzione.setAlignment(Pos.CENTER);
        rigaTasti.setAlignment(Pos.CENTER);
    }
    
    private void setStyleElements(Configurazione userConfig)
    {
        ObservableList<String> elements = FXCollections.observableArrayList("Prestati","Disponibili","Ricerca Per Titolo");
        ObservableList<String> ratings = FXCollections.observableArrayList("1","2","3","4","5");
        
        menu.getItems().addAll(elements);
        menu.setPromptText("Scegli Azione");
        
        voto.getItems().addAll(ratings);
        voto.setPromptText("Seleziona Voto");
        
        inserimentoTitle.setOpacity(0.4);
        inserimentoTitle.setPromptText("Cerca Titolo");
        
        search.setOpacity(0.4);
        dataInizio.setOpacity(0.4);
        dataFine.setOpacity(0.4);
        aggiorna.setOpacity(0.4);
        
        table.setMaxWidth(userConfig.dim_X);
        table.setPrefHeight(400);
        // 08
        table.columnResizePolicyProperty().set(TableView.CONSTRAINED_RESIZE_POLICY);
        
        usernameForm.setPrefWidth(200);
        usernameForm.setPromptText("Inserisci Username");
     
        header.setFont(Font.font(userConfig.fontTitolo, 60));
        
        user.setAlignment(Pos.BASELINE_CENTER);
        
        setButtons(userConfig);  
    }
    
    private void setButtons(Configurazione ini)
    {
        prestito.setStyle("-fx-background-color:"+ini.buttonColor+"; -fx-text-fill:"+ini.buttonFontColor+";");
        prestito.setPrefSize(200, 100);
        
        restituzione.setStyle("-fx-background-color:"+ini.buttonColor+"; -fx-text-fill:"+ini.buttonFontColor+";");
        restituzione.setPrefSize(200, 100);
        
        aggiorna.setStyle("-fx-background-color:"+ini.buttonColor+"; -fx-text-fill:"+ini.buttonFontColor+";");
        aggiorna.setPrefSize(200, 100);
        
        search.setStyle("-fx-background-color:"+ini.buttonColor+"; -fx-text-fill:"+ini.buttonFontColor+";");
        search.setPrefSize(200, 100);
        
        menu.setStyle("-fx-background-color:"+ini.buttonColor+"; -fx-text-inner-color:"+ini.buttonFontColor+";");
        menu.setPrefSize(200, 40);
        
        voto.setStyle("-fx-background-color:"+ini.buttonColor+"; -fx-prompt-text-fill:"+ini.buttonFontColor+";");
        voto.setPrefSize(200, 100);
        
        dataInizio.setPrefSize(200, 40);
        dataFine.setPrefSize(200, 40);
        usernameForm.setPrefSize(200, 80);
        inserimentoTitle.setPrefSize(200, 40);
    }
    
    public TabellaLibri getTable()
    {
        return table;
    }
    
    public Group getGroupRoot()
    {
        return radice;
    }
    
    public ComboBox getMenu()
    {
        return menu;
    }
    
    public ComboBox getRanking()
    {
        return voto;
    }
    
    public TextField getCercaTitolo()
    {
        return inserimentoTitle;
    }
    
    public Button getCerca()
    {
        return search;
    }
    
    public DatePicker getDataInizio()
    {
        return dataInizio;
    }
    
    public DatePicker getDataFine()
    {
        return dataFine;
    }
    
    public Button getAggiorna()
    {
        return aggiorna;
    }
    
    public Button getPrestito()
    {
        return prestito;
    }
    
    public Button getRestituzione()
    {
        return restituzione;
    }
    
    public String getValueCercaTitolo()
    {
        return inserimentoTitle.getText();
    }
    
    public TextField getUsernameForm()
    {
        return usernameForm;
    }
    
    public String getValueUsername()
    {
        return usernameForm.getText();
    }
    
    public LocalDate getValueDataInizio()
    {
        return dataInizio.getValue();
    }
    
    public LocalDate getValueDataFine()
    {
        return dataFine.getValue();
    }
    
    public int getIdRowSelected()
    {
        Libro selezionato = (Libro)table.getSelectionModel().getSelectedItem();
        table.getSelectionModel().clearSelection(); //04
        return selezionato.getId();
    }
    
    public Boolean getIsSelected()
    {
        if(table.getSelectionModel().getSelectedItem() == null)
            return false;
        else
            return true;
    }
    
    // 06
    public void disabilitaAzioneRestituzione(Boolean discriminante)
    {
        double opacity = 0.4;
        
        if(!discriminante)
            opacity = 1;
        
        restituzione.setOpacity(opacity);
        restituzione.setDisable(discriminante);
        voto.setOpacity(opacity);
        voto.setDisable(discriminante);
    }
    
    // 07
    public void disabilitaAzionePrestito(Boolean discriminante)
    {
        double opacity = 0.4;
        
        if(!discriminante)
            opacity = 1;
        
        prestito.setOpacity(opacity);
        prestito.setDisable(discriminante);
    }
    
    public void gestisciTasti(int i)
    {
        double tastoCerca = 0.4;
        double tastoPrestiti = 0.4;
        
        boolean disabilitaCerca = false; // 09
        boolean disabilitaPrestiti = false; //05
        
        if(i==1 || i==3)
        {
            if(i==1)
            {
                tastoPrestiti = 1;
                disabilitaCerca = true;
            }
            else
            {
                tastoCerca = 1;
                disabilitaPrestiti = true; 
            }
        }
        else
        {
            disabilitaCerca = true;
            disabilitaPrestiti = true;
        }
        
        inserimentoTitle.setOpacity(tastoCerca);
        search.setOpacity(tastoCerca);
        search.setDisable(disabilitaCerca);
        inserimentoTitle.setDisable(disabilitaCerca);
        
        dataInizio.setOpacity(tastoPrestiti);
        dataFine.setOpacity(tastoPrestiti);
        aggiorna.setOpacity(tastoPrestiti);
        dataInizio.setDisable(disabilitaPrestiti);
        dataFine.setDisable(disabilitaPrestiti);
        aggiorna.setDisable(disabilitaPrestiti);
    }
}


/*  Commenti:
    (01)    Inserisco all'interno di questa VBox tutte le altre box
    (02)    Setto la box principale, tutte le box secondarie e gli elementi tramite tre funzioni, una per ognuna dei precedenti
            gruppi di elementi
    (03)    Creo il gruppo che verrà settato come scena in Library
    (04)    Una volta salvato l'item selezionato è necessario effettuare una clearSelection per fare in modo
            di ripulire il SelectionModel evitando errori alla successiva selezione di un'altra entrata
            della TableView
    (05)    Si riferisce alla disabilitazione/abilitazione dei campi relativi all'entry prestito del menu
    (06)    Una unica funzione si occupa di abilitare/disabilitare i tasti relativi all'azione di restituzione
            in base al valore booleano passato come parametro
    (07)    Una unica funzione si occupa di abilitare/disabilitare i tasti relativo all'azione del prestito
            in base al valore booleano passato come parametro
    (08)    setto la seguente proprietà in modo da evitare la stampa di una colonna vuota a dx della tabella e per
            impostare il dimensionamento delle colonne in modo che occupino tutto lo spazio a loro disponibile
    (09)    Si riferisce alla disabilitazione/abilitazione dei campi relativi alla ricerca per titolo
*/