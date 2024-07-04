package javaassignment.Entry;

import javaassignment.IdGeneration;

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

public class ItemsEntry extends Entry {
    private String ItemName;
    private String ItemUnit;
    private int approvedInt;
    private float ApprovedPrice;
    private String ItemPrice;
    private String ItemMinimum;
    private String AvailableItems;
    private String SupplierID;
    private int LineNumber;
    private String Portion;
    Scanner sc = new Scanner(System.in);


    private ArrayList<String> ModifiedLine = new ArrayList<>();

    public ItemsEntry(String Portion) {
        this.Portion = Portion;
    }

    public ItemsEntry(String ItemID, String SupplierID) {
        this.ID = ItemID;
        this.SupplierID = SupplierID;
    }

    public void setLineNumber(int lineNumber) {
        this.LineNumber = lineNumber;
    }

    // Add to item text file
    @Override
    public void Add() {
        try {
            // Check if it's adding a new item or a new supplier to an item
            if (Portion != null) {
                // Adding a new item
                displayFileContents("Items");

                System.out.println("Enter the item name:\n" +
                        "[ -1 ] To Exit");
                ItemName = sc.nextLine();
                if (ItemName.equals("-1")) {
                    System.out.println("YOU CHOSEN TO EXIT");
                    return;
                }

                System.out.println("Enter the item unit:\n" +
                        "[ -1 ] To Exit");
                ItemUnit = sc.nextLine();
                if (ItemUnit.equals("-1")) {
                    System.out.println("YOU CHOSEN TO EXIT");
                    return;
                }

                System.out.println("Enter the item price:\n" +
                        "[ -1 ] To Exit");
                ItemPrice = sc.nextLine();
                if (ItemPrice.equals("-1")) {
                    System.out.println("YOU CHOSE TO EXIT");
                    return;
                } else {
                    ApprovedPrice = 0.0f;
                    while (true) {
                        ApprovedPrice = Validity.FloatChecker(ItemPrice);

                        if (ApprovedPrice == 0.0) {
                            ItemPrice = sc.next();
                            if (ItemPrice.equals("-1")) {
                                System.out.println("YOU CHOSE TO EXIT");
                                return;
                            }
                        } else {
                            ItemPrice = String.valueOf(ApprovedPrice);
                            break;
                        }
                    }
                }

                System.out.println("Enter the item minimum:\n" +
                        "[ -1 ] To Exit");
                ItemMinimum = sc.nextLine();
                if (ItemMinimum.equals("-1")) {
                    System.out.println("YOU CHOSE TO EXIT");
                    return;
                } else {
                    while (true) {
                        approvedInt = Validity.IntChecker(ItemMinimum);

                        if (approvedInt == 0) {
                            ItemMinimum = sc.next();

                            if (ItemMinimum.equals("-1")) {
                                System.out.println("YOU CHOSE TO EXIT");
                                return;
                            }
                        } else {
                            int approvedMinimum = approvedInt;
                            ItemMinimum = String.valueOf(approvedMinimum);
                            break;
                        }
                    }
                }

                System.out.println("Enter the item available quantity:\n" +
                        "[ -1 ] To Exit");
                AvailableItems = sc.nextLine();
                if (AvailableItems.equals("-1")) {
                    System.out.println("YOU CHOSE TO EXIT");
                    return;
                } else {
                    while (true) {
                        approvedInt = Validity.IntChecker(AvailableItems);

                        if (approvedInt == 0) {
                            AvailableItems = sc.next();

                            if (AvailableItems.equals("-1")) {
                                System.out.println("YOU CHOSE TO EXIT");
                                return;
                            }
                        } else {
                            int approvedMinimum = approvedInt;
                            AvailableItems = String.valueOf(approvedMinimum);
                            break;
                        }
                    }
                }

                IdGeneration GenerateItemID = new IdGeneration("Items");
                ID = GenerateItemID.GenerationProcess();

                SupplierID = Portion.equals("Items") ? getSupplierID(ID) : Portion;

                if (!SupplierID.equals("-1")) {
                    // Initialize a set to track supplier ID and item ID pairs
                    Set<String> supplierItemPairs = new HashSet<>();
                    String supplierItemPair = SupplierID.split(" , ")[0].trim() + " , " + ID;

                    if (!supplierItemPairs.contains(supplierItemPair)) {
                        supplierItemPairs.add(supplierItemPair);

                        String Line = ID + " , " + ItemName + " , " + ItemUnit + " , " + ItemPrice + " , " + ItemMinimum + " , " + AvailableItems + " , " + SupplierID.split(" , ")[0];

                        // Create a temporary file
                        File tempFile = File.createTempFile("tempItems", ".txt");

                        // Copy the contents of the original file to the temporary file
                        Files.copy(Paths.get("Items.txt"), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                        // Append the new content to the temporary file
                        Files.write(tempFile.toPath(), (Line + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);

                        // Replace the original file with the modified temporary file
                        Files.move(tempFile.toPath(), Paths.get("Items.txt"), StandardCopyOption.REPLACE_EXISTING);

                        if (SupplierID.split(" , ")[1].equals("Old")) {
                            SupplierEntry SuppliersEntry = new SupplierEntry(SupplierID.split(" , ")[0], ID);
                            SuppliersEntry.Add();
                        }
                    } else {
                        System.out.println("Duplicate supplier ID and item ID pair found. Entry not added.");
                    }
                } else {
                    System.out.println("YOU CHOSE TO EXIT");
                }
            } else {
                // Adding a new supplier to an item

                // Set to track supplier ID and item ID pairs
                Set<String> supplierItemPairs = new HashSet<>();

                // Updating the "Items.txt" file
                // -----------------------------------
                File itemsFile = new File("Items.txt");
                File tempFile = File.createTempFile("tempItems", ".txt");

                try (Scanner scanner = new Scanner(itemsFile);
                     PrintWriter writer = new PrintWriter(tempFile)) {
                    boolean IteNewSupplierWritten = false;

                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] parts = line.split(" , ");
                        String currentID = parts[0].trim();

                        if (currentID.trim().equals(ID.trim())) {
                            String supplierIDFromItem = parts[6].trim(); // Get existing supplier ID
                            String supplierItemPair = supplierIDFromItem + " , " + ID; // Construct pair

                            if (!supplierItemPairs.contains(supplierItemPair)) {
                                supplierItemPairs.add(supplierItemPair); // Add to set

                                String UpdatedLine = String.join(" , ", parts);
                                writer.println(UpdatedLine);

                                // Update the supplier ID in the parts array
                                parts[6] = SupplierID;

                                if (!UpdatedLine.split(" , ")[6].trim().equals(SupplierID) && !IteNewSupplierWritten) {
                                    // Join the parts back together and print the updated line
                                    UpdatedLine = String.join(" , ", parts);
                                    writer.println(UpdatedLine);
                                    IteNewSupplierWritten = true;
                                }
                            }
                        } else {
                            writer.println(line);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error updating Items.txt: " + e.getMessage());
                }

                // Replace the original file with the updated file
                if (!itemsFile.delete()) {
                    System.out.println("Error deleting the original Items.txt file.");
                }

                if (!tempFile.renameTo(itemsFile)) {
                    System.out.println("Error renaming the temporary file.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }



    // Delete from item text file
    @Override
    public void Delete() {
        List<String> lines = new ArrayList<>();

        try {
            File file = new File("Items.txt");
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                lines.add(fileScanner.nextLine());
            }

            fileScanner.close();

            // Create a temporary file to store the modified lines
            File tempFile = File.createTempFile("tempItems", ".txt");
            FileWriter fw = new FileWriter(tempFile);
            PrintWriter pw = new PrintWriter(fw);

            Iterator<String> wordsInLine = lines.iterator();
            int count = 0;

            while (wordsInLine.hasNext()) {
                count++;
                String line = wordsInLine.next();

                // If the line number matches the input, skip writing the line
                if (Portion != null) {
                    if (count != LineNumber) {
                        pw.println(line); // Write the line back to the temp file
                    } else {
                        // Delete associated supplier entry
                        SupplierEntry SupplierEntry = new SupplierEntry(line.split(" , ")[6].trim(), line.split(" , ")[0].trim());
                        SupplierEntry.Delete();
                    }
                } else if (ID.equals(line.split(" , ")[0].trim()) && SupplierID.equals(line.split(" , ")[6].trim())) {
                    continue; // Skip writing the line to delete it
                } else {
                    pw.println(line);
                }
            }

            pw.close();

            if (LineNumber > count) {
                System.out.println("\n-----------------------\nLine number does not exist");
            } else {
                // Delete the old file and rename the temp file to the original one
                if (file.delete() && tempFile.renameTo(file)) {
                    System.out.println("Item was deleted successfully.");
                } else {
                    System.out.println("Failed to update the file.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // Modify the items in the text fil
    @Override
    public void Modify() {
        try {
            File originalFile = new File("Items.txt");
            Scanner sc1 = new Scanner(originalFile);
            Scanner scc = new Scanner(System.in);

            int currentLineNumber = 0;
            while (sc1.hasNextLine()) {
                currentLineNumber++;
                String line = sc1.nextLine();
                ModifiedLine.add(line); // Store the original line

                // Check if the current line number matches the provided lineNumberToModify
                if (currentLineNumber == LineNumber) {
                    String[] wordsInLine = line.split(" , ");

                    System.out.println(wordsInLine[0] + " | " + wordsInLine[1] + " | " + wordsInLine[2] + " | " + wordsInLine[3] + " | " + wordsInLine[4] + " | " + wordsInLine[5] + " | " + wordsInLine[6]);

                    System.out.println("What would you like to modify?\n"
                            + "1- Item name\n"
                            + "2- Item Unit\n"
                            + "3- Item Price\n"
                            + "4- Item Minimum\n"
                            + "5- Item available units\n"
                            + "6- Everything in the item\n"
                            + "[ -1 ] Cancel");

                    String modificationChoice = scc.nextLine();

                    // Modify the chosen thing
                    if (modificationChoice.equals("1")) {
                        System.out.println("Type the new name:\n");
                        wordsInLine[1] = scc.nextLine();
                    } else if (modificationChoice.equals("2")) {
                        System.out.println("Type the new Unit:\n");
                        wordsInLine[2] = scc.nextLine();
                    } else if (modificationChoice.equals("3")) {
                        System.out.println("Type the new Price:\n");
                        wordsInLine[3] = scc.nextLine();
                    } else if (modificationChoice.equals("4")) {
                        System.out.println("Type the new Minimum:\n");
                        wordsInLine[4] = scc.nextLine();
                    } else if (modificationChoice.equals("5")) {
                        System.out.println("Type the new available units:\n");
                        wordsInLine[5] = scc.nextLine();
                    } else if (modificationChoice.equals("6")) {
                        System.out.println("Type the new name:\n");
                        wordsInLine[1] = scc.nextLine();
                        System.out.println("Type the new Unit:\n");
                        wordsInLine[2] = scc.nextLine();
                        System.out.println("Type the new Price:\n");
                        wordsInLine[3] = scc.nextLine();
                        System.out.println("Type the new Minimum:\n");
                        wordsInLine[4] = scc.nextLine();
                        System.out.println("Type the new available units:\n");
                        wordsInLine[5] = scc.nextLine();
                    } else {
                        return;
                    }

                    // Modify the line in the ModifiedLine ArrayList
                    ModifiedLine.set(currentLineNumber - 1, String.join(" , ", wordsInLine));
                }
            }

            sc1.close();

            // To store the modified content here temporarily until renamed
            File tempFile = File.createTempFile("temp", ".txt");
            FileWriter fw = new FileWriter(tempFile, false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter tempWriter = new PrintWriter(bw);

            // Write the new content line by line
            for (String line : ModifiedLine) {
                tempWriter.println(line);
            }

            tempWriter.close();

            // Copy the contents of the temporary file to the original file
            Files.copy(tempFile.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Delete the temporary file
            tempFile.delete();

            // Clear the ModifiedLine list for the next modification
            ModifiedLine.clear();

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }


    public String getSupplierID(String ItemID) {
        displayFileContents("Suppliers");

        System.out.println();
        System.out.println("Does the Supplier that supplies this item on of the suppliers above?\n" +
                "1- Yes\n" +
                "2- No");
        String UserChoice = sc.nextLine();

        while (true) {
            if (UserChoice.equals("1")) {
                System.out.println("Type the line number of the chosen supplier:\n");
                LineNumber = Integer.parseInt(getLine());

                List<String> lines = new ArrayList<>();

                try {
                    File file = new File("Suppliers.txt");  // Replace with the actual file path
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
                        if (count == LineNumber) {
                            String SupplierID = line.split(" , ")[0] + " , " + "Old";

                            return SupplierID;
                        }
                    }

                    System.out.println("\n-----------------------\nLine number does not exist");
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());

                }
                return "-1";  // Means error

            } else {
                System.out.println("Would you like to add a new supplier or cancel the process?\n" +
                        "1- Add new supplier\n" +
                        "2- Cancel");
                UserChoice = sc.nextLine();
                if (UserChoice.equals("1")) {
                    SupplierEntry SupplierEntry = new SupplierEntry(ItemID);
                    SupplierEntry.Add();
                    return SupplierEntry.getSuplierID() + " , New";
                }
                else {
                    return "-1";
                }
            }
        }

    }
}
