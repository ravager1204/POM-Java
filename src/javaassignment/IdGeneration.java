
package javaassignment;

import java.io.*;
import java.util.Scanner;


public class IdGeneration {
    
    private String IdRequest;
    private static final String[] IdFormat = { "I00", "S00", "PR00", "PO00", "U00" };
    
    public IdGeneration(String IdRequest) {
        this.IdRequest = IdRequest;
    }
    
    // knowing what types of ID I want
    private int getIndexForIdRequest() {
        String[] idRequests = { "Items", "Suppliers", "PurchaseRequisition", "PurchaseOrder", "User" };
        for (int i = 0; i < idRequests.length; i++) {
            if (IdRequest.equals(idRequests[i])) {
                return i;
            }
        }
        return -1;
    }

    public String GenerationProcess() {
        try {
            File originalFile = new File("IdNumbering.txt");
            // To read the original file line by line
            Scanner sc = new Scanner(originalFile);

            // To store the new content here temporarily until I rename it to rep
            File tempFile = new File("temp1.txt");
            FileWriter fw = new FileWriter(tempFile, false); //  I do not want to append but to create
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter tempWriter = new PrintWriter(bw);

            if (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] wordsInLine = line.split(" , ");
                int index = getIndexForIdRequest();

                if (index != -1) {
                    int IdCount = Integer.parseInt(wordsInLine[index]) + 1;
                    wordsInLine[index] = String.valueOf(IdCount);
                    String newId = IdFormat[index] + IdCount;

                    tempWriter.println(String.join(" , ", wordsInLine));
                    tempWriter.close(); // Close the tempWriter

                    sc.close(); // Close the scanner

                    // Replace the original file with the content of the temporary file
                    try (FileReader tempFileReader = new FileReader(tempFile);
                         BufferedReader tempBufferedReader = new BufferedReader(tempFileReader);
                         FileWriter originalFileWriter = new FileWriter(originalFile)) {
                        String tempLine;
                        while ((tempLine = tempBufferedReader.readLine()) != null) {
                            originalFileWriter.write(tempLine + System.lineSeparator());
                        }
                    }

                    // Delete the temporary file
                    if (!tempFile.delete()) {
                        System.out.println("Error: Unable to delete the temporary file.");
                    }

                    return newId;
                } else {
                    sc.close(); // Close the scanner
                    return "-1";
                }
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        return "";
    }

}
