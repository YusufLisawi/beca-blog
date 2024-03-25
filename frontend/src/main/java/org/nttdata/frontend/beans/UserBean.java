package org.nttdata.frontend.beans;

import org.nttdata.frontend.models.Post;
import org.nttdata.frontend.models.User;
import org.nttdata.frontend.services.PostService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "userBean", eager = true)
@SessionScoped
public class UserBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private User loggedUser = new User(2, "admin", "admin");
    private List<Post> posts = new ArrayList<>();
    private final PostService postService = new PostService();

    @PostConstruct
    public void init() {
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public List<Post> getPosts() {
        posts = postService.getPostsByUser(loggedUser.getId());
        return posts;
    }
}