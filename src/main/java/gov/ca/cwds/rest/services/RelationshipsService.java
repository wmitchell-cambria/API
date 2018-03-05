package gov.ca.cwds.rest.services;

import com.google.inject.Inject;
import gov.ca.cwds.authorizer.ClientAbstractReadAuthorizer;
import gov.ca.cwds.data.cms.ClientRelationshipDao;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.data.persistence.cms.RelationshipWrapper;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipList;
import gov.ca.cwds.rest.services.auth.AuthorizationService;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.shiro.authz.AuthorizationException;

import java.util.*;

public class RelationshipsService  implements TypedCrudsService<String, Relationship, Response> {
    ClientRelationshipDao relationshipDao;
    Genealogist genealogist;
    AuthorizationService authCheckService;

    /**
     * Constructor and injecting the beans
     *
     * @param relationshipDao - relationshipDao instance
     * @param genealogist - genealogist instance
     */
    @Inject
    public RelationshipsService(ClientRelationshipDao relationshipDao, Genealogist genealogist, AuthorizationService authCheckService){
        this.relationshipDao = relationshipDao;
        this.genealogist = genealogist;
        this.authCheckService = authCheckService;
    }

    public Response find(String id) {
        List <RelationshipWrapper> relations = getClientRelationships(id);
        return genealogist.buildRelationships(relations, id);
    }

    private List<RelationshipWrapper> getClientRelationships( String clientId){
        return relationshipDao.findRelationshipsByClientId(clientId);
    }

    public Response findForIds(List<String> clientIds) {
        Set relationships = new HashSet();
        for (String id : clientIds) {
          if(notAuthorized(id)) {
            relationships.add(find(id));
          }
        }
        return new RelationshipList(relationships);
    }

    private boolean notAuthorized(String clientId){
        boolean authorized = true;
        try{
            authCheckService.ensureClientAccessAuthorized(clientId);
        } catch(AuthorizationException e){
            authorized = false;
        }
        return authorized;
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
