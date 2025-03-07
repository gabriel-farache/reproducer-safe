package org.acme.poc.sonataflow.consent.web.client;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login/oauth/access_token")
@RegisterRestClient(configKey = "githubOAuth")
public interface AccessTokenRestClient {

    String ACCEPT = MediaType.APPLICATION_JSON_TYPE.toString();

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Response requestAccessToken(@FormParam("client_id") String clientId, @FormParam("client_secret") String clientSecret, @FormParam("code") String sessionCode, @FormParam("accept") String accept);
}
