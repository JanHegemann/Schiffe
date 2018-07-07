
/**
 * @author 
 * @version 
 */
public class Player
{
    // Bezugsobjekte

    // Attribute
    private String  name, password, clientIp;
    private int clientPort;
    private Field field;
    // Konstruktor
    public Player(String pname,String pClientIp, int pClientPort)
    {
        this.name=pname;
        this.clientIp=pClientIp;
        this.clientPort=pClientPort;
        this.field=new Field(10);
    }

    // Dienste
    public String getName (){
        return this.name;
    }
    public String getPassword (){
        return this.password;
    }
    public String getClientIP (){
        return this.clientIp;
    }
    public int getClientPort (){
        return this.clientPort;
    }
}
