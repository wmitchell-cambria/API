package gov.ca.cwds.rest.api.domain.enums;

import static gov.ca.cwds.rest.api.domain.enums.Language.AMERICAN_SIGN_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.ARABIC_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.ARMENIAN_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.CAMBODIAN_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.CANTNESE_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.ENGLISH_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.FARSI_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.FILIPINO_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.FRENCH_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.GERMAN_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.HAWAIIAN_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.HEBREW_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.HMONG_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.ILACANO_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.INDOCHINESE_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.ITALIAN_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.JAPANESE_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.KOREAN_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.LAO_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.MANDARIN_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.MEN_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.OTHER_CHINESE_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.OTHER_NON_ENLISH_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.POLISH_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.PORTUGUESE_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.ROMANIAN_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.RUSSIAN_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.SAMOAN_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.SIGN_NOT_ASL_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.SPANISH_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.TAGALOG_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.THAI_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.TURKISH_LANGUAGE;
import static gov.ca.cwds.rest.api.domain.enums.Language.VIETNAMESE_LANGUAGE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import org.junit.Test;

/**
 * @author Intake Team 4
 */
public class LanguageTest {

  String[] languagesStringArrayValid = {
      "American Sign Language", "Arabic", "Armenian", "Cambodian", "Cantonese", "English", "Farsi",
      "Filipino", "French", "German", "Hawaiian", "Hebrew", "Hmong", "Ilacano", "Indochinese",
      "Italian", "Japanese", "Korean", "Lao", "Mandarin", "Mien", "Other Chinese",
      "Other Non-English", "Polish", "Portuguese", "Romanian", "Russian", "Samoan",
      "Sign Language (Not ASL)", "Spanish", "Tagalog", "Thai", "Turkish", "Vietnamese"};

  String[] languagesStringArrayNotValid = {"Some Other Language"};

  @Test
  public void hasValidLanguages() {
    assertEquals(AMERICAN_SIGN_LANGUAGE.getTheLanguage(), "American Sign Language");
    assertEquals(ARABIC_LANGUAGE.getTheLanguage(), "Arabic");
    assertEquals(ARMENIAN_LANGUAGE.getTheLanguage(), "Armenian");
    assertEquals(CAMBODIAN_LANGUAGE.getTheLanguage(), "Cambodian");
    assertEquals(CANTNESE_LANGUAGE.getTheLanguage(), "Cantonese");
    assertEquals(ENGLISH_LANGUAGE.getTheLanguage(), "English");
    assertEquals(FARSI_LANGUAGE.getTheLanguage(), "Farsi");
    assertEquals(FILIPINO_LANGUAGE.getTheLanguage(), "Filipino");
    assertEquals(FRENCH_LANGUAGE.getTheLanguage(), "French");
    assertEquals(GERMAN_LANGUAGE.getTheLanguage(), "German");
    assertEquals(HAWAIIAN_LANGUAGE.getTheLanguage(), "Hawaiian");
    assertEquals(HEBREW_LANGUAGE.getTheLanguage(), "Hebrew");
    assertEquals(HMONG_LANGUAGE.getTheLanguage(), "Hmong");
    assertEquals(ILACANO_LANGUAGE.getTheLanguage(), "Ilacano");
    assertEquals(INDOCHINESE_LANGUAGE.getTheLanguage(), "Indochinese");
    assertEquals(ITALIAN_LANGUAGE.getTheLanguage(), "Italian");
    assertEquals(JAPANESE_LANGUAGE.getTheLanguage(), "Japanese");
    assertEquals(KOREAN_LANGUAGE.getTheLanguage(), "Korean");
    assertEquals(LAO_LANGUAGE.getTheLanguage(), "Lao");
    assertEquals(MANDARIN_LANGUAGE.getTheLanguage(), "Mandarin");
    assertEquals(MEN_LANGUAGE.getTheLanguage(), "Mien");
    assertEquals(OTHER_CHINESE_LANGUAGE.getTheLanguage(), "Other Chinese");
    assertEquals(OTHER_NON_ENLISH_LANGUAGE.getTheLanguage(), "Other Non-English");
    assertEquals(POLISH_LANGUAGE.getTheLanguage(), "Polish");
    assertEquals(PORTUGUESE_LANGUAGE.getTheLanguage(), "Portuguese");
    assertEquals(ROMANIAN_LANGUAGE.getTheLanguage(), "Romanian");
    assertEquals(RUSSIAN_LANGUAGE.getTheLanguage(), "Russian");
    assertEquals(SAMOAN_LANGUAGE.getTheLanguage(), "Samoan");
    assertEquals(SIGN_NOT_ASL_LANGUAGE.getTheLanguage(), "Sign Language (Not ASL)");
    assertEquals(SPANISH_LANGUAGE.getTheLanguage(), "Spanish");
    assertEquals(TAGALOG_LANGUAGE.getTheLanguage(), "Tagalog");
    assertEquals(THAI_LANGUAGE.getTheLanguage(), "Thai");
    assertEquals(TURKISH_LANGUAGE.getTheLanguage(), "Turkish");
    assertEquals(VIETNAMESE_LANGUAGE.getTheLanguage(), "Vietnamese");

    assertTrue(Language.hasLanguage(AMERICAN_SIGN_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(ARABIC_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(ARMENIAN_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(CAMBODIAN_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(CANTNESE_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(ENGLISH_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(FARSI_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(FILIPINO_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(FRENCH_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(GERMAN_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(HAWAIIAN_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(HEBREW_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(HMONG_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(ILACANO_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(INDOCHINESE_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(ITALIAN_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(JAPANESE_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(KOREAN_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(LAO_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(MANDARIN_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(MEN_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(OTHER_CHINESE_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(OTHER_NON_ENLISH_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(POLISH_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(PORTUGUESE_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(ROMANIAN_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(RUSSIAN_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(SAMOAN_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(SIGN_NOT_ASL_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(SPANISH_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(TAGALOG_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(THAI_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(TURKISH_LANGUAGE.getTheLanguage()));
    assertTrue(Language.hasLanguage(VIETNAMESE_LANGUAGE.getTheLanguage()));

  }

  @Test
  public void hasLanguages() {
    assertTrue(Language.hasLanguages(languagesStringArrayValid));
    assertFalse(Language.hasLanguages(languagesStringArrayNotValid));
  }

  @Test
  public void hasLanguages1() {
    assertTrue(Language.hasLanguages(Arrays.asList(languagesStringArrayValid)));
    assertFalse(Language.hasLanguages(Arrays.asList(languagesStringArrayNotValid)));
  }

  @Test
  public void getAllTheLanguages() {
    String[] allLanguages = Language.getAllTheLanguages();
    assertNotNull(allLanguages);
    assertTrue(Language.hasLanguages(allLanguages));
    assertEquals(String.join(",",languagesStringArrayValid), String.join(",",allLanguages));
  }
}