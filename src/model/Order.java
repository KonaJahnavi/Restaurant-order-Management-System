package model;

import java.util.ArrayList;
import java.time.LocalDate;


public class Order {

    // Attributes
    private int orderId;
    private Customer customer;
    private ArrayList<OrderItem> orderItems;
    private double grandTotal;
    private LocalDate orderDate;
    // Default Constructor
    public Order() {
        orderItems = new ArrayList<>();
    }

    // Parameterized Constructor
    public Order(int orderId, Customer customer, ArrayList<OrderItem> orderItems, double grandTotal) {
        this.orderId = orderId;
        this.customer = customer;
        this.orderItems = orderItems;
        this.grandTotal = grandTotal;
    }
    public Order(int orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.orderItems = new ArrayList<>();
        this.orderDate = LocalDate.now();
    }


    // Getter for Order ID
    public int getOrderId() {
        return orderId;
    }

    // Setter for Order ID
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    // Getter for Customer
    public Customer getCustomer() {
        return customer;
    }

    // Setter for Customer
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // Getter for Order Items
    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    // Setter for Order Items
    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    // Getter for Grand Total
    public double getGrandTotal() {
        return grandTotal;
    }

    // Setter for Grand Total
    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    // Add one item to the order
    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
    }
    public LocalDate getOrderDate() {
        return orderDate;
    }

    // Calculate Grand Total
    public double calculateGrandTotal() {
        grandTotal = 0;

        for (OrderItem item : orderItems) {
            grandTotal += item.getItemTotal();
        }
        return grandTotal;
    }


    // Display Order Details
    @Override
    public String toString() {

        StringBuilder bill = new StringBuilder();

        bill.append("\nOrder ID : ").append(orderId);
        bill.append("\nCustomer : ").append(customer.getCustomerName());
        bill.append("\nTable No : ").append(customer.getTableNumber());

        bill.append("\n\nOrdered Items:\n");

        for (OrderItem item : orderItems) {
            bill.append(item).append("\n");
        }

        bill.append("\nGrand Total : ₹").append(grandTotal);

        return bill.toString();
    }
}