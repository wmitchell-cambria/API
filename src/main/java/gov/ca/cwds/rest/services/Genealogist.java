package gov.ca.cwds.rest.services;

import com.google.inject.Inject;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipTo;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.HashSet;
import java.util.Set;

public class Genealogist {
    ClientDao clientDao;

    /**
     * Constructor
     *
     * @param clientDao The Client Dao object
     */
    @Inject
    public Genealogist(ClientDao clientDao){
        this.clientDao = clientDao;
    }

    public Relationship buildRelationForClient(String clientId, ClientRelationship[] relatedClients) {
        Set<RelationshipTo> relations = new HashSet<>();
        Client primaryClient;
        for (ClientRelationship clientRelationship : relatedClients) {
            RelationshipTo relationship = buildRelationship(clientRelationship);
            relations.add(relationship);
        }
        primaryClient = findClient(clientId);
        return new Relationship(primaryClient, relations);
    }

    private RelationshipTo buildRelationship(ClientRelationship clientRelationship ){
        String secondaryClientId = clientRelationship.getSecondaryClientId();
        Client secondaryClient = findClientRelation(secondaryClientId);
        return new RelationshipTo(clientRelationship, secondaryClient);
    }

    private Client findClientRelation(String id){
        return this.clientDao.find(id);
    }

    private Client findClient(String id){
        return this.clientDao.find(id);
    }
}
