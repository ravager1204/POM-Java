
package javaassignment;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidityMethods {
    private String value;
    
    public ValidityMethods() {}
    
    // Checking whether a value is float or not
    public float FloatChecker(String value) {
        float result = 0.0f;

        try {
            float floatValue = Float.parseFloat(value);

            if (floatValue >= 0.0f) {
                result = floatValue;
            } else {
                throw new NumberFormatException(); // Throw an exception to enter the catch block
            }
        } catch (NumberFormatException e) {
            System.out.println("You should be entering a non-negative number as a price! Enter another number or [-1] to exit");
        }

        return result;
    }

    
    // Checking whether a value is int or not
    public int IntChecker(String value) {
        int result = 0;

        try {
            if (Integer.parseInt(value) >= 1){
                result = Integer.parseInt(value);
            }
            else {
                throw new NumberFormatException();
            }
            
        } catch (NumberFormatException e) {
            System.out.println("You should be entering an integer! Enter another integer or [-1] to exit");
        }

        return result;
    }

    public static String isValidPhonNum(String phoneNumber) {
        try {
            String regex = "\\+601\\d{8}";
            if (phoneNumber != null && phoneNumber.matches(regex)) {
                return phoneNumber;
            }
        } catch (NullPointerException e) {
            // Handle the null value of phoneNumber
        }
        return null;
    }

    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        int minLength = 8;
        boolean hasLetter = false;
        boolean hasDigit = false;

        if (password.length() < minLength) {
            return false;
        }

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }

            // If both a letter and a digit have been found, no need to continue checking.
            if (hasLetter && hasDigit) {
                break;
            }
        }

        return hasLetter && hasDigit;
    }


}
