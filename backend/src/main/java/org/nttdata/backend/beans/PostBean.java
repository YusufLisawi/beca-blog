package org.nttdata.backend.beans;

import org.nttdata.backend.dao.impl.PostDAOImpl;
import org.nttdata.backend.model.Post;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import java.io.Serializable;
import java.util.List;


@ManagedBean(name = "postBean", eager = true)
@ViewScoped
public class PostBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private PostDAOImpl postDao;
    private List<Post> postList;

    @PostConstruct
    public void init() {
        postDao = new PostDAOImpl();
        postList = postDao.listPosts();
    }
    
    public void updateData() {
        postList = postDao.listPosts();
        PrimeFaces.current().ajax().update(":form:postsTable");
    }
    
    public void deletePost(int postId) {
        postDao.removePost(postId);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Post deleted successfully");
        FacesContext.getCurrentInstance().addMessage(null, message);
        updateData();
    }

    public void onRowEdit(RowEditEvent<Post> event) {
        Post editedPost = event.getObject();
        postDao.updatePost(editedPost);
        FacesMessage msg = new FacesMessage("Post Edited", "Post " + editedPost.getTitle() + " edited successfully");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<Post> event) {
//    	 FacesMessage msg = new FacesMessage("Edit Cancelled", null);
//         FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<Post> getPostList() {
//        postList = postDao.listPosts();
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public String truncateContent(String content) {
        if (content.length() > 20) {
            return content.substring(0, 20) + "...";
        } else {
            return content;
        }
    }
}
