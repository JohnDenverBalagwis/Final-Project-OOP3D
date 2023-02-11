import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        MenuForLender();
    }

    private static void MenuForLender() {
        CreateDirectory();
        System.out.println("----------------------------------------------------");
        System.out.println("|Main Menu                                         |");
        System.out.println("----------------------------------------------------");
        System.out.println("|                                                  |");
        System.out.println("|  [1]  View List of Borrowers                     |");
        System.out.println("|                                                  |");
        System.out.println("|  [2]  Borrow Money                               |");
        System.out.println("|                                                  |");
        System.out.println("|  [3]  Pay debt                                   |");
        System.out.println("|                                                  |");
        System.out.println("|  [4]  Exit                                       |");
        System.out.println("|                                                  |");
        System.out.println("----------------------------------------------------\n");

        System.out.print("Enter operation: ");
        String operation = scan.nextLine();

        switch (operation) {
            case "1":
                ShowAllListahan();
                break;
            case "2":
                BorrowMoney();
                break;
            case "3":
                PayDebt();
                break;
            case "4":
                Borrower borrower = new Borrower();
                borrower.Message();
                System.exit(0);
                break;
            default:
                System.out.println("Invalid input. You will be redirected to main menu.");
                scan.nextLine();
                MenuForLender();
                break;
        }

    }

    // create directory for listahan file
    private static void CreateDirectory() {
        File directory = new File("Listahan");
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    // get all text file form listahan folder and print its filename
    private static void ShowAllListahan() {
        File listahan = new File("Listahan");
        File[] files = listahan.listFiles(new FilenameFilter() {
            public boolean accept(File listahan, String name) {
                return name.toLowerCase().endsWith(".txt");
            }
        });

        System.out.println("---------------------------------");
        System.out.println("|List of borrowers              |");
        System.out.println("---------------------------------");
        for (File file : files) {
            if (file.isFile()) {
                String fname = file.getName();
                System.out.println(fname.split(".txt")[0]);
                System.out.println("");
            }
        }
        System.out.println("---------------------------------");
        System.out.println("Press 'ENTER' to be redirected in main menu.");
        scan.nextLine();
        MenuForLender();
    }

    private static void BorrowMoney() {
        System.out.println("----------------------------------------------------");
        System.out.println("|Borrow Money                                      |");
        System.out.println("----------------------------------------------------");
        System.out.println("|                                                  |");
        System.out.println("|  [1]  Borrow Minimum Amount (Php 500.00)         |");
        System.out.println("|                                                  |");
        System.out.println("|  [2]  Borrow Specific Amount                     |");
        System.out.println("|                                                  |");
        System.out.println("|  [3]  Back                                       |");
        System.out.println("----------------------------------------------------\n");

        System.out.print("Enter operation: ");
        String operation = scan.nextLine();

        switch (operation) {
            case "1":
                System.out.print("Borrower's Name: ");
                String bName = scan.nextLine();

                Borrower borrower = new Borrower();
                borrower.setName(bName);

                if (createListahan(borrower.getName())) {
                    System.out.println("Created a listahan for " + borrower.getName());
                }

                borrower.Borrow();
                System.out.println("Press 'ENTER' to be redirected in main menu.");
                scan.nextLine();
                MenuForLender();
                break;

            case "2":
                System.out.print("Borrower's Name: ");
                String bName2 = scan.nextLine();

                Borrower borrower2 = new Borrower(bName2);

                if (createListahan(borrower2.getName())) {
                    System.out.println("Created a listahan for " + borrower2.getName());
                }

                System.out.print("Amount to borrow: ");
                int amount = 0;
                try {
                    amount = Integer.parseInt(scan.nextLine());
                    borrower2.Borrow(amount);
                    System.out.println("---------------------------------");
                    System.out.println("Press 'ENTER' to be redirected in main menu.");
                    scan.nextLine();
                    MenuForLender();
                } catch (NumberFormatException nFormatException) {
                    System.out.println("Invalid input. You will be redirected to main menu.");
                    scan.nextLine();
                    MenuForLender();
                }
                break;

            case "3":
                MenuForLender();
                break;

            default:
                System.out.println("Invalid input. You will be redirected to main menu.");
                scan.nextLine();
                MenuForLender();
                break;
        }
    }

    private static void PayDebt() {
        System.out.println("----------------------------------------------------");
        System.out.println("|Pay Debt                                          |");
        System.out.println("----------------------------------------------------");
        System.out.println("|                                                  |");
        System.out.println("|  [1]  Pay Whole Amount                           |");
        System.out.println("|                                                  |");
        System.out.println("|  [2]  Back                                       |");
        System.out.println("----------------------------------------------------\n");

        System.out.print("Enter operation: ");
        String operation = scan.nextLine();

        switch (operation) {
            case "1":
                System.out.print("Borrower's Name: ");
                String bName = scan.nextLine();
                Borrower borrower = new Borrower(bName);
                File listahan = new File("Listahan/" + borrower.getName() + ".txt");
                Double wholeAmount = 0d;
                try {
                    if (listahan.exists()) {
                        System.out.println("------------------------------------");
                        System.out.println("| " + borrower.getName() + "'s Debt");
                        System.out.println("------------------------------------");
                        Scanner reader = new Scanner(listahan);
                        while (reader.hasNextLine()) {
                            String data = reader.nextLine();
                            double amount = Double.parseDouble(data.split(",")[0]);
                            String date = data.split(",")[1];
                            wholeAmount += amount;
                            System.out.println("Php" + amount + "\t\t\t" + date);
                            System.out.println();
                        }
                        System.out.println("------------------------------------");
                        System.out.println("Total Amount to Pay: Php" + wholeAmount);
                        System.out.println("------------------------------------");

                        System.out.print("Amount handed over: ");
                        try {
                            double amountHanded = Double.parseDouble(scan.nextLine());
                            if (amountHanded >= wholeAmount) {
                                reader.close();
                                if (listahan.delete()) {
                                    System.out.println("The listahan for " + borrower.getName()
                                            + " has been cleared and removed.\nThanks for paying.");
                                    System.out.println("Press 'ENTER' to be redirected in main menu.");
                                    scan.nextLine();
                                    MenuForLender();
                                }

                            } else {
                                System.out.println(
                                        "The money that was handed over is not enough.\nNo changes made.\nYou will be redirected to main menu.");
                                scan.nextLine();
                                MenuForLender();
                            }

                        } catch (NumberFormatException nFormatException) {
                            System.out.println("Invalid input. You will be redirected to main menu.");
                            scan.nextLine();
                            MenuForLender();
                        }

                    } else {
                        System.out.println("Borrower doesn't exists.");
                        System.out.println("Press 'ENTER' to be redirected in main menu.");
                        scan.nextLine();
                        MenuForLender();

                    }

                } catch (FileNotFoundException fNotFoundException) {
                }
                break;

            case "2":
                MenuForLender();
                break;

            default:
                System.out.println("Invalid input. You will be redirected to main menu.");
                scan.nextLine();
                MenuForLender();
                break;
        }
    }

    // create a text file if not exists
    private static boolean createListahan(String BorrowerName) {
        boolean isCreated = false;
        try {
            File listahanFile = new File("Listahan/" + BorrowerName + ".txt");
            isCreated = listahanFile.createNewFile();
        } catch (IOException ioException) {
            System.out.println("Error: " + ioException.getMessage());
        }
        return isCreated;
    }
}