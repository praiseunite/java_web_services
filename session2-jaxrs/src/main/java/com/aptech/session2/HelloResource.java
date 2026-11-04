package com.aptech.session2;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/greeting")
public class HelloResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String greet(@DefaultValue("Student") @QueryParam("name") String name) {
        if (name.isBlank()) {
            name = "Student";
        }
        return "Hello, " + name.trim() + "!";
    }
}
