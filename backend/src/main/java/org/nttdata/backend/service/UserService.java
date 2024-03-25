package org.nttdata.backend.service;


import org.nttdata.backend.model.Response;
import org.nttdata.backend.model.User;

import java.util.List;

public interface UserService {
    public Response addUser(User u);
    public Response deleteUser(int id);
    public User getUser(int id);
    public List<User> getAllUsers();
    public Response updateUser(User u);

}
