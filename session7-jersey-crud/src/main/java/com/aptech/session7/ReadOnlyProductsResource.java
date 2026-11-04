package com.aptech.session7;

import java.util.Map;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

// A separate starting mode: no write methods are registered.
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ReadOnlyProductsResource {
    @GET
    public Response list(@QueryParam("category") String category) {
        return Response.ok(ProductStore.findAll(category)).build();
    }
    @GET
    @Path("/{id}")
    public Response find(@PathParam("id") int id) {
        if (id < 1) {
            return Response.status(400).entity(Map.of("error", "id must be positive.")).build();
        }
        Product item = ProductStore.findById(id);
        if (item == null) {
            return Response.status(404).entity(Map.of("error", "Product " + id + " was not found.")).build();
        }
        return Response.ok(item).build();
    }
}
