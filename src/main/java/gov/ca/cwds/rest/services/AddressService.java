package gov.ca.cwds.rest.services;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;

import gov.ca.cwds.rest.api.domain.Address;

public class AddressService implements CrudsService<Address> {

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
	 */
	@Override
	public Address find(Serializable primaryKey) {
		if( "found".equals(primaryKey)) {
			return new Address("742 Evergreen Terrace", "Springfield", "WA", 98700);
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
	 */
	@Override
	public Address delete(Serializable id) {
		throw new NotImplementedException("Delete is not implemented");
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.domain.DomainObject)
	 */
	@Override
	public Serializable create(Address object) {
		return "someid";
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.CrudsService#update(gov.ca.cwds.rest.api.domain.DomainObject)
	 */
	@Override
	public String update(Address object) {
		throw new NotImplementedException("Update is not implemented");
	}

}
