package javaassignment.Entry;

import javaassignment.IdGeneration;


import java.io.*;
import java.util.*;


public class PurchaseRequisition extends Entry {
    private String UserID;
    private String ItemID;
    private String SupplierID;
    private String LineNumber;
    private String quantityRequested;
    private int approvedInt;


    Scanner sc = new Scanner(System.in);
    public void SetUserID(String UserID) {
        this.UserID = UserID;
    }
    public PurchaseRequisition(){}

    @Override
    public void Add() {
        displayFileContents("Items");

        System.out.println("Above are the items with stock below 10" +
                "\nSelect an item by entering the row number: ");
        LineNumber = getLine();

        if (Integer.parseInt(LineNumber) < 1 && LineNumber.equals("-1")) {
            System.out.println("Invalid row number selected.");
            return;
        }

        String[] selectedRow = getRowData("Items.txt", Integer.parseInt(LineNumber) - 1); // Get the selected row data
        ItemID = selectedRow[0]; // Item ID
        SupplierID = selectedRow[6]; // Supplier ID

        // Input quantity
        System.out.println("Enter the quantity requested:\n[ -1 ] To Exit");
        quantityRequested = sc.nextLine();
        if (quantityRequested.equals("-1")) {
            System.out.println("YOU CHOSE TO EXIT");
            return;
        } else {
            while (true) {
                approvedInt = Validity.IntChecker(quantityRequested);

                if (approvedInt == 0) {
                    quantityRequested = sc.next();

                    if (quantityRequested.equals("-1")) {
                        System.out.println("YOU CHOSE TO EXIT");
                        return;
                    }
                } else {
                    int approvedMinimum = approvedInt;
                    quantityRequested = String.valueOf(approvedMinimum);
                    break;
                }
            }
        }

        // Input date components separately
        String[] dateComponents = {"day", "month", "year"};
        int[] dateValues = new int[3];

        for (int i = 0; i < dateComponents.length; i++) {
            System.out.println("Enter the needed " + dateComponents[i] + ":\n[ -1 ] To Exit");
            String inputDateValue = sc.nextLine();

            if (inputDateValue.equals("-1")) {
                System.out.println("YOU CHOSE TO EXIT");
                return;
            } else {
                while (true) {
                    approvedInt = Validity.IntChecker(inputDateValue);

                    if (approvedInt == 0) {
                        inputDateValue = sc.next();

                        if (inputDateValue.equals("-1")) {
                            System.out.println("YOU CHOSE TO EXIT");
                            return;
                        }
                    } else {
                        int approvedMinimum = approvedInt;
                        dateValues[i] = approvedMinimum;
                        break;
                    }
                }
            }
        }

        // Construct the date string in dd/MM/yyyy format
        String requisitionDate = String.format("%02d/%02d/%04d", dateValues[0], dateValues[1], dateValues[2]);

        // Generate Purchase Requisition ID
        IdGeneration generateID = new IdGeneration("PurchaseRequisition");
        String purchaseRequisitionID = generateID.GenerationProcess();

        // Format the line to be added to the file
        String newPurchaseRequisition = purchaseRequisitionID + " , "
                + UserID + " , "
                + SupplierID + " , "
                + ItemID + " , "
                + quantityRequested + " , "
                + requisitionDate + " , "
                + "Pending";

        // Write the new Purchase Requisition to a temporary file
        try {
            File tempFile = File.createTempFile("tempPR", ".txt");

            // Move the content from the original PurchaseRequisition.txt file to the temporary file
            File oldFile = new File("PurchaseRequisition.txt");
            if (oldFile.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(oldFile));
                     BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                    String line;

                    // Copy the existing content to the temporary file
                    while ((line = reader.readLine()) != null) {
                        writer.write(line + System.lineSeparator());
                    }

                    // Add the new Purchase Requisition
                    writer.write(newPurchaseRequisition + System.lineSeparator());
                }

                // Delete the original PurchaseRequisition.txt file
                if (oldFile.delete()) {
                    // Rename the temporary file to PurchaseRequisition.txt
                    if (tempFile.renameTo(new File("PurchaseRequisition.txt"))) {
                        System.out.println("Purchase Requisition updated successfully.");
                    } else {
                        System.out.println("Error: Unable to rename the temporary file.");
                    }
                } else {
                    System.out.println("Error: Unable to delete the original PurchaseRequisition.txt file.");
                }
            } else {
                System.out.println("Error: The original PurchaseRequisition.txt file does not exist.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Purchase Requisition Number to delete: ");
        String PRNumberToDelete = scanner.nextLine();

        try (BufferedReader prReader = new BufferedReader(new FileReader("PurchaseRequisition.txt"));
             BufferedWriter prWriter = new BufferedWriter(new FileWriter("PR_temp.txt"))) {
            String line;
            while ((line = prReader.readLine()) != null) {
                // Check if the line contains the purchase requisition number to delete
                if (!line.contains(PRNumberToDelete)) {
                    prWriter.write(line);
                    prWriter.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error deleting lines from Purchase Requisition: " + e.getMessage());
        }

        // Rename the temporary file to replace the original "PurchaseRequisition.txt" file
        File prFile = new File("PurchaseRequisition.txt");
        File prTempFile = new File("PR_temp.txt");
        prFile.delete();
        if (prTempFile.renameTo(prFile)) {
            System.out.println("Deleted lines with Purchase Requisition Number: " + PRNumberToDelete);
        } else {
            System.err.println("Error renaming temporary file.");
        }
    }

    @Override
    public void Modify(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Purchase Requisition Number to edit: ");
        String PRNumberToEdit = scanner.nextLine();



        // Define variables for edited columns
        String newSMId = null;
        String newItemId = null;
        String newSupplierId = null;
        String newQuantity = null;
        String newRequiredDate = null;
        String newstatus = null;

        System.out.print("Edit Sales Manager ID (leave blank to keep current): ");
        String newSMIdInput = scanner.nextLine();
        if (!newSMIdInput.isEmpty()) {
            newSMId = newSMIdInput;
        }

        System.out.print("Edit Supplier ID (leave blank to keep current): ");
        String newSupplierIdInput = scanner.nextLine();
        if (!newSupplierIdInput.isEmpty()) {
            newSupplierId = newSupplierIdInput;
        }

        System.out.print("Edit Item ID (leave blank to keep current): ");
        String newItemIdInput = scanner.nextLine();
        if (!newItemIdInput.isEmpty()) {
            newItemId = newItemIdInput;
        }

        System.out.print("Edit Quantity (leave blank to keep current): ");
        String newQuantityInput = scanner.nextLine();
        if (!newQuantityInput.isEmpty()) {
            newQuantity = newQuantityInput;
        }

        System.out.print("Edit Required Date (dd/MM/yyyy): ");
        String newRequiredDateInput = scanner.nextLine();
        if (!newRequiredDateInput.isEmpty()) {
            newRequiredDate = newRequiredDateInput;
        }

        System.out.print("Edit Status (leave blank to keep current): ");
        String newStatusinput = scanner.nextLine();
        if (!newStatusinput.isEmpty()) {
            newstatus = newStatusinput;
        }

        try (BufferedReader prReader = new BufferedReader(new FileReader("PurchaseRequisition.txt"));
             BufferedWriter prWriter = new BufferedWriter(new FileWriter("PR_temp.txt"))) {
            String line;
            while ((line = prReader.readLine()) != null) {
                // Check if the line contains the purchase requisition number to edit
                String[] columns = line.split(" , ");

                if (line.contains(PRNumberToEdit)&& columns.length > 1) {

                    if (newSMId != null) {
                        columns[1] = newSMId;
                    }

                    if (newSupplierId != null) {
                        columns[2] = newSupplierId;
                    }
                    if (newItemId != null) {
                        columns[3] = newItemId;
                    }
                    if (newQuantity != null) {
                        columns[4] = newQuantity;
                    }
                    if (newRequiredDate != null) {
                        columns[5] = newRequiredDate;
                    }
                    if (newstatus != null) {
                        columns[6] = newstatus;
                    }
                    String updatedLine = String.join(" , ", columns);
                    prWriter.write(updatedLine);
                    prWriter.newLine();
                } else {

                    // Write unchanged lines to the temporary file
                    prWriter.write(line);
                    prWriter.newLine();
                }
            }
            System.out.println("\nEdited Purchase Requisition with Number: " + PRNumberToEdit);

        } catch (IOException e) {
            System.err.println("Error editing Purchase Requisition: " + e.getMessage());
        }

        File prTempFile = new File("PR_temp.txt");
        File prFile = new File("PurchaseRequisition.txt");
        prFile.delete();

        if (prTempFile.renameTo(prFile)) {
            System.out.println("PR file updated.");
        } else {
            System.out.println("Failed to rename file.");
        }
    }


    public String[] getRowData(String fileName, int rowIndex) {
        List<String> lines = new ArrayList<>();

        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            return new String[0]; // Return an empty array if file not found
        }

        if (rowIndex >= 0 && rowIndex < lines.size()) {
            String row = lines.get(rowIndex);
            return row.split(" , ");
        } else {
            System.out.println("Invalid row index.");
            return new String[0]; // Return an empty array for an invalid index
        }
    }
}


