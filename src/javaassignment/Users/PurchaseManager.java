
package javaassignment.Users;

import javaassignment.Entry.*;
import javaassignment.ValidityMethods;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class PurchaseManager extends Users {

    private String UserChoice;

    public PurchaseManager(){}

    public String toString() {
        return "Hello Mr./Mrs. " + UserName + " You are the purchase manager";
    }

    public void SetUserID(String UserID) {
        this.UserID = UserID;
    }

    public void SetUserName(String UserName) {
        this.UserName = UserName;
    }

    PurchaseOrder PO = new PurchaseOrder();
    public PurchaseManager UserChoice() {

        System.out.println("\n\nWelcome "+ UserName +", what do you want to do as a Purchase Manager?\nYour user ID is : " + UserID + "\n");
        System.out.println("1. List of Items");
        System.out.println("2. List of Suppliers");
        System.out.println("3. Display Requisition");
        System.out.println("4. Generate Purchase Order");
        System.out.println("[-1.] Exit");
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
            PurchaseRequisitionHandling();
        }

        // Purchase Requisition
        else if (UserChoice.equals("4")) {
            PurchaseOrderHandling();
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
        ItemsEntry.displayFileContents("Items");
    }

    public void SuppliersHandling() {
        SupplierEntry SupplierEntry = new SupplierEntry();
        SupplierEntry.displayFileContents("Suppliers");
    }


    public void PurchaseRequisitionHandling() {

        PurchaseRequisition PurchaseRequisition = new PurchaseRequisition();
        PurchaseRequisition.SetUserID(UserID);

        PurchaseRequisition.displayFileContents("PurchaseRequisition");

    }

    public void PurchaseOrderHandling() {

        PurchaseOrder PO = new PurchaseOrder();

        while (true) {
            System.out.println("Generate Purchase Order Menu\n");
            System.out.println("1. Update Status");
            System.out.println("2. Add Purchase Orders");
            System.out.println("3. Save Purchase Orders");
            System.out.println("4. Delete Purchase Orders");
            System.out.println("5. Modify Purchase Orders");
            System.out.println("6. View All Purchase Orders");
            System.out.println("7. Return to main menu");
            System.out.print("Enter your choice: ");
            UserChoice = sc.nextLine();

            switch (UserChoice){
                case "1":
                    PO.UpdatePRStatus();
                    System.out.println("\nType something to go back to the previous page");
                    sc.nextLine();
                    break;
                case "2":
                    PO.Add();
                    System.out.println("\nType something to go back to the previous page");
                    sc.nextLine();
                    break;
                case "3":
                    PO.savePurchaseOrdersToFile();
                    System.out.println("\nType something to go back to the previous page");
                    sc.nextLine();
                    break;
                case "4":
                    PO.Delete();
                    System.out.println("\nType something to go back to the previous page");
                    sc.nextLine();
                    break;
                case "5":
                    PO.Modify();
                    System.out.println("\nType something to go back to the previous page");
                    sc.nextLine();
                    break;

                case "6":
                    PO.viewPurchaseOrders();
                    System.out.println("\nType something to go back to the previous page");
                    sc.nextLine();
                    break;

                case "7":
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
