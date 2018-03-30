package gov.ca.cwds.data.persistence.ns;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class ScreeningWrapperTest {
  private String id = "testscreeningdashboard";
  private String reference = "screening dashboard reference";
  private String name = "screening name";
  private String screeningDecision = "5 days";
  private String screeningDecisionDetail = "screening decision detail";
  private String assignee = "Test User";
  private String assigneeStaffId = "abb";
  private Date startedAt;

  @Test
  public void testConstructor() throws Exception {
	ScreeningWrapper screeningWrapper = new ScreeningWrapper(id, reference, name, screeningDecision,
		screeningDecisionDetail, assignee, assigneeStaffId, startedAt);
	assertThat(screeningWrapper.getId(), is(equalTo(id)));
	assertThat(screeningWrapper.getReference(), is(equalTo(reference)));
	assertThat(screeningWrapper.getName(), is(equalTo(name)));
	assertThat(screeningWrapper.getScreeningDecision(), is(equalTo(screeningDecision)));
	assertThat(screeningWrapper.getScreeningDecisionDetail(), is(equalTo(screeningDecisionDetail)));
	assertThat(screeningWrapper.getAssignee(), is(equalTo(assignee)));
	assertThat(screeningWrapper.getAssigneeStaffId(), is(equalTo(assigneeStaffId)));
	assertThat(screeningWrapper.getStartedAt(), is(equalTo(startedAt)));
  }
  
  @Test
  public void testDefaultConstructorNotNull() throws Exception {
	ScreeningWrapper screeningWrapper = new ScreeningWrapper();
	assertNotNull(screeningWrapper);
  }
  
  @Test
  public void testEquaslHashCodeWorks() throws Exception {
	EqualsVerifier.forClass(ScreeningWrapper.class).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
  }
  
}
