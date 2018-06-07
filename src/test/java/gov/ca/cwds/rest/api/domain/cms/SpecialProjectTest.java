package gov.ca.cwds.rest.api.domain.cms;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import java.util.Date;
import org.junit.Test;
import gov.ca.cwds.rest.api.domain.DomainChef;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class SpecialProjectTest {

  private Boolean archiveAssociationIndicator = Boolean.FALSE;
  private String archiveAssociationIndicatorString = "N";
  private String description = "special project description";
  private String endDateString = "2018-05-30";
  private Date endDate = null;
  private Short governmentEntityType = 1084;
  private String name = "S-CESC";
  private String startDateString = "2018-05-01";
  private Date startDate = new Date();
  private String id = "1234567ABC";
  
  @Test
  public void testConstructor() throws Exception {
    SpecialProject sp = new SpecialProject(archiveAssociationIndicator, description,
        endDateString, governmentEntityType, name, startDateString);
    assertThat(sp.getArchiveAssociationIndicator(), is(equalTo(archiveAssociationIndicator)));
    assertThat(sp.getDescription(), is(equalTo(description)));
    assertThat(sp.getEndDate(), is(equalTo(endDateString)));
    assertThat(sp.getGovernmentEntityType(), is(equalTo(governmentEntityType)));
    assertThat(sp.getName(), is(equalTo(name)));
    assertThat(sp.getStartDate(), is(equalTo(startDateString)));
  }
  
  @Test
  public void testConstructorUsingPersistentObject() throws Exception {
    gov.ca.cwds.data.persistence.cms.SpecialProject persistent = 
        new gov.ca.cwds.data.persistence.cms.SpecialProject(archiveAssociationIndicatorString, 
            description, endDate, governmentEntityType, id, name, startDate);
    
    SpecialProject sp = new SpecialProject(persistent);
    assertThat(sp.getArchiveAssociationIndicator(), is(equalTo(DomainChef.uncookBooleanString(persistent.getArchiveAssociationIndicator()))));
    assertThat(sp.getDescription(), is(equalTo(persistent.getProjectDescription())));
    assertThat(sp.getEndDate(), is(equalTo(DomainChef.cookDate(persistent.getEndDate()))));
    assertThat(sp.getGovernmentEntityType(), is(equalTo(persistent.getGovernmentEntityType())));
    assertThat(sp.getName(), is(equalTo(persistent.getName())));
    assertThat(sp.getStartDate(), is(equalTo(DomainChef.cookDate(persistent.getStartDate()))));
  }
  
  @Test
  public void equalsHashCodeWork() {
    EqualsVerifier.forClass(SpecialProject.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

}
