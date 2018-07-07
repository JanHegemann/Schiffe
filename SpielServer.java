import java.net.*;

/**
 * Klasse fuer einen SpielServer
 * @author Henning Ainödhofer
 * @version 21.03.2009
 */

public class SpielServer extends Server {

    private HighscoreGateway DBhighscore;
    private SpielerGateway DBspieler;
    private List<Game> spieleOnline;

    public SpielServer(int p) {
        super(p);
        DBhighscore = new HighscoreGateway();
        DBhighscore.erzeugeTabelle(); 
        DBspieler=new SpielerGateway();
        DBspieler.erzeugeTabelle();
        spieleOnline = new List<>();
    }

    /**
     * Diese Methode der Server-Klasse wird hiermit ueberschrieben.
     * Der angemeldete Client bekommt die Meldung, dass er angenommen wurde.
     */

    public void processNewConnection(String pClientIP, int pClientPort){
        this.send(pClientIP, pClientPort, "CON complete");
    }

    /**
     * Diese Methode der Server-Klasse wird hiermit ueberschrieben.
     * Der angemeldete Client bekommt die gesendete Meldung zurueckgeschickt.
     */
    public void processMessage(String pClientIP, int pClientPort, String pMessage){ 
        switch(gibBefehlsbereich(pMessage))
        {        
            case "PLY": //Spielen
            {
                spieleOnline.append(new Game(gibTextbereich(pMessage,0),pClientIP, pClientPort,gibTextbereich(pMessage,1),DBspieler.holeIP(gibTextbereich(pMessage,1)),DBspieler.holePort(gibTextbereich(pMessage,1))));
                this.send(pClientIP, pClientPort, "PLY Hallo "+gibTextbereich(pMessage,0)+", Sie spielen jetzt mit " +gibTextbereich(pMessage,1)+ "!");
                this.send(DBspieler.holeIP(gibTextbereich(pMessage,1)),DBspieler.holePort(gibTextbereich(pMessage,1)), "PLY Hallo "+gibTextbereich(pMessage,1)+", Sie spielen jetzt mit " +gibTextbereich(pMessage,0)+ "!");
                break;
            }
            case "HSC": //Highscoreliste mit den 10 Besten zurückgeben
            {
                this.send(pClientIP, pClientPort, "HSC "+generiereStringAusListe(DBhighscore.holeZehn()));
                break;
            }
            case "EXIT": //Der Client möchte sich abmelden
            {
                this.send(pClientIP, pClientPort, "EXIT complete");
                break;
            }
            default:
            {
                this.send(pClientIP, pClientPort, "ERR Befehl nicht bekannt!");
                break;
            }
        }
    }

    /**
     * Diese Methode der Server-Klasse wird hiermit ueberschrieben.
     * Die Verbindung wird beendet und aus der Liste der Clients gestrichen.
     */
    public void processClosingConnection(String pClientIP, int pClientPort){
        this.loescheOnlineSpiel(pClientIP, pClientPort); // OnlineSpiel wird aus der Liste gelöscht.
        this.closeConnection(pClientIP, pClientPort);
    }

    /**
     * Main-Methode die den Server auf Port 2000 startet.
     */
    public static void main(String [] args)
    {
        SpielServer es = new SpielServer(2000);
    }

    /**
     * Diese Methode gibt den Befehl zurück die die message beinhaltet
     * 
     * @param message
     * 
     * @return Befehl
     */
    private synchronized String gibBefehlsbereich(String message)
    {
        return message.split(" ")[0];
    }

    /**
     * Diese Methode gibt den Text zurück die die message beinhaltet
     * 
     * @param message
     * @param stelle
     * 
     * @return Text
     */
    private synchronized String gibTextbereich(String message, int stelle)
    {
        String [] messageArray = message.split(" ");
        if(stelle < messageArray.length-1)
        {
            return messageArray[stelle+1];
        }
        else
        {
            return "";
        }
    }

    /**
     * Diese Methode löscht ein Spiel aus der Liste OnlineSpiel
     * 
     * @param pclientIP
     */
    private synchronized void loescheOnlineSpiel(String pClientIP, int pClientPort)
    {
        spieleOnline.toFirst();
        while(spieleOnline.hasAccess())
        {
            if(spieleOnline.getContent().getClientIP(1).equals(pClientIP) && spieleOnline.getContent().getClientPort(1) == pClientPort)
            {
                spieleOnline.remove();
                break;
            }
            else
            {
                spieleOnline.next();
            }
        }
    }

    /**
     * Diese Methode gibt zurück, ob sich der angemeldete Client im Spielmodus befindet.
     * @param pClientIP
     * 
     * @return ist das Spiel bereits Online
     */
    private synchronized boolean istSpielOnline(String pClientIP, int pClientPort)
    {
        spieleOnline.toFirst();
        while(spieleOnline.hasAccess())
        {
            if(spieleOnline.getContent().getClientIP(1).equals(pClientIP) && spieleOnline.getContent().getClientPort(1) == pClientPort)
            {
                return true;
            }
            spieleOnline.next();
        }
        return false;
    }

    /**
     * Diese Methode wandelt die übergebene Lsite in einen String im folgenden Format um:
     * 1.TestspielerA|12
     * 2.TestspielerB|13
     * ...
     * Die Leerzeichen sind wichtig, damit der Client die Liste wieder auslesen kann!
     * 
     * @param List <Eintrag>
     * @return String Highscoreliste im String
     */
    private synchronized String generiereStringAusListe(List<Eintrag> list)
    {
        String erg = "";
        list.toFirst();
        while(list.hasAccess())
        {
            erg = erg + list.getContent().gibName() + "|"+list.getContent().gibPunkte()+" ";
            list.next();
        }
        return erg; // Das letzte Leerzeichen entfernen
    }
}
