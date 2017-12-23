package gov.ca.cwds.rest.services.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.cms.DrmsDocumentTemplateDao;
import gov.ca.cwds.rest.api.domain.cms.DrmsDocumentTemplate;
import gov.ca.cwds.rest.services.TypedCrudsService;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Business layer object to work on {@link DrmsDocumentTemplate}
 *
 * @author Intake Team 4
 */
public class DrmsDocumentTemplateService implements TypedCrudsService<String, DrmsDocumentTemplate, DrmsDocumentTemplate> {

  private static final Logger LOGGER = LoggerFactory.getLogger(DrmsDocumentTemplate.class);
  private static final Short APPLICATION_CONTEXT_REFERRAL_DOCUMENTS = 82;
  private static final Short GOVERMENT_ENTITY_SYSTEM = 0;
  private static final String SCREENERNARRATIVE = "Screener Narrative";

  private DrmsDocumentTemplateDao drmsDocumentTemplateDao;

  @Inject
  public DrmsDocumentTemplateService(DrmsDocumentTemplateDao drmsDocumentTemplateDao) {
    super();
    this.drmsDocumentTemplateDao = drmsDocumentTemplateDao;
  }

  public DrmsDocumentTemplate findScreenerNarrativeTemplate(Short govermentEntity){
    gov.ca.cwds.data.persistence.cms.DrmsDocumentTemplate template = null;
    gov.ca.cwds.data.persistence.cms.DrmsDocumentTemplate[] templates = drmsDocumentTemplateDao.findByApplicationContextAndGovermentEntity(APPLICATION_CONTEXT_REFERRAL_DOCUMENTS, govermentEntity);
    for (gov.ca.cwds.data.persistence.cms.DrmsDocumentTemplate doc: templates){
      //TODO For now can't use DOT files as templates with POI. So only _NS files are considered, which will be a DOC files
      if (SCREENERNARRATIVE.equals(doc.getTitleName()) && doc.getDocumentDOSFilePrefixName().toUpperCase().endsWith("_NS")){
        template = doc;
        if ((GOVERMENT_ENTITY_SYSTEM.equals(govermentEntity) && GOVERMENT_ENTITY_SYSTEM.equals(doc.getGovermentEntityType()))
            || (!GOVERMENT_ENTITY_SYSTEM.equals(govermentEntity) && !GOVERMENT_ENTITY_SYSTEM.equals(doc.getGovermentEntityType()))){
          // Asked for system and found system or asked for not system and found not system use it right away. If both exists then not system takes priority
          break;
        }
      }
    }

    if (template == null) return null;

    return new DrmsDocumentTemplate(template);
  }

  @Override
  public DrmsDocumentTemplate find(String s) {
    throw new NotImplementedException("find not implement");
  }

  @Override
  public DrmsDocumentTemplate delete(String s) {
    throw new NotImplementedException("delete not implement");
  }

  @Override
  public DrmsDocumentTemplate create(DrmsDocumentTemplate request) {
    throw new NotImplementedException("create not implement");
  }

  @Override
  public DrmsDocumentTemplate update(String s, DrmsDocumentTemplate request) {
    throw new NotImplementedException("update not implement");
  }
}
