
package View_Controller;

import Model.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import Model.InhousePart;
import Model.Utilities.Validator;
import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;



/**
 * FXML Controller class
 *
 * @author Dani
 */
public class AddPartController implements Initializable {

    /**
     * Initializes the controller class.
     */ 
    @FXML private int getAllParts;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private TextField id;
    @FXML private TextField name;
    @FXML private TextField stock;
    @FXML private TextField price;
    @FXML private TextField max;
    @FXML private TextField min;
    @FXML private TextField companynm;
    @FXML private RadioButton inhousebutton;
    @FXML private RadioButton outsourcedbutton;
    @FXML private Label companynamelabel;
    @FXML Part part;
    int idCounterParts;
    boolean validation = false;

    public void addPartSaveButtonPressed (ActionEvent event) throws IOException{
        idCounterParts = Inventory.getAllParts().size()+1;

        if (inputvalidation()){
            int stockToVal = Integer.parseInt(stock.getText());
            int maxToVal = Integer.parseInt(max.getText());
            int minToVal = Integer.parseInt(min.getText());
            if (Validator.Numericalconditions(stockToVal, maxToVal, minToVal)){
            if(inhousebutton.isSelected()){
               InhousePart newPart = new InhousePart(
                      idCounterParts,
                      name.getText(),
                      Integer.parseInt( stock.getText()),
                      Double.parseDouble(price.getText()),
                      Integer.parseInt(max.getText()),
                      Integer.parseInt(min.getText()),
                      Integer.parseInt(companynm.getText())
               );
               Inventory.addPart(newPart);  
           }
           else if (outsourcedbutton.isSelected()){
               OutsourcedPart newPart = new OutsourcedPart(
                      idCounterParts,
                      name.getText(),
                      Integer.parseInt(stock.getText()),
                      Double.parseDouble(price.getText()),
                      Integer.parseInt(max.getText()),
                      Integer.parseInt(min.getText()),
                      companynm.getText()
               );
               Inventory.addPart(newPart); 
           }
            Parent mainscreen_page = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene mainscreen_scene = new Scene(mainscreen_page);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(mainscreen_scene);
            app_stage.show();
           
        }
    }
    }
    
    public void addPartCancelButtonPressed (ActionEvent event) throws IOException{
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
   
    
    public boolean inputvalidation() {
        if (!inhousebutton.isSelected() && !outsourcedbutton.isSelected()){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("ERROR");
            alert.setContentText("Please Select Type of product (Inhouse or Outsourced)");
            alert.showAndWait();
            validation = false;
        }
        if(Validator.validatenumber(stock, "Stock") && 
           Validator.validatedouble(price, "Price") && 
           Validator.validatenumber(max, "Max") && 
           Validator.validatenumber(min,"Min") && 
           Validator.validatestring(name, "name")){
            if (inhousebutton.isSelected() && Validator.validatenumber(companynm, "Machine ID")){
                validation = true;
            }
            if(outsourcedbutton.isSelected() && Validator.validatestring(companynm, "Company name")){
                validation = true;
            }
        }
        return validation;
    }
    public void inhouseselected(){
        companynamelabel.setText("Machine ID");
    }
    public void outsourcedselected(){
        companynamelabel.setText("Company name");
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
