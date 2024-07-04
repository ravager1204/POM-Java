
package javaassignment;
import javaassignment.Users.Administrator;
import javaassignment.Users.PurchaseManager;
import javaassignment.Users.SalesManager;
import javaassignment.Users.Users;

import java.util.Scanner;

public class JavaAssignment {

    public static void main(String[] args) {
        while (true) {
            String LoginCondition = "";
            Scanner sc = new Scanner(System.in);
            Users UserLogin = new Users();

            System.out.println("User Login");
            System.out.println("-----------");

            System.out.println("Enter your user ID:\n");
            String UserID = sc.nextLine();
            if (UserID.equals("-1")) {
                LoginCondition = "-1";
            }
            UserLogin.setUserID(UserID);

            System.out.println("Enter your Password:\n");
            String UserPassword = sc.nextLine();
            if (UserPassword.equals("-1")) {
                LoginCondition = "-1";
            }
            UserLogin.setUserPassword(UserPassword);

            LoginCondition = UserLogin.UserLogin();


            if (!(LoginCondition == null))  {
                if (LoginCondition.equals("Sales Manager")) {

                    SalesManager SalesManager = new SalesManager();
                    SalesManager.SetUserID(UserLogin.getUserID());
                    SalesManager.SetUserName(UserLogin.getUserName());

                    while (true) {

                        String UserChoice = SalesManager.UserChoice().ExecutingUserChoice();

                        if (UserChoice.equals("-1")) {
                            break;
                        }

                        System.out.println("__________________________________________");
                        System.out.println();
                        System.out.println();
                    }

                }
                else if (LoginCondition.equals("Purchase Manager")) {

                    PurchaseManager PM = new PurchaseManager();
                    PM.SetUserID(UserLogin.getUserID());
                    PM.SetUserName(UserLogin.getUserName());

                    while (true) {

                        String UserChoice = PM.UserChoice().ExecutingUserChoice();

                        if (UserChoice.equals("-1")) {
                            break;
                        }

                        System.out.println("__________________________________________");
                        System.out.println();
                        System.out.println();
                    }

                }
                else if (LoginCondition.equals("Administrator")){

                    Administrator administrator=new Administrator();
                    administrator.setUserID(UserLogin.getUserID());
                    administrator.SetUserName(UserLogin.getUserName());

                    while (true) {

                        String UserChoice = administrator.UserChoice().ExecutingUserChoice();

                        if (UserChoice.equals("-1")) {
                            break;
                        }

                        System.out.println("__________________________________________");
                        System.out.println();
                        System.out.println();
                    }

                }
            } else {
                System.out.println("Would you like to try again?\n" +
                        "1- Yes\n" +
                        "2- No");
                String UserChoice = sc.nextLine();

                if (!UserChoice.equals("1")) {
                    break;
                }
            }
        }
    }
    
}
