
/**
 * Beschreiben Sie hier die Klasse Field.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Field
{
    public boolean ownField [] [];
    public boolean otherField [] [];

    public Field(int pSize){
        this.ownField=new boolean[pSize][pSize];
        this.otherField=new boolean[pSize][pSize];
    }
    
    public void printField(boolean [] [] pField){
     for(int i=0;i<pField.length;i++){
         for (int j=0;j<pField[i].length;j++){
             if(pField[i][j]==true){
                 System.out.print("x ");
                }
             else{
                 System.out.print("o ");
                }
            }
         System.out.println("");   
        }
    }
    
    
}
