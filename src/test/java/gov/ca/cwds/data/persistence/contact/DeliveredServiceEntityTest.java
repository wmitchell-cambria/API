package gov.ca.cwds.data.persistence.contact;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.fixture.contacts.DeliveredServiceResourceBuilder;
import gov.ca.cwds.rest.api.contact.DeliveredServiceDomain;
import gov.ca.cwds.rest.api.domain.DomainChef;


/**
 * @author CWDS API Team
 *
 */
public class DeliveredServiceEntityTest {

  private Short cftLeadAgencyType = (short) 12;
  private String coreServiceIndicator = "N";
  private Short communicationMethodType = (short) 408;
  private Short contactLocationType = (short) 415;
  private String contactVisitCode = "C";
  private String countySpecificCode = "99";
  private String detailText = "ABC12345679";
  private String hardCopyDocumentOnFileCode = "N";
  private String detailTextContinuation = "ABC12345t7";
  private Date endDate = new Date();
  private Date endTime = new Date();
  private String primaryDeliveredServiceId = "ABC1at0875";
  private String otherParticipantsDesc = "description of the world";
  private String providedByCode = "S";
  private String providedById = "ABC097r534";
  private Date startDate = new Date();
  private Date startTime = new Date();
  private String statusCode = "C";
  private String supervisionCode = "C";
  private Short serviceContactType = (short) 408;
  private String wraparoundServiceIndicator = "N";

  private String id = "1234567ABC";
  private String lastUpdatedId = "0X5";
  private Date lastUpdatedTime = new Date();

  /**
   * Constructor test
   * 
   * @throws Exception - exception
   */
  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(DeliveredServiceEntity.class.newInstance(), is(notNullValue()));
  }

  @SuppressWarnings("javadoc")
  @Test
  public void testPersistentConstructor() throws Exception {
    DeliveredServiceEntity persistent = new DeliveredServiceEntity(cftLeadAgencyType,
        coreServiceIndicator, communicationMethodType, contactLocationType, contactVisitCode,
        countySpecificCode, detailText, hardCopyDocumentOnFileCode, detailTextContinuation, endDate,
        endTime, primaryDeliveredServiceId, id, otherParticipantsDesc, providedByCode, providedById,
        startDate, startTime, statusCode, supervisionCode, serviceContactType,
        wraparoundServiceIndicator);

    assertThat(persistent.getCftLeadAgencyType(), is(equalTo(cftLeadAgencyType)));
    assertThat(persistent.getCoreServiceIndicator(), is(equalTo(coreServiceIndicator)));
    assertThat(persistent.getCommunicationMethodType(), is(equalTo(communicationMethodType)));
    assertThat(persistent.getContactLocationType(), is(equalTo(contactLocationType)));
    assertThat(persistent.getContactVisitCode(), is(equalTo(contactVisitCode)));
    assertThat(persistent.getCountySpecificCode(), is(equalTo(countySpecificCode)));
    assertThat(persistent.getDetailText(), is(equalTo(detailText)));
    assertThat(persistent.getHardCopyDocumentOnFileCode(), is(equalTo(hardCopyDocumentOnFileCode)));
    assertThat(persistent.getDetailTextContinuation(), is(equalTo(detailTextContinuation)));
    assertThat(persistent.getEndDate(), is(equalTo(endDate)));
    assertThat(persistent.getEndTime(), is(equalTo(endTime)));
    assertThat(persistent.getPrimaryDeliveredServiceId(), is(equalTo(primaryDeliveredServiceId)));
    assertThat(persistent.getId(), is(equalTo(id)));
    assertThat(persistent.getOtherParticipantsDesc(), is(equalTo(otherParticipantsDesc)));
    assertThat(persistent.getProvidedByCode(), is(equalTo(providedByCode)));
    assertThat(persistent.getProvidedById(), is(equalTo(providedById)));
    assertThat(persistent.getStartDate(), is(equalTo(startDate)));
    assertThat(persistent.getStartTime(), is(equalTo(startTime)));
    assertThat(persistent.getStatusCode(), is(equalTo(statusCode)));
    assertThat(persistent.getSupervisionCode(), is(equalTo(supervisionCode)));
    assertThat(persistent.getServiceContactType(), is(equalTo(serviceContactType)));
    assertThat(persistent.getWraparoundServiceIndicator(), is(equalTo(wraparoundServiceIndicator)));
    assertThat(persistent.getPrimaryKey(), is(equalTo(id)));
  }

  /**
   * @throws Exception - exception
   */
  @Test
  public void testConstructorUsingDomain() throws Exception {

    DeliveredServiceDomain deliveredServiceDomain =
        new DeliveredServiceResourceBuilder().buildDeliveredServiceResource();

    DeliveredServiceEntity deliveredServiceEntity =
        new DeliveredServiceEntity(id, deliveredServiceDomain, lastUpdatedId, lastUpdatedTime);

    assertThat(deliveredServiceEntity.getId(), is(equalTo(id)));
    assertThat(deliveredServiceEntity.getCftLeadAgencyType(),
        is(equalTo(deliveredServiceDomain.getCftLeadAgencyType())));
    assertThat(deliveredServiceEntity.getCoreServiceIndicator(),
        is(equalTo(DomainChef.cookBoolean(deliveredServiceDomain.getCoreServiceIndicator()))));
    assertThat(deliveredServiceEntity.getCommunicationMethodType(),
        is(equalTo(deliveredServiceDomain.getCommunicationMethodType().shortValue())));
    assertThat(deliveredServiceEntity.getContactLocationType(),
        is(equalTo(deliveredServiceDomain.getContactLocationType().shortValue())));
    assertThat(deliveredServiceEntity.getContactVisitCode(),
        is(equalTo(deliveredServiceDomain.getContactVisitCode())));
    assertThat(deliveredServiceEntity.getCountySpecificCode(),
        is(equalTo(deliveredServiceDomain.getCountySpecificCode())));
    assertThat(deliveredServiceEntity.getDetailText(),
        is(equalTo(deliveredServiceDomain.getDetailText())));
    assertThat(deliveredServiceEntity.getHardCopyDocumentOnFileCode(),
        is(equalTo(deliveredServiceDomain.getHardCopyDocumentOnFileCode())));
    assertThat(deliveredServiceEntity.getDetailTextContinuation(),
        is(equalTo(deliveredServiceDomain.getDetailTextContinuation())));
    assertThat(deliveredServiceEntity.getEndDate(),
        is(equalTo(DomainChef.uncookDateString(deliveredServiceDomain.getEndDate()))));
    assertThat(deliveredServiceEntity.getEndTime(),
        is(equalTo(DomainChef.uncookTimeString(deliveredServiceDomain.getEndTime()))));
    assertThat(deliveredServiceEntity.getPrimaryDeliveredServiceId(),
        is(equalTo(deliveredServiceDomain.getPrimaryDeliveredServiceId())));
    assertThat(deliveredServiceEntity.getOtherParticipantsDesc(),
        is(equalTo(deliveredServiceDomain.getOtherParticipantsDesc())));
    assertThat(deliveredServiceEntity.getProvidedByCode(),
        is(equalTo(deliveredServiceDomain.getProvidedByCode())));
    assertThat(deliveredServiceEntity.getProvidedById(),
        is(equalTo(deliveredServiceDomain.getProvidedById())));
    assertThat(deliveredServiceEntity.getStartDate(),
        is(equalTo(DomainChef.uncookDateString(deliveredServiceDomain.getStartDate()))));
    assertThat(deliveredServiceEntity.getStartTime(),
        is(equalTo(DomainChef.uncookTimeString(deliveredServiceDomain.getStartTime()))));
    assertThat(deliveredServiceEntity.getStatusCode(),
        is(equalTo(deliveredServiceDomain.getStatusCode())));
    assertThat(deliveredServiceEntity.getSupervisionCode(),
        is(equalTo(deliveredServiceDomain.getSupervisionCode())));
    assertThat(deliveredServiceEntity.getServiceContactType(),
        is(equalTo(deliveredServiceDomain.getServiceContactType().shortValue())));
    assertThat(deliveredServiceEntity.getWraparoundServiceIndicator(), is(
        equalTo(DomainChef.cookBoolean(deliveredServiceDomain.getWraparoundServiceIndicator()))));
    assertThat(deliveredServiceEntity.getPrimaryKey(), is(equalTo(id)));

  }
  
  @Test
  public void equalsShouldBeTrueWhenSameObject() throws Exception {
	DeliveredServiceEntity ds = new DeliveredServiceEntity();	  
    assertTrue(ds.equals(ds));
  }

}
