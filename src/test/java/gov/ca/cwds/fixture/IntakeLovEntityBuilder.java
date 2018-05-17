package gov.ca.cwds.fixture;

import gov.ca.cwds.data.persistence.ns.IntakeLov;

/**
 * @author CWDS API Team
 *
 */
public class IntakeLovEntityBuilder {

  Long legacySystemCodeId;
  String legacyMeta;
  String legacyShortDescription;
  String legacyLogicalCode;
  boolean useLogical;
  String legacyCategoryId;
  String parentCategoryId;
  String legacyOtherCode;
  String legacyLongDescription;
  String intakeType;
  String parentIntakeType;
  String intakeCode;
  String intakeDisplay;

  /**
   * @return the IntakeLov
   */
  public IntakeLov build() {
    return new IntakeLov(legacySystemCodeId, legacyMeta, legacyShortDescription, legacyLogicalCode,
        useLogical, legacyCategoryId, legacyOtherCode, legacyLongDescription, intakeType,
        intakeCode, intakeDisplay);
  }

  /**
   * @param legacySystemCodeId - legacySystemCodeId
   * @return the legacySystemCodeId
   */
  public IntakeLovEntityBuilder setLegacySystemCodeId(Long legacySystemCodeId) {
    this.legacySystemCodeId = legacySystemCodeId;
    return this;
  }

  /**
   * @param legacyMeta - legacyMeta
   * @return the legacyMeta
   */
  public IntakeLovEntityBuilder setLegacyMeta(String legacyMeta) {
    this.legacyMeta = legacyMeta;
    return this;
  }

  /**
   * @param legacyShortDescription - legacyShortDescription
   * @return the legacyShortDescription
   */
  public IntakeLovEntityBuilder setLegacyShortDescription(String legacyShortDescription) {
    this.legacyShortDescription = legacyShortDescription;
    return this;
  }

  /**
   * @param legacyLogicalCode - legacyLogicalCode
   * @return the legacyLogicalCode
   */
  public IntakeLovEntityBuilder setLegacyLogicalCode(String legacyLogicalCode) {
    this.legacyLogicalCode = legacyLogicalCode;
    return this;
  }

  /**
   * @param useLogical - useLogical
   * @return the useLogical
   */
  public IntakeLovEntityBuilder setUseLogical(boolean useLogical) {
    this.useLogical = useLogical;
    return this;
  }

  /**
   * @param legacyCategoryId - legacyCategoryId
   * @return the legacyCategoryId
   */
  public IntakeLovEntityBuilder setLegacyCategoryId(String legacyCategoryId) {
    this.legacyCategoryId = legacyCategoryId;
    return this;
  }

  /**
   * @param parentCategoryId - parentCategoryId
   * @return the parentCategoryId
   */
  public IntakeLovEntityBuilder setParentCategoryId(String parentCategoryId) {
    this.parentCategoryId = parentCategoryId;
    return this;
  }

  /**
   * @param legacyOtherCode - legacyOtherCode
   * @return the legacyOtherCode
   */
  public IntakeLovEntityBuilder setLegacyOtherCode(String legacyOtherCode) {
    this.legacyOtherCode = legacyOtherCode;
    return this;
  }

  /**
   * @param legacyLongDescription - legacyLongDescription
   * @return the legacyLongDescription
   */
  public IntakeLovEntityBuilder setLegacyLongDescription(String legacyLongDescription) {
    this.legacyLongDescription = legacyLongDescription;
    return this;
  }

  /**
   * @param intakeType - intakeType
   * @return the intakeType
   */
  public IntakeLovEntityBuilder setIntakeType(String intakeType) {
    this.intakeType = intakeType;
    return this;
  }

  /**
   * @param parentIntakeType - parentIntakeType
   * @return the parentIntakeType
   */
  public IntakeLovEntityBuilder setParentIntakeType(String parentIntakeType) {
    this.parentIntakeType = parentIntakeType;
    return this;
  }

  /**
   * @param intakeCode - intakeCode
   * @return the intakeCode
   */
  public IntakeLovEntityBuilder setIntakeCode(String intakeCode) {
    this.intakeCode = intakeCode;
    return this;
  }

  /**
   * @param intakeDisplay - intakeDisplay
   * @return the intakeDisplay
   */
  public IntakeLovEntityBuilder setIntakeDisplay(String intakeDisplay) {
    this.intakeDisplay = intakeDisplay;
    return this;
  }

}
