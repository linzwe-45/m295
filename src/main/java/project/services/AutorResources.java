package project.services;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import project.autor.Autor;
import project.db.AutorDAO;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Path("/service")
public class AutorResources {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed({"ADMIN", "USER"})
    public Response findAutorById(@QueryParam("id") int id) {
        //Logger logger = LogManager.getLogger(AutorResource.class);
        //Eigentlich DB Abfrage zu nr
        AutorDAO dao = new AutorDAO();
        Autor a = null;
        try {
            a = dao.getAutorById(id);
            //logger.info("modul fetched");
        } catch (SQLException e) {
            //logger.error(e.getMessage());
        }
        if (a != null) {
            return Response.ok(a).build();
        } else
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
