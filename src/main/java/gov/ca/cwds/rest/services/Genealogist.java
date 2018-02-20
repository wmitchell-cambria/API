package gov.ca.cwds.rest.services;

import com.google.inject.Inject;
import gov.ca.cwds.data.cms.ClientDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.data.persistence.cms.ClientRelationship;
import gov.ca.cwds.rest.api.domain.investigation.Relationship;
import gov.ca.cwds.rest.api.domain.investigation.RelationshipTo;

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

    public Relationship buildRelationForClient(String clientId, ClientRelationship[] primaryRelatedClients, ClientRelationship[] secondaryRelatedClients) {
        Set<RelationshipTo> relations = new HashSet<>();
        Client primaryClient;
        relations.addAll(addRelatedToClients(primaryRelatedClients, clientId));
        relations.addAll(AddRelatedFromClients(secondaryRelatedClients, clientId));
        primaryClient = findClient(clientId);
        return new Relationship(primaryClient, relations);
    }

    private Set<RelationshipTo>  addRelatedToClients(ClientRelationship[] relatedClients, String clientId) {
        Set<RelationshipTo> relations = new HashSet<>();
        for (ClientRelationship clientRelationship : relatedClients) {
            if( clientId.equals(clientRelationship.getPrimaryClientId())){
                RelationshipTo relationship = buildRelatedToClient(clientRelationship);
                relations.add(relationship);
            }
        }
        return relations;
    }

    private RelationshipTo buildRelatedToClient(ClientRelationship clientRelationship ){
        String secondaryClientId = clientRelationship.getSecondaryClientId();
        Client secondaryClient = findClient(secondaryClientId);
        return new RelationshipTo(clientRelationship, secondaryClient);
    }

    private Set<RelationshipTo>  AddRelatedFromClients(ClientRelationship[] relatedClients, String clientId) {
        Set<RelationshipTo> relations = new HashSet<>();
        for (ClientRelationship clientRelationship : relatedClients) {
            if( clientId.equals(clientRelationship.getSecondaryClientId())){
                RelationshipTo relationship = buildRelatedFromClient(clientRelationship);
                relations.add(relationship);
            }
        }
        return relations;
    }

    private RelationshipTo buildRelatedFromClient(ClientRelationship clientRelationship ){
        String primaryClientId = clientRelationship.getPrimaryClientId();
        Client primaryClient = findClient(primaryClientId);
        return new RelationshipTo(clientRelationship, primaryClient);
    }

    private Client findClient(String id){
        return this.clientDao.find(id);
    }
}
