package org.nttdata.frontend.beans;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.nttdata.frontend.models.User;
import org.nttdata.frontend.models.Response;

import org.nttdata.frontend.services.UserService;
import org.primefaces.event.RowEditEvent;

import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "adminBean")
@ViewScoped
public class AdminBean implements Serializable {
  
    private static final long serialVersionUID = 1L;
    private UserService userService;
    private List<User> userList;
    private boolean usersFetchedSuccessfully;

    @PostConstruct
    public void init() {
        userService = new UserService();
        loadUsers();
    }

    public void loadUsers() {
        userList = userService.getUsers();
        usersFetchedSuccessfully = userList != null && !userList.isEmpty();
    }

    public boolean isUsersFetchedSuccessfully() {
        return usersFetchedSuccessfully;
    }

    public void setUsersFetchedSuccessfully(boolean usersFetchedSuccessfully) {
        this.usersFetchedSuccessfully = usersFetchedSuccessfully;
    }

    public void onRowEdit(RowEditEvent<User> event) {
        User editedUser = event.getObject();
        Response response = userService.updateUser(editedUser);
        if (response != null && response.isStatus()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User updated successfully");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to update user");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public void onRowCancel() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Edit canceled");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
