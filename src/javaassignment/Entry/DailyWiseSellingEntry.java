package javaassignment.Entry;



import java.io.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class DailyWiseSellingEntry extends Entry {

    private List<String> ModifiedLineContent;
    private int LineNumber;
    Scanner sc = new Scanner(System.in);


    public DailyWiseSellingEntry(){}

    public void setLineNumber(int LineNumber) {
        this.LineNumber = LineNumber;
    }

    public DailyWiseSellingEntry(List<String> ModifiedLineContent) {
        this.ModifiedLineContent = ModifiedLineContent;
    }


    public void Add(){

        int count = 0;

        try {
            File itemsFile = new File("Items.txt");
            File dailyFile = new File("DailyWiseSales.txt");

            // Load all lines from Items.txt into a list
            List<String> itemsLines = new ArrayList<>();
            try (Scanner itemsScanner = new Scanner(itemsFile)) {
                while (itemsScanner.hasNextLine()) {
                    count++;
                    itemsLines.add(itemsScanner.nextLine());
                }
            }


            String itemsLine = itemsLines.get(LineNumber - 1); // Adjust for 0-based index

            String[] parts = itemsLine.split(" , ");

            System.out.println("How many units were sold?");
            String unitsSold = sc.nextLine();

            if (LineNumber > count) {
                System.out.println("Line does not exist.");
            }
            if (unitsSold.equals("-1")) {
                return; // No need to update anything
            }

            int soldQuantity = Integer.parseInt(unitsSold);
            int availableQuantity = Integer.parseInt(parts[5]);

            if (soldQuantity <= availableQuantity) {
                float totalSelling = soldQuantity * Float.parseFloat(parts[3]);
                String dailySellingLine = parts[0] + " , " + parts[1] + " , " + parts[2] + " , " + unitsSold + " , " + totalSelling;

                // Append the daily sale record to DailyWiseSales.txt
                try (PrintWriter dailyWriter = new PrintWriter(new FileWriter(dailyFile, true))) {
                    dailyWriter.println(dailySellingLine);
                }

                // Update the available quantity in the items line
                availableQuantity -= soldQuantity;
                parts[5] = Integer.toString(availableQuantity);

                // Update the corresponding line in Items.txt
                itemsLines.set(LineNumber - 1, String.join(" , ", parts));

                // Rewrite the entire Items.txt file
                try (PrintWriter itemsWriter = new PrintWriter(itemsFile)) {
                    for (String line : itemsLines) {
                        itemsWriter.println(line);
                    }
                }
            } else {
                System.out.println("THE PROCESS WAS NOT SUCCESSFULLY DONE (Units sold is more than what we have)");
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }

    public void Delete(){
        List<String> lines = new ArrayList<>();
        String deletingCondition = "";

        try {
            File file = new File("DailyWiseSales.txt");  // Replace with the actual file path
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                lines.add(fileScanner.nextLine());
            }

            fileScanner.close();

            File tempFile = new File("temp_DailyWiseSales.txt"); // Temporary file
            FileWriter fw = new FileWriter(tempFile);
            PrintWriter pw = new PrintWriter(fw);

            Iterator<String> wordsInLine = lines.iterator();
            int count = 0;

            while (wordsInLine.hasNext()) {
                count++;
                String line = wordsInLine.next();

                // If the line number matches the input, skip writing the line
                if (count == LineNumber) {
                    deletingCondition = "Done";

                }
                else {
                    pw.println(line); // Write the line to the temporary file
                }

            }

            pw.close();

            if (deletingCondition.equals("Done")) {
                if (file.delete()) {
                    if (tempFile.renameTo(file)) {
                        System.out.println("Line " + LineNumber + " deleted and file updated.");
                        System.out.println();
                    } else {
                        System.out.println("Error renaming temp file.");
                    }
                } else {
                    System.out.println("Error deleting the original file.");
                }
            } else {
                System.out.println("\n----------------------\nThis line is not in our record");
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
    }

    public void Modify(){
        Scanner sc1 = new Scanner(System.in);
        ArrayList<String> modifiedContent = new ArrayList<>();
        boolean Status = false;

        try {
            File originalFile = new File("DailyWiseSales.txt");
            Scanner sc = new Scanner(originalFile);

            int currentLineNumber = 0;
            while (sc.hasNextLine()) {
                currentLineNumber++;
                String line = sc.nextLine();
                modifiedContent.add(line); // Store the original line

                if (currentLineNumber == LineNumber) {
                    String[] wordsInLine = line.split(" , ");

                    Status = true;

                    System.out.println("\n\nThe line you will be modifying:\n"
                            + wordsInLine[0] + " | " + wordsInLine[1] + " | " + wordsInLine[2] + " | " + wordsInLine[3] + " | " + wordsInLine[4]
                            + "\n\n");

                    System.out.println("You should be able to only modify the units sold! Or add another one if you would like\n" +
                            "Enter the new units sold:\n" +
                            "[ -1 ] To Cancel\n");
                    String newUnitsSold = sc1.nextLine();

                    if (!newUnitsSold.equals("-1")) {
                        float newUnits = Float.parseFloat(newUnitsSold);
                        float unitPrice = Float.parseFloat(wordsInLine[4]) / Float.parseFloat(wordsInLine[3]);

                        wordsInLine[3] = newUnitsSold;
                        wordsInLine[4] = String.valueOf(newUnits * unitPrice); // Calculate new total selling

                        // Modify the line in the modifiedContent ArrayList
                        modifiedContent.set(currentLineNumber - 1, String.join(" , ", wordsInLine));
                    } else {
                        return;
                    }
                }
            }
            sc.close();

            if (!Status) {
                System.out.println("Line number is not in our record");
            }
            // To store the modified content here temporarily until renamed
            File tempFile = new File("temp.txt");
            FileWriter fw = new FileWriter(tempFile, false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter tempWriter = new PrintWriter(bw);

            // Write the new content line by line
            for (String line : modifiedContent) {
                tempWriter.println(line);
            }

            tempWriter.close();

            // Rename the temporary file to 2replace the original file
            boolean renamed = tempFile.renameTo(originalFile);

            if (!renamed) {
                System.out.println("Error: Unable to rename the temp file.");
            }

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
