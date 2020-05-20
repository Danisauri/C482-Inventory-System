
package View_Controller;

import Model.*;
import Model.Utilities.Validator;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class AddProductController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML TableView<Part> allPartsTable;
    @FXML TableColumn prodAddIdCol;
    @FXML TableColumn prodAddNameCol;
    @FXML TableColumn prodAddStockCol;
    @FXML TableColumn prodAddPriceCol;
    @FXML TableView<Part> asocPartsTable;
    @FXML TableColumn prodAsocIdCol;
    @FXML TableColumn prodAsocNameCol;
    @FXML TableColumn prodAsocStockCol;
    @FXML TableColumn prodAsocPriceCol;
    @FXML TextField name;
    @FXML TextField stock;
    @FXML TextField price;
    @FXML TextField max;
    @FXML TextField min;
    @FXML TextField addProductSearchField;
    
    int idCounterProducts;
    boolean validation = false;
    private Part selectedPart;
    Product newProduct = new Product (0,null, 0, 0.0,0 ,0);
    
    public void addProductSaveButtonPressed (ActionEvent event) throws IOException{
        idCounterProducts = Inventory.getAllProducts().size()+1;
        if (inputvalidation()){
            int stockToVal = Integer.parseInt(stock.getText());
            int maxToVal = Integer.parseInt(max.getText());
            int minToVal = Integer.parseInt(min.getText());
            double priceToVal = Double.parseDouble(price.getText());
            if (Validator.ProductConditions(newProduct) 
                    && Validator.Numericalconditions(stockToVal, maxToVal, minToVal)
                    && Validator.pricePartsProduct(newProduct, priceToVal)){
                newProduct.setId(idCounterProducts);
                newProduct.setName(name.getText());
                newProduct.setStock(Integer.parseInt( stock.getText()));
                newProduct.setPrice(Double.parseDouble(price.getText()));
                newProduct.setMax(Integer.parseInt(max.getText()));
                newProduct.setMin(Integer.parseInt(min.getText()));
                Inventory.addProduct(newProduct);
                Parent mainscreen_page = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene mainscreen_scene = new Scene(mainscreen_page);
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                app_stage.setScene(mainscreen_scene);
                app_stage.show();
           }
           }
    }
    public void addProductCancelButtonPressed (ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Do you really want to cancel??");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Parent mainscreen_page = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene mainscreen_scene = new Scene(mainscreen_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(mainscreen_scene);
            app_stage.show();
        }
    }
    public void addProductDeleteButtonPressed (ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Do you really want to delete this item from the product??");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            int selectedIndex = asocPartsTable.getSelectionModel().getSelectedIndex();
            asocPartsTable.getItems().remove(selectedIndex);
        }
    }
    public void addProductAddButtonPressed (ActionEvent event){
        selectedPart = allPartsTable.getSelectionModel().getSelectedItem();
        newProduct.addAssociatedParts(selectedPart);
        asocPartsTable.setItems(newProduct.getAllAssociatedParts());
    }
    public void addProductSearchButtonPressed (ActionEvent event){
        String searchProductinput = addProductSearchField.getText();
            ObservableList foundProduct = Inventory.lookupProduct(searchProductinput);
        if (foundProduct.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("ERROR");
            alert.setContentText("No Match found");
            alert.showAndWait();
        }
        else{
            allPartsTable.setItems(foundProduct);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prodAddIdCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        prodAddNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        prodAddStockCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        prodAddPriceCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        allPartsTable.setItems(Inventory.getAllParts());
        
        prodAsocIdCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        prodAsocNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        prodAsocStockCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        prodAsocPriceCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        asocPartsTable.setItems(newProduct.getAllAssociatedParts());
        
    }
   
    public boolean inputvalidation() {
        if(Validator.validatenumber(stock, "Stock") && 
           Validator.validatedouble(price, "Price") && 
           Validator.validatenumber(max, "Max") && 
           Validator.validatenumber(min,"Min") && 
           Validator.validatestring(name, "name")){
            validation = true;
        }
        return validation;
    }
}
