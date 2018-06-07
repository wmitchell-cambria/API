package gov.ca.cwds.data.persistence.cms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

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
  private Date endDate = null;
  private Short governmentEntityType = 1084;
  private String name = "S-CESC";
  private Date startDate = new Date();
  
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
  public void testEqualsHashCodeWorks() throws Exception {
    EqualsVerifier.forClass(gov.ca.cwds.data.persistence.cms.SpecialProject.class)
        .suppress(Warning.STRICT_INHERITANCE).withRedefinedSuperclass().verify();
  }
}
