/**package com.mycompany.bookstoreapi.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("javaee8")
public class JavaEE8Resource {
    
    @GET
    public Response ping(){
        return Response
                .ok("ping")
                .build();
    }
}
* */

package com.mycompany.bookstoreapi.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/javaee8")
public class JavaEE8Resource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "REST endpoint reached successfully! Java EE 8 is working.";
    }
}
