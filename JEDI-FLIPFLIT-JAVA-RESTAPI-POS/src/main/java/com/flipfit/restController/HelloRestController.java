package com.flipfit.restController;




import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Path("/helloRestService")
public class HelloRestController {
    @GET
    public String HelloWorld(){
        return "This is my First DropWizard Service!";
    }
}
