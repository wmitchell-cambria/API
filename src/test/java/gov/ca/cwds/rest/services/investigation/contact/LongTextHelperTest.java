package gov.ca.cwds.rest.services.investigation.contact;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import gov.ca.cwds.data.cms.LongTextDao;
import gov.ca.cwds.data.persistence.cms.LongText;
import gov.ca.cwds.rest.filters.TestingRequestExecutionContext;
import gov.ca.cwds.rest.services.cms.LongTextService;

public class LongTextHelperTest {

  LongTextDao longTextDao;
  LongTextService longTextService;
  LongTextHelper longTextHelper;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() throws Exception {
    new TestingRequestExecutionContext("02f");
    longTextDao = mock(LongTextDao.class);
    longTextService = new LongTextService(longTextDao);
    longTextHelper = new LongTextHelper(longTextService);

    when(longTextDao.create(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(new LongText("ABC1234567", "99", "some test"));

    when(longTextDao.update(any(gov.ca.cwds.data.persistence.cms.LongText.class)))
        .thenReturn(new LongText("ABC1234567", "99", "some test"));

  }

  @Test
  public void type() throws Exception {
    assertThat(LongTextHelper.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(longTextHelper, notNullValue());
  }


  @Test
  public void getLongTextReturnsNullWhenIdentifierIsNull() throws Exception {
    String detailText = null;
    String found = longTextHelper.getLongText(detailText);
    String expected = null;
    assertThat(found, is(expected));

  }

  @Test
  public void getLongTextReturnsExpectedWhenIdentifierIsNotNull() throws Exception {
    when(longTextDao.find("ABC1234567")).thenReturn(new LongText("ABC1234567", "99", "some test"));
    String detailText = "ABC1234567";
    String found = longTextHelper.getLongText(detailText);
    String expected = "some test";
    assertThat(found, is(expected));
  }

  @Test
  public void createLongTextCallsLongTextDaoCreate() throws Exception {
    longTextHelper.createLongText("some test", "99");
    verify(longTextDao, atLeastOnce()).create(any());
  }

  @Test
  public void updateLongTextCallsLongTextDaoCreateWhenEmptyIdAndNonEmptyText() throws Exception {
    longTextHelper.updateLongText("", "some test", "99");
    verify(longTextDao, atLeastOnce()).create(any());
  }

  @Test
  public void updateLongTextCallsLongTextDaoDeleteWhenNonEmptyIdAndEmptyText() throws Exception {
    longTextHelper.updateLongText("ABC1234567", "", "99");
    verify(longTextDao, atLeastOnce()).delete(any());
  }

  @Test
  public void updateLongTextCallsLongTextDaoUpdateWhenNonEmptyId() throws Exception {
    longTextHelper.updateLongText("ABC1234567", "some test", "99");
    verify(longTextDao, atLeastOnce()).update(any());
  }

  @Test
  public void updateLongTextReturnsEmptyWhenEmptyIdAndEmptyText() throws Exception {
    String id = longTextHelper.updateLongText("", "", "99");
    String expected = "";
    assertThat(id, is(expected));
  }

}
