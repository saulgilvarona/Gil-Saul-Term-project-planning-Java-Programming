import java.util.ArrayList;
import java.util.Scanner;

// Employee categories
enum EmployeeCategory {
    CASHIER,
    REPLENISHER,
    MANAGER,
    CO_OWNER
}

// Food class
class Food {
    private String name;
    private int quantity;
    private double price;

    public Food(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void reduceQuantity(int amount) {
        quantity -= amount;
    }
    public void addQuantity(int amount) {
        quantity += amount;
    }

    @Override
    public String toString() {
        return name +
                " | Quantity: " + quantity +
                " | Price: $" + price;
    }
}

// Employee class
class Employee {
    private String name;
    private EmployeeCategory category;
    private double salary;

    public Employee(String name, EmployeeCategory category, double salary) {
        this.name = name;
        this.category = category;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void raiseSalary(double amount) {
        salary += amount;
    }

    @Override
    public String toString() {
        return name +
                " | " + category +
                " | Salary: $" + salary;
    }
}


class Bill {
    private String customerName;
    private String products;
    private double totalCost;
    private int day;
    private String cashierName;

    public Bill(String customerName, String products, double totalCost, int day, String cashierName) {
        this.customerName = customerName;
        this.products = products;
        this.totalCost = totalCost;
        this.day = day;
        this.cashierName = cashierName;
    }

    public int getDay() {
        return day;
    }

    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public String toString() {
        return "Customer: " + customerName +
               "\nProducts: " + products +
               "\nTotal Paid: $" + totalCost +
               "\nDay: " + day +
               "\nCashier: " + cashierName;
    }
}

// Supermarket class
class Supermarket {
    private ArrayList<Food> foods;
    private ArrayList<Employee> employees;
    private ArrayList<Bill> bills;
    private double money;

    public Supermarket() {
        foods = new ArrayList<>();
        employees = new ArrayList<>();
        bills = new ArrayList<>();
        money = 10000;

        foods.add(new Food("Bread", 50, 1.50));
        foods.add(new Food("Milk", 40, 2.20));
        foods.add(new Food("Eggs", 60, 3.00));
        foods.add(new Food("Rice", 30, 4.50));

        employees.add(
            new Employee("Michael",
                        EmployeeCategory.CASHIER,
                        1800)
        );

        employees.add(
            new Employee("Sarah",
                        EmployeeCategory.CASHIER,
                        1850)
        );
    }

    // Show foods
    public void displayFoods() {
        System.out.println("\n----- FOODS -----");

        for (int i = 0; i < foods.size(); i++) {
            Food food = foods.get(i);

            System.out.println(
                    i + ". " +
                    food.getName() +
                    " | Quantity: " +
                    food.getQuantity() +
                    " | Price: $" +
                    food.getPrice()
            );
        }
    }

    // Update food
    public void updateFood(int index, double price) {
        if (index >= 0 && index < foods.size()) {
            foods.get(index).setPrice(price);
        }
    }

    // Place order
    public void orderFood(int index, int amount) {
        if (index < 0 || index >= foods.size()) {
            System.out.println("Invalid food.");
            return;
        }

        Food food = foods.get(index);
        food.addQuantity(amount);

        double total = amount * (food.getPrice() / 2); //Half of the current price
        money -= total;

        System.out.println("Order completed.");
        System.out.println(amount + " units added to stock.");
        System.out.println("Cost of order: $" + total);
        System.out.println("Remaining money: $" + money);
    }

    // Show supermarket money
    public void showMoney() {
        System.out.println("Supermarket money: $" + money);
    }

    // Hire employee
    public void hireEmployee(Employee employee) {
        employees.add(employee);
        System.out.println(employee.getName() + " hired.");
    }

    // Remove employee
    public void removeEmployee(String name) {
        employees.removeIf(e -> e.getName().equalsIgnoreCase(name));
    }

    // Raise employee salary
    public void raiseEmployee(String name, double amount) {
        for (Employee e : employees) {
            if (e.getName().equalsIgnoreCase(name)) {
                e.raiseSalary(amount);
                System.out.println(name + " got a raise.");
                return;
            }
        }

        System.out.println("Employee not found.");
    }

    // Display employees
    public void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees.");
            return;
        }

        for (Employee e : employees) {
            System.out.println(e);
        }
    }

    public double getFoodPrice(String foodName) {
        for (Food food : foods) {
            if (food.getName().equalsIgnoreCase(foodName)) {
                return food.getPrice();
            }
        }

        return -1;
    }

    public Food getFood(String foodName) {
        for (Food food : foods) {
            if (food.getName().equalsIgnoreCase(foodName)) {
                return food;
            }
        }
        return null;
    }

    public void createBill(String customerName, String products, int day, String cashierName) {

        double totalCost = 0;

        String[] productList = products.split(",");

        for (String p : productList) {

            p = p.trim();

            String[] parts = p.split(" ");

            String foodName = parts[0];
            int quantity = Integer.parseInt(parts[1]);

            Food food = getFood(foodName);

            if (food != null) {

                if (food.getQuantity() >= quantity) {
                    totalCost += food.getPrice() * quantity;
                    food.reduceQuantity(quantity);
                }
                else {
                    System.out.println(
                            "Not enough " + foodName + " in stock."
                    );
                }
            }
        }

        Bill bill = new Bill(
                customerName,
                products,
                totalCost,
                day,
                cashierName
        );

        bills.add(bill);

        money += totalCost;

        System.out.println("Bill created successfully.");
        System.out.println("Total paid: $" + totalCost);
    }


    public void displayBills() {
        if (bills.isEmpty()) {
            System.out.println("No bills.");
            return;
        }

        for (Bill b : bills) {
            System.out.println("----------------");
            System.out.println(b);
        }
    }


    public void showDayEarnings(int day) {

        double earnings = 0;

        for (Bill b : bills) {
            if (b.getDay() == day) {
                earnings += b.getTotalCost();
            }
        }

        System.out.println("Earnings of day "+ day+ ": $"+ earnings);
    }

}

// Main class
public class SupermarketManager {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Supermarket supermarket = new Supermarket();

        int option;

        do {
            System.out.println("\n===== SUPERMARKET MENU =====");
            System.out.println("1. Show foods");
            System.out.println("2. Update food");
            System.out.println("3. Place order");
            System.out.println("4. Show supermarket money");
            System.out.println("5. Hire employee");
            System.out.println("6. Remove employee");
            System.out.println("7. Raise employee salary");
            System.out.println("8. Show employees");
            System.out.println("9. Create bill");
            System.out.println("10. Show bills");
            System.out.println("11. Show earnings of a day");
            System.out.println("0. Exit");

            option = sc.nextInt();

            switch (option) {
                case 1:
                    supermarket.displayFoods();
                    break;

                case 2:
                    supermarket.displayFoods();

                    System.out.print("Food index: ");
                    int index = sc.nextInt();

                    System.out.print("New price: ");
                    double price = sc.nextDouble();

                    supermarket.updateFood(index, price);
                    break;

                case 3:
                    supermarket.displayFoods();

                    System.out.print("Food index: ");
                    int foodIndex = sc.nextInt();

                    System.out.print("Amount: ");
                    int amount = sc.nextInt();

                    supermarket.orderFood(foodIndex, amount);
                    break;

                case 4:
                    supermarket.showMoney();
                    break;

                case 5:
                    sc.nextLine();

                    System.out.print("Employee name: ");
                    String name = sc.nextLine();

                    System.out.println("1.CASHIER");
                    System.out.println("2.REPLENISHER");
                    System.out.println("3.MANAGER");
                    System.out.println("4.CO_OWNER");

                    int cat = sc.nextInt();

                    System.out.print("Salary: ");
                    double salary = sc.nextDouble();

                    EmployeeCategory category =
                            EmployeeCategory.values()[cat - 1];

                    supermarket.hireEmployee(
                            new Employee(name, category, salary));
                    break;

                case 6:
                    sc.nextLine();

                    System.out.print("Employee name: ");
                    String removeName = sc.nextLine();

                    supermarket.removeEmployee(removeName);
                    break;

                case 7:
                    sc.nextLine();

                    System.out.print("Employee name: ");
                    String raiseName = sc.nextLine();

                    System.out.print("Raise amount: ");
                    double raise = sc.nextDouble();

                    supermarket.raiseEmployee(raiseName, raise);
                    break;

                case 8:
                    supermarket.displayEmployees();
                    break;
                case 9:

                    sc.nextLine();

                    System.out.print("Customer name: ");
                    String customer = sc.nextLine();

                    System.out.print(
                            "Products bought (Bread, Rice, Milk...): ");
                    String products = sc.nextLine();

                    System.out.print("Day: ");
                    int day = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Cashier name: ");
                    String cashier = sc.nextLine();

                    supermarket.createBill(
                            customer,
                            products,
                            day,
                            cashier
                    );

                    break;
                case 10:
                    supermarket.displayBills();
                    break;
                case 11:

                    System.out.print("Day: ");
                    int d = sc.nextInt();

                    supermarket.showDayEarnings(d);

                    break;
                case 0:
                    System.out.println("Program closed.");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        } while (option != 0);

        sc.close();
    }
}
