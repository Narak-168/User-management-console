package Notify;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TelegramNotification {
    private static final String BOT_TOKEN = "7873021651:AAHnThl_Uh0ZArdhx1ma79kAvmAH1CtJL_I"; // Replace with your bot token
    private static final String CHAT_ID = "841252589"; // Replace with your chat ID

    public void sendNotification(String message) {
        try {
            // Encode the message to handle special characters
            String encodedMessage = URLEncoder.encode(message, "UTF-8");

            // Construct the API URL
            String apiUrl = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage?chat_id=" + CHAT_ID + "&text=" + encodedMessage;

            // Debug: Print the full API URL
            System.out.println("API URL: " + apiUrl);

            // Open a connection
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Check the response code
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Print the response
            System.out.println("Response from Telegram: " + response);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
