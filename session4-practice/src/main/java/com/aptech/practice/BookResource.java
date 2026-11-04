package com.aptech.practice;

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

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {
    private final BookStore store;

    public BookResource(BookStore store) {
        this.store = store;
    }

    @GET
    public List<Book> list(@QueryParam("title") String title) {
        return store.list(title);
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") long id) {
        if (id < 1) return Response.status(400).entity(new Error("Book id must be positive.")).build();
        Book book = store.find(id);
        if (book == null) return Response.status(404).entity(new Error("Book not found.")).build();
        return Response.ok(book).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Input input, @Context UriInfo uriInfo) {
        try {
            Book saved = store.add(input == null ? null : input.title);
            return Response.created(uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(saved.getId())).build()).entity(saved).build();
        } catch (IllegalArgumentException problem) {
            return Response.status(400).entity(new Error(problem.getMessage())).build();
        }
    }

    public static class Input {
        public String title;
    }

    public static class Error {
        public String message;

        public Error(String message) {
            this.message = message;
        }
    }
}
