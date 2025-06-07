import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.security.MessageDigest;
import java.util.Arrays;

public class FileEncryptorGUI extends JFrame {

    private JTextField keyField;
    private JTextField inputField;
    private JTextField outputField;
    private JButton browseInputBtn, browseOutputBtn, encryptBtn, decryptBtn;

    public FileEncryptorGUI() {
        setTitle("File Encryptor/Decryptor - AES");
        setSize(500, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 5, 5));

        // Key panel
        JPanel keyPanel = new JPanel();
        keyPanel.add(new JLabel("Password Key:"));
        keyField = new JTextField(30);
        keyPanel.add(keyField);
        add(keyPanel);

        // Input file panel
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Input File:"));
        inputField = new JTextField(25);
        inputPanel.add(inputField);
        browseInputBtn = new JButton("Browse");
        inputPanel.add(browseInputBtn);
        add(inputPanel);

        // Output file panel
        JPanel outputPanel = new JPanel();
        outputPanel.add(new JLabel("Output File:"));
        outputField = new JTextField(25);
        outputPanel.add(outputField);
        browseOutputBtn = new JButton("Browse");
        outputPanel.add(browseOutputBtn);
        add(outputPanel);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        encryptBtn = new JButton("Encrypt");
        decryptBtn = new JButton("Decrypt");
        buttonPanel.add(encryptBtn);
        buttonPanel.add(decryptBtn);
        add(buttonPanel);

        // Action Listeners
        browseInputBtn.addActionListener(e -> chooseFile(inputField));
        browseOutputBtn.addActionListener(e -> chooseFile(outputField));
        encryptBtn.addActionListener(e -> handleEncryptDecrypt(true));
        decryptBtn.addActionListener(e -> handleEncryptDecrypt(false));
    }

    private void chooseFile(JTextField field) {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            field.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void handleEncryptDecrypt(boolean encrypt) {
        try {
            String key = keyField.getText();
            File inputFile = new File(inputField.getText());
            File outputFile = new File(outputField.getText());

            if (encrypt) {
                encryptFile(key, inputFile, outputFile);
                JOptionPane.showMessageDialog(this, "Encryption successful!");
            } else {
                decryptFile(key, inputFile, outputFile);
                JOptionPane.showMessageDialog(this, "Decryption successful!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // AES Key generator
    private static SecretKeySpec getSecretKey(String myKey) throws Exception {
        byte[] key = myKey.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); // AES-128
        return new SecretKeySpec(key, "AES");
    }

    // Encrypt method
    private static void encryptFile(String key, File inputFile, File outputFile) throws Exception {
        SecretKeySpec secretKey = getSecretKey(key);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        FileInputStream fis = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(outputFile);
        CipherOutputStream cos = new CipherOutputStream(fos, cipher);

        byte[] buffer = new byte[1024];
        int read;
        while ((read = fis.read(buffer)) != -1) {
            cos.write(buffer, 0, read);
        }
        fis.close();
        cos.flush();
        cos.close();
    }

    // Decrypt method
    private static void decryptFile(String key, File inputFile, File outputFile) throws Exception {
        SecretKeySpec secretKey = getSecretKey(key);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        FileInputStream fis = new FileInputStream(inputFile);
        CipherInputStream cis = new CipherInputStream(fis, cipher);
        FileOutputStream fos = new FileOutputStream(outputFile);

        byte[] buffer = new byte[1024];
        int read;
        while ((read = cis.read(buffer)) != -1) {
            fos.write(buffer, 0, read);
        }
        cis.close();
        fos.flush();
        fos.close();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileEncryptorGUI gui = new FileEncryptorGUI();
            gui.setVisible(true);
        });
    }
}
