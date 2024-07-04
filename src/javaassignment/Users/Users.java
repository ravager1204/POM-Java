
package javaassignment.Users;

import javaassignment.IdGeneration;
import javaassignment.ValidityMethods;


import java.io.*;
import java.util.Scanner;

public class Users {
    protected String UserID;
    protected String UserName;
    protected String UserNamenew;
    private String UserNumber;
    private String UserAddress;
    private String UserPassword;
    private String USERS_FILE = "Users.txt";
    private String TEMP_USERS_FILE = "TempUsers.txt";
    protected static final Scanner sc = new Scanner(System.in);




    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public void setUserPassword(String UserPassword) {
        this.UserPassword = UserPassword;
    }

    public String getUserID() {
        return UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public String UserLogin() {

        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(" , ");
                if (userData.length > 1 &&
                        userData[0].equals(UserID) &&
                        userData[1].equals(UserPassword)) {

                    this.UserName = userData[3];
                    System.out.println("Login successful!\n");

                    return userData[2];
                }
            }
            return null; // Returning null indicates login failure
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public String UserRegistration() {
        System.out.println("User Registration");
        System.out.println("------------------");

        System.out.println("Enter the user role:\n" +
                "1- Sales Manager\n" +
                "2- Purchase Manager\n" +
                "3- Administrator\n" +
                "[ -1 ] To Exit");
        String UserRole = sc.next();

        if (UserRole.equals("1")) {
            UserRole =  "Sales Manager";
        } else if (UserRole.equals("2")) {
            UserRole =  "Purchase Manager";
        } else if (UserRole.equals("3")) {
            UserRole =  "Administrator";
        } else {
            UserRole = "-1";
        }
        if (UserRole.equals("-1")) {
            return "-1";
        }

        System.out.println("Enter the Username:\n" +
                "[ -1 ] To Exit");
        UserNamenew = sc.next();
        if (UserNamenew.equals("-1")) {
            return "-1";
        }

        System.out.println("Enter the contact number (Without +60):\n" +
                "[ -1 ] To Exit.");
        String SupplierNum = "";

        while (true) {
            SupplierNum = sc.next();
            if (SupplierNum.equals("-1")) {
                UserNumber = "-1";
                break;
            }
            ValidityMethods isValidPhonNum = new ValidityMethods();

            String SupplierNum2 = isValidPhonNum.isValidPhonNum("+60" + SupplierNum);

            if (SupplierNum2 != null) {
                if (isValidPhonNum.isValidPhonNum(SupplierNum2).equals("+60"+ SupplierNum) ) {
                    UserNumber = SupplierNum;
                    break;
                }
            } else {
                System.out.println("Invalid phone number, enter again (without +60)\n" +
                        "[ -1 ] To Exit");
            }
        }
        if (UserNumber.equals("-1")) {
            return "-1";
        }

        System.out.println("Enter the User address:\n" +
                "[ -1 ] To Exit");
        UserAddress = sc.next();
        if (UserAddress.equals("-1")) {
            return "-1";
        }

        while (true) {
            System.out.println("Enter the user password:\n" +
                    "[ -1 ] To Exit");

            String Password = sc.next();

            if (Password.equals("-1")) {
                UserPassword = "-1";
                break;
            }

            ValidityMethods CheckPassValidity = new ValidityMethods();

            if (CheckPassValidity.isValidPassword(Password)) {
                UserPassword = Password;
                break;
            }
        }
        if (UserPassword.equals("-1")) {
            return "-1";
        }

        IdGeneration GenerateUserID = new IdGeneration("User");

        UserID = GenerateUserID.GenerationProcess();

        String UserFullLine = UserID + " , " + UserPassword + " , " + UserRole + " , " + UserNamenew + " , " + UserNumber + " , " + UserAddress;

        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE, true))) {
            writer.println(UserFullLine); // Append new user data
            System.out.println("User registered successfully!\n" +
                    "The UserID of the new user registered  =  [  " + UserID + "  ]");
            return "Registration successful!";
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return "Registration failed.";
        }
    }


    public String toString() {
        return "Welcome " + UserName;
    }
}
