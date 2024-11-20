# Crypto Project Cipher

## Overview
This project implements a simple cryptographic cipher using Mono-Alphabetic Substitution and Caesar Cipher techniques. It supports both encryption and decryption of plaintext and ciphertext through user interaction.

## How to Use

1. **Run the Application**:
   - Compile and execute the Java file `CryptoProjectCipher.java` using a Java IDE or command line:
     ```bash
     javac CryptoProjectCipher.java
     java CryptoProjectCipher
     ```

2. **Choose an Operation**:
   - Upon running, the program will prompt you to choose:
     1. Perform Encryption
     2. Perform Decryption

3. **Select the Encryption/Decryption Key**:
   - Choose to:
     1. Use the default Mono-Alphabetic Substitution key.
     2. Enter a custom Mono-Alphabetic Substitution key (26 unique lowercase letters).

4. **Provide Input Data**:
   - Select an input mode:
     1. Enter text directly.
     2. Provide the path to a `.txt` or `.rtf` file containing the plaintext or ciphertext.

5. **View and Save Results**:
   - The program will display the encrypted or decrypted result.
   - The result will also be saved in an output file named `Output-X.txt` in the current directory.

## Input/Output Files
- **PlainText Files**:
  - `PlainTextOne.txt`
  - `PlainTextTwo.txt`
- **CipherText Files**:
  - `CipherTextOne.rtf`
  - `CipherTextTwo.rtf`

## Notes
- Ensure all files are in the same directory as the program.
- Follow the format requirements when entering a custom key or providing file paths.

## Contributors
- Khalifa Alfalasi
- Sufyan Alsayeh
- Krunal Thumar
