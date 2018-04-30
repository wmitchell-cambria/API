package gov.ca.cwds.data.persistence.xa;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.db2.jcc.DB2Connection;

import gov.ca.cwds.rest.filters.RequestExecutionContext;

/**
 * Set DB2 user information on the active connection, including user logon and staff id.
 * 
 * @author CWDS API Team
 */
public class WorkDB2UserInfo implements Work {

  private static final Logger LOGGER = LoggerFactory.getLogger(WorkDB2UserInfo.class);

  @Override
  public void execute(Connection connection) throws SQLException {
    if (connection instanceof DB2Connection) {
      LOGGER.info("DB2 connection! Set user info ...");
      final DB2Connection db2conn = (DB2Connection) connection;

      final RequestExecutionContext ctx = RequestExecutionContext.instance();
      final String staffId = ctx.getStaffId();
      final String userId = ctx.getUserId();

      db2conn.setDB2ClientAccountingInformation(userId);
      db2conn.setDB2ClientApplicationInformation(userId);
      db2conn.setDB2ClientProgramId("FERB");
      db2conn.setClientInfo("ApplicationName", "FERB");
      db2conn.setClientInfo("ClientUser", staffId);
      db2conn.setDB2ClientUser(staffId);
    }
  }

}
