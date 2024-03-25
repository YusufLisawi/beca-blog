package org.nttdata.backend.service;

import org.nttdata.backend.model.Response;
import org.nttdata.backend.model.User;

public interface AuthService {

    public Response register(User u);
    public Response login(User u);
}