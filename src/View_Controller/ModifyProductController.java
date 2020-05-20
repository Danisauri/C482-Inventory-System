
package View_Controller;

import Model.*;
import Model.Utilities.Validator;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
public class ModifyProductController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TextField id;
    @FXML private TextField name;
    @FXML private TextField stock;
    @FXML private TextField price;
    @FXML private TextField max;
    @FXML private TextField min;
    @FXML private TextField modProdSearchText;
    @FXML private TableColumn modProdIdCol;
    @FXML private TableColumn modProdNameCol;
    @FXML private TableColumn modProdStockCol;
    @FXML private TableColumn modProdPriceCol;
    @FXML private TableView modProdAllTable;
    @FXML private TableColumn modProdAsocPartIDCol;
    @FXML private TableColumn modProdAsocPartNameCol;
    @FXML private TableColumn modProdAsocPartStockCol;
    @FXML private TableColumn modProdAsocPartPriceCol;
    @FXML private TableView modProdAssocTable;
    private Product productToModify;
    private Part selectedPart;
    boolean validation = false;
    ObservableList<Part> tempList = FXCollections.observableArrayList();

    
    public void initDataProduct (Product product){
        id.setText(Integer.toString(product.getId()));
        name.setText(product.getName());
        stock.setText(Integer.toString(product.getStock()));
        price.setText(Double.toString(product.getPrice()));
        max.setText(Integer.toString(product.getMax()));
        min.setText(Integer.toString(product.getMin()));
        productToModify = product;
        tempList.clear();
        modProdAssocTable.getItems().clear();
        productToModify.getAllAssociatedParts().forEach(element ->  tempList.add(element));
        modProdAssocTable.setItems(tempList);
        
        
    }

    public void modifyProductSaveButtonPressed (ActionEvent event) throws IOException{
        if(inputvalidation()){
        int stockToVal = Integer.parseInt(stock.getText());
        int maxToVal = Integer.parseInt(max.getText());
        int minToVal = Integer.parseInt(min.getText());
        double priceToVal = Double.parseDouble(price.getText());
                if(Validator.ProductConditions(productToModify) 
                    && Validator.Numericalconditions(stockToVal, maxToVal, minToVal)
                    && Validator.pricePartsProduct(productToModify, priceToVal)){
                    productToModify.setName(name.getText());
                    productToModify.setStock(Integer.parseInt(stock.getText()));
                    productToModify.setPrice(Double.parseDouble(price.getText()));
                    productToModify.setMax(Integer.parseInt(max.getText()));
                    productToModify.setMin(Integer.parseInt(min.getText()));
                    productToModify.getAllAssociatedParts().clear();
                    tempList.forEach(element -> productToModify.addAssociatedParts(element));
                    modProdAssocTable.getItems().clear();
                    tempList.clear();
                    
                    Parent mainscreen_page = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                    Scene mainscreen_scene = new Scene(mainscreen_page);
                    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    app_stage.setScene(mainscreen_scene);
                    app_stage.show();
            }
        else{
            modProdAssocTable.setItems(tempList);
        }
        }
    }
    public void modifyProductCancelButtonPressed (ActionEvent event) throws IOException{
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
    public void modifyProductDeleteButtonPressed (ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Do you really want to delete this item from the product??");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            int selectedIndex = modProdAssocTable.getSelectionModel().getSelectedIndex();
            modProdAssocTable.getItems().remove(selectedIndex);
        }
    }
    public void modifyProductAddButtonPressed (ActionEvent event){
        selectedPart = (Part) modProdAllTable.getSelectionModel().getSelectedItem();
        tempList.add(selectedPart);
        modProdAssocTable.setItems(tempList);        
    }
    public void modifyProductSearchButtonPressed (ActionEvent event){
        String searchPartinput = modProdSearchText.getText();
            ObservableList foundPart = Inventory.lookupPart(searchPartinput);
        if (foundPart.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("ERROR");
            alert.setContentText("No match Found");
            alert.showAndWait();
        }
        else{
            modProdAllTable.setItems(foundPart);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        modProdIdCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        modProdNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        modProdStockCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        modProdPriceCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        modProdAllTable.setItems(Inventory.getAllParts());
        
        modProdAsocPartIDCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        modProdAsocPartNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        modProdAsocPartStockCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        modProdAsocPartPriceCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));        
        
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
