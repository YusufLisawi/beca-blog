package org.nttdata.backend.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.nttdata.backend.dao.impl.UserDAOImpl;
import org.nttdata.backend.model.Response;
import org.nttdata.backend.model.User;
import org.nttdata.backend.service.AuthService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthServiceImpl implements AuthService {

    private UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    @POST
    @Path("/register")
    public Response register(User u) {
        User existingUser = userDAO.getUserByUsername(u.getUsername());
        if (existingUser != null) {
            return new Response("Username is already taken", false);
        }

        String hashedPassword = BCrypt.withDefaults().hashToString(12, u.getPassword().toCharArray());
        User newUser = new User();
        newUser.setUsername(u.getUsername());
        newUser.setPassword(hashedPassword);
        newUser.setAdmin(u.isAdmin());

        userDAO.addUser(newUser);

        return new Response("Registration successful", true);
    }

    @Override
    @POST
    @Path("/login")
    public Response login(User u) {
        User existingUser = userDAO.getUserByUsername(u.getUsername());
        if (existingUser == null) {
            return new Response("User not found", false);
        }

        BCrypt.Result result = BCrypt.verifyer().verify(u.getPassword().toCharArray(), existingUser.getPassword());
        if (!result.verified) {
            return new Response("Invalid password", false);
        }

        return new Response("Login successful", true, existingUser);
    }
}