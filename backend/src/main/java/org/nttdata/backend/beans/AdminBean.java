package org.nttdata.backend.beans;


import at.favre.lib.crypto.bcrypt.BCrypt;
import org.nttdata.backend.dao.impl.UserDAOImpl;
import org.nttdata.backend.model.Response;
import org.nttdata.backend.model.User;
import org.nttdata.backend.service.impl.AuthServiceImpl;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "adminBean", eager = true)
@SessionScoped
public class AdminBean implements Serializable {
  
	private static final long serialVersionUID = 1L;
	private UserDAOImpl userDao;
    private List<User> userList;
    private User newUser = new User();
    private User selectedUser;
    private final AuthServiceImpl authService = new AuthServiceImpl();
    private User loggedUser = null;

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    @PostConstruct
    public void init() {
        if (loggedUser != null)
            System.out.println("Logged in as " + loggedUser.getUsername() + " with id " + loggedUser.getId() + " and isAdmin " + loggedUser.isAdmin());
        userDao = new UserDAOImpl();
        loadUsers();
    }

    public void loadUsers() {
        userList = userDao.listUsers();
    }

	public void addUser() {
        Response res = authService.register(newUser);
        if (res != null && !res.isStatus()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Registration Failed", res.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }
        else if (res != null && res.isStatus()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User added successfully");
            FacesContext.getCurrentInstance().addMessage(null, message);
            PrimeFaces.current().ajax().update(":form:usersTable");
            PrimeFaces.current().executeScript("PF('userDialogVar').hide();");

        }
        newUser = new User();
        loadUsers();
    }

    public void deleteUser(int userId) {
        userDao.removeUser(userId);
        loadUsers(); 
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User deleted successfully");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void onRowEdit(RowEditEvent<User> event) {
        User editedUser = (User) event.getObject();
        String hashedPassword = BCrypt.withDefaults().hashToString(12, editedUser.getPassword().toCharArray());
        editedUser.setPassword(hashedPassword);
        userDao.updateUser(editedUser);
        FacesMessage msg = new FacesMessage("User Edited", "User " + editedUser.getUsername() + " edited successfully");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<User> event) {
//        FacesMessage msg = new FacesMessage("Edit Cancelled", null);
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

}
