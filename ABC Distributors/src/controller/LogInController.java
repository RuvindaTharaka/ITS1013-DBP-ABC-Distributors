package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;



public class LogInController {
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public RadioButton cashierBtn;
    public RadioButton managementBtn;
    public AnchorPane logInContext;


    public void logInOnAction(ActionEvent actionEvent) throws IOException {
        if(txtUserName.getText().equalsIgnoreCase("1")&&txtPassword.getText().equalsIgnoreCase("1")){
                if(cashierBtn.isSelected()){
                    Parent load= FXMLLoader.load(getClass().getResource("../view/CashierDashBoard.fxml"));
                    Stage window = (Stage) logInContext.getScene().getWindow();
                    window.fullScreenProperty();
                    window.setScene(new Scene(load));
                }else if(managementBtn.isSelected()){
                    Parent load= FXMLLoader.load(getClass().getResource("../view/ManagementDashBoard.fxml"));
                    Stage window = (Stage) logInContext.getScene().getWindow();
                    window.fullScreenProperty();
                    window.setScene(new Scene(load));
                }else{
                    new Alert(Alert.AlertType.WARNING,"Please Select Cashier or Management either").show();
                }
        }else{
            new Alert(Alert.AlertType.WARNING,"Invalid User Name or Password.").show();
        }

    }
}
