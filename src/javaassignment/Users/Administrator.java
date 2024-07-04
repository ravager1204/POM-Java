
package javaassignment.Users;

import javaassignment.Entry.*;
import javaassignment.Users.SalesManager;
import javaassignment.Users.PurchaseManager;

import java.util.Scanner;

public class Administrator extends Users {
    private String UserChoice;
    private SalesManager salesManager;
    private PurchaseManager purchaseManager;


    public String toString() {
        return "Hello Mr./Mrs. " + UserName + " You are the administrator and you User ID = " + UserID;
    }

    public void SetUserID(String UserID) {

        this.UserID = UserID;
    }

    public void SetUserName(String UserName) {

        this.UserName = UserName;
    }

    public Administrator UserChoice() {

        salesManager=new SalesManager();
        purchaseManager=new PurchaseManager();

        System.out.println("\n\nWelcome "+ UserName +", what do you want to do as an administrator?\nYour user ID is : " + UserID + "\n"
                + "1- Register New User\n"
                + "2- Purchase Manager Menu\n"
                + "3- Sales Manager Menu\n"
                + "[-1] - To Exit\n"
                + "Type the number of your choice\n");

        UserChoice = sc.nextLine();

        return this;
    }

    public String ExecutingUserChoice() {
        if (UserChoice.equals("1")) {

            UserRegistration();

        }

        // Purchase manager menu
        else if (UserChoice.equals("2")) {

            purchaseManager.SetUserID(UserID);
            purchaseManager.SetUserName(UserName);

            while(true){

                String PurchaseManagerChoice = purchaseManager.UserChoice().ExecutingUserChoice();

                if(PurchaseManagerChoice.equals("-1")){
                    break;
                }

                System.out.println("__________________________________________");
                System.out.println();
                System.out.println();

            }

        }

        // Sales manager menu
        else if(UserChoice.equals("3")) {

            salesManager.SetUserID(UserID);
            salesManager.SetUserName(UserName);

            while (true) {
                String SalesManagerChoice = salesManager.UserChoice().ExecutingUserChoice();

                if (SalesManagerChoice.equals("-1")) {
                    break;
                }

                System.out.println("__________________________________________");
                System.out.println();
                System.out.println();
            }
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










    /*public void Admin_Menu(){

        Scanner scanner=new Scanner(System.in);
        boolean exit = false;

        SalesManager sales_Manager = new SalesManager();

        do {

            System.out.println("\nADMINISTRATOR MENU");
            System.out.println("1. Register New User");
            System.out.println("2. Purchase Manager Menu");
            System.out.println("3. Sales Manager Menu");
            System.out.println("4. Exit to Main Menu");
            System.out.println("Please Enter Your Selection: ");
            int UserChoice = scanner.nextInt();

            switch (UserChoice) {
                case 1:
                    UserRegistration();
                    break;

                case 2:
                    //Purchase manager menu here
                    break;

                case 3:
                    sales_Manager.UserChoice();
                    break;

                case 4:
                    exit = true;
                    break;

                default:
                    System.err.println("Please enter a valid choice and try again");
            }
        }while (!exit);

    }*/

}