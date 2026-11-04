package com.aptech.persistence;

import java.util.List;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {
    private final StudentStore store;

    public StudentResource(StudentStore store) {
        this.store = store;
    }

    @GET
    public List<Student> list(@QueryParam("name") String name) {
        return name == null ? store.list() : store.search(name);
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") long id) {
        if (id < 1) return Response.status(400).entity(new Error("Id must be positive.")).build();
        Student student = store.find(id);
        if (student == null) return Response.status(404).entity(new Error("Student not found.")).build();
        return Response.ok(student).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Input input, @Context UriInfo uriInfo) {
        try {
            Student saved = store.add(input == null ? null : input.name);
            return Response.created(uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(saved.getId())).build()).entity(saved).build();
        } catch (IllegalArgumentException problem) {
            return Response.status(400).entity(new Error(problem.getMessage())).build();
        }
    }

    public static class Input {
        public String name;
    }

    public static class Error {
        public String message;

        public Error(String message) {
            this.message = message;
        }
    }
}
