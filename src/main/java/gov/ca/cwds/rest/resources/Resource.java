package gov.ca.cwds.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

/**
 * Marker for Resources
 * 
 * @author CWDS API Team 
 */
//@SwaggerDefinition(
//        info = @Info(
//                description = "CWDS API",
//                version = "V1.0",
//                title = "CWDS API Documentation",
//                termsOfService = "share and care",
//                contact = @Contact(name = "Sponge-Bob", email = "sponge-bob@swagger.io", url = "http://swagger.io"),
//                license = @License(name = "Apache 2.0", url = "http://www.apache.org")),
//        consumes = {"application/json" },
//        produces = {"application/json" },
//        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
//        externalDocs = @ExternalDocs(value = "About me", url = "http://about.me/me")
//)
@Produces(gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1)
@Consumes(gov.ca.cwds.rest.core.Api.MEDIA_TYPE_JSON_V1)
public interface Resource {}
