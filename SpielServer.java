import java.net.*;

/**
 * Klasse fuer einen SpielServer
 * @author Henning Ainödhofer
 * @version 21.03.2009
 */

public class SpielServer extends Server {

    private HighscoreGateway DBhighscore;
    private SpielerGateway DBspieler;
    private List<Spiel> spieleOnline;

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
                spieleOnline.append(new Spiel(pClientIP, pClientPort, gibZufallszahl(), gibTextbereich(pMessage,0)));
                this.send(pClientIP, pClientPort, "PLY Hallo "+this.gibNameVonSpiel(pClientIP, pClientPort)+", raten Sie eine Zahl zwischen 0 und 20!");
                break;
            }
            case "SND": //Zahl gesendet
            {
                if(istSpielOnline(pClientIP, pClientPort))
                {
                    this.versucheErhoehenVonSpiel(pClientIP, pClientPort);
                    if(Integer.parseInt(gibTextbereich(pMessage,0)) == this.gibZahlVonSpiel(pClientIP, pClientPort))
                    {
                        DBhighscore.hinzufuegen(this.gibNameVonSpiel(pClientIP, pClientPort), this.gibVersucheVonSpiel(pClientIP, pClientPort));
                        this.send(pClientIP, pClientPort, "POS Super, Sie haben das Spiel mit "+gibVersucheVonSpiel(pClientIP, pClientPort)+" Versuchen gewonnen!");
                    }
                    else
                    {
                        if(Integer.parseInt(gibTextbereich(pMessage,0)) < this.gibZahlVonSpiel(pClientIP, pClientPort))
                        {
                            this.send(pClientIP, pClientPort, "NEG Leider falsch, versuchen Sie es noch einmal! Die gesuchte Zahl ist größer!");
                        }
                        else
                        {
                            this.send(pClientIP, pClientPort, "NEG Leider falsch, versuchen Sie es noch einmal! Die gesuchte Zahl ist kleiner!");
                        }
                    }
                }
                else
                {
                    this.send(pClientIP, pClientPort, "ERR Sie haben noch kein Spiel gestartet!");
                }
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
            if(spieleOnline.getContent().gibClientIP().equals(pClientIP) && spieleOnline.getContent().gibClientPort() == pClientPort)
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
     * Methode, die die zu erratende Zahl vom Spiel mit der übergebenen ClientIP zurück gibt.
     * @param pClientIP
     * @return zu erratende Zahl
     */
    private synchronized int gibZahlVonSpiel(String pClientIP, int pClientPort)
    {
        Spiel erg = null;
        spieleOnline.toFirst();
        while(spieleOnline.hasAccess())
        {
            if(spieleOnline.getContent().gibClientIP().equals(pClientIP) && spieleOnline.getContent().gibClientPort() == pClientPort)
            {
                erg = spieleOnline.getContent();
                break;
            }
            spieleOnline.next();
        }
        return erg.gibZahl();
    }

    /**
     * Methode, die die zu erratende Zahl vom Spiel mit der übergebenen ClientIP zurück gibt.
     * @param pClientIP
     * @return zu erratende Zahl
     */
    private synchronized String gibNameVonSpiel(String pClientIP, int pClientPort)
    {
        Spiel erg = null;
        spieleOnline.toFirst();
        while(spieleOnline.hasAccess())
        {
            if(spieleOnline.getContent().gibClientIP().equals(pClientIP) && spieleOnline.getContent().gibClientPort() == pClientPort)
            {
                erg = spieleOnline.getContent();
                break;
            }
            spieleOnline.next();
        }
        return erg.gibName();
    }

    /**
     * Methode, die die Anzahl der bisherigen Versuche vom Spiel mit der übergebenen ClientIP zurück gibt.
     * @param pClientIP
     * @return bisherige Versuche
     */
    private synchronized int gibVersucheVonSpiel(String pClientIP, int pClientPort)
    {
        int erg = 20;
        spieleOnline.toFirst();
        while(spieleOnline.hasAccess())
        {
            if(spieleOnline.getContent().gibClientIP().equals(pClientIP) && spieleOnline.getContent().gibClientPort() == pClientPort)
            {
                erg = spieleOnline.getContent().gibVersuche();
                break;
            }
            spieleOnline.next();
        }
        return erg;
    }

    /**
     * Methode, die beim Spiel mit der übergebenen Client-IP, die Versuche um 1 erhöht.
     * @param pClientIP
     */
    private synchronized void versucheErhoehenVonSpiel(String pClientIP, int pClientPort)
    {
        spieleOnline.toFirst();
        while(spieleOnline.hasAccess())
        {
            if(spieleOnline.getContent().gibClientIP().equals(pClientIP) && spieleOnline.getContent().gibClientPort() == pClientPort)
            {
                spieleOnline.getContent().erhoeheVeruche();
                break;
            }
            spieleOnline.next();
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
            if(spieleOnline.getContent().gibClientIP().equals(pClientIP) && spieleOnline.getContent().gibClientPort() == pClientPort)
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
