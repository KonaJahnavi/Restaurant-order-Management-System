package main;
import model.Customer;
import java.util.Scanner;
import service.RestaurantService;
public class Main{

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        RestaurantService restaurant = new RestaurantService();
        Customer customer=new Customer();
        // Load food items
        restaurant.loadMenu();

        // Display Restaurant Name
        restaurant.displayRestaurant();
        restaurant.displayMenu();
        int choice;

        do {
            System.out.println("Enter Your Choice:");
            choice = sc.nextInt();

            switch (choice) {
               // Here is the Menu
                case 1:
                    restaurant.displayMenu();
                    break;
                //to add Customer
                case 2:
                    restaurant.addCustomer();
                    break;
                 //To take order
                case 3:
                    restaurant.takeOrder();
                    break;
                    //print the bill for specified orderId s
                case 4:
                    restaurant.printBill();
                    break;
                  // to print all orders that all customers ordered
                case 5:
                    restaurant.printAllOrders();
                    break;
                    // to display all customers
                case 6:
                    restaurant.displayAllCustomers();
                    break;
                    // search for customer By id
                case 7:
                    restaurant.searchCustomerById();
                    if(restaurant.searchCustomerById()==null){
                        System.out.println("Customer Not Found!");
                    }
                    break;
                    //search for customer by name
                case 8:
                    restaurant.searchCustomerByName();
                    if(restaurant.searchCustomerByName()==null){
                        System.out.println("Customer Not Found!");
                    }
                    break;
                    //Search for food by Id
                case 9:
                    restaurant.searchFoodById();
                    if(restaurant.searchFoodById()==null){
                        System.out.println("Food Not Available Right Now");
                    }
                    break;
                    //Search for food by name
                case 10:
                    restaurant.searchFoodByName();
                    if(restaurant.searchFoodById()==null){
                        System.out.println("Food Not Available Right Now");
                    }
                    break;
                    //to Update the customer details
                case 11:
                     restaurant.updateCustomer();
                     break;
                     // to display all orders;
                case 12:
                    restaurant.displayOrders();
                    break;
                    // remove an item from the orderID
                case 13:
                    restaurant.removeItemFromOrder();
                    break;
                    //to cancelling the order
                case 14:
                    restaurant.cancelOrder();
                    break;
                    //to display the sales
                case 15:
                    restaurant.displayTodaySales();
                    break;
                    // to display the conclusion
                case 16:
                    restaurant.displayStatistics();
                    break;
                    // to display the sales of item
                case 17:
                    restaurant.displaySales();
                    break;
                    //Final Stage
                case 18:
                    restaurant.customerCheckout();
                    break;
                    //Items under orderID
                case 19:
                    restaurant.displayItemsBasedOnOrderID();
                    break;
                case 20:
                    restaurant.showOccupiedTables();
                    break;
                case 21:
                    restaurant.showAvailableTables();
                    break;
                case 22:
                    restaurant.assignTable();
                    break;
                case 23:
                    restaurant.showWaitingList();
                    break;
                    default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 0);


    }
}