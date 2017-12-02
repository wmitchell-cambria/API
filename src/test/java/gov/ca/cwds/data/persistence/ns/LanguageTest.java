package gov.ca.cwds.data.persistence.ns;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class LanguageTest {
  private Long languageId = (long) 12345;
  private String languageCodeId = "English";
  private String languageName = "English";
  private String userId = "0X5";

  @Test
  public void testConstructor() {
    Language language = new Language(languageId, languageCodeId);
    assertEquals(language.getLanguageId(), languageId);
    assertEquals(language.getLanguageCodeId(), languageCodeId);
    assertEquals(language.getPrimaryKey(), languageId);
    assertTrue(language.getPersonLanguages().isEmpty());
  }

  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(Language.class.newInstance(), is(notNullValue()));
  }

  @Test
  public void testConstructorUsingDomain() {
    gov.ca.cwds.rest.api.domain.Language domain =
        new gov.ca.cwds.rest.api.domain.Language(languageName);
    Language language = new Language(domain, userId, userId);
    assertEquals(language.getLanguageCodeId(), domain.getTheLanguage());
  }

}
