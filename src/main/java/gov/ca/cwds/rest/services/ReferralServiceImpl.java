package gov.ca.cwds.rest.services;

import gov.ca.cwds.rest.api.domain.ReferralSummary;
import gov.ca.cwds.rest.api.persistence.Referral;

import java.util.HashMap;
import java.util.UUID;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link ReferralService} backed by a DAO layer.
 * 
 * @author CDWS API Team
 */
public class ReferralServiceImpl implements ReferralService {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReferralServiceImpl.class);
	
	HashMap<String, Referral> dummyData = new HashMap<String, Referral>();
	
	@Override
	public ReferralSummary findReferralSummary(String id) {
		Referral referral = dummyData.get(id);
		if( referral != null ) {
			return new ReferralSummary(referral.getId(), referral.getReferralName(), referral.getReceivedDate());
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.Service#find(java.lang.String)
	 */
	@Override
	public Referral find(String id) {
		return dummyData.get(id);
	}

	@Override
	public Referral delete(String id) {
		return dummyData.remove(id);
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.Service#create(java.lang.Object)
	 */
	@Override
	public Referral create(Referral object) {
		Referral referral = dummyData.get(object.getId()) ;
		if( referral != null ) {
			try {
				throw new EntityExistsException();
			} catch ( EntityExistsException e ) {
				throw new ServiceException("not unique",e);
			}
		}
		String id = UUID.randomUUID().toString();
		Referral created = new Referral(id, object.getReferralName(), object.getReceivedDate());
		dummyData.put(id, created);
		return created;
	}

	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.services.Service#update(java.lang.Object)
	 */
	@Override
	public Referral update(Referral object) {
		Referral referral = dummyData.get(object.getId()) ;
		if( referral == null ) {
			try {
				
			} catch ( EntityNotFoundException e ) {
				throw new ServiceException("not found",e);
			}
		}
		Referral updated = new Referral(referral.getId(), object.getReferralName(), object.getReceivedDate());
		dummyData.put(updated.getId(), updated);
		return updated;
	}
	
	
}
