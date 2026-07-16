package service;
import model.OrderItem;
import model.Customer;
import model.Food;
import model.Order;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import static java.awt.SystemColor.menu;
import java.util.Iterator;


public class RestaurantService {
    Scanner sc = new Scanner(System.in);
    private ArrayList<Food> menu = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();
    private Queue<Customer> waitingList = new LinkedList<>();
    private int orderIdCounter = 1001;
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private int nextCustomerId = 101;


    public void loadMenu() {

        menu.add(new Food(1, "Veg Biryani", 180));
        menu.add(new Food(2, "Chicken Biryani", 250));
        menu.add(new Food(3,"Paneer Butter Masala",220));
        menu.add(new Food(4,"Butter Naan",40));
        menu.add(new Food(5,"Fried Rice",170));
        menu.add(new Food(6,"Chicken Fried Rice",210));
        menu.add(new Food(7,"Veg Noodles",160));
        menu.add(new Food(8,"Chicken Noodles",200));
        menu.add(new Food(9,"Soft Drink",40));
        menu.add(new Food(10,"Ice Cream",90));

            System.out.println("Menu Loaded Successfully!");


    }

    public void displayRestaurant() {

        System.out.println("=================================================");
        System.out.println("           DELICIOUS DINE RESTAURANT");
        System.out.println("=================================================");

    }

    public void displayMenu() {

        System.out.println("\n================= MENU ======================");
        System.out.println();
        System.out.printf("%-10s %-30s %-10s%n", "Food ID", "Food Name", "Price");
        System.out.println("------------------------------------------------");

        for (Food food : menu) {
            System.out.printf("%-10d %-30s ₹%.2f%n",
                    food.getFoodId(),
                    food.getFoodName(),
                    food.getPrice());
        }

        System.out.println("------------------------------------------------");

    }

    // Add Customer
    public void addCustomer() {

        System.out.println("===== Add Customer =====");

        int customerId = nextCustomerId++;
        sc.nextLine();
        System.out.print("Enter Customer Name:");
        String customerName = sc.nextLine();
        //phone number validity
        String phoneNumber;
        while (true) {

            System.out.println("Enter Phone Number: ");
            phoneNumber = sc.nextLine();

            if (phoneNumber.matches("\\d{10}")) {
                break;
            }

            System.out.println("Invalid phone number! Please enter exactly 10 digits.");
        }
        //table availabe and autoassign
        int tableNumber = assignTable();
        System.out.print("Enter Table Number: ");
        Customer customer = new Customer(customerId, customerName, phoneNumber, tableNumber);

        if (tableNumber == -1) {
            System.out.println("Sorry! No tables are available.");
            waitingList.offer(customer);
            System.out.println(customer.getCustomerName() + " has been added to the waiting list.");
            return;
        }

        System.out.println("Assigned Table Number : " + tableNumber);
        customers.add(customer);
        System.out.println("Customer added successfully!");
        System.out.println(customer.getCustomerId() + " " + customer.getCustomerName() + " " + customer.getTableNumber());

    }

    // Take Order
    public void takeOrder() {

        System.out.print("Enter Customer ID: ");
        int customerId = sc.nextInt();

        Customer customer = null;

        // Find Customer
        for (Customer c : customers) {
            if (c.getCustomerId() == customerId) {
                customer = c;
                break;
            }
        }

      for(Customer c:customers) {
          if (customer == null) {
              System.out.println("Customer not found!");
              return;
          }
      }
        Order order = new Order(orderIdCounter++, customer);
        order.setCustomer(customer);


        char choice = ' ';

        do {

            displayMenu();

            Food food = searchFoodById();

            if (food == null) {
                System.out.println("Food not found!");
                continue;
            }

            System.out.print("Enter Quantity: ");
            int quantity = sc.nextInt();

            boolean found = false;

            for (OrderItem item : order.getOrderItems()) {

                if (item.getFood().getFoodId() == food.getFoodId()) {

                    item.setQuantity(item.getQuantity() + quantity);
                    item.calculateItemTotal();   // Recalculate total

                    found = true;
                    break;
                }
            }

            if (!found) {

                OrderItem item = new OrderItem(food, quantity);
                order.addOrderItem(item);
            }

            System.out.print("Add another item? (Y/N): ");
            choice = sc.next().charAt(0);

        } while (choice == 'Y' || choice == 'y');

        order.calculateGrandTotal();

        orders.add(order);

        System.out.println("Order placed successfully.");

    }


    // Calculate Bill
    public void calculateBill() {

        System.out.print("Enter Order ID: ");
        int orderId = sc.nextInt();

        for (Order order : orders) {

            if (order.getOrderId() == orderId) {

                order.calculateGrandTotal();

                System.out.println("Bill Calculated Successfully.");
                System.out.println("Grand Total : ₹" + order.getGrandTotal());

                return;
            }
        }

        System.out.println("Order not found!");
    }


    // Print Customer Bill
    public void printBill() {
        System.out.print("Enter Order ID: ");
        int orderId = sc.nextInt();

        Order order = findOrderById(orderId);

        if (order == null) {
            System.out.println("Order not found!");
            return;
        }

        order.calculateGrandTotal();

        double subtotal = order.getGrandTotal();
        double gst = subtotal * 0.05;
        double grandTotal = subtotal + gst;

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        DateTimeFormatter timeFormatter =
                DateTimeFormatter.ofPattern("hh:mm a");

        System.out.println("\n===========================================");
        System.out.println("          DELICIOUS DINE RESTAURANT");
        System.out.println("===========================================");

        System.out.println("Bill No      : " + order.getOrderId());
        System.out.println("Date         : " + date);
        System.out.println("Time         : " + time.format(timeFormatter));

        System.out.println();

        System.out.println("Customer     : " + order.getCustomer().getCustomerName());
        System.out.println("Phone        : " + order.getCustomer().getPhoneNumber());
        System.out.println("Table No     : " + order.getCustomer().getTableNumber());

        System.out.println("\n-------------------------------------------");
        System.out.printf("%-20s %-8s %-10s%n", "Item", "Qty", "Price");
        System.out.println("-------------------------------------------");

        for (OrderItem item : order.getOrderItems()) {

            System.out.printf("%-20s %-8d ₹%.2f%n",
                    item.getFood().getFoodName(),
                    item.getQuantity(),
                    item.getItemTotal());
        }

        System.out.println("-------------------------------------------");

        System.out.printf("%-28s ₹%.2f%n", "Subtotal", subtotal);
        System.out.printf("%-28s ₹%.2f%n", "GST (5%)", gst);
        System.out.println("-------------------------------------------");
        System.out.printf("%-28s ₹%.2f%n", "Grand Total", grandTotal);

        System.out.println("===========================================");
        System.out.println("      Thank You! Visit Again!");
        System.out.println("===========================================");
    }

    // Print All Customer Orders
    public void printAllOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders have been placed yet.");
            return;
        }

        System.out.println("\n========== ALL ORDERS ==========");
        System.out.println("Date & Time : "
                + now.format(formatter));
        for (Order order : orders) {

            order.calculateGrandTotal(); // Ensure total is up to date

            System.out.println(order);
            System.out.println("----------------------------------------");
        }
    }

    public void displayAllCustomers() {

        if (customers.isEmpty()) {
            System.out.println("\nNo customers found.");
            System.out.println("Date & Time : "
                    + now.format(formatter));
            return;
        }

        System.out.println("\n==============================================");
        System.out.println("              CUSTOMER LIST");
        System.out.println("==============================================");
        System.out.println("Date & Time : "
                + now.format(formatter));
        System.out.printf("%-5s %-20s %-15s%n", "ID", "NAME", "PHONE");
        System.out.println("----------------------------------------------");

        for (Customer customer : customers) {
            System.out.printf("%-5d %-20s %-15s%n",
                    customer.getCustomerId(),
                    customer.getCustomerName(),
                    customer.getPhoneNumber());
        }

        System.out.println("==============================================");
    }

    public Customer searchCustomerById() {
        System.out.print("Enter Customer ID: ");
        int id = sc.nextInt();
        for (Customer customer : customers) {

            if (customer.getCustomerId() == id) {
                System.out.println("\nCustomer Found");
                System.out.println("-------------------------");
                System.out.println("Date & Time : "
                        + now.format(formatter));
                System.out.println("ID      : " + customer.getCustomerId());
                System.out.println("Name    : " + customer.getCustomerName());
                System.out.println("Phone   : " + customer.getPhoneNumber());
                return customer;
            }

        }
        return null;
    }
    public Customer searchCustomerByName() {
        System.out.print("Enter Customer Name: ");
        String name = sc.next();
        for (Customer customer : customers) {

            if (customer.getCustomerName().equalsIgnoreCase(name)) {
                System.out.println("\nCustomer Found");
                System.out.println("-------------------------");
                System.out.println("Date & Time : "
                        + now.format(formatter));
                System.out.println("ID      : " + customer.getCustomerId());
                System.out.println("Name    : " + customer.getCustomerName());
                System.out.println("Phone   : " + customer.getPhoneNumber());
                return customer;
            }
        }
        return null;
    }

    public Food searchFoodByName() {
        System.out.print("Enter Food Name: ");
        sc.nextLine(); // Clear the buffer if needed
        String foodName = sc.nextLine();
        for (Food food : menu) {

            if (food.getFoodName().equalsIgnoreCase(foodName)) {
                System.out.println("\n========== FOOD FOUND ==========");
                System.out.println("Date & Time : "
                        + now.format(formatter));
                System.out.println("ID    : " + food.getFoodId());
                System.out.println("Name  : " + food.getFoodName());
                System.out.println("Price : ₹" + food.getPrice());
                System.out.println("================================");
                return food;
            }
        }
        return null;

    }
    public Food searchFoodById() {
        System.out.print("Enter Food ID: ");
        int id = sc.nextInt();
        for (Food food : menu) {

            if (food.getFoodId() == id){
                System.out.println("\n========== FOOD FOUND ==========");
                System.out.println("Date & Time : "
                        + now.format(formatter));
                System.out.println("ID    : " + food.getFoodId());
                System.out.println("Name  : " + food.getFoodName());
                System.out.println("Price : ₹" + food.getPrice());
                System.out.println("================================");
                return food;
            }

        }

        return null;
    }
    public void displayOrders() {

        if (orders.isEmpty()) {
            System.out.println("No Orders Found!");
            return;
        }
        System.out.println("\n=================================");
        System.out.println("          ALL ORDERS");
        System.out.println("=================================");
        System.out.println("Date & Time : "
                + now.format(formatter));
        for(Order order : orders) {

            System.out.println("Order ID : " + order.getOrderId());

            System.out.println("Customer : "
                    + order.getCustomer().getCustomerName());
            System.out.println("-------------------------------");
            for(OrderItem item : order.getOrderItems()) {
                System.out.println(
                        item.getFood().getFoodName()
                                + "  Qty: "
                                + item.getQuantity()
                );
            }
            System.out.println("Total : ₹"
                    + order.getGrandTotal());
            System.out.println("=================================");
        }
    }
    public void removeItemFromOrder() {
        System.out.println("Enter orderId to remove an item:");
        int orderId=sc.nextInt();
        System.out.println("Enter foodId To remove:");
        int id=sc.nextInt();
        Order order = null;

        // Find Order
        for (Order o : orders) {

            if (o.getOrderId() == orderId) {
                order = o;
                break;
            }
        }
        if (order == null) {
            System.out.println("Order Not Found!");
            return;
        }
        // Remove Food Item
        Iterator<OrderItem> iterator =
                order.getOrderItems().iterator();

        boolean removed = false;
        while(iterator.hasNext()) {

            OrderItem item = iterator.next();


            if(item.getFood().getFoodId() == id) {

                iterator.remove();
                removed = true;
                break;
            }
        }
        if(removed) {
            System.out.println("Item Removed Successfully!");
            calculateBill();

        }
        else {

            System.out.println("Item Not Found In This Order!");

        }

    }
    public void cancelOrder() {
        System.out.print("Enter Order ID: ");
        int id = sc.nextInt();
        Iterator<Order> iterator = orders.iterator();

        while (iterator.hasNext()) {

            Order order = iterator.next();

            if (order.getOrderId() == id) {

                iterator.remove();

                System.out.println("Order Cancelled Successfully!");
                return;
            }
        }

        System.out.println("Order Not Found!");
    }
    public void displayTodaySales() {

        double totalSales = 0;

        LocalDate today = LocalDate.now();

        for (Order order : orders) {

            if (order.getOrderDate().equals(today)) {

                totalSales += order.calculateGrandTotal();

            }
        }

        System.out.println("\n==============================");
        System.out.println("       TODAY'S SALES");
        System.out.println("==============================");
        System.out.println("Date : " + today);
        System.out.println("Total Sales : ₹" + totalSales);
        System.out.println("==============================");
    }

    public void displayStatistics() {

        System.out.println("\n==================================");
        System.out.println("      RESTAURANT DASHBOARD");
        System.out.println("==================================");

        System.out.println("Total Customers : " + customers.size());

        System.out.println("Total Foods     : " + menu.size());

        System.out.println("Total Orders    : " + orders.size());


        double totalSales = 0;
        double highestBill = 0;


        for (Order order : orders) {

            totalSales += order.calculateGrandTotal();

            if (order.calculateGrandTotal() > highestBill) {
                highestBill = order.calculateGrandTotal();
            }
        }


        double averageBill = 0;

        if (!orders.isEmpty()) {
            averageBill = totalSales / orders.size();
        }


        System.out.println("Total Sales     : ₹" + totalSales);

        System.out.println("Highest Bill    : ₹" + highestBill);

        System.out.println("Average Bill    : ₹" + averageBill);

        System.out.println("==================================");
    }
    public void displaySales() {

        HashMap<String, Integer> foodCount = new HashMap<>();

        for (Order order : orders) {

            for (OrderItem item : order.getOrderItems()) {

                String foodName = item.getFood().getFoodName();

                int quantity = item.getQuantity();

                foodCount.put(foodName,
                        foodCount.getOrDefault(foodName, 0) + quantity);
            }
        }
        System.out.println("Quantities Sale of each one:");
        System.out.println(  "Item"+"            :"+"Quantity");
        for(Map.Entry<String, Integer> entry : foodCount.entrySet()){
            System.out.println( entry.getKey() +"            :"+ entry.getValue());
        }
        String mostOrderedFood = "";
        int max = 0;

        for (String food : foodCount.keySet()) {

            if (foodCount.get(food) > max) {

                max = foodCount.get(food);
                mostOrderedFood = food;
            }
        }

        if (mostOrderedFood.isEmpty()) {

            System.out.println("No Orders Available.");

        } else {

            System.out.println("\n==============================");
            System.out.println("     MOST ORDERED FOOD");
            System.out.println("==============================");
            System.out.println("Food : " + mostOrderedFood);
            System.out.println("Ordered Quantity : " + max);
            System.out.println("==============================");
        }
    }
    public  boolean isTableOccupied(int tableNumber) {
        for (Customer customer : customers) {
            if (customer.getTableNumber() == tableNumber) {
                return true;
            }
        }

        return false;
    }
    public void customerCheckout() {

        System.out.print("Enter Order ID: ");
        int orderId = sc.nextInt();

        Order order = findOrderById(orderId);

        if (order == null) {
            System.out.println("Order not found!");
            return;
        }

        order.calculateGrandTotal();

        System.out.println("\n========== FINAL BILL ==========");
        System.out.println(order);

        Customer customer = order.getCustomer();

        orders.remove(order);
        customers.remove(customer);

        System.out.println("\nCustomer checked out successfully.");
        System.out.println("Table " + customer.getTableNumber() + " is now available.");
        if (!waitingList.isEmpty()) {

            Customer nextCustomer = waitingList.poll();

            nextCustomer.setTableNumber(customer.getTableNumber());

            customers.add(nextCustomer);

            System.out.println("\nTable assigned to waiting customer.");

            System.out.println("Customer : "
                    + nextCustomer.getCustomerName());

            System.out.println("Table : "
                    + nextCustomer.getTableNumber());
        }
    }
    public Order findOrderById(int orderId) {

        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }

        return null;
    }
    public void displayItemsBasedOnOrderID() {
        System.out.print("Enter Order ID: ");
        int orderId = sc.nextInt();

        Order order = findOrderById(orderId);

        if (order == null) {
            System.out.println("Order not found!");
            return;
        }

        System.out.println(order);
    }
    public void showOccupiedTables() {

        System.out.println("\n===== Occupied Tables =====");

        for (Customer customer : customers) {
            System.out.println(
                    "Table " + customer.getTableNumber() +
                            " -> " + customer.getCustomerName()
            );
        }
    }
    public void showAvailableTables() {

        System.out.println("\n========== AVAILABLE TABLES ==========");

        boolean available = false;

        for (int tableNo = 1; tableNo <= 10; tableNo++) {

            if (!isTableOccupied(tableNo)) {
                System.out.println("Table " + tableNo);
                available = true;
            }
        }

        if (!available) {
            System.out.println("No tables are available.");
        }
    }
    public int assignTable() {

        for (int tableNo = 1; tableNo <= 10; tableNo++) {

            if (!isTableOccupied(tableNo)) {
                return tableNo;
            }
        }

        return -1; // No tables available
    }
    public void showWaitingList(){
        System.out.println(waitingList.size());
        for (Customer cus : waitingList) {
            System.out.println(cus);
        }

    }
    public void updateCustomer() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Customer ID to update: ");
        int customerId = sc.nextInt();


        Customer customer = searchCustomerById();


        if (customer != null) {

            System.out.println("Current Details:");
            System.out.println("--------------------");
            System.out.println("Name  : " + customer.getCustomerName());
            System.out.println("Phone : " + customer.getPhoneNumber());


            System.out.print("Enter New Customer Name: ");
            String name = sc.next();


            System.out.print("Enter New Phone Number: ");
            String phone = sc.next();


            // Update details
            customer.setCustomerName(name);
            customer.setPhoneNumber(phone);


            System.out.println("\nCustomer Updated Successfully!");

            System.out.println("--------------------");
            System.out.println("ID    : " + customer.getCustomerId());
            System.out.println("Name  : " + customer.getCustomerName());
            System.out.println("Phone : " + customer.getPhoneNumber());

        } else {
            System.out.println("Customer not found!");
            return;
        }
    }
}


