package gov.ca.cwds.rest.services;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.lang3.NotImplementedException;

import gov.ca.cwds.rest.api.domain.Address;
import gov.ca.cwds.rest.api.domain.Screening;

public class ScreeningService implements CrudsService<Screening> {

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Screening find(Serializable primaryKey) {
    if ("found".equals(primaryKey)) {
      Address address = new Address("10 main st", "Sacramento", "CA", 95814);
      ArrayList<Integer> involvedPersonIds = new ArrayList<Integer>();
      involvedPersonIds.add(1);
      involvedPersonIds.add(2);
      involvedPersonIds.add(3);
      Screening screening = new Screening((long) 2, "X5HNJK", "2016-10-13T01:07", "amador",
          "2016-10-13", "Relative's Home", "email", "first screening", "immediate",
          "accept_for_investigation", "2016-10-05T01:01", "first narrative", address,
          involvedPersonIds);
      return screening;
    } else {
      return null;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Screening delete(Serializable id) {
    throw new NotImplementedException("Delete is not implemented");
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.domain.DomainObject)
   */
  @Override
  public Serializable create(Screening object) {
    return "someid";
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(gov.ca.cwds.rest.api.domain.DomainObject)
   */
  @Override
  public String update(Screening object) {
    throw new NotImplementedException("Delete is not implemented");
  }

}
