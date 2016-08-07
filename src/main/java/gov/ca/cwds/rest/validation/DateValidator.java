package gov.ca.cwds.rest.validation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateValidator implements ConstraintValidator<Date, String> {
	private static final Logger LOGGER = LoggerFactory.getLogger(DateValidator.class);
	
	private String format;

    @Override
    public void initialize(Date constraintAnnotation) {
        this.format = constraintAnnotation.format();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        DateFormat df = new SimpleDateFormat(format);
        if( value != null ) {
        	try {
				df.parse(value);
			} catch (ParseException e) {
				LOGGER.info("Unable to validate date string {} with format {}", value, format);
				return false;
			}
        } else {
        	LOGGER.info("Unable to validate null date string with format {}", format);
        	return false;
        }
    	//return value !=null &&
        return true;
    }
}