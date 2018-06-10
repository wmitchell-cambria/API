package gov.ca.cwds.rest.api.domain.es;

import java.util.Arrays;

import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.api.Response;

/**
 * API {@link Response} domain object, contains an array of ElasticSearch person results,
 * {@link ESPerson}.
 * 
 * @author CWDS API Team
 * @see ESPerson
 */
public class ESPersons implements Response, ApiMarker {

  private static final long serialVersionUID = 1L;

  private final ESPerson[] persons;

  /**
   * Disallow use of the default constructor.
   */
  @SuppressWarnings("unused")
  private ESPersons() {
    // Prohibit access to default constructor, since the person array must be populated.
    this.persons = new ESPerson[0];
  }

  /**
   * Constructor. Sets immutable array of {@link ESPerson}.
   * 
   * @param persons array of {@link ESPerson}.
   */
  public ESPersons(ESPerson[] persons) {
    this.persons = Arrays.copyOf(persons, persons.length);
  }

  /**
   * Getter for immutable array of {@link ESPerson}.
   * 
   * @return immutable array of {@link ESPerson}
   */
  public ESPerson[] getPersons() {
    return Arrays.copyOf(persons, persons.length);
  }

}
