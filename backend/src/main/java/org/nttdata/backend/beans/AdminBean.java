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
    private final AuthServiceImpl authService = new AuthServiceImpl();
    private User loggedUser = null;
    private User selectedUser;
    private String newPassword;

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    @PostConstruct
    public void init() {
        userDao = new UserDAOImpl();
        userList = userDao.listUsers();
    }

    public void updateData() {
        userList = userDao.listUsers();
        PrimeFaces.current().ajax().update(":form:usersTable");
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
            PrimeFaces.current().executeScript("PF('userDialogVar').hide();");

        }
        newUser = new User();
        updateData();
    }

    public void deleteUser(int userId) {
        userDao.removeUser(userId);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User deleted successfully");
        FacesContext.getCurrentInstance().addMessage(null, message);
        updateData();
    }
    
    public void onRowEdit(RowEditEvent<User> event) {
        User editedUser = event.getObject();
        userDao.updateUser(editedUser);
        FacesMessage msg = new FacesMessage("User Edited", "User " + editedUser.getUsername() + " edited successfully");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<User> event) {
//        FacesMessage msg = new FacesMessage("Edit Cancelled", null);
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void openChangePassword(int id) {
        selectedUser = userDao.getUserById(id);
        PrimeFaces.current().executeScript("PF('passwordDialog').show();");
    }

    public void changePassword() {
        String hashedPassword = BCrypt.withDefaults().hashToString(12, newPassword.toCharArray());
        selectedUser.setPassword(hashedPassword);
        userDao.updateUser(selectedUser);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Password changed successfully");
        FacesContext.getCurrentInstance().addMessage(null, message);
        PrimeFaces.current().ajax().update(":form:usersTable");
        PrimeFaces.current().executeScript("PF('passwordDialog').hide();");
        selectedUser = new User();
        newPassword = "";
    }
    
    public List<User> getUserList() {
//        userList = userDao.listUsers();
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

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
