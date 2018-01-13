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
	private String personNumber = "p2345";
	private String serialNumber = "N338380A";
	private Date startDate;
	private Date endDate;
	private String stateIdCaseFirstName = "Firstname";
	private String stateIdCaseLastName = "Lastname";
	private String stateIdCaseMiddleName = "Middle";
	private String fkClientId = "2345678ABC";
	
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
	  
}
