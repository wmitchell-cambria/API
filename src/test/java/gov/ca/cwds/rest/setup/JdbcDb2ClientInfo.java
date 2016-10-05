package gov.ca.cwds.rest.setup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import com.ibm.db2.jcc.DB2Connection;

public class JdbcDb2ClientInfo {

  public static void main(String[] args) {

    try {
      Class.forName("com.ibm.db2.jcc.DB2Driver");

      // DOCKER:
      // final String url = "jdbc:db2://localhost:50000/DB0TDEV";
      // final String user = "db2inst1";
      // final String password = "prasad12";

      // MAINFRAME:
      final String url =
          "jdbc:db2://localhost:9000/DB0TSOC:retrieveMessagesFromServerOnGetMessage=true;emulateParameterMetaDataForZCalls=1;";
      final String user = "cwdsrba";
      final String password = "1Passwor";

      Connection conn = DriverManager.getConnection(url, user, password);

      if (conn instanceof DB2Connection) {
        DB2Connection db2conn = (DB2Connection) conn;

        db2conn.setDB2ClientUser("0X5"); // staff id
        db2conn.setDB2ClientWorkstation("192.168.9.121");

        // Execute SQL to force extended client information to be sent to the server.
        ResultSet rs = conn
            .prepareStatement(
                "SELECT CURRENT CLIENT_USERID as staff_id FROM sysibm.sysdummy1 for read only")
            .executeQuery();

        while (rs.next()) {
          final String staffId = rs.getString("staff_id");
          System.out.println("staffId = " + staffId);
        }
      }
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

}

