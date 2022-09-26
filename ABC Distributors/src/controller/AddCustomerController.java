package controller;

import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddCustomerController {
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
    public void initialize(){
        txtCustID.setText(null);
        txtCustName.setText(null);
        txtCustTitle.setText(null);
        txtCustAddress.setText(null);
        txtCustCity.setText(null);
        txtCustProvince.setText(null);
        txtCustPostCode.setText(null);
    }

    public boolean searchCustOnAction() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer WHERE custID=?");
        stm.setObject(1, txtCustID.getText());
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            new Alert(Alert.AlertType.WARNING,"Customer Exists").show();
            txtCustID.setText(null);
            txtCustName.setText(null);
            txtCustTitle.setText(null);
            txtCustAddress.setText(null);
            txtCustCity.setText(null);
            txtCustProvince.setText(null);
            txtCustPostCode.setText(null);
           return  false;
        }
        return true;
    }



    public void confirmOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       if(searchCustOnAction()) {
           try {
               if (txtCustID.getText().equalsIgnoreCase(null) || txtCustTitle.getText().equalsIgnoreCase(null) || txtCustName.getText().equalsIgnoreCase(null) || txtCustCity.getText().equalsIgnoreCase(null) || txtCustProvince.getText().equalsIgnoreCase(null) || txtCustPostCode.getText().equalsIgnoreCase(null)) {
                   new Alert(Alert.AlertType.WARNING, "Fill all fields..").show();
               }else{
                   Customer c1 = new Customer(txtCustID.getText(), txtCustTitle.getText(), txtCustName.getText(), txtCustAddress.getText(), txtCustCity.getText(), txtCustProvince.getText(), txtCustPostCode.getText());
                   if (saveCustomer(c1)) {
                       txtCustID.setText(null);
                       txtCustName.setText(null);
                       txtCustTitle.setText(null);
                       txtCustAddress.setText(null);
                       txtCustCity.setText(null);
                       txtCustProvince.setText(null);
                       txtCustPostCode.setText(null);
                       new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
                       customerList.add(c1.getCustID());
                   } else {
                       new Alert(Alert.AlertType.WARNING, "Try Again..").show();
                   }
               }
           }catch(Exception ex){
               new Alert(Alert.AlertType.WARNING, "Fill all fields..").show();
               }

           }


    }

    private boolean saveCustomer(Customer i) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Customer VALUES(?,?,?,?,?,?,?)";
        PreparedStatement stm= con.prepareStatement(query);
        stm.setObject(1,i.getCustID());
        stm.setObject(2,i.getCustTitle());
        stm.setObject(3,i.getCustName());
        stm.setObject(4,i.getCustAddress());
        stm.setObject(5,i.getCity());
        stm.setObject(6,i.getProvince());
        stm.setObject(7,i.getPostalCode());
        return stm.executeUpdate()>0;
    }
}

