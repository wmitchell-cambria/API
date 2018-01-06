package gov.ca.cwds.rest.business.rules;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.rest.business.RuleValidator;
import java.util.regex.Pattern;

/**
 * CWDS API Team<br>
 * <p>
 * R - 04966 Names must have at least one alpha char<br>
 * <p>
 * Rule Text <br>
 * There must be at least one alpha character in at least one of the name fields (first, middle, last).<br>
 * <p>
 * Access Logic<br>
 * For the following pages: Client/fraNameAndID, Client/fraNames, Placement Home/fraSCP, Person/fraID. <br>
 * If ((txtFirstName does not have an alpha character) and (txtMiddleName does not have an alpha character) and <br>
 * (txtLastName does not have an alpha character)), <br>
 * then return the cursor to the txtFirstName control and trigger Error 1201. <br>
 * For the following pages in the Search notebook: <br>
 * - Search/fraClientSearch, <br>
 * - Search/fraClientSubstituteCareProviderSearch,<br>
 * - fraSubstituteCareProvider, <br>
 * - fraHomeSearch(Rel/FFH/SFH/Grdn),<br>
 * - fraHomeSearch(FFACH/FFH),<br>
 * - fraHomeSearch(GH/FFA), <br>
 * - and fraHomeSearch(GH/FFACH) <br>
 *   if you are using First Name, Middle Name, Last Name as part of your search criteria and <br>
 *   the rule check fails then disable the OK button.<br>
 */
public class R04966NamesMustHaveAtLeastOneAlphaChar implements RuleValidator {

  private Client client;
  private Pattern pattern = Pattern.compile(".*[a-zA-Z]+.*");

  public R04966NamesMustHaveAtLeastOneAlphaChar(Client client) {
    this.client = client;
  }

  @Override
  public boolean isValid() {
    return isMatches(client.getCommonFirstName())
        || isMatches(client.getLastName())
        || isMatches(client.getMiddleName());
  }

  private boolean isMatches(String name) {
    return name != null && pattern.matcher(name).matches();
  }
}
