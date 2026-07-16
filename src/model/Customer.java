package model;
import service.*;
import java.util.*;
public class Customer {

    // Attributes
    private int customerId;
    private String customerName;
    private String phoneNumber;
    private int tableNumber;

    // Default Constructor
    public Customer() {
    }

    // Parameterized Constructor
    public Customer(int customerId, String customerName, String phoneNumber, int tableNumber) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.tableNumber = tableNumber;
    }

    // Getter for customerId
    public int getCustomerId() {
        return customerId;
    }

    // Setter for customerId
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    // Getter for customerName
    public String getCustomerName() {
        return customerName;
    }

    // Setter for customerName
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // Getter for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Setter for phoneNumber
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Getter for tableNumber
    public int getTableNumber() {
        return tableNumber;
    }

    // Setter for tableNumber
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    // Display Customer Details
    @Override
    public String toString() {
        return "Customer ID   : " + customerId +
                "\nCustomer Name : " + customerName +
                "\nPhone Number  : " + phoneNumber +
                "\nTable Number  : " + tableNumber;
    }


}