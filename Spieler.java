
/**
 * @author 
 * @version 
 */
public class Spieler
{
    // Bezugsobjekte

    // Attribute
    private String id, name, password, clientIp;
    private int clientPort;
    // Konstruktor
    public Spieler(String pId, String pname, String ppassword,String pClientIp, int pClientPort)
    {
        this.id=pId;
        this.name=pname;
        this.password=ppassword;
        this.clientIp=pClientIp;
        this.clientPort=pClientPort;
    }

    // Dienste
    public String gibId (){
        return this.id;
    }
    public String gibName (){
        return this.name;
    }
    public String gibPassword (){
        return this.password;
    }
    public String gibClientIP (){
        return this.clientIp;
    }
    public int gibClientPort (){
        return this.clientPort;
    }
}
