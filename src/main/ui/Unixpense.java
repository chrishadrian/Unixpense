package ui;

import model.Expense;
import model.Expenses;

import java.time.LocalDate;
import java.util.Scanner;

public class Unixpense {
    private Expenses exp;
    private Scanner input;

    // EFFECTS: runs the Unixpense application
    public Unixpense() {
        runUnixpense();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runUnixpense() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("v")) {
            doView();
        } else if (command.equals("t")) {
            doTrack();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        exp = new Expenses();
        exp.addExpense(new Expense(LocalDate.now(), "Groceries", 32, ""));
        exp.addExpense(new Expense(LocalDate.now(), "Personal", 32, ""));
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tv -> to view your expenses");
        System.out.println("\tt -> to track your expenses");
        System.out.println("\tq -> to quit");
    }

    // MODIFIES: this
    // EFFECTS: conducts a deposit transaction
    private void doView() {
        String space = "        ";
        System.out.println("Date" + space + space + "Category" + space + "Amount" + space + "Comment");
        int sum = 0;
        for (int i = 0; i < exp.length(); i++) {
            Expense ex = exp.getExpense(i);
            sum = sum + ex.getAmount();
            System.out.println(ex.getDate() + space + ex.getCategory() + space
                    + ex.getAmount() + space + ex.getComment());
        }
        System.out.println("Sum of the month:" + space + sum);
    }

    private void doTrack() {
        String command;
        LocalDate date;
        System.out.print("Do you want to use current date? (Y/N)");

        command = input.next();
        command = command.toLowerCase();

        if (command.equals("y")) {
            date = LocalDate.now();
        } else {
            System.out.println("Input your desired date:");
            System.out.println("YYYY/MM/DD: ");
            String newDate = input.next();
            int year = Integer.parseInt(newDate.substring(0,4));
            int mon = Integer.parseInt(newDate.substring(5,7));
            int day = Integer.parseInt(newDate.substring(8,10));
            date = LocalDate.of(year, mon, day);
        }

        System.out.println("Input the expense's category:");
        String category = input.next();
        System.out.println("Input the expense's amount:");
        int amount = Integer.parseInt(input.next());
        System.out.println("Input the expense's comment:");
        String comment = input.next();
        exp.addExpense(new Expense(date, category, amount, comment));
    }



    //use get expense method and print it in ui
//        // if (category.length < groceries.length) --> add space
//    }
}
