package org.nttdata.frontend.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;

@ManagedBean
@SessionScoped
public class AuthBean {
    private String username;
    private String password;

    // Add getters and setters for username and password

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean login() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://api.example.com/users?username=" + username))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the response to get the password
            String correctPassword = parsePassword(response.body());

            if (password.equals(correctPassword)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String parsePassword(String responseBody) {
        // Implement this method to parse the password from the response body
        // This will depend on the format of the response body
        return "";
    }
}