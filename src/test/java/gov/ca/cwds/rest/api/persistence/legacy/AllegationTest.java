package gov.ca.cwds.rest.api.persistence.legacy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Test;

public class AllegationTest {

	private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private String id = "a";
	private String abuseEndDate = "1973-11-22";
	private String abuseStartDate = "2006-09-12";
	private Integer abuseFrequency = 1;
	private String abuseFrequencyPeriodCode = "b";
	private String abuseLocationDescription = "c";
	private Integer allegationDispositionType= 2;
	private Integer allegationType = 3;
	private String dispositionDescription = "d";
	private String dispositionDate = "1963-11-22";
	private Boolean injuryHarmDetailIndicator = Boolean.TRUE;
	private String nonProtectingParentCode = "e";
	private Boolean staffPersonAddedIndicator = Boolean.FALSE;
	private String fkClientT = "f";
	private String fkClient0 = "g";
	private String fkReferralT = "h";
	private String countySpecificCode = "i";
	private Boolean zippyCrestedIndicator = Boolean.TRUE;
	private Integer placementFacilityType = 4;
		
	private String lastUpdatedId = "z";
	
	/*
	 * Constructor test
	 */
	@Test
	public void emtpyConstructorIsNotNull() throws Exception {
		assertThat(Allegation.class.newInstance(), is(notNullValue()));
	}

	@Test
	public void domainAllegationLastUpdateConstructorTest() throws Exception {
		gov.ca.cwds.rest.api.domain.Allegation domain = new gov.ca.cwds.rest.api.domain.Allegation(id, abuseEndDate,
				abuseFrequency, abuseFrequencyPeriodCode, abuseLocationDescription, abuseStartDate,
				allegationDispositionType, allegationType, dispositionDescription, dispositionDate,
				injuryHarmDetailIndicator, nonProtectingParentCode, staffPersonAddedIndicator, fkClientT, fkClient0,
				fkReferralT, countySpecificCode, zippyCrestedIndicator, placementFacilityType);

		Allegation persistent = new Allegation(domain, "z");
		assertThat(persistent.getId(), is(equalTo(id)));
		assertThat(persistent.getAbuseEndDate(), is(equalTo(df.parse(abuseEndDate))));
		assertThat(persistent.getAbuseStartDate(), is(equalTo(df.parse(abuseStartDate))));
		assertThat(persistent.getAbuseFrequency(), is(equalTo(abuseFrequency)));
		assertThat(persistent.getAbuseFrequencyPeriodCode(), is(equalTo(abuseFrequencyPeriodCode)));
		assertThat(persistent.getAbuseLocationDescription(), is(equalTo(abuseLocationDescription)));
		assertThat(persistent.getAllegationDispositionType(), is(equalTo(allegationDispositionType)));
		assertThat(persistent.getAllegationType(), is(equalTo(allegationType)));
		assertThat(persistent.getDispositionDescription(), is(equalTo(dispositionDescription)));
		assertThat(persistent.getDispositionDate(), is(equalTo(df.parse(dispositionDate))));
		assertThat(persistent.getInjuryHarmDetailIndicator(), is(equalTo("Y")));
		assertThat(persistent.getNonProtectingParentCode(), is(equalTo(nonProtectingParentCode)));
		assertThat(persistent.getStaffPersonAddedIndicator(), is(equalTo("N")));
		assertThat(persistent.getFkClient0(), is(equalTo(fkClient0)));
		assertThat(persistent.getFkClientT(), is(equalTo(fkClientT)));
		assertThat(persistent.getCountySpecificCode(), is(equalTo(countySpecificCode)));
		assertThat(persistent.getZippyCrestedIndicator(), is(equalTo("Y")));
		assertThat(persistent.getPlacementFacilityType(), is(equalTo(placementFacilityType)));
		assertThat(persistent.getLastUpdatedId(), is(equalTo(lastUpdatedId)));
	}


}
