package javaassignment.Entry;


import java.io.*;
import java.util.*;


public class PurchaseOrder extends Entry {
    private String UserID;

    public void SetUserID(String UserID) {

        this.UserID = UserID;
    }

    public PurchaseOrder() {}
    public void UpdatePRStatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the PR number to update status: ");
        String prNumberToSearch = scanner.nextLine();

        String newStatus;
        boolean validInput= false;

        while(!validInput){
            System.out.print("Enter the new status (APPROVED or REJECTED): ");
            newStatus = scanner.nextLine().toUpperCase();

            if ("APPROVED".equals(newStatus)||"REJECTED".equals(newStatus)){
                validInput = true;

                try (BufferedReader prReader = new BufferedReader(new FileReader("PurchaseRequisition.txt"));
                     BufferedWriter prWriter = new BufferedWriter(new FileWriter("PR_temp.txt"))) {

                    String line;
                    boolean found = false;

                    while ((line = prReader.readLine()) != null) {
                        // Split the line into fields using comma as the delimiter
                        String[] fields = line.split(",");

                        // Ensure that there are at least 1 field in each line and check the PR number
                        if (fields.length >= 1 && fields[0].trim().equals(prNumberToSearch)) {

                            line = line.substring(0, line.lastIndexOf(",") + 1) + " " +newStatus; // Update status
                            found = true;
                        }

                        prWriter.write(line);
                        prWriter.newLine();
                    }

                    if (found) {
                        System.out.println("\nUpdated PR status.");
                    } else {
                        System.out.println("\nPR not found in PurchaseRequisition.txt.");
                    }
                } catch (IOException e) {
                    System.err.println("Error updating PR status: " + e.getMessage());
                }

                // Rename the temporary file to replace the original "PurchaseRequisition.txt" file
                File prFile = new File("PurchaseRequisition.txt");
                File tempFile = new File("PR_temp.txt");
                prFile.delete();

                if (tempFile.renameTo(prFile)) {
                    System.out.print("");
                } else {
                    System.err.println("Error renaming temporary file.");
                }

            }

        }

    }
    private int getNextAvailablePoNumber() {
        int highestPoNumber = 0;

        try (BufferedReader existingPoReader = new BufferedReader(new FileReader("PurchaseOrder.txt"))) {
            String existingPoLine;
            while ((existingPoLine = existingPoReader.readLine()) != null) {
                String[] fields = existingPoLine.split(",");
                if (fields.length >= 1) {
                    String poId = fields[0].trim();
                    if (poId.startsWith("PO")) {
                        int poNumber = Integer.parseInt(poId.substring(2)); // Extract the numeric part of PO ID
                        if (poNumber > highestPoNumber) {
                            highestPoNumber = poNumber;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading existing Purchase Orders: " + e.getMessage());
        }

        // Increment the highest PO number to assign the next available PO number
        return highestPoNumber + 1;
    }

    @Override
    public void Add(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the PR number to search for: ");
        String prNumberToSearch = scanner.nextLine();

        System.out.print("Enter the PO ID (or leave blank to auto-generate): ");
        String poIdInput = scanner.nextLine();
        String poId;

        if (poIdInput.isEmpty()) {
            // Auto-generate the PO ID
            poId = "PO" + getNextAvailablePoNumber();
        } else {
            poId = poIdInput;
        }

        try (BufferedReader prReader = new BufferedReader(new FileReader("PurchaseRequisition.txt"));
             BufferedWriter poWriter = new BufferedWriter(new FileWriter("PurchaseOrder.txt", true))) {

            String line;
            boolean found = false;

            while ((line = prReader.readLine()) != null) {
                // Split the line into fields using comma as the delimiter
                String[] fields = line.split("\\s*,\\s*");

                // Ensure that there are at least 6 fields in each line and check the PR number
                if (fields.length >= 1 && fields[0].trim().equals(prNumberToSearch)) {
                    // Create a new line for the purchase order based on the PR line
                    String poLine = poId + " , " + line;

                    // Write the new line to the "PurchaseOrder.txt" file
                    poWriter.write(poLine);
                    poWriter.newLine();

                    found = true;
                    break; // Exit the loop once the PR is found and added to PO
                }
            }

            if (found) {
                System.out.println("\nAdded PR to PO with PO number.");
            } else {
                System.out.println("\nPR not found in PurchaseRequisition.txt.");
            }
        } catch (IOException e) {
            System.err.println("Error adding requisition to Purchase Order: " + e.getMessage());
        }
    }


    @Override
    public void Delete(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Purchase Order Number to delete: ");
        String purchaseOrderNumberToDelete = scanner.nextLine();

        try (BufferedReader poReader = new BufferedReader(new FileReader("PurchaseOrder.txt"));
             BufferedWriter poWriter = new BufferedWriter(new FileWriter("PO_temp.txt"))) {
            String line;
            while ((line = poReader.readLine()) != null) {
                // Check if the line contains the purchase order number to delete
                if (!line.contains(purchaseOrderNumberToDelete)) {
                    poWriter.write(line);
                    poWriter.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error deleting lines from Purchase Order: " + e.getMessage());
        }

        // Rename the temporary file to replace the original "PurchaseOrder.txt" file
        File poFile = new File("PurchaseOrder.txt");
        File poTempFile = new File("PO_temp.txt");
        poFile.delete();
        if (poTempFile.renameTo(poFile)) {
            System.out.println("Deleted lines with Purchase Order Number: " + purchaseOrderNumberToDelete);
        } else {
            System.err.println("Error renaming temporary file.");
        }
    }

    @Override
    public void Modify(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Purchase Order Number to edit: ");
        String purchaseOrderNumberToEdit = scanner.nextLine();

        /*System.out.print("Enter the Supplier ID associated with the Purchase Order: ");
        String supplierIdToEdit = scanner.nextLine();*/

        String newItemId = null;
        String newSupplierId = null;
        String newQuantity = null;
        String newRequiredDate = null;
        String newstatus = null;

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

        try (BufferedReader poReader = new BufferedReader(new FileReader("PurchaseOrder.txt"));
             BufferedWriter poWriter = new BufferedWriter(new FileWriter("PO_temp.txt"))) {
            String line;
            while ((line = poReader.readLine()) != null) {
                //Check if the line contains the purchase order number to edit
                String[] columns = line.split("\\s*,\\s*");

                if (line.contains(purchaseOrderNumberToEdit)&& columns.length > 2) {

                    if (newSupplierId != null) {
                        columns[3] = newSupplierId;
                    }
                    if (newItemId != null) {
                        columns[4] = newItemId;
                    }
                    if (newQuantity != null) {
                        columns[5] = newQuantity;
                    }
                    if (newRequiredDate != null) {
                        columns[6] = newRequiredDate;
                    }
                    if (newstatus != null) {
                        columns[7] = newstatus;
                    }
                    String updatedLine = String.join(" , ", columns);
                    poWriter.write(updatedLine);
                    poWriter.newLine();
                } else {

                    // Write unchanged lines to the temporary file
                    poWriter.write(line);
                    poWriter.newLine();
                }
            }
            System.out.println("\nEdited Purchase Order with Number: " + purchaseOrderNumberToEdit);

        } catch (IOException e) {
            System.err.println("Error editing Purchase Order: " + e.getMessage());
        }

        File poTempFile = new File("PO_temp.txt");
        File poFile = new File("PurchaseOrder.txt");
        poFile.delete();

        if (poTempFile.renameTo(poFile)) {
            System.out.println("Purchase Order file updated.");
        } else {
            System.out.println("Failed to rename file.");
        }
    }

    public void viewPurchaseOrders() {
        System.out.println("\nList of Purchase Orders:");
        // Define the file name
        String fileName = "PurchaseOrder.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            // Read and display each line from the file
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading the requisition file: " + e.getMessage());
        }
    }

    public void savePurchaseOrdersToFile() {
        try (BufferedReader poReader = new BufferedReader(new FileReader("PurchaseOrder.txt"));
             BufferedWriter poWriter = new BufferedWriter(new FileWriter("PO_temp.txt"))) {
            String line;
            while ((line = poReader.readLine()) != null) {
                poWriter.write(line);
                poWriter.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving Purchase Order: " + e.getMessage());
        }
        File poFile = new File("PurchaseOrder.txt");
        File poTempFile = new File("PO_temp.txt");
        poFile.delete();
        if (poTempFile.renameTo(poFile)) {
            System.out.println("\nPurchase Order saved to PurchaseOrder.txt.");
        } else {
            System.err.println("Error renaming temporary file.");
        }
    }
}
