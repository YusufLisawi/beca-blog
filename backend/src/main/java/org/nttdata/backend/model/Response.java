package org.nttdata.backend.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Response {
    public Response( String message, boolean status) {
        this.status = status;
        this.message = message;
    }
    public Response( String message, boolean status, User user) {
        this.status = status;
        this.message = message;
        this.user = user;
    }
    public Response() {
    }

    private boolean status;
    private String message;
    private User user;

    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status){
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public User getUser() {
        return user;
    }
}
