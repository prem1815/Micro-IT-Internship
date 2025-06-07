# Micro-IT-Internship
#1 🔐 File Encryption & Decryption Tool

A simple Java-based GUI tool to encrypt and decrypt files using password-based AES encryption. It helps protect sensitive files by converting them into unreadable formats unless decrypted with the correct password.

## 🚀 Features

- Encrypt any file using AES encryption
- Decrypt previously encrypted files
- Password-based access
- Simple GUI (Java Swing)
- Error handling for wrong passwords or corrupted files

## 🛠️ Technologies Used

- Java
- Swing (GUI)
- AES (Java Cryptography Extension)

## 📦 How to Run

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/file-encryption-tool.git
   cd file-encryption-tool
2. Compile and run:
javac FileEncryptor.java
java FileEncryptor

3. How It Works
. Converts user-entered password to a key
. Encrypts/decrypts the file byte stream using AES
. Saves output as a new encrypted/decrypted file

#2 URL Shortener
A basic console-based Java app that generates short URLs from long ones and vice versa. Uses an in-memory HashMap to store mappings.

🚀 Features
. Shortens any long URL
. Expands short URLs back to the original
. Random 6-character alphanumeric key generation
. Simulates a service like bit.ly
. Easily extendable to Swing or Spring Boot

📦 How to Run
cd url-shortener
Compile and run:
javac URLShortener.java
java URLShortener

How It Works
. Generates a 6-character unique key
. Prepends it to a base URL like http://short.ly/
. Stores mappings in HashMap for quick lookup
