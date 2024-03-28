package org.nttdata.backend.beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nttdata.backend.model.Response;
import org.nttdata.backend.model.User;
import org.nttdata.backend.service.impl.AuthServiceImpl;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ManagedBean(name = "authBean", eager = true)
//@SessionScoped
public class AuthBean {
    private User user = new User();
    private AuthServiceImpl authService = new AuthServiceImpl();
    @ManagedProperty(value="#{adminBean}")
    private AdminBean adminBean;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String login() {
        try {
            Response res = authService.login(user);
            if (res.isStatus() && res.getUser().isAdmin()) {
                adminBean.setLoggedUser(res.getUser());
                user = new User();
                return "admin?faces-redirect=true";
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Failed", res.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, message);
                return "login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "login?faces-redirect=true";
        }
    }

    public String logout() {
        adminBean.setLoggedUser(null);
        return "index?faces-redirect=true";
    }

    public AdminBean getAdminBean() {
        return this.adminBean;
    }

    public void setAdminBean(AdminBean adminBean) {
        this.adminBean = adminBean;
    }
}