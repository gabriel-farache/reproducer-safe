package org.acme.poc.sonataflow.consent.web.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/basic")
@RegisterRestClient(configKey = "workflow")
public interface WorkflowClient {

    /**
     * Executes the workflow in a remote location at http://localhost:8080.
     * @param githubAuthorizationBearer custom authentication header expected by the workflow. See the application.properties file in the `flow-gh` project. Expected value is `Bearer <token>`
     * @return the workflow response
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    Response execWorkflow(@HeaderParam("X-GitHub-Authorization") String githubAuthorizationBearer, String workflowData);

}