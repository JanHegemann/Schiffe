/**
 * Diese Klasse setzt das Entwurfsmuster DataTableGateway um. Dabei stellt es alle Datenbankrelevanten Funktionen, die die Anwendung benötigt 
 * zur Verfügung. Erweiterungen und Einschränkungen sind möglich.
 * 
 * @author Henning Ainödhofer
 * @version 10.04.2018
 */
public class HighscoreGateway
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private DatabaseConnector db;

    /**
     * Konstruktor für Objekte der Klasse HighscoreGateway
     */
    public HighscoreGateway()
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
    public Eintrag hole(int id)
    {
        verbinde();
        db.executeStatement("SELECT * FROM highscore WHERE id ="+id);
        QueryResult ergebnis = db.getCurrentQueryResult();
        Eintrag erg = new Eintrag(ergebnis.getData()[0][0], ergebnis.getData()[0][1], Integer.parseInt(ergebnis.getData()[0][2]));
        beende();
        return erg;
    }
    
    /**
     * Diese Methode setzt die READ-Funktion um, indem man sich alle Objekte der Tabelle liefern lassen kann.
     * 
     * @return Liste aller Einträge
     */
    public List<Eintrag> holeAlle()
    {
        verbinde();
        List <Eintrag> highscore = new List();
        db.executeStatement("Select id, name, punkte from highscore ORDER BY punkte ASC");
        QueryResult ergebnis = db.getCurrentQueryResult();
        if(ergebnis != null)
        {
            for(int i = 0; i < ergebnis.getRowCount(); i++)
            {
                highscore.append(new Eintrag(ergebnis.getData()[i][0], ergebnis.getData()[i][1], Integer.parseInt(ergebnis.getData()[i][2])));
            }
        }
        beende();
        return highscore;
    }
    
    /**
     * Diese Methode setzt die READ-Funktion um, indem man sich alle Objekte der Tabelle liefern lassen kann.
     * 
     * @return Liste aller Einträge
     */
    public List<Eintrag> holeZehn()
    {
        verbinde();
        List <Eintrag> highscore = new List();
        db.executeStatement("Select id, name, punkte from highscore ORDER BY punkte ASC Limit 10");
        QueryResult ergebnis = db.getCurrentQueryResult();
        if(ergebnis != null)
        {
            for(int i = 0; i < ergebnis.getRowCount(); i++)
            {
                highscore.append(new Eintrag(ergebnis.getData()[i][0], ergebnis.getData()[i][1], Integer.parseInt(ergebnis.getData()[i][2])));
            }
        }
        beende();
        return highscore;
    }
    
    /**
     * Diese Methode setzt die READ-Funktion um, indem man sich alle Objekte der Tabelle liefern lassen kann, die den selben Namen besitzen.
     * 
     * @param text
     * 
     * @return Liste aller Einträge
     */
    public List<Eintrag> sucheNachName(String name)
    {
        verbinde();
        List <Eintrag> highscore = new List();
        db.executeStatement("Select id, name, punkte from highscore WHERE name = '"+name+"' ORDER BY punkte ASC");
        QueryResult ergebnis = db.getCurrentQueryResult();
        if(ergebnis != null)
        {
            for(int i = 0; i < ergebnis.getRowCount(); i++)
            {
                highscore.append(new Eintrag(ergebnis.getData()[i][0], ergebnis.getData()[i][1], Integer.parseInt(ergebnis.getData()[i][2])));
            }
        }
        beende();
        return highscore;
    }
    
    /**
     * Diese Methode setzt die CREATE-Funktion um, indem hier neue Highscores in die Datenbank eingetragen werden.
     * 
     * @param name
     * @param punkte
     */
    public void hinzufuegen(String name, int punkte)
    {
        verbinde();
        db.executeStatement("INSERT INTO highscore (name, punkte) VALUES ('"+name+"', "+punkte+")");
        beende();
    }
    
    /**
     * Diese Methode setzt die DELETE-Funktion um, indem hier Datensätze über die Angabe der id gelöscht werden können.
     * 
     * @param id
     */
    public void loesche(String id)
    {
        verbinde();
        db.executeStatement("DELETE FROM highscore WHERE id ="+id);
        beende();
    }
    
    /**
     * Diese Methode erzeugt die Tabelle highscore, wenn diese nicht schon exisitiert.
     */
    public void erzeugeTabelle()
    {
         verbinde();
         db.executeStatement("Create table if not exists highscore (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, punkte INT)");
         beende();
    }
    
    /**
     * Diese Methode stellt eine Verbindung zur Datenbank her.
     */
    private void verbinde()
    {
        if(db == null)
        {
            db = new DatabaseConnector("",0,"spielstand","","");
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
