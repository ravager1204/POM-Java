
package javaassignment.Entry;

import javaassignment.ValidityMethods;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public abstract class Entry {
    protected String ID;
    protected static final ValidityMethods Validity = new ValidityMethods();

    abstract void Add();
    
    abstract void Delete();
    
    abstract void Modify();

    public void displayFileContents(String FileName) {
        List<String> lines = new ArrayList<>();
        String formattedLine = "";
        int count = 0;

        try {
            File file = new File(FileName + ".txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            return;
        }



        Iterator<String> wordsInLine = lines.iterator();

        while (wordsInLine.hasNext()) {
            String[] data = (wordsInLine.next()).split(" , ");

            if (FileName.equals("Items")) {
                if (count == 0) {
                    System.out.println("    Item ID | Item Name          | Unit     | Price     | Minimum     | Available Unit| Supplier ID");
                    System.out.println("_______________________________________________________________________________________________");
                    System.out.println();
                    count++;
                }
                formattedLine = String.format(
                        "%-2d] %-7s | %-18s | %-8s | %-9s | %-11s | %-13s | %-10s",
                        count, data[0].trim(), data[1].trim(), data[2].trim(),
                        data[3].trim(), data[4].trim(), data[5].trim(), data[6].trim()
                );
            }
            else if (FileName.equals("Suppliers")) {
                if (count == 0) {
                    System.out.println("    Supplier ID | Supplier Name     | Supplier Number| Supplier Address | Item ID");
                    System.out.println("_______________________________________________________________________________________________");
                    System.out.println();
                    count++;
                }
                formattedLine = String.format(
                        "%-2d] %-11s | %-17s | %-14s | %-16s | %-7s",
                        count, data[0].trim(), data[1].trim(), data[2].trim(),data[3].trim(),data[4].trim());
            }
            else if (FileName.equals("DailyWiseSales")) {
                if (count == 0) {
                    System.out.println("    Item ID | Item Name     | Item Unit     | Number of Units Sold | Total Price");
                    System.out.println("_______________________________________________________________________________________________");
                    System.out.println();
                    count++;
                }
                formattedLine = String.format(
                        "%-2d] %-7s | %-13s | %-13s | %-20s | %-7s",
                        count, data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim());
            }
            else if (FileName.equals("PurchaseRequisition")) {
                if (count == 0) {
                    System.out.println("    Request ID | Requester ID | Supplier ID | Requested Item ID | Num of Units |   Date      | Status");
                    System.out.println("________________________________________________________________________________________________________");
                    System.out.println();
                    count++;
                }
                formattedLine = String.format(
                        "%-2d] %-10s | %-12s | %-11s | %-17s | %-12s | %-11s | %-5s ",
                        count, data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim(), data[5].trim(), data[6].trim());
            }
            else if (FileName.equals("PurchaseOrder")) {
                if (count == 0) {
                    System.out.println("   PO ID |   PR ID |   SM ID | Supplier ID | Requested Item ID | Quantity | Date");
                    System.out.println("___________________________________________________________________________________");
                    System.out.println();
                    count++;
                }
                formattedLine = String.format(
                        "%6s | %7s | %7s | %11s | %17s | %8s | %6s ",
                        data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim(), data[5].trim(), data[6].trim());
            }


            System.out.println(formattedLine);
            count++;
        }
        System.out.println();
    }

    public String getLine() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the line number you would like to choose:\n" +
                "[ -1 ] To Exit");
        String LineNum = sc.next();
        if (LineNum.equals("-1")) {
            return LineNum;
        }
        else {
            while (true) {
                int approvedInt = Validity.IntChecker(LineNum);

                if (approvedInt == 0) {
                    LineNum = sc.next();

                    if (LineNum.equals("-1")) {
                        return "-1";
                    }
                } else {
                    // Set ApprovedAvailableItems to the approved integer value
                    int approvedAvailable = approvedInt;
                    return String.valueOf(approvedAvailable);
                }
            }
        }
    }
    
}
