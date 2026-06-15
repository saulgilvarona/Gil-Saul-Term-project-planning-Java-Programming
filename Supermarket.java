import java.util.ArrayList;
import java.util.Scanner;

enum EmployeeCategory {
    CASHIER,
    REPLENISHER,
    MANAGER,
    CO_OWNER
}

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

    @Override
    public String toString() {
        return name +
                " | Quantity: " + quantity +
                " | Price: $" + price;
    }
}

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

class Supermarket {
    private ArrayList<Food> foods;
    private ArrayList<Employee> employees;
    private double money;

    public Supermarket() {
        foods = new ArrayList<>();
        employees = new ArrayList<>();
        money = 0;

        foods.add(new Food("Bread", 50, 1.50));
        foods.add(new Food("Milk", 40, 2.20));
        foods.add(new Food("Eggs", 60, 3.00));
        foods.add(new Food("Rice", 30, 4.50));
    }

    public void displayFoods() {
        for (int i = 0; i < foods.size(); i++) {
            System.out.println(i + ". " + foods.get(i));
        }
    }

    public void updateFood(int index, int quantity, double price) {
        if (index >= 0 && index < foods.size()) {
            foods.get(index).setQuantity(quantity);
            foods.get(index).setPrice(price);
        }
    }

    public void orderFood(int index, int amount) {
        if (index < 0 || index >= foods.size()) {
            System.out.println("Invalid food.");
            return;
        }

        Food food = foods.get(index);

        if (amount > food.getQuantity()) {
            System.out.println("Not enough stock.");
            return;
        }

        food.reduceQuantity(amount);
        double total = amount * food.getPrice();
        money += total;

        System.out.println("Order completed.");
        System.out.println("Total: $" + total);
    }

    public void showMoney() {
        System.out.println("Supermarket money: $" + money);
    }

    public void hireEmployee(Employee employee) {
        employees.add(employee);
        System.out.println(employee.getName() + " hired.");
    }

    public void removeEmployee(String name) {
        employees.removeIf(e -> e.getName().equalsIgnoreCase(name));
    }

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

    public void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees.");
            return;
        }

        for (Employee e : employees) {
            System.out.println(e);
        }
    }
}

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

                    System.out.print("New quantity: ");
                    int quantity = sc.nextInt();

                    System.out.print("New price: ");
                    double price = sc.nextDouble();

                    supermarket.updateFood(index, quantity, price);
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
