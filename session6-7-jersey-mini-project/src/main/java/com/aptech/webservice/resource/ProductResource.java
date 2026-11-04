package com.aptech.webservice.resource;

import java.net.URI;
import java.util.List;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.aptech.webservice.dao.ProductDatabase;
import com.aptech.webservice.model.Product;

/**
 * RESTful Resource Class: ProductResource.
 * 
 * For Beginners:
 * This class is the "Reception Desk" / "Waiter" of our Web Service.
 * Every incoming HTTP request (from a web browser, mobile phone, or Postman)
 * is routed to one of the methods below based on:
 * 1. The URL Path (@Path)
 * 2. The HTTP Verb (@GET, @POST, @PUT, @DELETE)
 */
@Path("/products")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class ProductResource {

    /**
     * 1. HTTP GET: Retrieve all products OR filter by category query parameter.
     * 
     * Example URLs:
     * - http://localhost:8080/api/products
     * - http://localhost:8080/api/products?category=Accessories
     */
    @GET
    public Response getAllProducts(@QueryParam("category") String category) {
        System.out.println("[RESOURCE LOG] GET request received. Category filter: " + category);
        
        List<Product> products;
        if (category != null && !category.trim().isEmpty()) {
            products = ProductDatabase.getProductsByCategory(category);
        } else {
            products = ProductDatabase.getAllProducts();
        }

        // Return HTTP 200 OK with the product list serialized to JSON/XML
        return Response.ok(products).build();
    }

    /**
     * 2. HTTP GET with Path Parameter: Retrieve a single product by its ID.
     * 
     * Example URL:
     * - http://localhost:8080/api/products/1
     */
    @GET
    @Path("/{id}")
    public Response getProductById(@PathParam("id") int id) {
        System.out.println("[RESOURCE LOG] GET request for Product ID: " + id);
        
        Product product = ProductDatabase.getProductById(id);
        
        if (product == null) {
            // Return HTTP 404 Not Found if product doesn't exist
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("{\"error\": \"Product with ID " + id + " not found.\"}")
                           .type(MediaType.APPLICATION_JSON)
                           .build();
        }

        // Return HTTP 200 OK with the found product
        return Response.ok(product).build();
    }

    /**
     * 3. HTTP POST: Create/Add a new product.
     * 
     * Example URL:
     * - POST http://localhost:8080/api/products
     * 
     * The client sends a JSON body like:
     * {
     *   "name": "USB-C Hub Multiport",
     *   "price": 35.99,
     *   "category": "Accessories"
     * }
     */
    @POST
    public Response createProduct(Product newProduct) {
        System.out.println("[RESOURCE LOG] POST request to create product: " + newProduct);

        if (newProduct == null || newProduct.getName() == null || newProduct.getName().trim().isEmpty()) {
            // Return HTTP 400 Bad Request
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"error\": \"Product name is required.\"}")
                           .type(MediaType.APPLICATION_JSON)
                           .build();
        }

        Product savedProduct = ProductDatabase.addProduct(newProduct);

        // Good REST practice: Return HTTP 201 Created and the URI of the newly created item
        URI createdUri = URI.create("/api/products/" + savedProduct.getId());
        return Response.created(createdUri).entity(savedProduct).build();
    }

    /**
     * 4. HTTP PUT: Update an existing product.
     * 
     * Example URL:
     * - PUT http://localhost:8080/api/products/2
     * 
     * The client sends the updated fields in the JSON body.
     */
    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") int id, Product productToUpdate) {
        System.out.println("[RESOURCE LOG] PUT request to update Product ID: " + id);

        Product updated = ProductDatabase.updateProduct(id, productToUpdate);

        if (updated == null) {
            // Return HTTP 404 Not Found
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("{\"error\": \"Cannot update. Product with ID " + id + " does not exist.\"}")
                           .type(MediaType.APPLICATION_JSON)
                           .build();
        }

        // Return HTTP 200 OK with the updated product
        return Response.ok(updated).build();
    }

    /**
     * 5. HTTP DELETE: Remove a product by ID.
     * 
     * Example URL:
     * - DELETE http://localhost:8080/api/products/3
     */
    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") int id) {
        System.out.println("[RESOURCE LOG] DELETE request for Product ID: " + id);

        boolean removed = ProductDatabase.deleteProduct(id);

        if (!removed) {
            // Return HTTP 404 Not Found
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("{\"error\": \"Cannot delete. Product with ID " + id + " does not exist.\"}")
                           .type(MediaType.APPLICATION_JSON)
                           .build();
        }

        // Return HTTP 200 OK with confirmation message
        return Response.ok("{\"message\": \"Product with ID " + id + " was successfully deleted.\"}")
                       .type(MediaType.APPLICATION_JSON)
                       .build();
    }
}
