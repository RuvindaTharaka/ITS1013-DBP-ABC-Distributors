package controller;


import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Item;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagementDashBoardController {
    public AnchorPane managementDashBoardContext;
    public  TableView tblItems;
    public  TableColumn colItemCode;
    public  TableColumn colDescription;
    public  TableColumn colPackSize;
    public  TableColumn colUnitPrice;
    public  TableColumn colQtyOnHand;
    public Label lblGoldCustomer;
    public Label lblMostMovable;
    public Label lblLeastMovable;

    public void addItemsOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        Parent load= FXMLLoader.load(getClass().getResource("../view/AddItems.fxml"));
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
        loadAllData();
    }

    public void updateItemsOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        Parent load= FXMLLoader.load(getClass().getResource("../view/ModifyItems.fxml"));
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
        loadAllData();
    }

    public void removeItemsOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        Parent load= FXMLLoader.load(getClass().getResource("../view/RemoveItems.fxml"));
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
        loadAllData();
    }

    public void openReportsOnAction(ActionEvent actionEvent) throws IOException {
        Parent load= FXMLLoader.load(getClass().getResource("../view/SystemReport.fxml"));
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        Parent load= FXMLLoader.load(getClass().getResource("../view/LogIn.fxml"));
        Stage window = (Stage) managementDashBoardContext.getScene().getWindow();
        window.fullScreenProperty();
        window.setScene(new Scene(load));

    }

    public  void initialize(){
        try {
            colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
            colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
            loadAllData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void loadAllData() throws ClassNotFoundException, SQLException {
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

    public void refreshOnAction(ActionEvent actionEvent) throws IOException {
        Parent load= FXMLLoader.load(getClass().getResource("../view/ManagementDashBoard.fxml"));
        Stage window = (Stage) managementDashBoardContext.getScene().getWindow();
        window.fullScreenProperty();
        window.setScene(new Scene(load));
    }
}
