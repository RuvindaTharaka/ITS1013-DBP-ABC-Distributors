package controller;

import javafx.scene.control.Label;

import static controller.CashierDashBoardController.*;
import static controller.PaymentController.bal;
import static controller.PaymentController.pay;

public class BillController {
    public Label lblCustID;
    public Label lblOrderId;
    public Label lblOrderDate;
    public Label lblNetTotal;
    public Label lblBalance;
    public Label lblPayment;

    public void initialize(){
        lblCustID.setText(custIDBill);
        lblOrderId.setText(orderIDBill);
        lblOrderDate.setText( orderDateBill);
        lblNetTotal.setText(String.valueOf(grossTot));
        lblPayment.setText(String.valueOf(pay));
        lblBalance.setText(String.valueOf(bal));
    }
}
