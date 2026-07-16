package model;

public class OrderItem {

    private Food food;
    private int quantity;
    private double itemTotal;

    public OrderItem() {
    }

    public OrderItem(Food food, int quantity) {
        this.food = food;
        this.quantity = quantity;
        this.itemTotal = food.getPrice() * quantity;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateItemTotal();
    }
    public void setItemTotal(double itemTotal){
        this.itemTotal=itemTotal;
    }

    public double getItemTotal() {
        return itemTotal;
    }

    public void calculateItemTotal() {
        itemTotal = food.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return "Food Name  : " + food.getFoodName() +
                "\nPrice      : ₹" + food.getPrice() +
                "\nQuantity   : " + quantity +
                "\nItem Total : ₹" + itemTotal;
    }
}