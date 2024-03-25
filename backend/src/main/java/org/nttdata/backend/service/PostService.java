package org.nttdata.backend.service;

import org.nttdata.backend.model.Post;
import org.nttdata.backend.model.Response;

import javax.ws.rs.PathParam;
import java.util.List;

public interface PostService {
    public Response addPost(Post post);
    public Response updatePost(Post post);
    public List<Post> listPosts();
    public List<Post> getPostByKeyword(String keyword);
    public Response removePost(int id);
    public List<Post> listPostsByUser(int id);
    public Post getPost(int id);
}
