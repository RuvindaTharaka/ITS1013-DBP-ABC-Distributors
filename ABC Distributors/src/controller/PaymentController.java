package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

import static controller.CashierDashBoardController.grossTot;

public class PaymentController {
    public Label lblNetTotal;
    public Label lblBalance;
    public JFXTextField txtPayment;
    static double bal;
    static double pay;

    public void initialize(){
        lblNetTotal.setText(String.valueOf(grossTot));
    }
    public void calBalOnAction(ActionEvent actionEvent) {
        pay=Double.parseDouble(txtPayment.getText());
        double net=grossTot;
        bal = pay-grossTot;
        String balance=String.valueOf(bal);
        lblBalance.setText(balance);
    }

    public void confirmOnAction(ActionEvent actionEvent) throws IOException {
        Parent load= FXMLLoader.load(getClass().getResource("../view/Bill.fxml"));
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
