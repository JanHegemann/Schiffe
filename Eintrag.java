/**
 * Die Klasse Eintrag dient dem Speichern der Highscorepunkte mit Namen
 */
public class Eintrag
{
    private int punkte;
    private String name;
    private String id;

    /**
     * Konstruktor der Klasse Eintrag
     * @param punkte : int
     * @param name : String
     */
    public Eintrag(String id, String name, int punkte)
    {
        this.id = id;
        this.punkte = punkte;
        this.name = name;
    }

    /**
     * Diese Methode gibt die Punkte des Eintrages zurück
     * 
     * @return punkte : int
     */
    public int gibPunkte()
    {
        return this.punkte;
    }
    
    /**
     * Diese Methode gibt den Namen des Eintrages zurück
     * 
     * @return name : String
     */
    public String gibName()
    {
        return this.name;
    }
    
    /**
     * Diese Methode gibt die id des Eintrages zurück
     */
    public String gibId()
    {
        return this.id;
    }
        
        
}
