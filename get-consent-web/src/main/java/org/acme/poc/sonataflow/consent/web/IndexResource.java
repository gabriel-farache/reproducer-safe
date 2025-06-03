package org.acme.poc.sonataflow.consent.web;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class IndexResource {

    @Inject
    Template index;

    @ConfigProperty(name = "org.acme.poc.sonataflow.consent.web.github.client_id")
    String clientId;

    @ConfigProperty(name = "org.acme.poc.sonataflow.consent.web.github.scope")
    String scope;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return index.data("clientId", clientId).data("scope", scope);
    }

}
