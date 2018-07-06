/**
 * Diese Klasse setzt das Entwurfsmuster DataTableGateway um. Dabei stellt es alle Datenbankrelevanten Funktionen, die die Anwendung benötigt 
 * zur Verfügung. Erweiterungen und Einschränkungen sind möglich.
 * 
 * @author Henning Ainödhofer
 * @version 10.04.2018
 */
public class SpielerGateway
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private DatabaseConnector db;

    /**
     * Konstruktor für Objekte der Klasse HighscoreGateway
     */
    public SpielerGateway()
    {
        // Instanzvariable initialisieren
        db = null;
    }

    /**
     * Diese Methode setzt die READ-Funktion um, indem man nach einem Objekt mit einer bestimmten id fragen kann.
     * 
     * @param id
     * 
     * @return Eintragobjekt mit passender id oder null
     */
    public Spieler hole(int id)
    {
        verbinde();
        db.executeStatement("SELECT * FROM spieler WHERE id ="+id);
        QueryResult ergebnis = db.getCurrentQueryResult();
        Spieler erg = new Spieler(ergebnis.getData()[0][0], ergebnis.getData()[0][1], ergebnis.getData()[0][2]);
        beende();
        return erg;
    }
    
    /**
     * Diese Methode setzt die READ-Funktion um, indem man sich alle Objekte der Tabelle liefern lassen kann.
     * 
     * @return Liste aller Einträge
     */
    public List<Spieler> holeAlle()
    {
        verbinde();
        List <Spieler> spieler = new List();
        db.executeStatement("Select id, name, passwort from spieler ORDER BY name ASC");
        QueryResult ergebnis = db.getCurrentQueryResult();
        if(ergebnis != null)
        {
            for(int i = 0; i < ergebnis.getRowCount(); i++)
            {
                spieler.append(new Spieler(ergebnis.getData()[i][0], ergebnis.getData()[i][1], ergebnis.getData()[i][2]));
            }
        }
        beende();
        return spieler;
    }
    
    /**
     * Diese Methode setzt die READ-Funktion um, indem man überprüft, ob ein Spieler mit dem Namen und dem Passwort exisitert.die den selben Namen besitzen.
     * 
     * @param name
     * @param passwort
     * 
     * @return Spieler vorhanden
     */
    public boolean sucheNachName(String name)
    {
        verbinde();
        boolean spielerVorhanden = false;
        db.executeStatement("Select id, name, passwort from spieler WHERE name = '"+name+"'");
        QueryResult ergebnis = db.getCurrentQueryResult();
        if(ergebnis != null)
        {
            if(ergebnis.getRowCount() != 0)
            {
                spielerVorhanden = true;
            }
        }
        beende();
        return spielerVorhanden;
    }
    
    /**
     * Diese Methode setzt die READ-Funktion um, indem man überprüft, ob ein Spieler mit dem Namen und dem Passwort exisitert.die den selben Namen besitzen.
     * 
     * @param name
     * @param passwort
     * 
     * @return Spieler vorhanden
     */
    public boolean sucheNachName(String name, String passwort)
    {
        verbinde();
        boolean spielerVorhanden = false;
        db.executeStatement("Select id, name, passwort from spieler WHERE name = '"+name+"' AND  passwort = '"+passwort+"'");
        QueryResult ergebnis = db.getCurrentQueryResult();
        if(ergebnis != null)
        {
            if(ergebnis.getRowCount() != 0)
            {
                spielerVorhanden = true;
            }
        }
        beende();
        return spielerVorhanden;
    }
    
    /**
     * Diese Methode setzt die CREATE-Funktion um, indem hier neue Highscores in die Datenbank eingetragen werden.
     * 
     * @param name
     * @param punkte
     */
    public void hinzufuegen(String name, String passwort)
    {
        verbinde();
        String sql = "INSERT INTO spieler (name, passwort) VALUES ('"+name+"', "+passwort+")";
        System.out.println(sql);
        db.executeStatement(sql);
        beende();
    }
    
    /**
     * Diese Methode setzt die DELETE-Funktion um, indem hier Datensätze über die Angabe der id gelöscht werden können.
     * 
     * @param id
     */
    public void loesche(int id)
    {
        verbinde();
        db.executeStatement("DELETE FROM spieler WHERE id ="+id);
        beende();
    }
    
    /**
     * Diese Methode setzt die UPDATE-Funktion um, indem hier Datensätze über die Angabe der id und der zu aktualisierenden Attributwerte aktualisiert werden können.
     * 
     * @param id
     * @param name
     * @param punkte
     */
    public void aktualisiere(String id, String name, int passwort)
    {
        verbinde();
        db.executeStatement("UPDATE spieler SET name = '"+name+"', passwort = "+passwort+" WHERE id = "+id);
        beende();
    }
    
    /**
     * Diese Methode erzeugt die Tabelle highscore, wenn diese nicht schon exisitiert.
     */
    public void erzeugeTabelle()
    {
         verbinde();
         db.executeStatement("Create table if not exists spieler (id INTEGER PRIMARY KEY AUTOINCREMENT, name Varchar(255), passwort Varchar(255))");
         beende();
    }
    
    /**
     * Diese Methode stellt eine Verbindung zur Datenbank her.
     */
    private void verbinde()
    {
        if(db == null)
        {
            db = new DatabaseConnector("",0,"spiel","","");
        }
    }
    
    /**
     * Diese Methode beendet die Verbindung zur Datenbank.
     */
    private void beende()
    {
        if(db != null)
        {
            db.close();
            db = null;
        }
    }

}
