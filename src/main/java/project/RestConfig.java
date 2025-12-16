package project;

import project.exceptions.ConstraintViolationExceptionMapper;
import project.services.AutorResources;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/resources")
public class RestConfig extends Application {
    public Set<Class<?>> getClasses() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new HashSet<Class<?>>(
                Arrays.asList(
                        ConstraintViolationExceptionMapper.class,
                        AutorResources.class
                ));
    }
}
