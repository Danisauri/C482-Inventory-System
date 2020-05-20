
package View_Controller;

import Model.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class MainScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private TextField searchPartTextField;
    @FXML private TextField searchProductTextField;
    @FXML private TableColumn partIdColumn;
    @FXML private TableColumn partNameColumn;
    @FXML private TableColumn partInventoryColumn;
    @FXML private TableColumn partPriceColumn;
    @FXML private TableColumn productIdColumn;
    @FXML private TableColumn productNameColumn;
    @FXML private TableColumn productInventoryColumn;
    @FXML private TableColumn productPriceColumn;
    @FXML private TableView partsTable;
    @FXML private TableView productsTable;
    @FXML Part part;
    Product product1 = new Product(0, null, 0, 0.0, 0, 0);
    Product product2 = new Product(0, null, 0, 0.0, 0, 0);
    
    
    public void onSearchPartsButtonPressed () {
        String searchPartinput = searchPartTextField.getText();

            ObservableList foundParts = Inventory.lookupPart(searchPartinput);

        if (foundParts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("ERROR");
            alert.setContentText("No match Found");
            alert.showAndWait();
        }
        else{
            partsTable.setItems(foundParts);
        }
    }
    public void addPartButtonPressed (ActionEvent event) throws IOException {
        Parent add_part_page = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene add_part_scene = new Scene(add_part_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(add_part_scene);
        app_stage.show();
    }
    public void modifyPartButtonPressed (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyPart.fxml"));
        Parent modify_part_page = loader.load();
        ModifyPartController controller = loader.getController();
        if((partsTable.getSelectionModel().getSelectedItem()) instanceof InhousePart){
            controller.initDataInhouse((InhousePart) (partsTable.getSelectionModel().getSelectedItem()));
        }
        else{
            controller.initDataOutsourced((OutsourcedPart) (partsTable.getSelectionModel().getSelectedItem()));
        }
        
        Scene modify_part_scene = new Scene(modify_part_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(modify_part_scene);
        app_stage.show();
    }
    public void deletePartButtonPressed (ActionEvent event){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Do you really want to delete this item??");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            int selectedIndex = partsTable.getSelectionModel().getSelectedIndex();
            partsTable.getItems().remove(selectedIndex);
        }
    }
    public void onSearchProductButtonPressed () {
        String searchProductinput = searchProductTextField.getText();

            ObservableList foundParts = Inventory.lookupProduct(searchProductinput);

        if (foundParts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("ERROR");
            alert.setContentText("No match Found");
            alert.showAndWait();
        }
        else{
            productsTable.setItems(foundParts);
        }
    }
    public void addProductButtonPressed (ActionEvent event) throws IOException {
        Parent add_product_page = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene add_product_scene = new Scene(add_product_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(add_product_scene);
        app_stage.show();
    }
    public void modifyProductButtonPressed (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyProduct.fxml"));
        Parent modify_product_page = loader.load();
        ModifyProductController controller = loader.getController();
        controller.initDataProduct((Product) (productsTable.getSelectionModel().getSelectedItem()));
        Scene modify_product_scene = new Scene(modify_product_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(modify_product_scene);
        app_stage.show();
    }
    public void deleteProductButtonPressed (ActionEvent event){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Do you really want to delete this item??");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Product selectedProductToDelete = (Product) productsTable.getSelectionModel().getSelectedItem();
            if (!selectedProductToDelete.getAllAssociatedParts().isEmpty()){
                alert.setTitle("Confirmation Dialog");
                alert.setContentText("You can't delete this item, it has associated parts");
                alert.showAndWait();
            }
            else{
            int selectedIndex = productsTable.getSelectionModel().getSelectedIndex();
            productsTable.getItems().remove(selectedIndex);
            }
        }
    }
    public void exitButtonPressed (ActionEvent event){
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        partsTable.setItems(getParts());

        productIdColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        productsTable.setItems(getProducts());
    }
    public ObservableList<Part> getParts(){
        if (Inventory.getAllParts().isEmpty()){
            Part part1 = new InhousePart(1, "part1", 1, 1.0, 1, 1, 1);
            Part part2 = new OutsourcedPart(2, "part2", 2, 2.0, 2, 2, "name2");
            Part part3 = new InhousePart(3, "part3", 3, 3.0, 3, 3, 3);
            Part part4 = new OutsourcedPart(4, "part4", 4, 4.0, 4, 4, "name4");
            Inventory.addPart(part1);
            Inventory.addPart(part2);
            Inventory.addPart(part3);
            Inventory.addPart(part4);
            product1.addAssociatedParts(part1);
            product1.addAssociatedParts(part2);
            product2.addAssociatedParts(part3);
            product2.addAssociatedParts(part4);
        }
        return Inventory.getAllParts();
    }
    public ObservableList<Product> getProducts(){
        if (Inventory.getAllProducts().isEmpty()){
        product1.setId(12);
        product1.setName("product12");
        product1.setStock(12);
        product1.setPrice(12.0);
        product1.setMax(12);
        product1.setMin(12);
        
        product2.setId(34);
        product2.setName("product34");
        product2.setStock(34);
        product2.setPrice(34.0);
        product2.setMax(34);
        product2.setMin(34);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        }
        return Inventory.getAllProducts();
    }
    
}
