package controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class AddItemsController {
    public JFXTextField txtItemCode;
    public JFXTextArea txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;

    public static ObservableList itemList = FXCollections.observableArrayList();

    public static ObservableList getItemList() {
        return itemList;
    }

    public void addOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Item c1= new Item(
                txtItemCode.getText(),txtDescription.getText(),txtPackSize.getText(),Double.parseDouble(txtUnitPrice.getText()),Double.parseDouble(txtQtyOnHand.getText()));
        if(saveItem(c1)){
            new Alert(Alert.AlertType.CONFIRMATION,"Saved..").show();
            itemList.add(c1.getItemCode());
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again..").show();
        }
    }

    private boolean saveItem(Item i) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Item VALUES(?,?,?,?,?)";
        PreparedStatement stm= con.prepareStatement(query);
        stm.setObject(1,i.getItemCode());
        stm.setObject(2,i.getDescription());
        stm.setObject(3,i.getPackSize());
        stm.setObject(4,i.getUnitPrice());
        stm.setObject(5,i.getQtyOnHand());
         return stm.executeUpdate()>0;
    }
}
