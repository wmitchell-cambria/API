package gov.ca.cwds.rest.business.rules;

/**
 * @author CWDS API team
 *
 * Rule Text:
 * For each row in the grid, create an allegation row and set the victim client to the client
 * that is chosen from the victim combo-box drop down list and set the perpetrator client to the
 * client that is chosen from the perpetrator combo-box drop down list and set the allegation type
 * to the type chosen from the abuse category combo-box drop down list.
 * If no choice is made from the perpetrator combo-box (or None is chosen),
 * no client will be stored as the perpetrator.
 *
 * The ALLGTN_T.ALG_TPC (allegation type) and ALLGTN_T.FKCLIENT_T (victim client ID) DB fields
 * are defined as not nullable.
 * The Allegation entity java class also define these fields as not nullable.
 * Therefore DB and java model itself will not allow storing allegations without allegation type or victim.
 */
@SuppressWarnings("unused")
public final class R06505ZippyAllegationStorage {
  
  private R06505ZippyAllegationStorage() {
    // no-op
  }
}
