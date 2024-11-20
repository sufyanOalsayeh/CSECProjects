/**
 * CryptoPhaseTwo - A program to perform frequency analysis and reverse Caesar cipher on a given ciphertext.
 * 
 * @author Khalifa Alfalasi
 * @author Sufyan Alsayeh
 * @author Krunal Thumar
 */

 import java.io.File;
 import java.io.FileNotFoundException;
 import java.util.HashMap;
 import java.util.HashSet;
 import java.util.Map;
 import java.util.Scanner;
 import java.util.Set;
 import java.util.TreeMap;
 
 public class CryptoPhaseTwo {
     private static Set<String> dictionary = new HashSet<>(); // Set to store dictionary words
 
     public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);
         
         // Load dictionary from file
         if (!loadDictionary("dictionary.txt")) {
             System.out.println("Dictionary Not Loaded (Place a dictionary.txt under the directory!)");
             return; // Exit if dictionary cannot be loaded
         }
         System.out.println("Dictionary loaded!");
 
         // Prompt the user for a filename to process
         System.out.print("Input the filename to be opened: ");
         String filename = scanner.nextLine();
         
         // Create a File object for the specified filename
         File file = new File(filename);
         
         // Check if the file exists and is readable
         if (file.exists() && file.canRead()) {
             System.out.println("File found: " + file.getAbsolutePath());
             
             // Read and print the content of the file
             StringBuilder contentBuilder = new StringBuilder();
             try (Scanner fileScanner = new Scanner(file)) {
                 while (fileScanner.hasNextLine()) {
                     contentBuilder.append(fileScanner.nextLine()).append("\n"); // Append each line to contentBuilder
                 }
             } catch (FileNotFoundException e) {
                 System.out.println("Error reading the file: " + e.getMessage());
             }
 
             // Get ciphertext and perform frequency analysis
             String content = contentBuilder.toString().trim(); // Remove trailing newline characters
             System.out.println("The ciphertext to break is: " + content);
             
             // Perform frequency analysis on the ciphertext
             performFrequencyAnalysis(content);
             performDigramFrequencyAnalysis(content);
             performTrigramFrequencyAnalysis(content);
             
             // Perform reverse Caesar cipher and check for words in the dictionary
             performReverseCaesarCipher(content);
             
         } else {
             System.out.println("File does not exist or cannot be read."); // Error message if file is not accessible
         }
         
         // Close the scanner to prevent resource leaks
         scanner.close();
     }
 
     /**
      * Loads a dictionary from a specified filename into a set.
      *
      * @param filename The name of the dictionary file.
      * @return true if the dictionary was loaded successfully, false otherwise.
      */
     private static boolean loadDictionary(String filename) {
         try (Scanner dictScanner = new Scanner(new File(filename))) {
             while (dictScanner.hasNextLine()) {
                 String word = dictScanner.nextLine().trim().toLowerCase(); // Read each line, trim whitespace, and convert to lowercase
                 if (!word.isEmpty()) {
                     dictionary.add(word); // Add non-empty words to the dictionary set
                 }
             }
             return true; // Return true if loading was successful
         } catch (FileNotFoundException e) {
             return false; // Return false if dictionary file not found
         }
     }
 
     /**
      * Performs frequency analysis on single letters in the given text.
      *
      * @param text The text to analyze for letter frequencies.
      */
     private static void performFrequencyAnalysis(String text) {
         Map<Character, Integer> letterCount = new HashMap<>(); // Map to count occurrences of each letter
         int totalLetters = 0; // Total count of letters
 
         for (char c : text.toLowerCase().toCharArray()) { // Convert text to lowercase and iterate through characters
             if (Character.isLetter(c)) { // Check if character is a letter
                 letterCount.put(c, letterCount.getOrDefault(c, 0) + 1); // Increment count for this letter
                 totalLetters++; // Increment total letter count
             }
         }
 
         System.out.println("\nSingle Letter Frequency Analysis:");
         for (char c = 'a'; c <= 'z'; c++) { // Iterate through each letter from 'a' to 'z'
             int count = letterCount.getOrDefault(c, 0); // Get count for this letter or default to 0
             double frequency = totalLetters > 0 ? (double) count / totalLetters * 100 : 0.0; // Calculate frequency percentage
             System.out.printf("%c: %d instance%s & Frequency - %.2f\n", 
                               c, count, count == 1 ? "" : "s", frequency); // Print results for this letter
         }
     }
 
     /**
      * Performs digram frequency analysis on the given text.
      *
      * @param text The text to analyze for digram frequencies.
      */
     private static void performDigramFrequencyAnalysis(String text) {
         Map<String, Integer> digramCount = new HashMap<>(); // Map to count occurrences of each digram
         
         for (int i = 0; i < text.length() - 1; i++) { // Iterate through text for digrams
             String digram = text.substring(i, i + 2).toLowerCase(); // Extract digram and convert to lowercase
             if (digram.matches("[a-z]{2}")) { // Check if it's a valid digram (two letters)
                 digramCount.put(digram, digramCount.getOrDefault(digram, 0) + 1); // Increment count for this digram
             }
         }
 
         // Sort by key and display results
         Map<String, Integer> sortedDigrams = new TreeMap<>(digramCount); // Sort digrams alphabetically
         System.out.print("\nDigram Frequency Analysis: ");
         for (Map.Entry<String, Integer> entry : sortedDigrams.entrySet()) { 
             System.out.printf("%s: %d, ", entry.getKey(), entry.getValue()); // Print each digram and its count
         }
         
         System.out.println(); // Newline after digram analysis results
     }
 
     /**
      * Performs trigram frequency analysis on the given text.
      *
      * @param text The text to analyze for trigram frequencies.
      */
     private static void performTrigramFrequencyAnalysis(String text) {
         Map<String, Integer> trigramCount = new HashMap<>(); // Map to count occurrences of each trigram
 
         for (int i = 0; i < text.length() - 2; i++) { // Iterate through text for trigrams
             String trigram = text.substring(i, i + 3).toLowerCase(); // Extract trigram and convert to lowercase
             if (trigram.matches("[a-z]{3}")) { // Check if it's a valid trigram (three letters)
                 trigramCount.put(trigram, trigramCount.getOrDefault(trigram, 0) + 1); // Increment count for this trigram
             }
         }
 
         // Sort by key and display results
         Map<String, Integer> sortedTrigrams = new TreeMap<>(trigramCount); 
         System.out.print("\nTrigram Frequency Analysis: ");
         for (Map.Entry<String, Integer> entry : sortedTrigrams.entrySet()) { 
             System.out.printf("%s: %d, ", entry.getKey(), entry.getValue()); // Print each trigram and its count
         }
         
         System.out.println(); // Newline after trigram analysis results
     }
 
     /**
      * Performs reverse Caesar cipher on the given ciphertext and checks against the dictionary.
      *
      * @param ciphertext The ciphertext to analyze using reverse Caesar cipher shifts.
      */
     private static void performReverseCaesarCipher(String ciphertext) {
         String continuousCiphertext = ciphertext.replaceAll("\\s+", ""); // Remove whitespace characters
         
         StringBuilder modifiedCiphertext = new StringBuilder(); 
         int letterCount = 0; 
 
         for (int i = 0; i < continuousCiphertext.length(); i++) { 
             char currentChar = continuousCiphertext.charAt(i);
             letterCount++;
             
             if ((letterCount % 6 == 1)) { 
                 modifiedCiphertext.append('_'); // Replace every sixth letter with '_'
             } else { 
                 modifiedCiphertext.append(currentChar); 
             }
         }
 
         String processedTextWithUnderscores = modifiedCiphertext.toString();
 
         StringBuilder restoredWhitespaceText = new StringBuilder();
         
         int originalIndex = 0; 
 
         for (char c : ciphertext.toCharArray()) { 
             if (Character.isWhitespace(c)) {
                 restoredWhitespaceText.append(' '); 
                 continue;
             }
             
             if (originalIndex < processedTextWithUnderscores.length()) {
                 restoredWhitespaceText.append(processedTextWithUnderscores.charAt(originalIndex));
                 originalIndex++;
             }
         }
 
         System.out.println("\nOriginal Ciphertext: " + ciphertext);
         
         System.out.println("Modified Ciphertext: " + restoredWhitespaceText.toString());
 
         System.out.println("\nReverse Caesar Cipher Shifts:");
         
         Map<Integer, String> foundWords = new TreeMap<>(); 
 
         for (int shift = 0; shift <= 25; shift++) {
             StringBuilder shiftedText = new StringBuilder();
             
             for (char c : restoredWhitespaceText.toString().toCharArray()) {
                 if (Character.isLetter(c)) {
                     char shiftedChar = (char) (((c - 'a' - shift + 26) % 26) + 'a'); 
                     shiftedText.append(shiftedChar);
                 } else {
                     shiftedText.append(c); 
                 }
             }
             
             String formattedOutput = shiftedText.toString();
             
             for (String word : formattedOutput.split("\\s+")) { 
                 if (dictionary.contains(word.toLowerCase())) { 
                     foundWords.put(shift, word); 
                 }
             }
             
             System.out.printf("Shift %2d: %s\n", shift, formattedOutput);
         }
 
          // Display found words with their corresponding shifts
          for (Map.Entry<Integer, String> entry : foundWords.entrySet()) {
              System.out.printf("'%s' is a word in Dictionary present in Shift %d\n", entry.getValue(), entry.getKey());
          }
     }
 }