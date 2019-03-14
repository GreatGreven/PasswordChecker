import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class PasswordRequester {

    static String requestPasswordPwn(String useragent, String baseDomain, String service, String parameters) throws IOException {
        URL url = new URL(baseDomain + service + parameters);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", useragent);
        int status = con.getResponseCode();
        String inputLine;
        StringBuilder content = new StringBuilder();
        if (status == 400){ // Bad request
            content.append(status);
            content.append("Bad request, the account does not comply with an acceptable format (i.e. it's an empty string)");
        } else if (status == 403) { // Access forbidden
            content.append(status);
            content.append("Forbidden, no user agent has been specified in the request");
        } else if (status == 404) { // Not Found
            content.append(status);
            content.append("Not found, the account could not be found and has therefore not been pwned");
        } else if (status == 429) { // Too many requests
            content.append(status);
            content.append("Too many requests, the rate limit has been exceeded");
        } else { //OK
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
                content.append("\n");
            }
            in.close();
        }
        return content.toString();
    }

    public static void main(String[] args) throws IOException { //Main method to test the API
        String domain = "https://api.pwnedpasswords.com";
        String service = "/range/";
        String parameters = "21BD1";
        String userAgent = "PasswordChecker by GreatGreven";
        String response = PasswordRequester.requestPasswordPwn(userAgent,domain,service,parameters);
        System.out.println(response);
    }
}
