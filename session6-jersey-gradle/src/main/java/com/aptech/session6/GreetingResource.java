package com.aptech.session6;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/greeting")
@Produces(MediaType.TEXT_PLAIN)
public class GreetingResource {
    @GET
    public Response greet(@DefaultValue("learner") @QueryParam("name") String name) {
        String cleanedName = name.trim();
        if (cleanedName.isEmpty() || cleanedName.length() > 40) {
            return Response.status(400)
                    .entity("name must contain 1 to 40 characters after trimming.").build();
        }
        return Response.ok(GreetingLogic.message(cleanedName)).build();
    }
}
