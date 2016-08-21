package gov.ca.cwds.rest.api.persistence.legacy;

import javax.persistence.Column;
import javax.persistence.Id;

import gov.ca.cwds.rest.api.persistence.PersistentObject;

public class Reporter extends PersistentObject {

	@Id
	@Column(name = "IDENTIFIER")
 	private String id;
 	
	public Reporter(gov.ca.cwds.rest.api.domain.Reporter reporter, String lastUpdatedId) {
		super(lastUpdatedId);
		this.id = reporter.getId();
	}
	
	/* (non-Javadoc)
	 * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
	 */
	@Override
	public String getPrimaryKey() {
		return getId();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
}
