package com.aptech.session2;

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

@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource {
    private static final CourseStore STORE = new CourseStore();

    @GET
    public List<Course> list(@QueryParam("search") String search) {
        return STORE.list(search);
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") int id) {
        if (id < 1) {
            return Response.status(400)
                .entity(new ApiError("Course id must be positive.")).build();
        }
        Course course = STORE.find(id);
        if (course == null) {
            return Response.status(404)
                .entity(new ApiError("Course not found.")).build();
        }
        return Response.ok(course).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(NewCourse input, @Context UriInfo uriInfo) {
        if (input == null || input.title == null || input.title.isBlank()) {
            return Response.status(400)
                .entity(new ApiError("A non-blank title is required.")).build();
        }
        String title = input.title.trim();
        if (title.length() > 80) {
            return Response.status(400)
                .entity(new ApiError("Title must be at most 80 characters.")).build();
        }
        Course saved = STORE.add(title);
        return Response.created(uriInfo.getAbsolutePathBuilder()
            .path(String.valueOf(saved.id)).build()).entity(saved).build();
    }
}
