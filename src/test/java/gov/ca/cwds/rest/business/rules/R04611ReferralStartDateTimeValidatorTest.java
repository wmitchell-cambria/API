package gov.ca.cwds.rest.business.rules;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import gov.ca.cwds.fixture.AssignmentEntityBuilder;
import gov.ca.cwds.fixture.ReferralResourceBuilder;
import gov.ca.cwds.data.persistence.cms.Assignment;
import gov.ca.cwds.rest.api.domain.cms.Referral;
@SuppressWarnings("javadoc")
public class R04611ReferralStartDateTimeValidatorTest {

	@Test
	public void isValidWhenFirstAssignmentIsNull() throws Exception {
		Assignment assignment = new Assignment();
	   Referral referral = new ReferralResourceBuilder().setReceivedDate("2017-12-08")
		        .setReceivedTime("16:01:01").build();
	   assertEquals(new R04611ReferralStartDateTimeValidator(referral, assignment).isValid(), Boolean.TRUE);
		
	}
	
	@Test
	public void isValidWhenAssignmentEndDateIsNull() throws Exception {
		Assignment assignment = new AssignmentEntityBuilder().setEndDate("").setEndTime("16:01:01").build();
		   Referral referral = new ReferralResourceBuilder().setReceivedDate("2017-12-08")
			        .setReceivedTime("16:01:01").build();
		   assertEquals(new R04611ReferralStartDateTimeValidator(referral, assignment).isValid(), Boolean.TRUE);
	}
	
	@Test
	public void isValidWhenAssignmentEndTimeIsNull() throws Exception {
		Assignment assignment = new AssignmentEntityBuilder().setEndDate("2017-12-08").setEndTime("").build();
		   Referral referral = new ReferralResourceBuilder().setReceivedDate("2017-12-08")
			        .setReceivedTime("16:01:01").build();
		   assertEquals(new R04611ReferralStartDateTimeValidator(referral, assignment).isValid(), Boolean.TRUE);		
	}
	
	@Test
	public void isNotValidWhenReferralStartDateTimeExceedsAssignmentEndDate() throws Exception {
		Assignment assignment = new AssignmentEntityBuilder().setEndDate("2017-12-08").setEndTime("00:01:01").build();
		   Referral referral = new ReferralResourceBuilder().setReceivedDate("2017-12-08")
			        .setReceivedTime("16:01:01").build();
		   assertEquals(new R04611ReferralStartDateTimeValidator(referral, assignment).isValid(), Boolean.FALSE);		
	}
	
	@Test
	public void isNotValidWhenReferralStartDateTimeEqualsAssignmentEndDateTime() throws Exception {
		Assignment assignment = new AssignmentEntityBuilder().setEndDate("2017-12-08").setEndTime("16:01:01").build();
		   Referral referral = new ReferralResourceBuilder().setReceivedDate("2017-12-08")
			        .setReceivedTime("16:01:01").build();
		   assertEquals(new R04611ReferralStartDateTimeValidator(referral, assignment).isValid(), Boolean.FALSE);		
		
	}
	
	@Test
	public void isValidWhenReferralStartDateTimeLessThanAssignmentEndDateTime() throws Exception {
		Assignment assignment = new AssignmentEntityBuilder().setEndDate("2017-12-08").setEndTime("16:01:01").build();
		   Referral referral = new ReferralResourceBuilder().setReceivedDate("2017-12-07")
			        .setReceivedTime("16:00:00").build();
		   assertEquals(new R04611ReferralStartDateTimeValidator(referral, assignment).isValid(), Boolean.TRUE);		
	}
}

