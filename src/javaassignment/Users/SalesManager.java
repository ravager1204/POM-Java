
package javaassignment.Users;

import javaassignment.Entry.DailyWiseSellingEntry;
import javaassignment.Entry.ItemsEntry;
import javaassignment.Entry.PurchaseRequisition;
import javaassignment.Entry.SupplierEntry;

//hihihihih
public class SalesManager extends Users {

    private String UserChoice;



    public String toString() {
        return "Welcome " + UserName + " What do you want today as a Sales Manager.\nYour user id is: " + UserID;
    }

    public void SetUserID(String UserID) {this.UserID = UserID;}

    public void SetUserName(String UserName) {this.UserName = UserName;}

    public SalesManager UserChoice() {

        System.out.println("\n\nWelcome "+ UserName +", what do you want to do as a sales manager?\nYour user ID is : " + UserID + "\n"
                + "1- Items Entries\n"
                + "2- Suppliers Entries\n"
                + "3- Daily Item-wise Sales Entries\n"
                + "4- Purchase Requisitions\n"
                + "5- Display Purchase Orders \n"
                + "[-1] - To Exit\n"
                + "Type the number of your choice\n");

        UserChoice = sc.nextLine();

        return this;
    }

    public String ExecutingUserChoice() {
        if (UserChoice.equals("1")) {
            ItemsHandling(new ItemsEntry("Items"));
        }

        // Supplier Entry
        else if (UserChoice.equals("2")) {
            SuppliersHandling();
        }

        // Items modifying process
        else if(UserChoice.equals("3")) {
            DailySalesHandling();
        }

        // Purchase Requisition
        else if (UserChoice.equals("4")) {
            PurchaseRequisitionHandling();
        }

        // display PO
        else if (UserChoice.equals("5")) {
            new PurchaseRequisition().displayFileContents("PurchaseOrder");
            System.out.println("Type something to go back to the previous page");
            sc.nextLine();
        }

        else if (UserChoice.equals("-1")) {
            return "-1";
        }
        // To Exit
        else {
            System.out.println("Invalid choice, please try again.");
        }
        return "";
    }

    public void ItemsHandling(ItemsEntry ItemsEntry) {

        while (true) {
            System.out.println("What type of item entry would you like to do?\n"
                    + "1- Display all items\n"
                    + "2- Add item\n"
                    + "3- Delete item\n"
                    + "4- Modify item\n"
                    + "[ -1 ] To Exit");

            String userChoice = sc.nextLine();

            switch (userChoice) {
                case "1":
                    ItemsEntry.displayFileContents("Items");
                    System.out.println("Type anything to go back to the previous page:");
                    sc.nextLine();
                    break;

                case "2":
                    while (true) {
                        ItemsEntry.Add();

                        System.out.println();
                        System.out.println();
                        System.out.println("THE PROCESS OF ADDING NEW ITEM HAS BEEN DONE SUCESSFULLY!\n" +
                                "Would you like to add another item?\n"
                                + "1- Yes\n"
                                + "2- No\n"
                                + "Type the number of your choice");
                        userChoice = sc.nextLine();
                        if (!userChoice.equals("1")) {
                            break;
                        }
                    }
                    break;

                case "3":
                    ItemsEntry DeleteItem = new ItemsEntry("Items");

                    while (true) {
                        DeleteItem.displayFileContents("Items");

                        String deletedItemLine = ItemsEntry.getLine();

                        if (!deletedItemLine.equals("-1")) {
                            DeleteItem.setLineNumber(Integer.parseInt(deletedItemLine));
                            DeleteItem.Delete();
                        }

                        System.out.println("Would you like to delete another item?\n"
                                + "1- Yes\n"
                                + "2- No\n"
                                + "Write the number of the choice only\n");
                        userChoice = sc.nextLine();
                        if (!userChoice.equals("1")) {
                            break;
                        }
                    }
                    break;
                case "4":
                    while (true) {
                        ItemsEntry.displayFileContents("Items");
                        String modifiedLine = ItemsEntry.getLine();
                        if (!modifiedLine.equals("-1")) {

                            ItemsEntry.setLineNumber(Integer.parseInt(modifiedLine));
                            ItemsEntry.Modify();

                            System.out.println("Process has been done successfully!\n"
                                    + "Would you like to modify another item?\n"
                                    + "1- Yes\n"
                                    + "2- No\n");

                            if (!sc.nextLine().equals("1")) {
                                break;
                            }
                        }
                    }
                    break;

                case "-1":
                    return; // Exit the loop and method

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    public void SuppliersHandling() {
        SupplierEntry SupplierEntry = new SupplierEntry();

        while (true) {
            System.out.println("What type of suppliers entry would you like to do?\n"
                    + "1- Display all suppliers\n"
                    + "2- Display suppliers with their supplies\n"
                    + "3- Add supplier\n"
                    + "4- Delete supplier\n"
                    + "5- Modify supplier information\n" +
                    "[ -1 ] To Exit");

            String userChoice = sc.nextLine();

            switch (userChoice) {
                case "1":
                    SupplierEntry.displayFileContents("Suppliers");
                    System.out.println("Type something to go back to the previous page:\n");
                    sc.nextLine();
                    break;
                case "2":
                    SupplierEntry.displaySupplierToSupplies();
                    System.out.println("\n\nType something to go back to the previous page");
                    sc.nextLine();
                    break;
                case "3":
                    while (true) {
                        SupplierEntry.displayFileContents("Suppliers");

                        SupplierEntry supplierEntry = new SupplierEntry("Supplier");
                        supplierEntry.Add();

                        System.out.println();
                        System.out.println();
                        System.out.println("Would you like to add another supplier?\n"
                                + "1- Yes\n"
                                + "2- No\n"
                                + "Type the number of your choice");

                        if (!sc.nextLine().equals("1")) {
                            break;
                        }
                    }
                    break;

                case "4":
                    while (true) {
                        SupplierEntry.displayFileContents("Suppliers");

                        SupplierEntry DeleteSupplier = new SupplierEntry("Suppliers");

                        String deletedSupplierLine = DeleteSupplier.getLine();

                        DeleteSupplier.setLineNumber(Integer.parseInt(deletedSupplierLine));
                        DeleteSupplier.Delete();

                        System.out.println("Would you like to delete another supplier?\n" +
                                "1- Yes\n" +
                                "2- No");

                        if (!sc.nextLine().equals("1")) {
                            break;
                        }
                    }
                    break;

                case "5":
                    while (true) {
                        SupplierEntry.displayFileContents("Suppliers");

                        String modifiedLine = SupplierEntry.getLine();

                        if (!modifiedLine.equals("-1")) {


                            SupplierEntry.setLineNumber(Integer.parseInt(modifiedLine));
                            SupplierEntry.Modify();

                            System.out.println("Would you like to modify another supplier?\n"
                                    + "1- Yes\n"
                                    + "2- No\n"
                                    + "Write the number of the choice only\n");

                            if (!sc.nextLine().equals("1")) {
                                break;
                            }
                        }
                    }
                    break;

                case "-1":
                    return; // Exit the loop and method

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    public void DailySalesHandling() {
        DailyWiseSellingEntry DailySelling = new DailyWiseSellingEntry();

        while (true) {
            System.out.println("What type of daily handling you would like to do?\n"
                    + "1- Display all Selling\n"
                    + "2- Add selling\n"
                    + "3- Delete selling\n"
                    + "4- Modify selling\n" +
                    "[ -1 ] To Exit");

            String userChoice = sc.nextLine();

            switch (userChoice) {
                case "1":
                    DailySelling.displayFileContents("DailyWiseSales");
                    System.out.println("Type something to go back to the previous page:\n");
                    sc.nextLine();
                    break;
                case "2":
                    while (true) {

                        System.out.println("_______________________________________ Available Items _______________________________________");
                        System.out.println();
                        DailySelling.displayFileContents("Items");
                        System.out.println();
                        System.out.println();
                        System.out.println("_______________________________________ Item Wise Selling _____________________________________");
                        System.out.println();
                        DailySelling.displayFileContents("DailyWiseSales");

                        System.out.println("Above are the items and the daily wise selling lists, you can choose the product was just sold");

                        String DailyWiseSellingLine = DailySelling.getLine();

                        if (!DailyWiseSellingLine.equals("-1")) {
                            DailySelling.setLineNumber(Integer.parseInt(DailyWiseSellingLine));
                            DailySelling.Add();
                        }

                        System.out.println();
                        System.out.println();
                        System.out.println("Would you like to add more?\n"
                                + "1- Yes\n"
                                + "2- No\n"
                                + "Type the number of your choice");
                        if (!sc.nextLine().equals("1")) {
                            break;
                        }
                    }
                    break;

                case "3":
                    while (true) {
                        DailySelling.displayFileContents("DailyWiseSales");

                        DailySelling.setLineNumber(Integer.parseInt(DailySelling.getLine()));
                        DailySelling.Delete();
                        System.out.println("The process has been done successfully\n" +
                                "Would you like to delete another Daily item-wise sale?\n" +
                                "1- Yes\n" +
                                "2- No");

                        if (!sc.nextLine().equals("1")) {
                            break;
                        }
                    }
                    break;

                case "4":
                    while (true) {
                        DailySelling.displayFileContents("DailyWiseSales");

                        String modifiedLine = DailySelling.getLine();

                        if (!modifiedLine.equals("-1")) {

                            DailySelling.setLineNumber(Integer.parseInt(modifiedLine));
                            DailySelling.Modify();

                            System.out.println("Would you like to modify another one?\n"
                                    + "1- Yes\n"
                                    + "2- No\n"
                                    + "Write the number of the choice only\n");

                            if (!sc.nextLine().equals("1")) {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                    break;

                case "-1":
                    return; // Exit the loop and method

                default:
                    System.out.println("Invalid choice, please try again.");

            }
        }
    }

    public void PurchaseRequisitionHandling() {

        PurchaseRequisition PurchaseRequisition = new PurchaseRequisition();
        PurchaseRequisition.SetUserID(UserID);

        while (true) {
            System.out.println("What type of item entry would you like to do?\n"
                    + "1- Display all PR\n"
                    + "2- Add purchase requisitions\n"
                    + "3- Delete purchase requisitions\n"
                    + "4- Modify purchase requisitions\n"
                    + "[ -1 ] To Exit");

            UserChoice = sc.nextLine();

            switch (UserChoice){
                case "1":
                    PurchaseRequisition.displayFileContents("PurchaseRequisition");
                    System.out.println("\nType something to go back to the previous page");
                    sc.nextLine();
                    break;


                case "2":
                    while (true) {
                        PurchaseRequisition.Add();

                        System.out.println("Would you like to add another purchase requisition?\n" +
                                "1- Yes\n" +
                                "2- No");
                        UserChoice = sc.nextLine();

                        if(!UserChoice.equals("1")) {
                            break;
                        }
                    }
                    break;

                case "3":
                    while (true) {
                        PurchaseRequisition.Delete();

                        System.out.println("Would you like to delete another purchase requisition?\n" +
                                "1- Yes\n" +
                                "2- No");
                        UserChoice = sc.nextLine();

                        if(!UserChoice.equals("1")) {
                            break;
                        }
                    }
                    break;

                case "4":
                    while (true) {
                        PurchaseRequisition.Modify();

                        System.out.println("Would you like to modify another purchase requisition?\n" +
                                "1- Yes\n" +
                                "2- No");
                        UserChoice = sc.nextLine();

                        if(!UserChoice.equals("1")) {
                            break;
                        }
                    }
                    break;

                case "-1":
                    return;

                    default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
