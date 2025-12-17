package project.services;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
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
    public Response everyAuthor() {

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
            logger.error("No authors found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/counter")
    @RolesAllowed({"ADMIN", "USER"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response countEveryAuthor() {

        AutorDAO dao = new AutorDAO();
        ArrayList<Autor> authorlist = null;
        try {
            authorlist = dao.getEveryModul();
            logger.info("try fetch every author");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        if (authorlist.size() != 0) {
            logger.info("At least one author was fetched");
            int count = authorlist.size();
            return Response.ok(count + " Autoren wurden in der Liste gefunden.").build();
        } else
        {
            logger.error("No authors found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/byId")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public Response findAuthorById(@QueryParam("id") int id) {
        AutorDAO dao = new AutorDAO();
        Autor a = null;
        try {
            a = dao.getAutorById(id);
            logger.info("try fetch author by id");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        if (a != null) {
            logger.info("Author successfully fetched by id");
            return Response.ok(a).build();
        } else
        {
            logger.error("Author not found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/byDatum")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public Response findAuthorByDate(
            @QueryParam("datum")
            String datum) {
        //Problem mit Datentyp LocalDate eingabe
        LocalDate date;
        try {
            date = LocalDate.parse(datum); // yyyy-MM-dd
        } catch (Exception e) {
            logger.error("Ungültiges Datumformat in der Eingabe");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (date.isAfter(LocalDate.now())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Datum muss in der Vergangenheit liegen")
                    .build();
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
            logger.info("Author successfully fetched by gebdat");
            return Response.ok(a).build();
        } else
        {
            logger.error("Author not found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    //Update
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("ADMIN")
    public Response updateAuthor(@Valid Autor a) {
        AutorDAO dao = new AutorDAO();
        int rows = 0;
        try {
            rows = dao.updateAutor(a);
            logger.info("Try update Author");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        if (rows == 1) {
            logger.info("Author successfully updated");
            return Response.ok("Autor " + a.idAutor + " updated").build();
        }else {
            logger.error("Author not found");
            return Response.status(Response.Status.NOT_FOUND)
                .entity("Kein Autor zum updaten, Autor darf nicht leer sein").build();
        }
    }

    //Delete
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("ADMIN")
    public Response deleteAuthor(@PathParam("id") int id) {
        AutorDAO dao = new AutorDAO();
        int rows = 0;
        try {
            rows = dao.deleteAutor(id);
            logger.info("Try delete author");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        if (rows != 0) {
            logger.info("Author successfully deleted");
            return Response.ok("Autor " + id + " geloscht").build();
        } else {
            logger.error("Author not found");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Kein Autor geloscht").build();
        }
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("ADMIN")
    public Response deleteAll() {
        AutorDAO dao = new AutorDAO();
        int rows = 0;
        try {
            rows = dao.deleteAll();
            logger.info("Try delete Inhalt");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        if (rows == 0) {
            logger.error("Entries could not be deleted");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Inhalt konnte nicht geloscht werden").build();
        } else {
            logger.info("All rows deleted in Table Autor");
            return Response.ok("Inhalt deleted").build();
        }
    }

    //Create
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public Response addAuthor(@Valid Autor a) {
        AutorDAO dao = new AutorDAO();
        int rows = 0;
        try {
            rows = dao.createAutor(a);
            logger.info("Try create Autor");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        if (rows == 1) {
            logger.info("Author added successfully");
            return Response.ok(a).build();
        } else {
            logger.error("Author not added");
            return Response.status(Response.Status.CONFLICT).entity("Kein Autor erstelllt").build();
        }
    }

    @POST
    @Path("/init")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public Response addDB() {
        AutorDAO dao = new AutorDAO();
        int rows = 0;
        try {
            rows = dao.createTables();
            logger.info("Try create DB");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        if (rows == 1) {
            logger.info("DB created");
            return Response.ok("Datenbanktabellen wurde mit je 2 Eintraegen erstellt").build();
        } else {
            logger.error("DB not created");
            return Response.status(Response.Status.CONFLICT).entity("Keine Tabellen erstelllt").build();
        }
    }
}
