package project.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/hello")
public class HelloResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response hello() {
        return Response.ok("Hello World!").build();
    }
    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello(@PathParam("name") String name){
        return Response.ok("Hello " + name).build();
    }
    @GET
    @Path("/qp")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello2(@QueryParam("name") String name){
        return Response.ok("Hello " + name).build();
    }
}
