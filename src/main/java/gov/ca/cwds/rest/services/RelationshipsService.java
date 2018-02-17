package gov.ca.cwds.rest.services;

import com.google.inject.Inject;
import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.api.Response;
import org.apache.commons.lang3.NotImplementedException;

public class RelationshipsService  implements TypedCrudsService<String, Relationship, Response> {
    ClientRelationshipDao relationshipDao;
    Genealogist genealogist;

    /**
     * Constructor and injecting the beans
     *
     * @param relationshipDao - relationshipDao instance
     * @param genealogist - genealogist instance
     */
    @Inject
    public RelationshipsService(ClientRelationshipDao relationshipDao, Genealogist genealogist){
        this.relationshipDao = relationshipDao;
        this.genealogist = genealogist;
    }

    public Response find(String id) {
        ClientRelationship[] relations = relationshipDao.findByPrimaryClientId(id);
        return genealogist.buildRelationForClient(id, relations);
    }

    @Override
    public Response delete(String id) {
        throw new NotImplementedException("Delete is not implemented");
    }

    @Override
    public Response create(Relationship relationship) {
        throw new NotImplementedException("Create is not implemented");
    }

    @Override
    public Response update(String id, Relationship relationship) {
        throw new NotImplementedException("Update is not implemented");
    }
}
