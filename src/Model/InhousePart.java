package Model;

/**
 *
 * @author Dani
 */
public class InhousePart extends Part {

    private int machineId;

    public InhousePart (int id, String name, int stock, double price, int max, int min, int machineId) {
        super(id, name, stock, price, max, min);
        this.machineId = machineId;
    };
    public void setMechineId (int machineId){
        this.machineId = machineId;
    };
    public int getMachineId (){
        return machineId;
    };
}
