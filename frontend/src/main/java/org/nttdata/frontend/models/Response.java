package org.nttdata.frontend.models;

public class Response {
    private boolean status;
    private String message;
    private User user;
    public boolean isStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public User getUser() {
        return user;
    }
}
