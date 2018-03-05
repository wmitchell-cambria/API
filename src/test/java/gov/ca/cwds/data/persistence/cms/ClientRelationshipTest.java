package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import gov.ca.cwds.fixture.ClientRelationshipEntityBuilder;
import gov.ca.cwds.rest.api.domain.DomainChef;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

/**
 * @author CWDS API Team
 *
 */
public class ClientRelationshipTest {
  private String id = "CLNTRELN";
  private Short clientRelationshipType = 172;
  private String absentParentCode = "N";
  private String endDate = "2017-01-07";
  private String startDate = "2017-01-07";
  private String secondaryClientId = "SECCLIENT";
  private String primaryClientId = "PRICLIENT";
  private String sameHomeCode = "Y";

  private String lastUpdatedId = "0XA";

  /*
   * Constructor test
   */
  @SuppressWarnings("javadoc")
  @Test
  public void emtpyConstructorIsNotNull() throws Exception {
    assertThat(StaffPerson.class.newInstance(), is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testConstructorUsingDomain() throws Exception {
    gov.ca.cwds.rest.api.domain.cms.ClientRelationship domain =
        new gov.ca.cwds.rest.api.domain.cms.ClientRelationship("N", (short) 172, "2017-01-07",
            "SECCLIENT", "PRICLIENT", "Y", "2017-01-07");

    ClientRelationship persistent = new ClientRelationship(id, domain, lastUpdatedId, new Date());
    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getEndDate(), is(equalTo(DomainChef.uncookDateString(endDate))));
    assertThat(persistent.getAbsentParentCode(), is(equalTo(absentParentCode)));
    assertThat(persistent.getClientRelationshipType(), is(equalTo(clientRelationshipType)));
    assertThat(persistent.getPrimaryClientId(), is(equalTo(primaryClientId)));
    assertThat(persistent.getSecondaryClientId(), is(equalTo(secondaryClientId)));
    assertThat(persistent.getSameHomeCode(), is(equalTo(sameHomeCode)));
    assertThat(persistent.getStartDate(), is(equalTo(DomainChef.uncookDateString(startDate))));
    assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
  }

  @Test
  public void testEqualsHashCodeWorks() throws Exception {
    EqualsVerifier.forClass(gov.ca.cwds.data.persistence.cms.ClientRelationship.class)
        .suppress(Warning.STRICT_INHERITANCE).withRedefinedSuperclass().verify();
  }

  @Test
  public void givenNullAsARelationThenRelatedToShouldFail(){
    ClientRelationship fredRelationships = new ClientRelationshipEntityBuilder().setPrimaryClientId("2XKC5DS").build();
    assertFalse(fredRelationships.relatedTo(null));
  }


  @Test
  public void givenRelationshipWithPrimaryClientIdNullThenComparisonShouldFail(){
    ClientRelationship nullClientRelationship = new ClientRelationship();
    ClientRelationship fred = new ClientRelationshipEntityBuilder().setPrimaryClientId("2XKC5DS").build();
    assertFalse(fred.relatedTo(nullClientRelationship));
  }

  @Test
  public void givenDifferentUnRelatedRelationshipsThenComparisonShouldBeFalse(){
    ClientRelationship fred = new ClientRelationshipEntityBuilder().setPrimaryClientId("1234345").setSecondaryClientId("9876543").build();
    ClientRelationship unrelatedToFred = new ClientRelationshipEntityBuilder().setPrimaryClientId("ABCDSF").setSecondaryClientId("ZYXW").build();
    assertFalse(fred.relatedTo( unrelatedToFred));
  }

  @Test
  public void givenSameRelatedRelationshipsThenComparisonShouldBeTrue(){
    String id1 = "1234567";
    String id2 = "9876543";
    ClientRelationship fred1 = new ClientRelationshipEntityBuilder().setPrimaryClientId(id1).setSecondaryClientId(id2).build();
    ClientRelationship fred2 = new ClientRelationshipEntityBuilder().setPrimaryClientId(id1).setSecondaryClientId(id2).build();
    assertTrue(fred1.relatedTo(fred2));
  }

  @Test
  public void givenTheReverseRelationshipsThenComparisonShouldBeTrue(){
    String id1 = "1234567";
    String id2 = "9876543";
    ClientRelationship fred1 = new ClientRelationshipEntityBuilder().setPrimaryClientId(id1).setSecondaryClientId(id2).build();
    ClientRelationship fred2 = new ClientRelationshipEntityBuilder().setPrimaryClientId(id2).setSecondaryClientId(id1).build();
    assertTrue(fred1.relatedTo(fred2));
  }
}
