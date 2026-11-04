package com.aptech.session7;

import java.net.URI;
import java.util.Map;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {
    @GET
    public Response list(@QueryParam("category") String category) {
        return Response.ok(ProductStore.findAll(category)).build();
    }

    @GET
    @Path("/{id}")
    public Response find(@PathParam("id") int id) {
        if (id < 1) { return error(400, "id must be positive."); }
        Product item = ProductStore.findById(id);
        if (item == null) { return error(404, "Product " + id + " was not found."); }
        return Response.ok(item).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(ProductInput input, @Context UriInfo uriInfo) {
        String problem = ProductRules.validate(input);
        if (problem != null) { return error(400, problem); }
        Product saved = ProductStore.create(input);
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(saved.getId())).build();
        return Response.created(location).entity(saved).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response replace(@PathParam("id") int id, ProductInput input) {
        if (id < 1) { return error(400, "id must be positive."); }
        String problem = ProductRules.validate(input);
        if (problem != null) { return error(400, problem); }
        Product replacement = ProductStore.replace(id, input);
        if (replacement == null) { return error(404, "Product " + id + " was not found."); }
        return Response.ok(replacement).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        if (id < 1) { return error(400, "id must be positive."); }
        if (!ProductStore.delete(id)) { return error(404, "Product " + id + " was not found."); }
        return Response.noContent().build();
    }

    private static Response error(int status, String message) {
        return Response.status(status).entity(Map.of("error", message)).build();
    }
}
