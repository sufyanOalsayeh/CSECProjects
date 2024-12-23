/**
 * Group Members - CSCI.462 - Phase One
 * @author Khalifa Alfalasi
 * @author Sufyan Alsayeh
 * @author Krunal Thumar
 */

import java.io.File; // For File Handling Operations
import java.io.FileNotFoundException; // For Handling cases when a file is not found
import java.io.PrintWriter;
import java.util.HashMap; // For Implementing Table-based mapping and storing key-value pairs
import java.util.Map; // For Defining key-value pairs
import java.util.Scanner; // For Reading Input from Standard Input

public class CryptoProjectCipher {
    // Mapping of characters 'a' to 'z' range to numbers 0 to 25 range
    private static final Map<Character, Integer> charToIndex = new HashMap<>(); // Maps Characters to their indices
    private static final char[] substitutionMapping = {'h', 'i', 'L', 'w', 'm', 'k', 'b', 'd',
            'p', 'c', 'v', 'a', 'z', 'u', 's',
            'j', 'g', 'r', 'y', 'n', 'q',
            'x', 'o', 'f', 't', 'e'};   // Default Mono-Alphabetic Substitution Mapping
    private static char[] userSubstitutionMapping = null;   // User-inputted Mono-Alphabetic Substituion Mapping, initialized to Null!
    private static int outputFileCounter = 1; // Using Counter for All the Output Files Generated

    static {
        // Initialize the character for index mapping
        for (char c = 'a'; c <= 'z'; c++) {
            charToIndex.put(c, c - 'a'); // Maps a -> 0, b -> 1, ..., z -> 25
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);   // Reading User-Input

        while (true) {  // Infinite Loop
            // Displaying Available Options for Encryption & Decryption
            System.out.println("");
            System.out.println("Available Options :");
            System.out.println("1. Perform Encryption");
            System.out.println("2. Perform Decryption");
            System.out.print("Choose your option? ");

            int option = scanner.nextInt(); // Reading User-inputted Option
            scanner.nextLine();

            if (option == 1) {
                performEncryption(scanner); // Calling Encryption Method
            } else if (option == 2) {
                performDecryption(scanner); // Calling Decryption Method
            } else {
                System.out.println("\n! Wrong option selected.\n"); // Error Handling for Wrong User-Input
            }
        }
    }

    private static void performEncryption(Scanner scanner) {
        // Displaying Available Options for Encryption Method
        System.out.println("");
        System.out.println("Available Options :");
        System.out.println("1. Use Default Mono-Alphabetic Key");
        System.out.println("2. Use New Mono-Alphabetic Key");
        System.out.print("Choose your option? ");

        int option = scanner.nextInt(); // Reading User-inputted Option
        scanner.nextLine();

        if (option == 1) {
            System.out.println("Default Mono-Alphabetic Key in Usage!");
            processPlaintext(scanner, true); // Considering that Default Key is in Usage for Encryption
        } else if (option == 2) {
            createUserKey(scanner);
            System.out.println("New Mono-Alphabetic Key accepted by User-Input");
            processPlaintext(scanner, false); // Considering that User-inputted Key is in Usage for Encryption
        } else {
            System.out.println("\n! Wrong option selected.\n"); // Error Handling for Wrong User-Input
        }
    }

    private static void performDecryption(Scanner scanner) {
        // Displaying Available Options for Decryption Method
        System.out.println("");
        System.out.println("Available Options :");
        System.out.println("1. Use Default Mono-Alphabetic Key");
        System.out.println("2. Use New Mono-Alphabetic Key");
        System.out.print("Choose your option? ");

        int option = scanner.nextInt(); // Reading User-inputted Option
        scanner.nextLine(); 

        if (option == 1) {
            System.out.println("Default Mono-Alphabetic Key in Usage!");
            processCiphertext(scanner, true); // Considering that Default Key is in Usage for Decryption
        } else if (option == 2) {
            createUserKey(scanner);
            System.out.println("New Mono-Alphabetic Key accepted by User-Input");
            processCiphertext(scanner, false); // Considering that User-inputted Key is in Usage for Decryption
        } else {
            System.out.println("\n! Wrong option selected.\n"); // Error Handling for Wrong User-Input
        }
    }

    private static void createUserKey(Scanner scanner) {
        // Creating User-inputted Custom Mono-Alphabetic Substitution Mapping Key
        while (true) {  // Infinite Loop
            System.out.print("Enter key distribution [Only Exact 26 Characters Accepted | Input Format - abcd...wxyz ]: ");
            String inputKey = scanner.nextLine().trim();    // Accepting User-inputted Mono-Alphabetic Key without Whitespace

            if (inputKey.length() == 26 && inputKey.chars().allMatch(Character::isLowerCase)) {
                userSubstitutionMapping = inputKey.toCharArray();   // Storing User-inputted Mono-Alphabetic Mapping Key
                break;  // Exit
            } else {
                System.out.println("Invalid key! Please enter exactly 26 lowercase characters.");   // Error Handling for Wrong User-Input
            }
        }
    }

    private static void processPlaintext(Scanner scanner, boolean isDefaultKey) {
        // Displaying Available Options for Processing User-inputted PlainText
        System.out.println("");
        System.out.println("Available Options :");
        System.out.println("1. Input PlainText String [(space) allowed]");
        System.out.println("2. Upload PlainText File Name [./FileName.txt]");
        System.out.print("Choose your option? ");

        int option = scanner.nextInt(); // Reading User-inputted Option
        scanner.nextLine(); 

        if (option == 1) {
            System.out.print("Enter plaintext: ");
            String plaintext = scanner.nextLine();
            String ciphertext = encrypt(plaintext.replace(" ", ""), isDefaultKey); // Performing Encryption without spaces
            String finalCiphertext = restoreSpaces(ciphertext, plaintext); // Restoring removed spaces in CipherText
            displayResult(finalCiphertext);
        } else if (option == 2) {
            processFile(scanner); // Processing PlainText through File Path Input
        } else {
            System.out.println("\n! Wrong option selected.\n"); // Error Handling for Wrong User-Input
        }
    }

    private static void processCiphertext(Scanner scanner, boolean isDefaultKey) {
        // Displaying Available Options for Processing User-inputted CipherText
        System.out.println("");
        System.out.println("Available Options :");
        System.out.println("1. Input CipherText String [(space) allowed]");
        System.out.println("2. Upload CipherText File Name [./FileName.txt]");
        System.out.print("Choose your option? ");

        int option = scanner.nextInt(); // Reading User-inputted Option
        scanner.nextLine(); 

        if (option == 1) {
            System.out.print("Enter ciphertext: ");
            String ciphertext = scanner.nextLine();
            String plaintext = decrypt(ciphertext.replace(" ", ""), isDefaultKey); // Performing Decryption without spaces
            String finalPlaintext = restoreSpaces(plaintext, ciphertext); // Restoring removed spaces in PlainText
            displayResult(finalPlaintext);
        } else if (option == 2) {
            processCipherFile(scanner); // Processing CipherText through File Path Input
        } else {
            System.out.println("\n! Wrong option selected.\n"); // Error Handling for Wrong User-Input
        }
    }

    private static void displayResult(String result) {
        // Displaying Final Calculated Result | Either PlainText or CipherText
       System.out.println("Calculated result: " + result);
       saveOutputToFile(result);    // Saving Result to Output File
       Scanner scanner = new Scanner(System.in); 
       System.out.print("\nEnter [B] to Go Back OR {Enter Key} to Exit! ");
       
       String input = scanner.nextLine();
       
       if (input.equalsIgnoreCase("B")) {   // Ensuring if User wants to Go Back
           return;  // Going Back to Program
       } else { 
           System.exit(0);  // Exiting the Program
       }
   }

   private static void saveOutputToFile(String result) {
    System.out.println("");
    String fileName = "Output-" + outputFileCounter + ".txt"; // Creating Output File 
    try (PrintWriter writer = new PrintWriter(fileName)) { // Writing Output to the Output File
        writer.println(result); // 
        writer.flush(); // Ensuring All the Data is Flushed to Output File
        System.out.println("Output Saved at " + fileName);
        outputFileCounter++; // Incrementing for Next Output File
    } catch (FileNotFoundException e) { 
        System.err.println("Error saving output to file: " + e.getMessage());
    }
}

   private static void processFile(Scanner scanner) {
       System.out.print("Enter file path (.txt or .rtf): ");
       String filePath = scanner.nextLine();

       File file = new File(filePath);
       if (!file.exists() || !file.canRead()) { // Checking if User-inputted File Path is Available and Readable
           System.out.println("\nSorry! File not found/Cannot read file.\n");
           return;
       }

       try (Scanner fileScanner = new Scanner(file)) {
           StringBuilder plaintextBuilder = new StringBuilder();
           while (fileScanner.hasNextLine()) {
               plaintextBuilder.append(fileScanner.nextLine()).append("\n"); 
           }
           String plaintext = plaintextBuilder.toString().trim(); 
           String ciphertext = encrypt(plaintext.replace(" ", ""), true); // Encrypting without spaces using Default Key
           String finalCiphertext = restoreSpaces(ciphertext, plaintext);
           displayResult(finalCiphertext);
       } catch (FileNotFoundException e) { 
           System.out.println("Error reading file: " + e.getMessage());
       }
   }

   private static void processCipherFile(Scanner scanner) {
       System.out.print("Enter file path (.txt or .rtf): ");
       String filePath = scanner.nextLine();

       File file = new File(filePath);
       if (!file.exists() || !file.canRead()) { // Checking if User-inputted File Path is Available and Readable
           System.out.println("\nSorry! File not found/Cannot read file.\n");
           return;
       }

       try (Scanner fileScanner = new Scanner(file)) {
           StringBuilder ciphertextBuilder = new StringBuilder();
           while (fileScanner.hasNextLine()) {
               ciphertextBuilder.append(fileScanner.nextLine()).append("\n"); 
           }
           String ciphertext = ciphertextBuilder.toString().trim(); 
           String plaintext = decrypt(ciphertext.replace(" ", ""), true); // Decrypting without spaces using Default Key
           String finalPlaintext = restoreSpaces(plaintext, ciphertext);
           displayResult(finalPlaintext);
       } catch (FileNotFoundException e) { 
           System.out.println("Error reading file: " + e.getMessage());
       }
   }

   private static String encrypt(String plaintext, boolean isDefaultKey) {
       StringBuilder ciphertext = new StringBuilder();
       int length = plaintext.length();
       
       // Code Block containing Encryption Algorithm Logic Steps
       StringBuilder logicStepsOutput = new StringBuilder();
       logicStepsOutput.append("\nLogic Steps :-\n").append("Original PlainText : ").append(plaintext).append("\n");

       for (int i = 0; i < length;) {

           char firstChar = plaintext.charAt(i);

           if (isLowerCase(firstChar)) {    // LowerChase User-Input Check

               char substitutedChar = substituteChar(firstChar, isDefaultKey); 
               ciphertext.append(substitutedChar);
               logicStepsOutput.append(String.format("Position %d PlainText Character '%c' - '%c' ( Mono-Alphabetic Substitution )\n", i + 1, firstChar, substitutedChar));
               i++; 

               int shiftValue = charToIndex.get(firstChar); 
               int caesarCount = 0; 

               while (caesarCount < 5 && i < length) {

                   char caesarChar = caesarShift(plaintext.charAt(i), shiftValue); 
                   ciphertext.append(caesarChar);
                   logicStepsOutput.append(String.format("Position %d PlainText Character '%c' - '%c' ( Caesar Shift '%d' )\n", i + 1, plaintext.charAt(i), caesarChar, shiftValue));
                   caesarCount++;
                   i++;
               }

               if (i < length) {

                   substitutedChar = substituteChar(plaintext.charAt(i), isDefaultKey);
                   ciphertext.append(substitutedChar);
                   logicStepsOutput.append(String.format("Position %d PlainText Character '%c' - '%c' ( Mono-Alphabetic Substitution )\n", i + 1, plaintext.charAt(i), substitutedChar));
                   i++; 

                   shiftValue = charToIndex.get(plaintext.charAt(i - 1)); 
                   caesarCount = 0; 

                   while (caesarCount < 5 && i < length) {

                       char caesarChar = caesarShift(plaintext.charAt(i), shiftValue); 
                       ciphertext.append(caesarChar);
                       logicStepsOutput.append(String.format("Position %d PlainText Character '%c' - '%c' ( Caesar Shift '%d' )\n", i + 1, plaintext.charAt(i), caesarChar, shiftValue));
                       caesarCount++;
                       i++;
                   }
               }
           } else {

               ciphertext.append(firstChar);
               i++;
           }
       }

       displayLogicSteps(logicStepsOutput.toString());
       
       return ciphertext.toString(); 
   }

   private static String decrypt(String ciphertext, boolean isDefaultKey) { 
       StringBuilder plaintext = new StringBuilder();
       int length = ciphertext.length();

       // Code Block containing Decryption Algorithm Logic Steps
       StringBuilder logicStepsOutput = new StringBuilder();
       logicStepsOutput.append("\nLogic Steps :-\n").append("Original CipherText : ").append(ciphertext).append("\n");

       for (int i = 0; i < length;) {

           char firstChar = ciphertext.charAt(i);

           if (isLowerCase(firstChar)) {    // LowerChase User-Input Check 

               char substitutedChar = reverseSubstituteChar(firstChar, isDefaultKey); 
               plaintext.append(substitutedChar);
               logicStepsOutput.append(String.format("%dth CipherText Character '%c' - '%c' ( Mono-Alphabetic Substitution )\n", i + 1, firstChar, substitutedChar));
               i++; 

               int shiftValue = charToIndex.get(substitutedChar); 
               int caesarCount = 0; 

               while (caesarCount < 5 && i < length) {

                   char caesarChar = reverseCaesarShift(ciphertext.charAt(i), shiftValue); 
                   plaintext.append(caesarChar);
                   logicStepsOutput.append(String.format("%dth CipherText Character '%c' - '%c' ( Reverse Caesar Shift '%d' )\n", i + 1, ciphertext.charAt(i), caesarChar, shiftValue));
                   caesarCount++;
                   i++;
               }

               if (i < length) {

                   substitutedChar = reverseSubstituteChar(ciphertext.charAt(i), isDefaultKey);
                   plaintext.append(substitutedChar);
                   logicStepsOutput.append(String.format("%dth CipherText Character '%c' - '%c' ( Mono-Alphabetic Substitution )\n", i + 1, ciphertext.charAt(i), substitutedChar));
                   i++; 

                   shiftValue = charToIndex.get(plaintext.charAt(plaintext.length() - 1)); 
                   caesarCount = 0; 

                   while (caesarCount < 5 && i < length) {

                       char caesarChar = reverseCaesarShift(ciphertext.charAt(i), shiftValue); 
                       plaintext.append(caesarChar);
                       logicStepsOutput.append(String.format("%dth CipherText Character '%c' - '%c' ( Reverse Caesar Shift '%d' )\n", i + 1, ciphertext.charAt(i), caesarChar, shiftValue));
                       caesarCount++;
                       i++;
                   }
               }
           } else {

               plaintext.append(firstChar);
               i++;
           }
       }

       displayLogicSteps(logicStepsOutput.toString());

       return plaintext.toString(); 
   }

   private static void displayLogicSteps(String stepsOutput){
      System.out.print(stepsOutput);    // Printing Logic Steps for Encryption or Decryption Algorithm
   }

   private static String restoreSpaces(String textWithoutSpaces, String originalTextWithSpaces) {
      StringBuilder restoredTextWithSpaces = new StringBuilder();
      int textIndexWithoutSpaces=0;

      for(char c : originalTextWithSpaces.toCharArray()){   // Iteration over User-Input
          if(c==' ') restoredTextWithSpaces.append(' ');
          else{
              restoredTextWithSpaces.append(textWithoutSpaces.charAt(textIndexWithoutSpaces));
              textIndexWithoutSpaces++;
          }
      }
      
      return restoredTextWithSpaces.toString(); 
   }

   private static char substituteChar(char c, boolean isDefaultKey) {
      int index;
      if(userSubstitutionMapping != null){
          index=charToIndex.get(c); // Getting Index from  Mapping Key
          return userSubstitutionMapping[index]; // Returning Corresponding Characters from User-Input Mapping
      }else{
          index=charToIndex.get(c); // Getting Index from first mapping
          return substitutionMapping[index]; // Returning corresponding Characters from Default Mapping
      }
   }

   private static char reverseSubstituteChar(char c, boolean isDefaultKey) {  
      int index;

      if(isDefaultKey){
          index=-1;
          for(int i=0;i<substitutionMapping.length;i++){
              if(substitutionMapping[i] == c){  // Index Checking for Substituted Characters for Default Mapping
                  index=i;
                  break;
              }
          }
      }else{
          index=-1;
          for(int i=0;i<userSubstitutionMapping.length;i++){
              if(userSubstitutionMapping[i] == c){  // Index Checking for Substituted Characters for User-Input Mapping
                  index=i;
                  break;
              }
          }
      }

      return index != -1 ? (char)(index+'a') : c;
   }

   private static char caesarShift(char c, int shift) { 
      if (!isLowerCase(c)) return c;
      
      int originalIndex = c - 'a'; // Getting Original index in range [0-25]
      int newIndex =(originalIndex + shift) % 26; // Applying Caesar Cipher Shift using Modulo of 26
      return (char) ('a' + newIndex); // Converting Indices Back to Character Format
   }

   private static char reverseCaesarShift(char c, int shift) { 
      if (!isLowerCase(c)) return c; 
      
      int originalIndex = c - 'a'; // Getting Original index in range [0-25]
      int newIndex =(originalIndex - shift + 26)%26; // Applying Reverse Caesar Cipher Shift using Modulo of 26
      return (char) ('a' + newIndex); // Converting Indices Back to Character Format
   }

   private static boolean isLowerCase(char c) { return c >= 'a' && c <= 'z'; /* Checking if Character is Between a and z */ }
}