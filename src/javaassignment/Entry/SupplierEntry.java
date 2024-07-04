/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaassignment.Entry;


import javaassignment.IdGeneration;
import javaassignment.ValidityMethods;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;


public class SupplierEntry extends Entry {
    private String SupplierName;
    private String SupplierNum;
    private String SupplierAddress;
    private String ItemID;
    private int lineNumber;
    private String Portion;
    Scanner sc = new Scanner(System.in);


    private List<String> ModifiedLine = new ArrayList<>();


    public SupplierEntry (String Portion) {
        this.Portion = Portion;
    }

    public SupplierEntry (String SupplierID, String ItemID) {
        this.ID = SupplierID;
        this.ItemID = ItemID;
    }

    public SupplierEntry() {}

    public String getSuplierID() {
        System.out.println(ID);
        return ID;
    }
    public SupplierEntry(List<String> ModifiedLineContent) {
        this.ModifiedLine = ModifiedLineContent;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public void Add() {

        if (Portion != null) {
            System.out.println("Enter the supplier name:\n" +
                    "[ -1 ] To Exit");
            SupplierName = sc.nextLine();
            if (SupplierName.equals("-1")) {
                System.out.println("YOU CHOSE TO EXIT");
                return;
            }


            // get phone number
            System.out.println("Enter the contact number (Without +60):\n" +
                    "[ -1 ] To Exit.");
            String Num = "";

            while (true) {
                Num = sc.nextLine();
                if (Num.equals("-1")) {
                    Num = "-1";
                    break;
                }
                ValidityMethods isValidPhonNum = new ValidityMethods();

                String SupplierNum2 = isValidPhonNum.isValidPhonNum("+60" + Num);

                if (SupplierNum2 != null) {
                    if (isValidPhonNum.isValidPhonNum(SupplierNum2).equals("+60"+ Num) ) {
                        SupplierNum = Num;
                        break;
                    }
                } else {
                    System.out.println("Invalid phone number, enter again (without +60)\n" +
                            "[ -1 ] To Exit");
                }
            }
            if (SupplierNum.equals("-1")) {
                System.out.println("YOU CHOSE TO EXIT");
                return;
            }

            System.out.println("Enter the supplier address:\n" +
                    "[ -1 ] To Exit");
            SupplierAddress = sc.nextLine();
            if (SupplierAddress.equals("-1")) {
                System.out.println("YOU CHOSE TO EXIT");
                return;
            }

            IdGeneration IdGetter = new IdGeneration("Suppliers");
            ID = IdGetter.GenerationProcess();

            if (ID.equals("-1")) {
                System.out.println("The process of adding a new supplier WAS NOT DONE SUCCESSFULLY!");
                return;
            }

            String SuppliedItem = "";
            if (Portion.equals("Supplier")) {
                SuppliedItem = getSuppliedItem();
                if (SuppliedItem.equals("-1")) {
                    return;
                }
            } else {
                SuppliedItem = Portion;
            }

            Set<String> supplierItemPairs = new HashSet<>();
            String supplierItemPair = ID + " , " + SuppliedItem;

            if (!supplierItemPairs.contains(supplierItemPair)) {
                supplierItemPairs.add(supplierItemPair);

                String Line = ID + " , " + SupplierName + " , " + SupplierNum + " , " + SupplierAddress + " , " + SuppliedItem;

                try {
                    File tempFile = File.createTempFile("tempSuppliers", ".txt");
                    Files.copy(Paths.get("Suppliers.txt"), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    Files.write(tempFile.toPath(), (Line + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
                    Files.move(tempFile.toPath(), Paths.get("Suppliers.txt"), StandardCopyOption.REPLACE_EXISTING);

                    ItemsEntry ItemsEntry = new ItemsEntry(SuppliedItem, ID);
                    ItemsEntry.Add();
                } catch (IOException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            } else {
                System.out.println("Duplicate combination of Supplier ID and Item ID found. Entry not added.");
            }
        }


        else {
            // Set to track supplier ID and item ID pairs
            Set<String> supplierItemPairs = new HashSet<>();

            // Updating the "Items.txt" file
            // -----------------------------------
            try {
                File SuppliersFile = new File("Suppliers.txt");
                File tempFile = File.createTempFile("tempSuppliers", ".txt");


                Scanner scanner = new Scanner(SuppliersFile);
                PrintWriter writer = new PrintWriter(tempFile);
                boolean SupplierNewItemWritten = false;

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(" , ");
                    String currentID = parts[0].trim();

                    if (currentID.trim().equals(ID.trim())) {
                        String ItemIDFromSuppliers = parts[4].trim(); // Get existing supplier ID
                        String supplierItemPair = ItemIDFromSuppliers + " , " + ID; // Construct pair

                        if (!supplierItemPairs.contains(supplierItemPair)) {
                            supplierItemPairs.add(supplierItemPair); // Add to set

                            String UpdatedLine = String.join(" , ", parts);
                            writer.println(UpdatedLine);


                            // Update the supplier ID in the parts array
                            parts[4] = ItemID;

                            if (!UpdatedLine.split(" , ")[4].trim().equals(ItemID) && SupplierNewItemWritten == false) {
                                // Join the parts back together and print the updated line
                                UpdatedLine = String.join(" , ", parts);
                                writer.println(UpdatedLine);
                                SupplierNewItemWritten = true;
                            }
                        } else {
                            // Handle duplicate supplier ID and item ID pair
                            System.out.println("Duplicate supplier ID and item ID pair found. Entry not added.");
                        }
                    } else {
                        writer.println(line);
                    }
                }

                scanner.close();
                writer.close();

                // Replace the original file with the updated file
                SuppliersFile.delete();
                tempFile.renameTo(SuppliersFile);
            } catch (IOException e) {
                System.out.println("Error updating Items.txt: " + e.getMessage());
                return;
            }
            // -----------------------------------
        }

    }
    
    @Override
    public void Delete() {
        List<String> lines = new ArrayList<>();
        File originalFile = new File("Suppliers.txt");  // Replace with the actual file path
        File tempFile = new File("TempSuppliers.txt");  // Replace with the actual temp file path

        try {
            Scanner fileScanner = new Scanner(originalFile);

            while (fileScanner.hasNextLine()) {
                lines.add(fileScanner.nextLine());
            }

            fileScanner.close();

            FileWriter fw = new FileWriter(tempFile);
            BufferedWriter bw = new BufferedWriter(fw);

            Iterator<String> wordsInLine = lines.iterator();
            int count = 0;

            while (wordsInLine.hasNext()) {
                count++;
                String line = wordsInLine.next();

                if (Portion != null) {
                    if (count != lineNumber) {
                        bw.write(line); // Write the line back to the file
                        bw.newLine();
                    }
                    else {
                        ItemsEntry ItemsEntry = new ItemsEntry(line.split(" , ")[4].trim(), line.split(" , ")[0].trim());
                        ItemsEntry.Delete();
                    }
                }
                else if (ID.equals(line.split(" , ")[0].trim()) && ItemID.equals(line.split(" , ")[4].trim())) {
                    continue;
                }
                else {
                    bw.write(line); // Write the line back to the file
                    bw.newLine();
                }
            }

            bw.close();

            // Delete the original file and rename the temp file
            originalFile.delete();
            tempFile.renameTo(originalFile);


            if (lineNumber > count) {
                System.out.println("\n----------------------\nThis line is not in our record");
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    @Override
    public void Modify() {
        ArrayList<String> modifiedContent = new ArrayList<>();

        try {
            File originalFile = new File("Suppliers.txt");
            Scanner sc1 = new Scanner(originalFile);

            int currentLineNumber = 0;
            while (sc1.hasNextLine()) {
                currentLineNumber++;
                String line = sc1.nextLine();
                modifiedContent.add(line); // Store the original line

                // Check if the current line number matches the provided lineNumberToModify
                if (currentLineNumber == lineNumber) {
                    String[] wordsInLine = line.split(" , ");

                    System.out.println("\n\nThe line you will be modifying:\n"
                            + wordsInLine[0] + " | " + wordsInLine[1] + " | " + wordsInLine[2] + " | " + wordsInLine[3] + " | " + wordsInLine[4] + "\n\n");

                    System.out.println("What would you like to modify?\n"
                            + "1- Supplier name\n"
                            + "2- Supplier contact number\n"
                            + "3- Supplier address\n"
                            + "4- Everything\n"
                            + "[ -1 ] To Cancel\n");

                    String modificationChoice = sc.nextLine();

                    // Modify the chosen thing
                    if (modificationChoice.equals("1")) {
                        System.out.println("Type the new name:\n" +
                                "[ -1 ] To Cancel\n");
                        wordsInLine[1] = sc.nextLine();
                    }
                    else if (modificationChoice.equals("2")) {
                        System.out.println("Type the new contact number:\n" +
                                "[ -1 ] To Cancel\n");
                        wordsInLine[2] = sc.nextLine();
                    }
                    else if (modificationChoice.equals("3")) {
                        System.out.println("Type the new address:\n" +
                                "[ -1 ] To Cancel\n");
                        wordsInLine[3] = sc.nextLine();
                    }
                    else if (modificationChoice.equals("4")) {
                        System.out.println("Type the new name:\n" +
                                "[ -1 ] To Cancel\n");
                        wordsInLine[1] = sc.nextLine();
                        if (!wordsInLine[1].equals("-1")) {
                            System.out.println("Type the new contact number:\n" +
                                    "[ -1 ] To Cancel\n");
                            wordsInLine[2] = sc.nextLine();
                            if (!wordsInLine[2].equals("-1")) {
                                System.out.println("Type the new address:\n" +
                                        "[ -1 ] To Cancel\n");
                                wordsInLine[3] = sc.nextLine();
                            }
                        }
                    } else {
                        wordsInLine[0] = "-1";
                    }
                    // Modify the line in the modifiedContent ArrayList
                    modifiedContent.set(currentLineNumber - 1, String.join(" , ", wordsInLine));
                }
            }

            sc1.close();

            // Specify the file name

            // To store the modified content here temporarily until renamed
            File tempFile = File.createTempFile("tempSuppliers", ".txt");
            FileWriter fw = new FileWriter(tempFile, false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter tempWriter = new PrintWriter(bw);

            // Write the new content line by line
            for (String line : modifiedContent) {
                tempWriter.println(line);
            }

            tempWriter.close();

            // Rename the temporary file to replace the original file
            boolean renamed = tempFile.renameTo(originalFile);

            if (!renamed) {
                System.out.println("Error: Unable to rename the temp file.");
            }

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void displaySupplierToSupplies() {
        int count = 0;

        try {
            File suppliersFile = new File("Suppliers.txt");
            File suppliesFile = new File("Items.txt");
            Scanner scSuppliers = new Scanner(suppliersFile);
            Scanner scSupplies = new Scanner(suppliesFile);

            List<String> suppliersLine = new ArrayList<>();
            List<String> suppliesLine = new ArrayList<>();

            while (scSuppliers.hasNextLine()) {
                suppliersLine.add(scSuppliers.nextLine());
            }

            while (scSupplies.hasNextLine()) {
                suppliesLine.add(scSupplies.nextLine());
            }

            scSuppliers.close();
            scSupplies.close();

            // Start processing the data
            if (count == 0) {
                System.out.println("________________________ Suppliers With Items They Are Supplying ______________________________");
                System.out.println();
                System.out.println("    Supplier ID  | Supplier Name     | Item ID       | Item Name         | Item Price");
                System.out.println("_______________________________________________________________________________________________");
                System.out.println();
                count++;
            }
            String PreviousItemID = "";
            for (String supplierLine : suppliersLine) {
                String ItemID = supplierLine.split(" , ")[4].trim();

                for (String supplyLine : suppliesLine) {
                    String[] supplyData = supplyLine.split(" , ");
                    String supplyID = supplyData[0].trim();

                    if (supplyID.equals(ItemID) && !PreviousItemID.equals(supplyID)) {
                        PreviousItemID = supplyID;
                        String formattedLine = String.format(
                                "%-2d] %-12s | %-17s | %-13s | %-17s | %-17s",
                                count, supplierLine.split(" , ")[0], supplierLine.split(" , ")[1].trim(),
                                supplyData[0].trim(), supplyData[1].trim(), supplyData[3].trim());
                        System.out.println(formattedLine);
                        count++;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public String getSuppliedItem() {
        String SuppliedItemID = "";

        Scanner sc = new Scanner(System.in);

        displayFileContents("Items");

        System.out.println("Does the supplier supply any of the above items?\n" +
                "1- Yes\n" +
                "2- No");

        if (sc.nextLine().equals("1")) {
            System.out.println();
            System.out.println("Type the line number of the item:");

            String SuppliedLineNum = getLine();

            if (SuppliedLineNum.equals("-1")) {
                return "-1";
            }

            List<String> lines = new ArrayList<>();

            try {
                File file = new File("Items.txt");
                Scanner fileScanner = new Scanner(file);

                while (fileScanner.hasNextLine()) {
                    lines.add(fileScanner.nextLine());
                }

                fileScanner.close();

                Iterator<String> wordsInLine = lines.iterator();
                int count = 0;

                while (wordsInLine.hasNext()) {
                    count++;
                    String line = wordsInLine.next();

                    // If the line number matches the input, skip writing the line
                    if (String.valueOf(count).equals(SuppliedLineNum)) {
                        SuppliedItemID = line.split(" , ")[0];
                        return SuppliedItemID;
                    }
                }

                System.out.println("\n-----------------------\nLine number does not exist");
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());

            }
        }

        // If the item is not there
        else {
            System.out.println("Would you like to add the item supplied by this supplier?\n" +
                    "1- Yes\n" +
                    "[ -1 ] Cancel the process");
            if (sc.nextLine().equals("1")) {
                ItemsEntry ItemsEntry = new ItemsEntry(ID + " , New");

                ItemsEntry.Add();

                // to retrieve the item ID of the last added item
                try {
                    File file = new File("Items.txt");
                    Scanner fileScanner = new Scanner(file);

                    String lastLine = null;
                    while (fileScanner.hasNextLine()) {
                        lastLine = fileScanner.nextLine();
                    }

                    fileScanner.close();


                    if (lastLine != null) {
                        SuppliedItemID = lastLine.split(" , ")[0];
                        return SuppliedItemID;
                    } else {
                        System.out.println("\n-----------------------\nFile is empty");
                    }
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }

            }
        }
        return "-1";
    }
}
