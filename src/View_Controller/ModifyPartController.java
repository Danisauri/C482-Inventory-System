
package View_Controller;

import Model.*;
import Model.Utilities.Validator;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * FXML Controller class
 *
 * @author Dani
 */
public class ModifyPartController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private TextField id;
    @FXML private TextField name;
    @FXML private TextField stock;
    @FXML private TextField price;
    @FXML private TextField max;
    @FXML private TextField min;
    @FXML private TextField companynm;
    @FXML RadioButton inhousebutton;
    @FXML RadioButton outsourcedbutton;
    @FXML Label companynamelabel;
    @FXML Part part;    
    private InhousePart selectedPartIn;
    private OutsourcedPart selectedPartOut;
    boolean validation = false;
    
    public void initDataInhouse (InhousePart part){
        selectedPartIn = part;
        id.setText(Integer.toString(selectedPartIn.getId()));
        name.setText(selectedPartIn.getName());
        stock.setText(Integer.toString(selectedPartIn.getStock()));
        price.setText(Double.toString(selectedPartIn.getPrice()));
        max.setText(Integer.toString(selectedPartIn.getMax()));
        min.setText(Integer.toString(selectedPartIn.getMin()));
        companynm.setText(Integer.toString(selectedPartIn.getMachineId())); 
        inhousebutton.setSelected(true);
        companynamelabel.setText("Machine ID");
    }
    public void initDataOutsourced (OutsourcedPart part){
        selectedPartOut = part;
        id.setText(Integer.toString(selectedPartOut.getId()));
        name.setText(selectedPartOut.getName());
        stock.setText(Integer.toString(selectedPartOut.getStock()));
        price.setText(Double.toString(selectedPartOut.getPrice()));
        max.setText(Integer.toString(selectedPartOut.getMax()));
        min.setText(Integer.toString(selectedPartOut.getMin()));
        companynm.setText(selectedPartOut.getCompanyName());
        outsourcedbutton.setSelected(true);
    }
      
    public void modifyPartCancelButtonPressed (ActionEvent event) throws IOException {
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
    public void modifyPartInHouseHandle (ActionEvent event){
       companynamelabel.setText("Machine ID");
    }
    public void modifyPartOutsourcedHandle (ActionEvent event){
        companynamelabel.setText("Company name");
    }
    
    public void modifyPartSaveButtonPressed (ActionEvent event) throws IOException{
        if (inputvalidation()){
            int stockToVal = Integer.parseInt(stock.getText());
            int maxToVal = Integer.parseInt(max.getText());
            int minToVal = Integer.parseInt(min.getText());
            if(Validator.Numericalconditions(stockToVal, maxToVal, minToVal)){
                    if(inhousebutton.isSelected()){
                        selectedPartIn.setName(name.getText());
                        selectedPartIn.setStock(Integer.parseInt(stock.getText()));
                        selectedPartIn.setPrice(Double.parseDouble(price.getText()));
                        selectedPartIn.setMax(Integer.parseInt(max.getText()));
                        selectedPartIn.setMin(Integer.parseInt(min.getText()));
                        selectedPartIn.setMechineId(Integer.parseInt(companynm.getText()));
                    }
                    else if (outsourcedbutton.isSelected()){
                        selectedPartOut.setName(name.getText());
                        selectedPartOut.setStock(Integer.parseInt(stock.getText()));
                        selectedPartOut.setPrice(Double.parseDouble(price.getText()));
                        selectedPartOut.setMax(Integer.parseInt(max.getText()));
                        selectedPartOut.setMin(Integer.parseInt(min.getText()));
                        selectedPartOut.setCompanyName(companynm.getText());
                    }
                Parent mainscreen_page = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene mainscreen_scene = new Scene(mainscreen_page);
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                app_stage.setScene(mainscreen_scene);
                app_stage.show();
        }
        }
    }

    
    public boolean inputvalidation() {
        if (!inhousebutton.isSelected() && !outsourcedbutton.isSelected()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    }
    

