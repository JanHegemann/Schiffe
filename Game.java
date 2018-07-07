
/**
 * Beschreiben Sie hier die Klasse Game.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Game
{
 private Player player1;
 private Player player2;
 
 public Game(String pname1, String pClientIp1, int pClientPort1,String pname2, String pClientIp2, int pClientPort2){
     player1=new Player(pname1,pClientIp1,pClientPort1);
     player2=new Player(pname2,pClientIp2,pClientPort2);
    }
    
 public String getClientIP(int number){
     if(number==1){
         return player1.getClientIP();
        }
     else{
         return player2.getClientIP();
        }
    }
    public int getClientPort(int number){
     if(number==1){
         return player1.getClientPort();
        }
     else{
         return player2.getClientPort();
        }
    }
}
