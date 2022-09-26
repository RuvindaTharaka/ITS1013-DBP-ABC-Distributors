package controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static controller.CashierDashBoardController.itemLists;


public class RemoveItemsController {
    public JFXTextField txtItemCode;
    public JFXTextArea txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;

    public void cancelOnAction(ActionEvent actionEvent){
        txtItemCode.setText(null);
        txtDescription.setText(null);
        txtPackSize.setText(null);
        txtUnitPrice.setText(null);
        txtQtyOnHand.setText(null);
    }

    public void removeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("DELETE  FROM Item WHERE itemCode='" + txtItemCode.getText() + "'");
        if(stm.executeUpdate()>0){
            itemLists.remove(txtItemCode.getText());
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted..").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

    public void searchCodeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item WHERE itemCode='" + txtItemCode.getText() + "'");
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            txtDescription.setText(rst.getString(2));
            txtPackSize.setText(rst.getString(3));
            txtUnitPrice.setText(rst.getString(4));
            txtQtyOnHand.setText(rst.getString(5));
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        }
    }
}
