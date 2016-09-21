package gov.ca.cwds.rest.validation;

import java.text.MessageFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import gov.ca.cwds.rest.api.persistence.PersistentObject;
import gov.ca.cwds.rest.jdbi.CrudsDao;
import gov.ca.cwds.rest.jdbi.DataAccessEnvironment;

/**
 * Validates a given {@link ForeignKey} exists.
 *  
 * @author CWDS API Team
 */
public class ForeignKeyValidator implements ConstraintValidator<ForeignKey, String> {
  private static final Logger LOGGER = LoggerFactory.getLogger(ForeignKeyValidator.class);

  private boolean required;
  private Class<? extends PersistentObject> persistentObjectClass;

  @Override
  public void initialize(ForeignKey constraintAnnotation) {
    this.persistentObjectClass = constraintAnnotation.persistentObjectClass();
    this.required = constraintAnnotation.required();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (required || !Strings.isNullOrEmpty(value)) {
    	@SuppressWarnings("rawtypes")
		CrudsDao crudsDao = DataAccessEnvironment.get(persistentObjectClass);
    	if( crudsDao == null ) {
    		throw new ValidationException(MessageFormat.format("Unable to find dao for class:{0}", persistentObjectClass.getName()));
    	}
    	if (crudsDao.find(value) == null) {
          LOGGER.info("Unable to validate Foreign Key {} ", value);
          return false;
        }
    }

    return true;
  }
}
