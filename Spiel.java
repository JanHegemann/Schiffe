
/**
 * Beschreiben Sie hier die Klasse Spiel.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Spiel
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private String clientIP,client2IP;
    private int clientPort,client2Port;
    private Integer[][] spielfeld11,spielfeld12,spielfeld21,spielfeld22;

    /**
     * Konstruktor für Objekte der Klasse Spsieler
     */
    public Spiel(String clientIP, int clientPort,String client2IP, int client2Port)
    {
        this.clientIP = clientIP;
        this.clientPort = clientPort;
        this.client2IP = clientIP;
        this.client2Port = clientPort;
        spielfeld11=new Integer[10][10];
        spielfeld12=new Integer[10][10];
        spielfeld21=new Integer[10][10];
        spielfeld22=new Integer[10][10];
    }

    public Integer[][] gibSpielfeld11()
    {
        return this.spielfeld11;
    }
    
    public Integer[][] gibSpielfeld12()
    {
        return this.spielfeld11;
    }
    
    public Integer[][] gibSpielfeld21()
    {
        return this.spielfeld11;
    }
    
    public Integer[][] gibSpielfeld22()
    {
        return this.spielfeld11;
    }

    public String gibClientIP()
    {
        return this.clientIP;
    }

    public int gibClientPort()
    {
        return this.clientPort;
    }
    
    public String gibClientIP2()
    {
        return this.client2IP;
    }

    public int gibClientPort2()
    {
        return this.client2Port;
    }

    
}
