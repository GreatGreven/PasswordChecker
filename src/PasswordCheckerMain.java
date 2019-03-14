import javax.swing.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class PasswordCheckerMain {
    private final static String API_DOMAIN = "https://api.pwnedpasswords.com";
    private final static String PASSWORD_SERVICE = "/range/";
    private final static String USER_AGENT = "PasswordChecker by GreatGreven";

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        String input = "";
        while (input != null && !input.equals("" + JOptionPane.CLOSED_OPTION)) {
            input = JOptionPane.showInputDialog("Enter a Password");
            if (input != null){
                String encipheredInput = SHA1Encipher.encrypt(input).toUpperCase();
                String parameter = encipheredInput.substring(0,5);
                String suffix = encipheredInput.substring(5);
                String response = PasswordRequester.requestPasswordPwn(USER_AGENT,API_DOMAIN,PASSWORD_SERVICE,parameter);
//                JOptionPane.showMessageDialog(null, passwordResponse, input + " --> " + encipheredInput, JOptionPane.PLAIN_MESSAGE);
                String [] hashedPasswordSuffixes = response.split("\n");
                boolean found = false;
                for (String s: hashedPasswordSuffixes){
                    if (s.contains(suffix)){
                        found = true;
                        String nbrOfTimes = s.split(":")[1];
                        String message = "Password: " + input +
                                "\nPassword in #-form: " + encipheredInput +
                                "\nThis password has been leaked " + nbrOfTimes + " times";
                        JOptionPane.showMessageDialog(null, message, "Password has been breached!", JOptionPane.PLAIN_MESSAGE);
                    }
                }
                if (!found){
                    String message = "Password: " + input +
                            "\nPassword in #-form: " + encipheredInput +
                            "\nThis password has not been leaked!";
                    JOptionPane.showMessageDialog(null, message, "Password is secure!", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
    }
}
