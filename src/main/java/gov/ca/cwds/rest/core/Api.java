package gov.ca.cwds.rest.core;


/**
 * Constants defining information about our API.
 * 
 * 1)  Available Resource
 * 2)  Version numbers and their associated media type
 * 
 * @author CDWS API Team
 */
public class Api {
	
    /**
     * A {@code String} constant representing {@value #RESOURCE_APPLICATION} api.
     */
    public final static String RESOURCE_APPLICATION = "application";

    /**
     * A {@code String} constant representing {@value #RESOURCE_REFERRAL} api.
     */
    public final static String RESOURCE_REFERRAL = "referral";

    public final static String MEDIA_TYPE_JSON_V1 = "application/gov.ca.cwds.rest-v1+json";
    
	/**
	 *  The enum containing media types for each of the API versions. 
	 */
	public enum Version {
		JSON_VERSION_1(MEDIA_TYPE_JSON_V1);
		
	    /**
	     * A {@code String} constant representing {@value #VERSION_1} media type.
	     */
		private String mediaType;
		
		/**
		 * Constructor
		 * 
		 * @param mediaType	The media type for this version of the API
		 */
		private Version(String mediaType) {
			this.mediaType = mediaType;
		}

		/**
		 * @return the mediaType	The mediaType for this version of the API
		 */
		public String getMediaType() {
			return mediaType;
		}
	}
}
