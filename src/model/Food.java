package model;

public class Food {

    // Attributes
    private int foodId;
    private String foodName;
    private double price;

    // Default Constructor
    public Food() {
    }

    // Parameterized Constructor
    public Food(int foodId, String foodName, double price) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
    }

    // Getter for foodId
    public int getFoodId() {
        return foodId;
    }

    // Setter for foodId
    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    // Getter for foodName
    public String getFoodName() {
        return foodName;
    }

    // Setter for foodName
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    // Getter for price
    public double getPrice() {
        return price;
    }

    // Setter for price
    public void setPrice(double price) {
        this.price = price;
    }

    // Display Food Details
    @Override
    public String toString() {
        return "Food ID      : " + foodId +
                "\nFood Name    : " + foodName +
                "\nPrice        : ₹" + price;
    }
}