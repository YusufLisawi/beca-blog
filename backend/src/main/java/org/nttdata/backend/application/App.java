package org.nttdata.backend.application;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class App extends Application {
    private Set<Object> singletons;
    public App() {
        singletons = new HashSet<Object>();
    }
    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
