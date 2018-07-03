import javax.swing.*;
/**
 * Klasse fuer einen SpielClient
 * @author Henning Ainödhofer
 * @version 21.3.2009
 */

public class SpielClient extends Client { 
    public SpielClient(String ip, int p) {
        super(ip, p);
    }

    /**
     * Diese Methode der Server-Klasse wird hiermit ueberschrieben.
     * Der Client gibt die erhaltende Meldung, auf dem Textfeld aus.
     */

    public void processMessage(String message){
        switch(gibBefehlsbereich(message))
        {
            case "CON": // Verbindung erfolgreich
            {
                System.out.println(message);
                break;
            }
            case "PLY": // Verbindung erfolgreich
            {
                System.out.println(this.gibTextbereich(message));
                break;
            }
            case "POS": //Zahl war richtig
            {
                System.out.println(this.gibTextbereich(message));
                break;
            }
            case "NEG": //Zahl war falsch
            {
                System.out.println(this.gibTextbereich(message));
                break;
            }
            case "HSC": //Highscoreliste mit den 10 Besten drucken
            {
                System.out.println("----Highscoreliste----");
                this.highscorelisteDrucken(this.gibTextbereich(message));
                break;
            }
            case "EXIT": //Highscoreliste mit den 10 Besten drucken
            {
                System.out.println(message);
                this.close();
                break;
            }
            default:
            {
                System.out.println(this.gibTextbereich(message));
                break;
            }
        }
    }

    /**
     * Diese Methode gibt den Befehl zurück die die message beinhaltet
     * 
     * @param message
     * 
     * @return Befehl
     */
    private String gibBefehlsbereich(String message)
    {
        return message.split(" ")[0];
    }

    /**
     * Diese Methode gibt den Text zurück die die message beinhaltet
     * 
     * @param message
     * 
     * @return Text
     */
    private String gibTextbereich(String message)
    {
        String [] messageArray = message.split(" ");
        String text = "";
        for(int i = 1; i < messageArray.length; i++)
        {
            text = text+" "+ messageArray[i];
        }
        return text;
    }

    /**
     * Diese Methode druckt die Higscoreliste auf der Konsole aus.
     * @param message
     */
    private void highscorelisteDrucken(String message)
    {
        if(message.length() < 3)
        {
            System.out.println("Noch kein Highscore vorhanden!");
        }
        else
        {
            String [] plaetze = message.split(" ");
            for(int i = 1; i < plaetze.length; i++)
            {
                System.out.println(i+". "+plaetze[i]);
            }
        }
    }
}
