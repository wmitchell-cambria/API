package gov.ca.cwds.rest.api.services.screeningparticipant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CWDS API Team
 *
 */
public enum LegacyDaoMapperEnum {

  //
  // CHECKSTYLE:OFF
  //
  CLIENT("CLIENT_T", "ClientDao", "ClientTransformer"),

  REPORTER("REPTR_T", "ReporterDao", "ReporterTransformer"),

  COLLATERAL_INDIVIDUAL("COLTRL_T", "CollateralIndividualDao", "CollateralIndividualTranformer"),

  SERVICE_PROVIDER("SVC_PVRT", "ServiceProviderDao", "ServiceProviderTransformer"),

  SUBSTITITE_CARE_PROVIDER("SB_PVDRT", "SubstituteCareProviderDao",
      "SubstituteCareProviderTransformer"),

  EDUCATION_PROVIDER_CONTACT("EDPRVCNT", "EducationProviderContactDao",
      "EducationProviderContactTransformer"),

  OTHER_ADULT_IN_PLACEMENT_HOME("OTH_ADLT", "OtherAdultInPlacemtHomeDao",
      "OtherAdultInPlacemtHomeTransformer"),

  OTHER_CHILD_IN_PLACEMENT_HOME("OTH_KIDT", "OtherChildInPlacemtHomeDao",
      "OtherChildInPlacemtHomeTransformer");

  private static final Map<String, LegacyDaoMapperEnum> legacyDaoMapper = new HashMap<>();

  private final String tableName;
  private final String daoName;
  private final String tranformerName;

  private LegacyDaoMapperEnum(String tableName, String daoName, String tranformerName) {
    this.tableName = tableName;
    this.daoName = daoName;
    this.tranformerName = tranformerName;
  }

  public String getTableName() {
    return tableName;
  }

  public String getDaoName() {
    return daoName;
  }

  public String getTranformerName() {
    return tranformerName;
  }

  public static LegacyDaoMapperEnum findByTableName(String tableName) {
    return legacyDaoMapper.containsKey(tableName) ? legacyDaoMapper.get(tableName) : null;
  }

  static {
    for (LegacyDaoMapperEnum e : LegacyDaoMapperEnum.values()) {
      legacyDaoMapper.put(e.tableName, e);
    }
  }

}
