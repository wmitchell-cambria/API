## JMeter Tests For Business Rules

Information on JMeter is provided in the API Wiki *https://github.com/ca-cwds/API/wiki/JMeter-Tests*

These tests are designed to be run in integration environment. This requires login to SAF and token to be extracted from Perry. The login process is the initial part of each test.

Some additional variables will need to be provide in the JMeter user.properties file. These variables are:

saf.client.id<br>
saf.users.path<br>
saf.users.nocaseload.path<br>
saf.users.county<br>
saf.users.staff.id<br>
saf.idp.host<br>
saf.idp.port<br>
saf.idp.context.root<br>
perry.host<br>
perry.port<br>
perry.context.root<br>
ferb.url<br>
legacy.db2.url<br>
legacy.db2.username<br>
legacy.db2.password<br>

The saf.users.path will point to a file on the file system that contains test user id in the form USER,PASSWORD

Similarly saf.users.nocaseload.path will  contains test user id that is associated with to a test staff person who does NOT have a Caseload and hence cannot create a Referral. 

These files need to be added to the environment in which the test is to be executed.


### R06560CaseloadRequiredForFirstPrimaryAssignment.jmx

This test logs in with a user id that is associated with a test staff person who has a Caseload and hence is able to create a Referral. 

The test creates a Referral successfully via the API New System to the CMS Legacy System.

The test checks that per the Rule R - 06560 an entry is created in the Assignment Table in the CMS Legacy System that is associated with this newly created Referral.

### R06560ReferralSubmitFailsWhenNoCaseloadForLoggedInUser.jmx

This test logs in with a user id that is associated with a test staff person who does NOT have a Caseload and hence cannot create a Referral. 

The test does NOT creates a Referral successfully via the API New System to the CMS Legacy System.

The test checks that per the Rule R - 06560 an appropriate error message is generated that informs the end user of the Rule - 06560.

### R00785AllegationClientRestrictionSameVictimPerpetrator.jmx

This test logs in with a user id that is associated with a test staff person who has a Caseload and hence is able to create a Referral.

The test is attempting to create a Referral where victim and perpetrator are the same person.

The test checks that per the Rule R - 00785 an appropriate error message is generated that informs the end user that perpetratorClientId can not be same as victimClientId.
 
