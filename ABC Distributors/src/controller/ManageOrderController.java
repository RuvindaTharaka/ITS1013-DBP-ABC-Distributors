package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.Item;
import model.Order;
import model.OrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static controller.CashierDashBoardController.itemLists;

public class ManageOrderController {

    public Label orderID;
    public Label orderDate;
    public Label customerID;
    public JFXTextField txtOrderID;
    public JFXTextField txtOrderDate;
    public JFXTextField txtCustID;
    public TableColumn colOrderIDList;
    public TableColumn colItemCodeList;
    public TableColumn ColQtyList;
    public TableColumn colDiscountList;
    public static ObservableList<OrderDetails> obList3= FXCollections.observableArrayList();
    public JFXComboBox cmbItemCode;
    public JFXTextField txtQty;
    public TableView tblList2;

    public static ObservableList<OrderDetails> obList4= FXCollections.observableArrayList();
    public JFXTextField txtPreItemList;

    public void initialize(){
        colOrderIDList.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colItemCodeList.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        ColQtyList.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        colDiscountList.setCellValueFactory(new PropertyValueFactory<>("discount"));
        cmbItemCode.setItems(itemLists);
    }

    public void searchOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Order` WHERE orderID=?");
        stm.setObject(1,txtOrderID.getText());
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            txtOrderDate.setText(rst.getString(2));
            txtCustID.setText(rst.getString(3));

        }else{
            new Alert(Alert.AlertType.WARNING,"Order Do not exists").show();
        }
        PreparedStatement stm1 = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM OrderDetail WHERE orderID=?");
        stm1.setObject(1,txtOrderID.getText());
        ResultSet rst1 = stm1.executeQuery();
        while(rst1.next()){
            txtPreItemList.setText(rst1.getString(2)+"-"+rst1.getInt(3)+", ");
        }

    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            if (txtOrderID.getText().equalsIgnoreCase(null) || txtOrderDate.getText().equalsIgnoreCase(null) || txtCustID.getText().equalsIgnoreCase(null) || txtQty.getText().equalsIgnoreCase(null) ) {
                new Alert(Alert.AlertType.WARNING, "Fill all fields..").show();
            }
        }catch(Exception ex){
            new Alert(Alert.AlertType.WARNING, "Fill all fields..").show();
        }
        Order c1 = new Order(txtOrderID.getText(),txtOrderDate.getText(),txtCustID.getText());
        if(updateOrder(c1)){

            new Alert(Alert.AlertType.CONFIRMATION, "Updated.").show();
        }else{
            new Alert(Alert.AlertType.CONFIRMATION, "Try Again..").show();
        }
    }

    private boolean updateOrder(Order o) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Order SET orderDate=?, custID=?  WHERE orderID=?");
        stm.setObject(2,o.getOrderDate());
        stm.setObject(3,o.getCustId());
        stm.setObject(1,o.getOrderID());
        return true;
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        PreparedStatement stm2 = DbConnection.getInstance().getConnection().prepareStatement("DELETE * FROM OrderDetail WHERE orderID=?");
        stm2.setObject(1,txtOrderID.getText());
         stm2.executeUpdate();
    }

    public void addSelectedItemsToTable(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        PreparedStatement stm2 = DbConnection.getInstance().getConnection().prepareStatement("DELETE  FROM OrderDetail WHERE orderID=?");
        stm2.setObject(1,txtOrderID.getText());
        stm2.executeUpdate();
            OrderDetails rst = new OrderDetails(txtOrderID.getText(), cmbItemCode.getValue().toString(), Integer.parseInt(txtQty.getText()), 0.0);
            obList4.add(new OrderDetails(
                    rst.getOrderID(), rst.getItemCode(), rst.getOrderQty(), rst.getDiscount()
            ));

            tblList2.setItems(obList4);


            if(saveOrderDetails(rst)){
                new Alert(Alert.AlertType.CONFIRMATION,"Added.").show();
            }


    }

    private boolean saveOrderDetails(OrderDetails od) throws SQLException, ClassNotFoundException {
        Connection con1= DbConnection.getInstance().getConnection();
        String query1="INSERT INTO OrderDetail VALUES(?,?,?,?)";
        PreparedStatement stm1= con1.prepareStatement(query1);
        stm1.setObject(1,od.getOrderID());
        stm1.setObject(2,od.getItemCode());
        stm1.setObject(3,od.getOrderQty());
        stm1.setObject(4,od.getDiscount());
        return stm1.executeUpdate()>0;
    }
}
