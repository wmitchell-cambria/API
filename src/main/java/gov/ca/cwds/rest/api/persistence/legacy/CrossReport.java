package gov.ca.cwds.rest.api.persistence.legacy;

import javax.persistence.Column;
import javax.persistence.Id;

import gov.ca.cwds.rest.api.persistence.PersistentObject;

public class CrossReport extends PersistentObject {

	@Id
	@Column(name = "IDENTIFIER")
 	private String id;
 	
	public CrossReport(gov.ca.cwds.rest.api.domain.CrossReport crossReport, String lastUpdatedId) {
		super(lastUpdatedId);
		this.id = crossReport.getId();
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
