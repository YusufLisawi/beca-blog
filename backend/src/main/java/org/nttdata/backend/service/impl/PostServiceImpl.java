package org.nttdata.backend.service.impl;

import org.nttdata.backend.model.Post;
import org.nttdata.backend.model.Response;
import org.nttdata.backend.service.PostService;
import org.nttdata.backend.dao.impl.PostDAOImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostServiceImpl implements PostService {

    private PostDAOImpl postDAO = new PostDAOImpl();

    @Override
    @POST
    @Path("/add")
    public Response addPost(Post p) {
        Response response = new Response();
        if (postDAO.getPostById(p.getId()) != null) {
            response.setStatus(false);
            return response;
        }
        postDAO.addPost(p);
        response.setStatus(true);
        response.setMessage("Post created successfully");
        return response;
    }

    @Override
    @PUT
    @Path("/update")
    public Response updatePost(Post post) {
        Response response = new Response();
        if (postDAO.getPostById(post.getId()) == null) {
            response.setStatus(false);
            response.setMessage("Post Doesn't Exists");
            return response;
        }
        postDAO.updatePost(post);
        response.setStatus(true);
        response.setMessage("Post updated successfully");
        return response;
    }


    @Override
    @GET
    @Path("/")
    public List<Post> listPosts() {
        return postDAO.listPosts();
    }

    @Override
    @GET
    @Path("/search")
    public List<Post> getPostByKeyword(@QueryParam("q") String keyword) {
        return postDAO.getPostByKeyword(keyword);
    }

    @Override
    @DELETE
    @Path("/{id}")
    public Response removePost(@PathParam("id") int id) {
        Response response = new Response();
        if (postDAO.getPostById(id) == null) {
            response.setStatus(false);
            response.setMessage("Post Doesn't Exists");
            return response;
        }
        postDAO.removePost(id);
        response.setStatus(true);
        response.setMessage("Post deleted successfully");
        return response;
    }

    @Override
    @GET
    @Path("/user/{id}")
    public List<Post> listPostsByUser(@PathParam("id") int id) {
        return postDAO.listPostsByUser(id);
    }

    @Override
    @GET
    @Path("/{id}")
    public Post getPost(@PathParam("id") int id) {
        return postDAO.getPostById(id);
    }
}
