package org.nttdata.backend.beans;


import org.nttdata.backend.dao.impl.UserDAOImpl;
import org.nttdata.backend.model.User;
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
    private boolean usersFetchedSuccessfully;
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
        usersFetchedSuccessfully = userList != null && !userList.isEmpty();
    }


    public boolean isUsersFetchedSuccessfully() {
		return usersFetchedSuccessfully;
	}

	public void setUsersFetchedSuccessfully(boolean usersFetchedSuccessfully) {
		this.usersFetchedSuccessfully = usersFetchedSuccessfully;
	}

	public void addUser() {
        userDao.addUser(newUser);
        loadUsers();
        newUser = new User(); 
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User added successfully");
        FacesContext.getCurrentInstance().addMessage(null, message);
        PrimeFaces.current().ajax().update(":form:usersTable");
        PrimeFaces.current().executeScript("PF('userDialogVar').hide();");    
    }

    public void deleteUser(int userId) {
        userDao.removeUser(userId);
        loadUsers(); 
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User deleted successfully");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void onRowEdit(RowEditEvent<User> event) {
        User editedUser = (User) event.getObject();
        userDao.updateUser(editedUser);
        FacesMessage msg = new FacesMessage("User Edited", String.valueOf(editedUser.getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<User> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(((User) event.getObject()).getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
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

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

}
