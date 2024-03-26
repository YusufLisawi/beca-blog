package org.nttdata.frontend.beans;

import org.nttdata.frontend.models.Post;
import org.nttdata.frontend.models.Response;
import org.nttdata.frontend.models.User;
import org.nttdata.frontend.services.PostService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "userBean", eager = true)
@SessionScoped
public class UserBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private User loggedUser = null;
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

    public void deletePost(int id) {
        Response res = postService.deletePost(id);
        addMessage(res.isStatus() ? "Confirmed" : "Failed", res.getMessage());
        if (res.isStatus()) {
            posts = getPosts(); // refetch the posts
        }
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}