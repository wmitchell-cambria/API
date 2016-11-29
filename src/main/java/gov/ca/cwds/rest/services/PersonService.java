package gov.ca.cwds.rest.services;

import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.Person;
import gov.ca.cwds.rest.api.domain.PostedPerson;
import gov.ca.cwds.rest.elasticsearch.db.ElasticsearchDao;
import gov.ca.cwds.rest.jdbi.Dao;
import gov.ca.cwds.rest.jdbi.ns.PersonDao;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Business layer object to work on {@link Person} and
 * {@link gov.ca.cwds.rest.api.persistence.ns.Person}
 * 
 * @author CWDS API Team
 */

public class PersonService implements CrudsService {
  private PersonDao personDao;
  private ElasticsearchDao elasticsearchDao;

  private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

  /**
   * Constructor
   * 
   * @param personDao The {@link Dao} handling {@link gov.ca.cwds.rest.api.persistence.ns.Person}
   *        objects.
   * @param elasticsearchDao the Elasticsearch DAO
   */
  public PersonService(PersonDao personDao, ElasticsearchDao elasticsearchDao) {
    this.personDao = personDao;
    this.elasticsearchDao = elasticsearchDao;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Person find(Serializable primaryKey) {
    assert (primaryKey instanceof Long);

    gov.ca.cwds.rest.api.persistence.ns.Person persistedPerson = personDao.find(primaryKey);
    if (persistedPerson != null) {
      return new Person(persistedPerson);
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
    assert (primaryKey instanceof Long);

    throw new NotImplementedException("Delete is not implemented");
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public PostedPerson create(Request request) {
    assert (request instanceof Person);

    Person person = ((Person) request);
    gov.ca.cwds.rest.api.persistence.ns.Person managed =
        new gov.ca.cwds.rest.api.persistence.ns.Person(person, null);

    managed = personDao.create(managed);
    PostedPerson postedPerson = new PostedPerson(managed);
    gov.ca.cwds.rest.api.elasticsearch.ns.Person esPerson =
        new gov.ca.cwds.rest.api.elasticsearch.ns.Person(postedPerson,
            DomainObject.cookTimestamp(managed.getLastUpdatedTime()));
    esPerson.setId(managed.getId().toString());
    String document = "";
    try {
      ObjectMapper mapper = new ObjectMapper();
      if (esPerson != null) {
        document = mapper.writeValueAsString(esPerson);
      }

      elasticsearchDao.start();
      elasticsearchDao.index(document, esPerson.getId().toString());
      elasticsearchDao.stop();
    } catch (JsonProcessingException e) {
      throw new ApiException("Unable to convert Person to json to Index in Elasticsearch");
    } catch (Exception e) {
      throw new ApiException("Unable to Index Person in Elasticsearch");
    }
    return postedPerson;
  }


  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   * gov.ca.cwds.rest.api.Request)
   */
  @Override
  public Response update(Serializable primaryKey, Request request) {
    assert (primaryKey instanceof Long);
    throw new NotImplementedException("Update is not implemented");
  }

}
