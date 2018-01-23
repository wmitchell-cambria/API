package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Date;

import static org.hamcrest.Matchers.equalTo;


import org.junit.Test;

import gov.ca.cwds.fixture.StateIdEntityBuilder;

public class StateIdTest {
	
	private String id = "1234567ABC";
	private String assistanceUnitCode = "";
	private Short governmentEntityType = 1146;
	private Short newGovernmentEntityType = 1148;
	private String personNumber = "p2345";
	private String newPersonNumber = "x2345";
	private String serialNumber = "N338380A";
	private String newSerialNumber = "N448480A";
	private Date startDate;
	private Date newStartDate = new Date(0);
	private Date endDate;
	private Date newEndDate = new Date(0);
	private String stateIdCaseFirstName = "Firstname";
	private String newFirstName = "newfirstname";
	private String stateIdCaseLastName = "Lastname";
	private String stateIdCaseMiddleName = "Middle";
	private String newLastName = "newlastname";
	private String newMiddleName = "newmiddlename";
	private String fkClientId = "2345678ABC";
	private String newFkClientId = "3456789ABC";
	
	  /**
	   * Constructor test
	   * 
	   * @throws Exception - exception
	   */
	  @Test
	  public void testEmptyConstructor() throws Exception {
	    assertThat(StateId.class.newInstance(), is(notNullValue()));
	  }

	  @Test
	  public void testPersistentConstructor() throws Exception {
		  StateId  validStateId = new StateIdEntityBuilder().build();
		  
		  assertThat(validStateId.getId(), is(equalTo(id)));
		  assertThat(validStateId.getAssistanceUnitCode(), is(equalTo(assistanceUnitCode)));
		  assertThat(validStateId.getGovernmentEntityType(), is(equalTo(governmentEntityType)));
		  assertThat(validStateId.getPersonNumber(), is(equalTo(personNumber)));
		  assertThat(validStateId.getSerialNumber(), is(equalTo(serialNumber)));
		  assertThat(validStateId.getStartDate(), is(equalTo(startDate)));
		  assertThat(validStateId.getEndDate(), is(equalTo(endDate)));
		  assertThat(validStateId.getStateIdCaseFirstName(), is(equalTo(stateIdCaseFirstName)));
		  assertThat(validStateId.getStateIdCaseLastName(), is(equalTo(stateIdCaseLastName)));
		  assertThat(validStateId.getStateIdCaseMiddleName(), is(equalTo(stateIdCaseMiddleName)));
		  assertThat(validStateId.getFkClientId(), is(equalTo(fkClientId)));
	  }
	  
	  @Test
	  public void testPersistentObjectSetters() throws Exception {
		  StateId validStateId = new StateIdEntityBuilder().build();
		  validStateId.setId("2345678ABC");
		  assertThat(validStateId.getId(), is(equalTo("2345678ABC")));
		  validStateId.setAssistanceUnitCode("ABC");
		  assertThat(validStateId.getAssistanceUnitCode(), is(equalTo("ABC")));
		  validStateId.setGovernmentEntityType(newGovernmentEntityType);
		  assertThat(validStateId.getGovernmentEntityType(), is(equalTo(newGovernmentEntityType)));
		  validStateId.setPersonNumber(newPersonNumber);
		  assertThat(validStateId.getPersonNumber(), is(equalTo(newPersonNumber)));
		  validStateId.setSerialNumber(newSerialNumber);
		  assertThat(validStateId.getSerialNumber(), is(equalTo(newSerialNumber)));
		  validStateId.setStartDate(newStartDate);
		  assertThat(validStateId.getStartDate(), is(equalTo(newStartDate)));
		  validStateId.setEndDate(newEndDate);
		  assertThat(validStateId.getEndDate(), is(equalTo(newEndDate)));
		  validStateId.setStateIdCaseFirstName(newFirstName);
		  assertThat(validStateId.getStateIdCaseFirstName(),is(equalTo(newFirstName)));
		  validStateId.setStateIdCaseMiddleName(newMiddleName);
		  assertThat(validStateId.getStateIdCaseMiddleName(), is(equalTo(newMiddleName)));
		  validStateId.setStateIdCaseLastName(newLastName);
		  assertThat(validStateId.getStateIdCaseLastName(), is(equalTo(newLastName)));
		  validStateId.setFkClientId(newFkClientId);
		  
		  
	  }
	  
}
