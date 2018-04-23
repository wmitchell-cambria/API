package gov.ca.cwds.rest.api.domain.enums;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Intake Team 4
 */
public enum Language {

  /**
   * Participant languages
   */
  AMERICAN_SIGN_LANGUAGE("American Sign Language"),
  ARABIC_LANGUAGE("Arabic"),
  ARMENIAN_LANGUAGE("Armenian"),
  CAMBODIAN_LANGUAGE("Cambodian"),
  CANTNESE_LANGUAGE("Cantonese"),
  ENGLISH_LANGUAGE("English"),
  FARSI_LANGUAGE("Farsi"),
  FILIPINO_LANGUAGE("Filipino"),
  FRENCH_LANGUAGE("French"),
  GERMAN_LANGUAGE("German"),
  HAWAIIAN_LANGUAGE("Hawaiian"),
  HEBREW_LANGUAGE("Hebrew"),
  HMONG_LANGUAGE("Hmong"),
  ILACANO_LANGUAGE("Ilacano"),
  INDOCHINESE_LANGUAGE("Indochinese"),
  ITALIAN_LANGUAGE("Italian"),
  JAPANESE_LANGUAGE("Japanese"),
  KOREAN_LANGUAGE("Korean"),
  LAO_LANGUAGE("Lao"),
  MANDARIN_LANGUAGE("Mandarin"),
  MEN_LANGUAGE("Mien"),
  OTHER_CHINESE_LANGUAGE("Other Chinese"),
  OTHER_NON_ENLISH_LANGUAGE("Other Non-English"),
  POLISH_LANGUAGE("Polish"),
  PORTUGUESE_LANGUAGE("Portuguese"),
  ROMANIAN_LANGUAGE("Romanian"),
  RUSSIAN_LANGUAGE("Russian"),
  SAMOAN_LANGUAGE("Samoan"),
  SIGN_NOT_ASL_LANGUAGE("Sign Language (Not ASL)"),
  SPANISH_LANGUAGE("Spanish"),
  TAGALOG_LANGUAGE("Tagalog"),
  THAI_LANGUAGE("Thai"),
  TURKISH_LANGUAGE("Turkish"),
  VIETNAMESE_LANGUAGE("Vietnamese");

  private static final String[] allTheLanguages = Arrays.stream(values())
      .map(Language::getTheLanguage).toArray(String[]::new);
  private final String theLanguage;

  Language(String theLanguage) {
    this.theLanguage = theLanguage;
  }

  /**
   * @param value - language value
   * @return true or false
   */
  public static boolean hasLanguage(String value) {
    if (value == null) {
      return false;
    }
    for (Language language : values()) {
      if (language.theLanguage.equals(value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param values - languages values - comma separated
   * @return true or false
   */
  public static boolean hasLanguages(String[] values) {
    if (values == null) {
      return false;
    }
    return hasLanguages(Arrays.asList(values));
  }

  /**
   * @param values - languages value - comma separated
   * @return true or false
   */
  public static boolean hasLanguages(Collection<String> values) {
    if (values == null) {
      return false;
    }
    for (String language : values) {
      if (!hasLanguage(language)) {
        return false;
      }
    }
    return true;
  }


  /**
   * @return string array of all the languages
   */
  public static String[] getAllTheLanguages() {
    return Arrays.copyOf(allTheLanguages, allTheLanguages.length);
  }

  /**
   * @return the language
   */
  public String getTheLanguage() {
    return theLanguage;
  }
}
