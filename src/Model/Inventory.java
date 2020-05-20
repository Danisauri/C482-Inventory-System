package Model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.*;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 *
 * @author Dani
 */

public class Inventory {

    private static ObservableList <Part> allParts = FXCollections.observableArrayList();;
    private static ObservableList <Product> allProducts = FXCollections.observableArrayList();
    

    public static void addPart (Part newPart) {
        allParts.add(newPart);
    }
    public static void addProduct (Product newProduct) {
        allProducts.add(newProduct);
    }
    public static Part lookupPart (int partId){
        Part result  = allParts.stream()
                .filter(part -> part.getId() == partId)
                .findFirst()
                .get(); 
        return result;
    };
    public static ObservableList<Part> lookupPart (String name){
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getName().toLowerCase().contains(name.toLowerCase())) {
                foundParts.add(allParts.get(i));
                }
            }
        return foundParts;   
    };
    public static ObservableList<Product> lookupProduct (String name){
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getName().toLowerCase().contains(name.toLowerCase())) {
                foundProducts.add(allProducts.get(i));
                }
            }
        return foundProducts; 
    };
    public static Product lookupProduct (int productId){
        Product result  = allProducts.stream()
                .filter(product -> product.getId() == productId)
                .findFirst()
                .get(); 
        return result; 
    };
    public static void updatePart (int index, Part selectedPart){
        allParts.set(index, selectedPart);
    };
    public static void updateProduct (int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    };
    public static void deletePart (Part selectedPart){
        allParts.remove(selectedPart);
    };
    public static void deleteProduct (Product selectedProduct){
        allProducts.remove(selectedProduct);
    };
    public static ObservableList <Part> getAllParts(){
        return  allParts;
    };
    public static ObservableList <Product> getAllProducts (){
        return allProducts;
    };   

}
    
