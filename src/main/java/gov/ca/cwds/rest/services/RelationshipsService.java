package gov.ca.cwds.rest.services;

import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.api.Response;
import org.apache.commons.lang3.NotImplementedException;

public class RelationshipsService  implements TypedCrudsService<String, Relationship, Response> {
    @Override
    public Response find(String s) {
        throw new NotImplementedException("Find is not implemented");
    }

    @Override
    public Response delete(String s) {
        throw new NotImplementedException("Delete is not implemented");
    }

    @Override
    public Response create(Relationship relationship) {
        throw new NotImplementedException("Create is not implemented");
    }

    @Override
    public Response update(String s, Relationship relationship) {
        throw new NotImplementedException("Update is not implemented");
    }
}
