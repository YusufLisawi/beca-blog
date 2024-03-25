package org.nttdata.backend.dao;

import org.nttdata.backend.model.Post;

import java.util.List;

public interface PostDAO {
    public void addPost(Post post);
    public void updatePost(Post post);
    public List<Post> listPosts();
    public List<Post> getPostByKeyword(String keyword);
    public void removePost(int id);
    public Post getPostById(int id);
    public List<Post> listPostsByUser(int id);
}
