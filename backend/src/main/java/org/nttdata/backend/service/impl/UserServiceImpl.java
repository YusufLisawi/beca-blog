package org.nttdata.backend.service.impl;


import org.nttdata.backend.dao.impl.UserDAOImpl;
import org.nttdata.backend.model.Response;
import org.nttdata.backend.model.User;
import org.nttdata.backend.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserServiceImpl implements UserService {

    private static UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    @POST
    @Path("/add")
    public Response addUser(User p) {
        Response response = new Response();
        if (userDAO.getUserById(p.getId()) != null) {
            response.setStatus(false);
            return response;
        }
        userDAO.addUser(p);
        response.setStatus(true);
        response.setMessage("User created successfully");
        return response;
    }

    @Override
    @PUT
    @Path("/update")
    public Response updateUser(User u) {
        Response response = new Response();
        if (userDAO.getUserById(u.getId()) == null) {
            response.setStatus(false);
            response.setMessage("User Doesn't Exists");
            return response;
        }
        userDAO.updateUser(u);
        response.setStatus(true);
        response.setMessage("User updated successfully");
        return response;
    }

    @Override
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        Response response = new Response();
        if (userDAO.getUserById(id) == null) {
            response.setStatus(false);
            response.setMessage("User Doesn't Exists");
            return response;
        }
        userDAO.removeUser(id);
        response.setStatus(true);
        response.setMessage("User deleted successfully");
        return response;
    }

    @Override
    @GET
    @Path("/{id}")
    public User getUser(@PathParam("id") int id) {
        return userDAO.getUserById(id);
    }

    @Override
    @GET
    @Path("/")
    public List<User> getAllUsers() {
        return userDAO.listUsers();
    }
}
