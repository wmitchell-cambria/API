package gov.ca.cwds.fixture;

import java.math.BigDecimal;
import java.util.Date;

import gov.ca.cwds.data.persistence.cms.OtherChildInPlacemtHome;

/**
 * @author CWDS API Team
 *
 */
public class OtherChildInPlacemtHomeEntityBuilder {

  BigDecimal annualUnearnedIncomeAmount = BigDecimal.ONE;
  Date birthDate = new Date();
  String fkplcHmT = "Amcp9H32oP";
  String genderCode = "M";
  String id = "Ahzpb2C0Ki";
  String name = "aaa bbb";

  /**
   * @return the otherChildInPlacemtHome
   */
  public OtherChildInPlacemtHome build() {
    return new OtherChildInPlacemtHome(annualUnearnedIncomeAmount, birthDate, fkplcHmT, genderCode,
        id, name);
  }

  /**
   * @param annualUnearnedIncomeAmount - annualUnearnedIncomeAmount
   * @return the annualUnearnedIncomeAmount
   */
  public OtherChildInPlacemtHomeEntityBuilder setAnnualUnearnedIncomeAmount(
      BigDecimal annualUnearnedIncomeAmount) {
    this.annualUnearnedIncomeAmount = annualUnearnedIncomeAmount;
    return this;
  }

  /**
   * @param birthDate - birthDate
   * @return the birthDate
   */
  public OtherChildInPlacemtHomeEntityBuilder setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
    return this;
  }

  /**
   * @param fkplcHmT - fkplcHmT
   * @return the fkplcHmT
   */
  public OtherChildInPlacemtHomeEntityBuilder setFkplcHmT(String fkplcHmT) {
    this.fkplcHmT = fkplcHmT;
    return this;
  }

  /**
   * @param genderCode - genderCode
   * @return the genderCode
   */
  public OtherChildInPlacemtHomeEntityBuilder setGenderCode(String genderCode) {
    this.genderCode = genderCode;
    return this;
  }

  /**
   * @param id - id
   * @return the id
   */
  public OtherChildInPlacemtHomeEntityBuilder setId(String id) {
    this.id = id;
    return this;
  }

  /**
   * @param name - name
   * @return the name
   */
  public OtherChildInPlacemtHomeEntityBuilder setName(String name) {
    this.name = name;
    return this;
  }

}
