package controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import model.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ModifyItemsController {
    public JFXTextField txtItemCode;
    public JFXTextArea txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Item c1= new Item(
                txtItemCode.getText(),txtDescription.getText(),
                txtPackSize.getText(),Double.parseDouble(txtUnitPrice.getText()),
                Double.parseDouble(txtQtyOnHand.getText())
        );


        if (update(c1))
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING,"Try Again").show();


    }

    public void searchCodeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item WHERE itemCode=?");
        stm.setObject(1, txtItemCode.getText());
        ResultSet rst = stm.executeQuery();
        if (rst.next()){
            Item c1= new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getDouble(5)
            );
            setData(c1);
        }else{
            new Alert(Alert.AlertType.WARNING, "Empty Set").show();
        }
    }

    boolean update(Item c) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Item SET description=?, packSize=?, unitPrice=?, qtyOnHand=? WHERE itemCode=?");
        stm.setObject(5,c.getItemCode());
        stm.setObject(1,c.getDescription());
        stm.setObject(2,c.getPackSize());
        stm.setObject(3,c.getUnitPrice());
        stm.setObject(4,c.getQtyOnHand());
        return stm.executeUpdate()>0;

    }

    void setData(Item c){
        txtItemCode.setText(c.getItemCode());
        txtDescription.setText(c.getDescription());
        txtPackSize.setText(c.getPackSize());
        txtUnitPrice.setText(String.valueOf(c.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(c.getQtyOnHand()));
    }

}
