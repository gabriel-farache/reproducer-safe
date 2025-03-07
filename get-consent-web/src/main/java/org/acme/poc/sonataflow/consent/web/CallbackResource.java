package org.acme.poc.sonataflow.consent.web;

import org.acme.poc.sonataflow.consent.web.client.AccessTokenRestClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Blocking
@Path("/callback")
public class CallbackResource {

    @Inject
    Template callback;

    @RestClient
    AccessTokenRestClient githubClient;

    @ConfigProperty(name = "org.acme.poc.sonataflow.consent.web.github.client_id")
    String clientId;

    @ConfigProperty(name = "org.acme.poc.sonataflow.consent.web.github.client_secret")
    String clientSecret;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("code") String code) {
        if (code == null || code.isEmpty()) {
            return callback.data("access_token", "").data("error", "");
        }
        try (Response response = githubClient.requestAccessToken(clientId, clientSecret, code, AccessTokenRestClient.ACCEPT)) {
            final Form formData = response.readEntity(Form.class);
            final String accessToken = formData.asMap().getFirst("access_token");
            if (accessToken == null || accessToken.isEmpty()) {
                return callback.data("error", formData.asMap().getFirst("error"),
                        "error_description", formData.asMap().getFirst("error_description"),
                        "access_token", "");
            }
            // access_token=&scope=&token_type=
            return callback.data("access_token", accessToken, "error", "");
        }
    }

}
