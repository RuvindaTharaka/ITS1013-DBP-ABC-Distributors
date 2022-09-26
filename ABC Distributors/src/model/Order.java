package model;

public class Order {
    String orderID;
    String orderDate;
    String CustId;

    public Order() {
    }

    public Order(String orderID, String orderDate, String custId) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.CustId = custId;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustId() {
        return CustId;
    }

    public void setCustId(String custId) {
        CustId = custId;
    }
}
