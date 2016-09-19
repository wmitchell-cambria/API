package gov.ca.cwds.rest.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.rest.api.persistence.PersistentObject;
import gov.ca.cwds.rest.api.persistence.legacy.StaffPerson;
import gov.ca.cwds.rest.jdbi.DataAccessEnvironment;
import gov.ca.cwds.rest.jdbi.legacy.StaffPersonDao;

public class ForeignKeyValidatorTest {

	private final static ForeignKey constraintAnnotationRequired;
	private final static ForeignKey constraintAnnotationNotRequired;

	private String notFoundKey = "notFound";
	private String foundKey = "found";
	private StaffPerson foundStaffPerson = mock(StaffPerson.class);
	private ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
	
	@Before
	public void setup() throws Exception {
		StaffPersonDao staffPersonDao = mock(StaffPersonDao.class);
		when(staffPersonDao.find(notFoundKey)).thenReturn(null);
		when(staffPersonDao.find(foundKey)).thenReturn(foundStaffPerson);
		
		DataAccessEnvironment.register(StaffPerson.class, staffPersonDao);
	}

	@Test
	public void isValidReturnsTrueWhenNotRequiredAndKeyFound() throws Exception {
		ForeignKeyValidator validator = new ForeignKeyValidator();
		validator.initialize(constraintAnnotationNotRequired);
		assertThat(validator.isValid(foundKey, context), is(equalTo(true)));
	}
	
	@Test
	public void isValidReturnsFalseWhenNotRequiredButKeyNotFound() throws Exception {
		ForeignKeyValidator validator = new ForeignKeyValidator();
		validator.initialize(constraintAnnotationNotRequired);
		assertThat(validator.isValid(notFoundKey, context), is(equalTo(false)));
	}

	@Test
	public void isValidReturnsFalseWhenRequiredButKeyNotFound() throws Exception {
		ForeignKeyValidator validator = new ForeignKeyValidator();
		validator.initialize(constraintAnnotationRequired);
		assertThat(validator.isValid(notFoundKey, context), is(equalTo(false)));
	}

	@Test
	public void isValidReturnsTrueWhenNotRequiredAndEmptyValue() throws Exception {
		ForeignKeyValidator validator = new ForeignKeyValidator();
		validator.initialize(constraintAnnotationNotRequired);
		assertThat(validator.isValid("", context), is(equalTo(true)));
	}
	
	@Test
	public void isValidReturnsTrueWhenRequiredAndKeyFound() throws Exception {
		ForeignKeyValidator validator = new ForeignKeyValidator();
		validator.initialize(constraintAnnotationRequired);
		assertThat(validator.isValid(foundKey, context), is(equalTo(true)));
	}

	/*
	 * static initialization
	 */
	static {
		constraintAnnotationRequired = new ForeignKey() {
			
			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}
			
			@Override
			public boolean required() {
				return true;
			}
			
			@Override
			public Class<? extends PersistentObject> persistentObjectClass() {
				return StaffPerson.class;
			}
			
			@Override
			public Class<? extends Payload>[] payload() {
				return null;
			}
			
			@Override
			public String message() {
				return "message";
			}
			
			@Override
			public Class<?>[] groups() {
				return null;
			}
			
			@Override
			public String format() {
				return null;
			}
		};
		
		constraintAnnotationNotRequired = new ForeignKey() {
			
			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}
			
			@Override
			public boolean required() {
				return false;
			}
			
			@Override
			public Class<? extends PersistentObject> persistentObjectClass() {
				return StaffPerson.class;
			}
			
			@Override
			public Class<? extends Payload>[] payload() {
				return null;
			}
			
			@Override
			public String message() {
				return "message";
			}
			
			@Override
			public Class<?>[] groups() {
				return null;
			}
			
			@Override
			public String format() {
				return null;
			}
		};
	}
}
