package gov.ca.cwds.rest.api.domain.intake;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.domain.legacy.Allegation;
import gov.ca.cwds.rest.api.domain.legacy.CrossReport;
import gov.ca.cwds.rest.api.domain.legacy.Referral;
import gov.ca.cwds.rest.api.domain.legacy.ReferralClient;
import gov.ca.cwds.rest.api.domain.legacy.Reporter;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.validation.AnonymousReporterInd;

/**
 * Logical representation of a Referral
 * 
 * @author CWDS API Team
 */
@InjectLinks({ 
	@InjectLink(value = "/{resource}/{id}", rel = "referral", style = Style.ABSOLUTE, bindings = { @Binding(name = "id", value = "${instance.referral.id}"), @Binding(name = "resource", value = Api.RESOURCE_REFERRAL) }),
	@InjectLink(value = "/{resource}/{id}", rel = "allegation", style = Style.ABSOLUTE, bindings = { @Binding(name = "id", value = "${instance.allegation.id}"), @Binding(name = "resource", value = Api.RESOURCE_ALLEGATION) }),
	@InjectLink(value = "/{resource}/{id}", rel = "crossReport", style = Style.ABSOLUTE, bindings = { @Binding(name = "id", value = "referralId=${instance.crossReport.referralId},thirdId=${instance.crossReport.thirdId}"), @Binding(name = "resource", value = Api.RESOURCE_CROSS_REPORT) }),
	@InjectLink(value = "/{resource}/{id}", rel = "referralClient", style = Style.ABSOLUTE, bindings = { @Binding(name = "id", value = "referralId=${instance.referralClient.referralId},clientId=${instance.referralClient.clientId}"), @Binding(name = "resource", value = Api.RESOURCE_REFERRAL) }),
	@InjectLink(value = "/{resource}/{id}", rel = "reporter", style = Style.ABSOLUTE, bindings = { @Binding(name = "id", value = "${instance.reporter.referralId}"), @Binding(name = "resource", value = Api.RESOURCE_REFERRAL) }),
	})

@AnonymousReporterInd
public class IntakeReferral {
	private Referral referral;
	private Allegation allegation;
	private CrossReport crossReport;
	private ReferralClient referralClient;
	private Reporter reporter;

	@JsonCreator
	public IntakeReferral(@JsonProperty("referral") Referral referral,
			@JsonProperty("allegation") Allegation allegation, @JsonProperty("crossReport") CrossReport crossReport,
			@JsonProperty("referralClient") ReferralClient referralClient,
			@JsonProperty("reporter") Reporter reporter) {
		super();
		this.referral = referral;
		this.allegation = allegation;
		this.crossReport = crossReport;
		this.referralClient = referralClient;
		this.reporter = reporter;
	}

	/**
	 * @return the referral
	 */
	public Referral getReferral() {
		return referral;
	}

	/**
	 * @return the allegation
	 */
	public Allegation getAllegation() {
		return allegation;
	}

	/**
	 * @return the crossReport
	 */
	public CrossReport getCrossReport() {
		return crossReport;
	}

	/**
	 * @return the referralClient
	 */
	public ReferralClient getReferralClient() {
		return referralClient;
	}

	/**
	 * @return the reporter
	 */
	public Reporter getReporter() {
		return reporter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allegation == null) ? 0 : allegation.hashCode());
		result = prime * result + ((crossReport == null) ? 0 : crossReport.hashCode());
		result = prime * result + ((referral == null) ? 0 : referral.hashCode());
		result = prime * result + ((referralClient == null) ? 0 : referralClient.hashCode());
		result = prime * result + ((reporter == null) ? 0 : reporter.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntakeReferral other = (IntakeReferral) obj;
		if (allegation == null) {
			if (other.allegation != null)
				return false;
		} else if (!allegation.equals(other.allegation))
			return false;
		if (crossReport == null) {
			if (other.crossReport != null)
				return false;
		} else if (!crossReport.equals(other.crossReport))
			return false;
		if (referral == null) {
			if (other.referral != null)
				return false;
		} else if (!referral.equals(other.referral))
			return false;
		if (referralClient == null) {
			if (other.referralClient != null)
				return false;
		} else if (!referralClient.equals(other.referralClient))
			return false;
		if (reporter == null) {
			if (other.reporter != null)
				return false;
		} else if (!reporter.equals(other.reporter))
			return false;
		return true;
	}

}
