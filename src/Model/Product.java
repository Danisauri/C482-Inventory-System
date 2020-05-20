package Model;

import javafx.collections.*;
/**
 *
 * @author Dani
 */
public class Product {

    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;


    public Product (int id, String name, int stock, double price, int min, int max) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.min = min;
        this.max = max;
        this.associatedParts = FXCollections.observableArrayList();
    };
    public void setId (int id){
        this.id = id;
    };
    public void setName (String name){
        this.name = name;
    };
    public void setPrice (double price){
        this.price = price;
    };
    public void setStock (int stock){
        this.stock = stock;
    };
    public void setMin (int min){
        this.min = min;
    };
    public void setMax (int max){
        this.max = max;
    };
    public int getId(){
        return id;
    };
    public String getName(){
        return name;
    };
    public double getPrice(){
        return price;
    };
    public int getStock() {
        return stock;
    };
    public int getMin(){
        return min;
    };
    public int getMax(){
        return max;
    };
    public void addAssociatedParts (Part part){
        associatedParts.add(part);
    };
    public void deleteAssociatedParts (Part part){
        associatedParts.remove(part);
    };
    public ObservableList<Part> getAllAssociatedParts (){
        return associatedParts;
    };
}
