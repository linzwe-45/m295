package project.services;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import project.autor.Autor;
import project.db.AutorDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Path("/service")
public class AutorResource {

    Logger logger = LogManager.getLogger(AutorResource.class);
    //GET
    @GET
    @Path("/ping")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public Response ping() {
        return Response.ok("API is running").build();
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response everyAutor() {

        AutorDAO dao = new AutorDAO();
        ArrayList<Autor> autorliste = null;
        try {
            autorliste = dao.getEveryModul();
            logger.info("try fetch every author");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        if (autorliste.size() != 0) {
            logger.info("at least one author was fetched");
            return Response.ok(autorliste).build();
        } else
        {
            logger.error("authors not found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/counter")
    @RolesAllowed({"ADMIN", "USER"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response countEveryAutor() {

        AutorDAO dao = new AutorDAO();
        ArrayList<Autor> autorliste = null;
        try {
            autorliste = dao.getEveryModul();
            logger.info("try fetch every author");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        if (autorliste.size() != 0) {
            logger.info("at least one author was fetched");
            int count = autorliste.size();
            return Response.ok(count + " Autoren wurden in der Liste gefunden.").build();
        } else
        {
            logger.error("authors not found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/byId")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public Response findAutorById(@QueryParam("id") int id) {
        AutorDAO dao = new AutorDAO();
        Autor a = null;
        try {
            a = dao.getAutorById(id);
            logger.info("try fetch author by id");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        if (a != null) {
            logger.info("author fetched by id");
            return Response.ok(a).build();
        } else
        {
            logger.error("author not found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/byDatum")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public Response findAutorByDate(@QueryParam("datum") String datum) {
        //Problem mit LocalDate eingabe
        LocalDate date;
        try {
            date = LocalDate.parse(datum); // yyyy-MM-dd
        } catch (Exception e) {
            logger.error("Ungültiges Datumformat in der Eingabe");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        AutorDAO dao = new AutorDAO();
        Autor a = null;
        try {
            a = dao.getAutorByDate(date);
            logger.info("try fetch author by gebdat");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        if (a != null) {
            logger.info("author fetched by gebdat");
            return Response.ok(a).build();
        } else
        {
            logger.error("author not found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    //Update
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)//benutzt json
    @Produces(MediaType.TEXT_PLAIN)//erstellt text
    @RolesAllowed("ADMIN")
    public Response updateAutor(@Valid Autor a) {
        AutorDAO dao = new AutorDAO();
        int rows = 0;
        try {
            rows = dao.updateAutor(a);
            logger.info("Autor updated");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        if (rows == 1) {
            return Response.ok("Autor " + a.idAutor + " updated").build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Kein Autor zum updaten, Autor darf nicht leer sein").build();
        //if Autor exisitert schon Fehler


    }

    //Delete
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("ADMIN")
    public Response deleteAutor(@PathParam("id") int id) {
        AutorDAO dao = new AutorDAO();
        int rows = 0;
        try {
            rows = dao.deleteAutor(id);
            logger.info("autor deleted");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        if (rows == 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Kein autor geloscht").build();
        }
        return Response.ok("Autor " + id + " geloscht").build();
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("ADMIN")
    public Response deleteAll() {
        //Delete from Modul where nr = nr
        AutorDAO dao = new AutorDAO();
        int rows = 0;
        try {
            rows = dao.deleteAll();
            logger.info("Try delete Inhalt");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        if (rows == 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Inhalt konnte nicht geloscht werden").build();
        }
        return Response.ok("Inhalt deleted").build();
    }

    //Create
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("ADMIN")
    public Response addTAutor(@Valid Autor a) {
        //Db Instert into Modul
        AutorDAO dao = new AutorDAO();
        int rows = 0;
        try {
            rows = dao.createAutor(a);
            logger.info("Try create Autor");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        if (rows == 1) {
            return Response.ok(a).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Kein Autor erstelllt").build();
        //if m exisitert schon Fehler
    }


    @POST
    @Path("/init")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("ADMIN")
    public Response addDB() {
        //Db Instert into Modul
        AutorDAO dao = new AutorDAO();
        int rows = 0;
        try {
            rows = dao.createTables();
            logger.info("Try create DB");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        if (rows == 1) {
            return Response.ok("Gut").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Keine Tabelle erstelllt").build();
        //if m exisitert schon Fehler
    }
}
