import javafx.collections.*;
import javafx.scene.chart.*;

public class TortaGeneri extends PieChart{
        private int max;
        
        public TortaGeneri(ObservableList<GenereStat> listaGeneri, int quantiGeneri)
        {  
            // 01
            if(quantiGeneri<=2)
                max = 3;
            else
                max = quantiGeneri;
            
            this.setTitle("Generi Top");
            int count = 0;
            ObservableList<PieChart.Data> datiTorta = FXCollections.observableArrayList();
            
            // 02
            for(GenereStat i:listaGeneri)
            {   
                if(count>=max)
                    break;
                else
                    datiTorta.add(new PieChart.Data(i.getGenere(),i.getPrestitiPerGenere()));
                
                count++;
            }
       
                
            this.setData(datiTorta);
            
        }
}

//  Commenti
//  (01)    Affinchè il grafico a torta abbia senso ci devono essere almeno tre generi (top 3)
//  (02)    Ogni GenereStat deve essere trasformato in un PieChart.Data
