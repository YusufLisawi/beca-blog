package org.nttdata.frontend.beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nttdata.frontend.models.Response;
import org.nttdata.frontend.models.User;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;

@ManagedBean(name = "authBean", eager = true)
@SessionScoped
public class AuthBean {
    private final String restResourceUrl = "http://localhost:8080/backend_war/api/auth/";
    private User user = new User();
    private final ObjectMapper mapper = new ObjectMapper();
    @ManagedProperty(value="#{userBean}")
    private UserBean userBean;
    private String confirmPassword;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String register() {
        try {
            if (!user.getPassword().equals(confirmPassword)) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Registration Failed", "Passwords do not match");
                FacesContext.getCurrentInstance().addMessage(null, message);
                return "register";
            }
            String json = mapper.writeValueAsString(user);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(restResourceUrl + "register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Response res = mapper.readValue(response.body(), Response.class);
            if (res.isStatus()) {
                userBean.setLoggedUser(res.getUser());
                user = new User();
                return "index?faces-redirect=true";
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Registration Failed", res.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, message);
                return "register";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "register?faces-redirect=true";
        }
    }

    public String login() {
        try {
            String json = mapper.writeValueAsString(user);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(restResourceUrl + "login" ))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Response res = mapper.readValue(response.body(), Response.class);
            if (res.isStatus()) {
                userBean.setLoggedUser(res.getUser());
                user = new User();
                return "index?faces-redirect=true";
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
        userBean.setLoggedUser(null);
        return "index?faces-redirect=true";
    }

    public UserBean getUserBean() {
        return this.userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}