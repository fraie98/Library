
public class Configurazione {
    public int maxRigheDaVisualizzare;
    public String fontTitolo;
    public String font;
    public int dim_X;
    public int dim_Y;
    public String backgroundColor;
    public String buttonColor;
    public String buttonFontColor;
    public int maxGeneriTorta;
    public int sogliaRosso;
    public int sogliaArancione;
    public String ipClient;
    public String ipServerLog;
    public int portaServerLog;
    public int periodoScadenzaStandard;
   
    
    public Configurazione() {}
    
    public Configurazione(int maxRow, String fontStyleTitle, String fontStyle, int x, int y, String coloreSfondo, String coloreTasti, String coloreFontTasti, int maxGenPie, int red, int orange, String ipCl, String ipSvr, int portaSvr,int Tscadenza)
    {
        maxRigheDaVisualizzare = maxRow;
        fontTitolo = fontStyleTitle;
        font = fontStyle;
        dim_X = x;
        dim_Y = y;
        backgroundColor = coloreSfondo;
        buttonColor = coloreTasti;
        buttonFontColor = coloreFontTasti;
        maxGeneriTorta = maxGenPie;
        sogliaRosso = red;
        sogliaArancione = orange;
        ipClient = ipCl;
        ipServerLog = ipSvr;
        portaServerLog = portaSvr;
        periodoScadenzaStandard = Tscadenza;
    }
}
