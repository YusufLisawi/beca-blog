package org.nttdata.backend.dao;


import org.nttdata.backend.model.Post;
import org.nttdata.backend.model.User;

import java.util.List;

public interface UserDAO {
    public void addUser(User user);
    public void updateUser(User user);
    public List<User> listUsers();
    public User getUserById(int id);
    public void removeUser(int id);
    public User getUserByUsername(String username);
    public User getUserByNameAndPassword(String username, String password);
}
