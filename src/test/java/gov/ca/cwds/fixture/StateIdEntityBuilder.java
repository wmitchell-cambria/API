package gov.ca.cwds.fixture;

import java.util.Date;

import gov.ca.cwds.data.persistence.cms.StateId;

public class StateIdEntityBuilder {

	private String id = "1234567ABC";
	private String assistanceUnitCode = "";
	private Short governmentEntityType = 1146;
	private String personNumber = "p2345";
	private String serialNumber = "N338380A";
	private Date startDate;
	private Date endDate;
	private String stateIdCaseFirstName = "Firstname";
	private String stateIdCaseLastName = "Lastname";
	private String stateIdCaseMiddleName = "Middle";
	private String fkClientId = "2345678ABC";
	
	public StateId build() {
		return new StateId(id, assistanceUnitCode, governmentEntityType,
			personNumber, serialNumber, startDate, endDate, stateIdCaseFirstName,
			stateIdCaseLastName, stateIdCaseMiddleName, fkClientId);
	}

	public String getId() {
		return id;
	}

	public StateIdEntityBuilder setId(String id) {
		this.id = id;
		return this;
	}

	public String getAssistanceUnitCode() {
		return assistanceUnitCode;
	}

	public StateIdEntityBuilder setAssistanceUnitCode(String assistanceUnitCode) {
		this.assistanceUnitCode = assistanceUnitCode;
		return this;
	}

	public Short getGovernmentEntityType() {
		return governmentEntityType;
	}

	public StateIdEntityBuilder setGovernmentEntityType(Short governmentEntityType) {
		this.governmentEntityType = governmentEntityType;
		return this;
	}

	public String getPersonNumber() {
		return personNumber;
	}

	public StateIdEntityBuilder setPersonNumber(String personNumber) {
		this.personNumber = personNumber;
		return this;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public StateIdEntityBuilder setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
		return this;
	}

	public Date getStartDate() {
		return startDate;
	}

	public StateIdEntityBuilder setStartDate(Date startDate) {
		this.startDate = startDate;
		return this;
	}

	public Date getEndDate() {
		return endDate;
	}

	public StateIdEntityBuilder setEndDate(Date endDate) {
		this.endDate = endDate;
		return this;
	}

	public String getStateIdCaseFirstName() {
		return stateIdCaseFirstName;
	}

	public StateIdEntityBuilder setStateIdCaseFirstName(String stateIdCaseFirstName) {
		this.stateIdCaseFirstName = stateIdCaseFirstName;
		return this;
	}

	public String getStateIdCaseLastName() {
		return stateIdCaseLastName;
	}

	public StateIdEntityBuilder setStateIdCaseLastName(String stateIdCaseLastName) {
		this.stateIdCaseLastName = stateIdCaseLastName;
		return this;
	}

	public String getStateIdCaseMiddleName() {
		return stateIdCaseMiddleName;
	}

	public StateIdEntityBuilder setStateIdCaseMiddleName(String stateIdCaseMiddleName) {
		this.stateIdCaseMiddleName = stateIdCaseMiddleName;
		return this;
	}

	public String getFkClientId() {
		return fkClientId;
	}

	public StateIdEntityBuilder setFkClientId(String fkClientId) {
		this.fkClientId = fkClientId;
		return this;
	}

	
}
