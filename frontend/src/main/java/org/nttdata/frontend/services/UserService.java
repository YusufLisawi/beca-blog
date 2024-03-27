package org.nttdata.frontend.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nttdata.frontend.models.User;
import org.nttdata.frontend.models.Response;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class UserService {
    private final String restResourceUrl = "http://localhost:8080/backend/api/users/";
    private final ObjectMapper mapper = new ObjectMapper();
    private HttpClient client;

    public UserService() {
        client = HttpClient.newHttpClient();
    }




    public Response addUser(User user) {
        try {
            String json = mapper.writeValueAsString(user);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(restResourceUrl + "add"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            return fetchResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<User> getUsers() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(restResourceUrl))
                    .GET()
                    .build();
            return fetchUsers(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserById(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(restResourceUrl + id))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return mapper.readValue(response.body(), User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUsersByUser(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(restResourceUrl + "user/" + id))
                    .GET()
                    .build();
            return fetchUsers(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response deleteUser(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(restResourceUrl + id))
                    .DELETE()
                    .build();
            return fetchResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Response updateUser(User user) {
        try {
            String json = mapper.writeValueAsString(user);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(restResourceUrl + "update"))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            return fetchResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<User> getUsersByKeyword(String keyword) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(restResourceUrl + "search?q=" + keyword))
                    .GET()
                    .build();
            return fetchUsers(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<User> fetchUsers(HttpRequest request) throws java.io.IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String result = response.body();
        List<User> Users = mapper.readValue(result, new TypeReference<List<User>>(){});
        return Users;
    }
    private Response fetchResponse(HttpRequest request) throws java.io.IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String result = response.body();
        return mapper.readValue(result, Response.class);
    }
}
