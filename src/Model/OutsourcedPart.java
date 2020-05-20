package Model;

/**
 *
 * @author Dani
 */
public class OutsourcedPart extends Part {

    private String companyName;

    public OutsourcedPart (int id, String name, int stock, double price, int max, int min, String companyName) {
        super(id, name, stock, price, max, min);
        this.companyName = companyName;
    };
    public void setCompanyName (String companyName){
        this.companyName = companyName;
    }
    public String getCompanyName (){
        return companyName;
    }
}
