package org.nttdata.backend.service.impl;

import org.nttdata.backend.dao.impl.UserDAOImpl;
import org.nttdata.backend.model.Response;
import org.nttdata.backend.model.User;
import org.nttdata.backend.service.AuthService;

public class AuthServiceImpl implements AuthService {

    private UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    public Response register(User u) {
        User existingUser = userDAO.getUserByUsername(u.getUsername());
        if (existingUser != null) {
            return new Response("Username is already taken", false);
        }

//        String hashedPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());
//        User newUser = new User(u.getUsername(), hashedPassword);
//        userDAO.save(newUser);

        return new Response("Registration successful", true);
    }

    @Override
    public Response login(User u) {
        return null;
    }
}