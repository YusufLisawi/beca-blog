package org.nttdata.backend.beans;


import org.nttdata.backend.dao.impl.UserDAOImpl;
import org.nttdata.backend.model.User;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "adminBean")
@ViewScoped
public class AdminBean implements Serializable {
  
	private static final long serialVersionUID = 1L;
	private UserDAOImpl userDao;
    private List<User> userList;
    private User newUser;
    private User selectedUser;
    private boolean usersFetchedSuccessfully;

    @PostConstruct
    public void init() {
        userDao = new UserDAOImpl();
        loadUsers();
        newUser = new User();
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
    }

    public void deleteUser(int userId) {
        userDao.removeUser(userId);
        loadUsers(); 
    }
   
    public void updateUser() {
        if (selectedUser != null) {
            userDao.updateUser(selectedUser);
            loadUsers(); // Refresh the user list after attempting to update
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User updated successfully");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "No user selected for update");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }


    
    // Getters and setters
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
