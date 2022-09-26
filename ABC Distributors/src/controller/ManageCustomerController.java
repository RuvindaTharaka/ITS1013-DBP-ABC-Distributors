package controller;

import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageCustomerController {
    public JFXTextField txtCustTitle;
    public JFXTextField txtCustID;
    public JFXTextField txtCustName;
    public JFXTextField txtCustAddress;
    public JFXTextField txtCustCity;
    public JFXTextField txtCustProvince;
    public JFXTextField txtCustPostCode;
    public boolean flag=true;
    public static ObservableList customerList = FXCollections.observableArrayList();

    public static ObservableList getCustomerList() {
        return customerList;
    }

    public void searchCustOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer WHERE custID=?");
        stm.setObject(1, txtCustID.getText());
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            txtCustTitle.setText(rst.getString(2));
            txtCustName.setText(rst.getString(3));
            txtCustAddress.setText(rst.getString(4));
            txtCustCity.setText(rst.getString(5));
            txtCustProvince.setText(rst.getString(6));
            txtCustPostCode.setText(rst.getString(7));
        }else{
            new Alert(Alert.AlertType.WARNING,"Customer Do not exists").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            if (txtCustID.getText().equalsIgnoreCase(null) || txtCustTitle.getText().equalsIgnoreCase(null) || txtCustName.getText().equalsIgnoreCase(null) || txtCustCity.getText().equalsIgnoreCase(null) || txtCustProvince.getText().equalsIgnoreCase(null) || txtCustPostCode.getText().equalsIgnoreCase(null)) {
                new Alert(Alert.AlertType.WARNING, "Fill all fields..").show();
            }
        }catch(Exception ex){
            new Alert(Alert.AlertType.WARNING, "Fill all fields..").show();
        }
        Customer c1 = new Customer(txtCustID.getText(), txtCustTitle.getText(), txtCustName.getText(), txtCustAddress.getText(), txtCustCity.getText(), txtCustProvince.getText(), txtCustPostCode.getText());
        if(updateCustomer(c1)){
            new Alert(Alert.AlertType.CONFIRMATION, "Updated.").show();
        }else{
            new Alert(Alert.AlertType.CONFIRMATION, "Try Again..").show();
        }
    }

    private boolean updateCustomer(Customer c) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Customer SET custTitle=?, custName=?, custAddress=?, city=?, province=?, postalCode=?  WHERE CustID=?");
        stm.setObject(1,c.getCustTitle());
        stm.setObject(2,c.getCustName());
        stm.setObject(3,c.getCustAddress());
        stm.setObject(4,c.getCity());
        stm.setObject(5,c.getProvince());
        stm.setObject(6,c.getPostalCode());
        stm.setObject(7,c.getCustID());
        return stm.executeUpdate()>0;
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("DELETE  FROM Customer WHERE CustID='" + txtCustID.getText() + "'");
        if(stm.executeUpdate()>0){
                customerList.remove(txtCustID);

            new Alert(Alert.AlertType.CONFIRMATION, "Deleted..").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }
}
