package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Item;
import model.Order;
import model.OrderDetails;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static controller.AddCustomerController.customerList;

public class CashierDashBoardController<grossTot> {
    public Label lblTime;
    public Label lblDate;
    public AnchorPane cashierContext;
    public JFXTextField txtOrderDate;
    public JFXTextField txtOrderId;
    public Label lblCustomerName;
    public Label lblGrossTotal;
    public JFXComboBox cmbCustId;
    public JFXTextField txtQty;
    public JFXTextField txtDiscount;
    public TableView tblItems;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TableView tblList;
    public TableColumn colOrderIDList;
    public TableColumn colItemCodeList;
    public TableColumn ColQtyList;
    public TableColumn colDiscountList;
    public JFXComboBox cmbItemCodes;
    static double grossTot;
    public static ObservableList itemLists = FXCollections.observableArrayList();
    public static ObservableList<Item> obList2= FXCollections.observableArrayList();
    public static String custIDBill;
    public static String orderIDBill;
    public static String orderDateBill;


    public void initialize() throws SQLException, ClassNotFoundException {
        showDate();
        showTime();
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        colOrderIDList.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colItemCodeList.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        ColQtyList.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        colDiscountList.setCellValueFactory(new PropertyValueFactory<>("discount"));

        loadDataToTblItems();
        loadCustList();
        loadItemList();
        txtDiscount.setText("0.0");
        cmbCustId.setItems(customerList);
        cmbItemCodes.setItems(itemLists);
    }
    public void showDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date d = new Date();
        lblDate.setText(formatter.format(d));
        txtOrderDate.setText(formatter.format(d));
    }

    public void showTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm a");
        Date d = new Date();
        lblTime.setText(formatter.format(d));
    }

    private void loadDataToTblItems() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item");
        ResultSet rst = stm.executeQuery();
        ObservableList<Item> obList= FXCollections.observableArrayList();

        while(rst.next()){

            obList.add(new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getDouble(5)
            ));
        }
        try {
            tblItems.setItems(obList);
        }catch (Exception ex){}
    }

    private void loadItemList() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item");
        ResultSet rst = stm.executeQuery();
        while(rst.next()){
            itemLists.add(rst.getString(1));
        }
    }

    public void loadCustList() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer");
        ResultSet rst = stm.executeQuery();
        while(rst.next()){
            customerList.add(rst.getString(1));
        }

    }


    public void updateOrderOnAction(ActionEvent actionEvent) throws IOException {
        Parent load= FXMLLoader.load(getClass().getResource("../view/ManageOrder.fxml"));
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }


    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        Parent load= FXMLLoader.load(getClass().getResource("../view/LogIn.fxml"));
        Stage window = (Stage) cashierContext.getScene().getWindow();
        window.fullScreenProperty();
        window.setScene(new Scene(load));
    }

    public void addCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Parent load= FXMLLoader.load(getClass().getResource("../view/AddCustomer.fxml"));
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void manageCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Parent load= FXMLLoader.load(getClass().getResource("../view/ManageCustomer.fxml"));
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void makeOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
       try {
           Order o = new Order(txtOrderId.getText(), txtOrderDate.getText(), cmbCustId.getValue().toString());

        if(saveOrder(o)){
            new Alert(Alert.AlertType.CONFIRMATION,"Saved..").show();
        }else{
            new Alert(Alert.AlertType.CONFIRMATION,"Try Again..").show();
        }
       }catch (Exception ex){
           new Alert(Alert.AlertType.WARNING,"Fill necessary fields").show();
       }
       custIDBill=cmbCustId.getValue().toString();
       orderIDBill=txtOrderId.getText();
       orderDateBill=txtOrderDate.getText();

       billAndPayment();
       txtOrderId.setText(null);
       txtOrderDate.setText(null);
       txtDiscount.setText("0.0");
       colItemCodeList.setText(null);
       colDiscountList.setText(null);
       colOrderIDList.setText(null);
       ColQtyList.setText(null);
       lblCustomerName.setText(null);
    }

    private void billAndPayment() throws IOException {
        Parent load= FXMLLoader.load(getClass().getResource("../view/Payment.fxml"));
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private boolean saveOrder(Order o) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO `Order` VALUES(?,?,?)";
        PreparedStatement stm= con.prepareStatement(query);
        stm.setObject(1,o.getOrderID());
        stm.setObject(2,o.getOrderDate());
        stm.setObject(3,o.getCustId());

        int i = stm.executeUpdate();
        /**Connection con1= DbConnection.getInstance().getConnection();
        String query1="INSERT INTO OrderDetails VALUES(?,?,?,?)";
        PreparedStatement stm1= con.prepareStatement(query);
        stm1.setObject(1,od.getOrderID());
        stm1.setObject(2,od.getItemCode());
        stm1.setObject(3,od.getOrderQty());
        stm1.setObject(4,od.getDiscount());
        int i1 = stm.executeUpdate();*/
        return (i>0);
       // return (i>0&&i1>0);
    }

    public void calGrossTot() throws SQLException, ClassNotFoundException {
        double dis=0.0;
        double price=0.0;
        double qty=0.0;
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item WHERE itemCode='"+cmbItemCodes.getValue().toString()+"'");
        ResultSet rst = stm.executeQuery();
        while(rst.next()){
             price = Double.parseDouble(rst.getString(4));
        }
        qty=Double.parseDouble(txtQty.getText());

        grossTot=grossTot+(price*qty);
        lblGrossTotal.setText(String.valueOf(grossTot));
    }

    public void fillCustName(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer WHERE custID='"+cmbCustId.getValue().toString()+"'");
        ResultSet rst = stm.executeQuery();
        if(rst.next()){
            lblCustomerName.setText(rst.getString(3));

        }else{
            new Alert(Alert.AlertType.WARNING,"Please Add The Customer..").show();
        }
    }

    public void addSelectedItemsToTable(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
                OrderDetails rst = new OrderDetails(txtOrderId.getText(), cmbItemCodes.getValue().toString(), Integer.parseInt(txtQty.getText()), Double.parseDouble(txtDiscount.getText()));
                obList2.add(new OrderDetails(
                        rst.getOrderID(), rst.getItemCode(), rst.getOrderQty(), rst.getDiscount()
                ));
                calGrossTot();
                tblList.setItems(obList2);
        }catch (Exception ex){
            new Alert(Alert.AlertType.WARNING,"Please fill Oder Id,Item code,Quantity and Discount").show();
        }
        try {
            OrderDetails od = new OrderDetails(txtOrderId.getText(), cmbItemCodes.getValue().toString(), Integer.parseInt(txtQty.getText()), Double.parseDouble(txtDiscount.getText()));
            saveOrderDetails(od);
        }catch (Exception ex){
            new Alert(Alert.AlertType.WARNING,"Please fill Oder Id,Item code,Quantity and Discount").show();
        }

    }

    private void saveOrderDetails(OrderDetails od) throws SQLException, ClassNotFoundException {
        Connection con1= DbConnection.getInstance().getConnection();
        String query1="INSERT INTO OrderDetail VALUES(?,?,?,?)";
        PreparedStatement stm1= con1.prepareStatement(query1);
        stm1.setObject(1,od.getOrderID());
        stm1.setObject(2,od.getItemCode());
        stm1.setObject(3,od.getOrderQty());
        stm1.setObject(4,od.getDiscount());
        stm1.executeUpdate();
    }
}
