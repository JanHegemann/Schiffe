
/**
 * Beschreiben Sie hier die Klasse Spiel.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Spiel
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private String clientIP;
    private int clientPort;
    private int zahl;
    private int versuche;
    private String name;

    /**
     * Konstruktor für Objekte der Klasse Spsieler
     */
    public Spiel(String clientIP, int clientPort, int zahl, String name)
    {
        this.clientIP = clientIP;
        this.clientPort = clientPort;
        this.zahl = zahl;
        this.versuche = 0;
        this.name = name;
    }

    public int gibZahl()
    {
        return this.zahl;
    }

    public String gibClientIP()
    {
        return this.clientIP;
    }

    public int gibClientPort()
    {
        return this.clientPort;
    }

    public int gibVersuche()
    {
        return this.versuche;
    }

    public String gibName()
    {
        return this.name;
    }

    public void setzeName(String name)
    {
        this.name = name;
    }

    public void erhoeheVeruche()
    {
        versuche = versuche  + 1;
    }
}
