package org.nttdata.frontend.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nttdata.frontend.models.Post;
import org.nttdata.frontend.models.Response;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class PostService {
    private final String restResourceUrl = "http://localhost:8080/backend_war/api/posts/";
    private final ObjectMapper mapper = new ObjectMapper();
    private HttpClient client;

    public PostService() {
        client = HttpClient.newHttpClient();
    }




    public Response addPost(Post post) {
        try {
            String json = mapper.writeValueAsString(post);
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
    public List<Post> getPosts() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(restResourceUrl))
                    .GET()
                    .build();
            return fetchPosts(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Post getPostById(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(restResourceUrl + id))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return mapper.readValue(response.body(), Post.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Post> getPostsByUser(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(restResourceUrl + "user/" + id))
                    .GET()
                    .build();
            return fetchPosts(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response deletePost(int id) {
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

    public List<Post> getPostsByKeyword(String keyword) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(restResourceUrl + "search?q=" + keyword))
                    .GET()
                    .build();
            return fetchPosts(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Post> fetchPosts(HttpRequest request) throws java.io.IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String result = response.body();
        List<Post> posts = mapper.readValue(result, new TypeReference<List<Post>>(){});
        return posts;
    }
    private Response fetchResponse(HttpRequest request) throws java.io.IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String result = response.body();
        return mapper.readValue(result, Response.class);
    }
}
