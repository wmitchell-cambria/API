package gov.ca.cwds.rest.api.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hamcrest.core.Is;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DomainObjectTest {
	protected static final String DATE_FORMAT = "yyyy-MM-dd";
	protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd-HH.mm.ss.SSS";
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	//cookBoolean tests
    @Test
    public void cookBooleanReturnsNullOnNullBoolean() throws Exception {
        assertThat(DomainObject.cookBoolean(null), is(nullValue()));
    }
    
    @Test
    public void cookBooleanReturnsYOnTrue() throws Exception {
        assertThat(DomainObject.cookBoolean(Boolean.TRUE), is(equalTo("Y")));
    }
	
    @Test
    public void cookBooleanReturnsNOnFalse() throws Exception {
    	assertThat(DomainObject.cookBoolean(Boolean.FALSE), is(equalTo("N")));
    }

    //uncookBoolean tests
    @Test
    public void uncookBooleanStringReturnsFalseOnN() throws Exception {
    	assertThat(DomainObject.uncookBooleanString("N"), is(equalTo(Boolean.FALSE)));
    }
    
    @Test
    public void uncookBooleanStringReturnsFalseOnSmallN() throws Exception {
    	assertThat(DomainObject.uncookBooleanString("n"), is(equalTo(Boolean.FALSE)));
    }
    
    @Test
    public void uncookBooleanStringReturnsFalseOnY() throws Exception {
    	assertThat(DomainObject.uncookBooleanString("Y"), is(equalTo(Boolean.TRUE)));
    }
    
    @Test
    public void uncookBooleanStringReturnsFalseOnSmallY() throws Exception {
    	assertThat(DomainObject.uncookBooleanString("y"), is(equalTo(Boolean.TRUE)));
    }

    @Test
    public void uncookBooleanStringThrowsDomainExceptionOnNonYOrN() throws Exception {
        thrown.expect(DomainException.class);
        thrown.expectCause(Is.isA(ParseException.class));
    	DomainObject.uncookBooleanString("T");
    }
    
    //cookDate tests
    @Test
    public void cookDateReturnsNullOnNullDate() throws Exception {
        assertThat(DomainObject.cookDate(null), is(nullValue()));
    }
    
    @Test
    public void cookDateReturnsCorrectString() throws Exception {
    	DateFormat df = new SimpleDateFormat(DATE_FORMAT);
    	Date date = new Date();
    	
        assertThat(DomainObject.cookDate(date), is(equalTo(df.format(date))));
    }
   
    //cookTimestamp tests
    @Test
    public void cookTimestampReturnsNullOnNullDate() throws Exception {
        assertThat(DomainObject.cookTimestamp(null), is(nullValue()));
    }
    
    @Test
    public void cookTimestampReturnsCorrectString() throws Exception {
    	DateFormat df = new SimpleDateFormat(TIMESTAMP_FORMAT);
    	Date date = new Date();
    	
        assertThat(DomainObject.cookTimestamp(date), is(equalTo(df.format(date))));
    }
    
    //uncookDateString tests
    @Test
    public void uncookDateStringReturnsCorrectDate() throws Exception {
    	DateFormat df = new SimpleDateFormat(DATE_FORMAT);
    	Date dateWithTime = new Date();
    	String dateString = df.format(dateWithTime);

    	Date dateBasedOnFormat = df.parse(df.format(dateWithTime));
    	
        assertThat(DomainObject.uncookDateString(dateString), is(equalTo(dateBasedOnFormat)));
    }
    
    @Test
    public void uncookDateStringReturnsNullOnNullString() throws Exception {
    	assertThat(DomainObject.uncookDateString(null), is(nullValue()));
    }
    
    @Test
    public void uncookDateStringThrowsExceptionOnBadInput() throws Exception {
        thrown.expect(DomainException.class);
        thrown.expectCause(Is.isA(ParseException.class));
    	DomainObject.uncookDateString("dlfjkdfjdkfjkd");
    }
    
    //uncookTimestampString tests
    @Test
    public void uncookTimestampStringReturnsCorrectDate() throws Exception {
    	DateFormat df = new SimpleDateFormat(TIMESTAMP_FORMAT);
    	Date date = new Date();
    	String dateString = df.format(date);
    	
        assertThat(DomainObject.uncookTimestampString(dateString), is(equalTo(date)));
    }
    
    @Test
    public void uncookTimestampStringReturnsNullOnNullString() throws Exception {
    	assertThat(DomainObject.uncookDateString(null), is(nullValue()));
    }
    
    @Test
    public void uncookTimestampStringThrowsExceptionOnBadInput() throws Exception {
        thrown.expect(DomainException.class);
        thrown.expectCause(Is.isA(ParseException.class));
    	DomainObject.uncookTimestampString("dlfjkdfjdkfjkd");
    }
}
