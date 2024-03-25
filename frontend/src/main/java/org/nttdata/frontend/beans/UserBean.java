package org.nttdata.frontend.beans;

import org.nttdata.frontend.models.User;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean(name = "userBean", eager = true)
@SessionScoped
public class UserBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private User loggedUser = null;

    @PostConstruct
    public void init() {}

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}