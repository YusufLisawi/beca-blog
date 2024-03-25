package org.nttdata.frontend.beans;

import org.nttdata.frontend.models.Post;
import org.nttdata.frontend.services.PostService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "postBean", eager = true)
@SessionScoped
public class PostBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final PostService postService = new PostService();
    private List<Post> posts = new ArrayList<>();

    @PostConstruct
    public void init() {
    }

    public List<Post> getPosts() {
        posts = postService.getPosts();
        return posts;
    }
}
