package org.nttdata.frontend.beans;

import org.nttdata.frontend.models.Post;
import org.nttdata.frontend.services.PostService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "postBean", eager = true)
public class PostBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final PostService postService = new PostService();
    private List<Post> posts = new ArrayList<>();
    private Post post;
    private Post editPost;
    private Post newPost = new Post();
    @ManagedProperty(value="#{userBean}")
    private UserBean userBean;

    public Post getNewPost() {
        return newPost;
    }

    public void setNewPost(Post newPost) {
        this.newPost = newPost;
    }

    @ManagedProperty(value = "#{param.id}")
    private String id;
    private String keyword;

    @PostConstruct
    public void init() {}

    public List<Post> getPosts() {
        if (keyword != null && !keyword.isEmpty()) {
            posts = postService.getPostsByKeyword(keyword);
        } else {
            posts = postService.getPosts();
        }
        return posts;
    }

    public Post getPost() {
        if (id == null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "No post specified", "Unable to show post");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        }
        post = postService.getPostById(Integer.parseInt(id));
        if (post == null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "No post found", "Unable to show post");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        return post;
    }

    public String addPost() {
        newPost.setUser(userBean.getLoggedUser());
        postService.addPost(newPost);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Post added", "Post added successfully");
        FacesContext.getCurrentInstance().addMessage(null, message);
        newPost = new Post();
        return "my-posts?faces-redirect=true";
    }

    public String updatePost() {
        postService.updatePost(editPost);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Post updated", "Post updated successfully");
        FacesContext.getCurrentInstance().addMessage(null, message);
        return "my-posts?faces-redirect=true";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String truncateContent(String content) {
        if (content.length() > 40) {
            return content.substring(0, 40) + "...";
        } else {
            return content;
        }
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public Post getEditPost() {
        return editPost;
    }

    public void setEditPost(Post editPost) {
        this.editPost = editPost;
    }
}
