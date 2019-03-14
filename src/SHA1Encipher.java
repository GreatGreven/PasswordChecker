import javax.swing.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Encipher {

    static String encrypt(String input) throws NoSuchAlgorithmException {
        MessageDigest digester = MessageDigest.getInstance("SHA1");
        byte[] result = digester.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : result) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException { //Main Method to try the encryption
        String input = "";
        while (input != null && !input.equals("" + JOptionPane.CLOSED_OPTION)) {
            input = JOptionPane.showInputDialog("Enter a Password");
            if (input != null){
                String encipheredInput = SHA1Encipher.encrypt(input);
                System.out.println(input + " --> " + encipheredInput);
                System.out.println("Encryption length: " + encipheredInput.length());
            }
        }
    }
}
