# Crypto Phase Two

## Overview
This project focuses on breaking ciphertexts using frequency analysis and reverse Caesar cipher techniques. It analyzes single letters, digrams, and trigrams, and uses a dictionary-based approach to identify meaningful words from the deciphered text.

## Features
1. **Frequency Analysis**:
   - Single-letter frequency analysis.
   - Digram (pair of letters) frequency analysis.
   - Trigram (three-letter combination) frequency analysis.

2. **Reverse Caesar Cipher**:
   - Attempts to decrypt the ciphertext by trying all possible Caesar cipher shifts (0-25).
   - Identifies words from a dictionary to evaluate potential correct shifts.

3. **Dictionary Integration**:
   - Uses a dictionary file (`dictionary.txt`) to validate deciphered words.

## Files in the Project
- **Source Code**:
  - `CryptoPhaseTwo.java`: The main program to analyze and decrypt ciphertext.
- **Input Files**:
  - `ciphertext.txt`: The ciphertext file to be analyzed.
  - `easyciphertext.txt`: An example of a simple ciphertext.
  - `newciphertext.txt`: Another sample ciphertext for testing.
  - `dictionary.txt`: A text file containing English words for validation.
- **Reference**:
  - `Relative-Frequency-English-Alphabet.png`: A graphical representation of letter frequencies in the English language.

## How to Use

### 1. Setup
- Ensure you have the following files in the same directory as the source code:
  - `dictionary.txt`: Contains the word list for deciphering.
  - Any ciphertext file you want to analyze (`ciphertext.txt`, `easyciphertext.txt`, etc.).

### 2. Compile and Run
- Compile the Java program:
  ```bash
  javac CryptoPhaseTwo.java
