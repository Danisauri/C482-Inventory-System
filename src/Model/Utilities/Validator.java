/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Utilities;

import Model.*;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 *
 * @author Dani
 */
public class Validator {
        public static boolean validatenumber (TextField inputText, String fieldName){
        
        try{
            Integer.parseInt(inputText.getText()); 
            return true;
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("ERROR");
            alert.setContentText("Please write a valid "+fieldName+" number");
            alert.showAndWait();
            return false;
        }
    }
    public static boolean validatestring (TextField inputText, String fieldName){
        if(inputText.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("ERROR");
            alert.setContentText("Please write a "+fieldName);
            alert.showAndWait();
            return false;
        }
        else{
            return true;
        }
    }
    public static boolean validatedouble (TextField inputText, String fieldName){
        try{
            Double.parseDouble(inputText.getText()); 
            return true;
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("ERROR");
            alert.setContentText("Please write a valid "+fieldName+" number");
            alert.showAndWait();
            return false;
        }
    }
    public static boolean Numericalconditions (int stock, int max, int min){
        if (max < min){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("ERROR");
            alert.setContentText("Max can't be smaller than min");
            alert.showAndWait();
            return false;
        }
        else if (min > max){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("ERROR");
            alert.setContentText("min can't be bigger than max");
            alert.showAndWait();
            return false;
        }
        else if (stock > max){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("ERROR");
            alert.setContentText("Stock Can't be more than max");
            alert.showAndWait();
            return false;
        }
        else if (stock < min){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("ERROR");
            alert.setContentText("Stock Can't be less than min");
            alert.showAndWait();
            return false;
        }
        else {
            return true;
        }
    }
    public static boolean ProductConditions (Product product){
        if(product.getAllAssociatedParts().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("ERROR");
            alert.setContentText("Products must have at least one part");
            alert.showAndWait();
            return false;
        }
        else{
            return true;
        }
    }
    public static boolean pricePartsProduct (Product product, double priceToVal){
        double sumPriceParts = product.getAllAssociatedParts().stream().mapToDouble(element -> element.getPrice()).sum();
        if(priceToVal < sumPriceParts){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("ERROR");
            alert.setContentText("Products can't have a cost that is less than the cost of its parts");
            alert.showAndWait();
            return false;
        }
        else{
            return true;
        }
        
    }
}
