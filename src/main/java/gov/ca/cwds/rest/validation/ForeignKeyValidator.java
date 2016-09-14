package gov.ca.cwds.rest.validation;

import gov.ca.cwds.rest.jdbi.CrudsDaoImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

public class ForeignKeyValidator implements ConstraintValidator<ForeignKey, String> {
  private static final Logger LOGGER = LoggerFactory.getLogger(ForeignKeyValidator.class);

  private boolean required;
  private String daoImplementer;
  private CrudsDaoImpl<?> crudsDao;
  private SessionFactory sessionFactory;

  @Override
  public void initialize(ForeignKey constraintAnnotation) {
    this.daoImplementer = constraintAnnotation.daoImplementer();
    this.required = constraintAnnotation.required();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (required || !Strings.isNullOrEmpty(value)) {
      try {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        sessionFactory.getCurrentSession().beginTransaction();
        crudsDao =
            (CrudsDaoImpl<?>) Class.forName("gov.ca.cwds.rest.jdbi.legacy." + daoImplementer)
                .getConstructor(SessionFactory.class).newInstance(sessionFactory);
        if (crudsDao.find(value) == null) {
          LOGGER.info("Unable to validate Staff Person Id string {} ", value);
          return false;
        }
        Object found = crudsDao.find(value);
      } catch (Exception e) {
        LOGGER.info("Exception in Foreign Key Validator {} ", e.toString());
        return false;
      } finally {
        sessionFactory.close();
      }
    }

    return true;
  }
}
