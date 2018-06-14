package gov.ca.cwds.data.persistence.cms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.persistence.cms.SpecialProject;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class SpecialProjectTest {

  private String id = "1234567ABC";
  private String lastUpdatedId = "aab";
  private Date lastUpdatedTime = new Date();
  private String archiveAssociationIndicator = "N";
  private String projectDescription = "special project description";
  private LocalDate endDate = null;
  private Short governmentEntityType = 1084;
  private String name = "S-CESC";
  private LocalDate startDate = LocalDate.now();
  
  private static final ObjectMapper MAPPER = SystemCodeTestHarness.MAPPER;

  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(SpecialProject.class.newInstance(), is(notNullValue()));
  }
  
  @Test
  public void testConstructor() throws Exception {
    SpecialProject sp = new SpecialProject(archiveAssociationIndicator, projectDescription,
        endDate, governmentEntityType, id, name, startDate);
    assertThat(sp.getId(), is(equalTo(id)));
    assertThat(sp.getArchiveAssociationIndicator(), is(equalTo(archiveAssociationIndicator)));
    assertThat(sp.getProjectDescription(), is(equalTo(projectDescription)));
    assertThat(sp.getEndDate(), is(equalTo(endDate)));
    assertThat(sp.getGovernmentEntityType(), is(equalTo(governmentEntityType)));
    assertThat(sp.getName(), is(equalTo(name)));
    assertThat(sp.getStartDate(), is(equalTo(startDate)));
  }
  
  @Test
  public void testSetters() throws Exception {
    SpecialProject sp = new SpecialProject();
    sp.setArchiveAssociationIndicator(archiveAssociationIndicator);
    assertThat(sp.getArchiveAssociationIndicator(), is(equalTo(archiveAssociationIndicator)));
    sp.setEndDate(endDate);
    assertThat(sp.getEndDate(), is(equalTo(endDate)));
    sp.setGovernmentEntityType(governmentEntityType);
    assertThat(sp.getGovernmentEntityType(), is(equalTo(governmentEntityType)));
    sp.setId(id);
    assertThat(sp.getId(), is(equalTo(id)));
    sp.setLastUpdatedId(lastUpdatedId);
    assertThat(sp.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
    sp.setLastUpdatedTime(lastUpdatedTime);
    assertThat(sp.getLastUpdatedTime(), is(equalTo(lastUpdatedTime)));
    sp.setName(name);
    assertThat(sp.getName(), is(equalTo(name)));
    sp.setProjectDescription(projectDescription);
    assertThat(sp.getProjectDescription(), is(equalTo(projectDescription)));
    sp.setStartDate(startDate);
    assertThat(sp.getStartDate(), is(equalTo(startDate)));
    
  }
  @Test
  public void testEqualsHashCodeWorks() throws Exception {
    EqualsVerifier.forClass(gov.ca.cwds.data.persistence.cms.SpecialProject.class)
        .suppress(Warning.STRICT_INHERITANCE).withRedefinedSuperclass().verify();
  }
}
