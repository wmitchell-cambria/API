package gov.ca.cwds.rest.util.jni;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import com.ibm.db2.jcc.DB2Connection;

@SuppressWarnings("squid:S2187")
public class DB2PassUserTest {

  @SuppressWarnings("deprecation")
  public static void main(String[] args) {

    try {
      Class.forName("com.ibm.db2.jcc.DB2Driver");

      // DOCKER:
      // final String url = "jdbc:db2://localhost:50000/DB0TDEV";

      // MAINFRAME:
      final String url =
          "jdbc:db2://localhost:9000/DB0TSOC:retrieveMessagesFromServerOnGetMessage=true;emulateParameterMetaDataForZCalls=1;";
      final String user = System.getenv("DB_CMS_USER");
      final String password = System.getenv("DB_CMS_PASSWORD");

      Connection conn = DriverManager.getConnection(url, user, password);

      if (conn instanceof DB2Connection) {
        DB2Connection db2conn = (DB2Connection) conn;
        db2conn.setDB2ClientUser("0X5"); // staff id
        // db2conn.setDB2ClientWorkstation("192.168.9.121");
        db2conn.setDB2ClientWorkstation("127.0.0.1");
        conn.setAutoCommit(true);
        conn.prepareStatement(
            "UPDATE CWSINT.TSCNTRLT C SET C.LST_UPD_ID = CURRENT CLIENT_USERID WHERE C.DOC_HANDLE = '0001121506110220*RAMESHA 00001'")
            .executeUpdate();

        // Execute SQL to force extended client information to be sent to the server.
        // Auto-close the ResultSet upon block exit.
        try (ResultSet rs = conn.prepareStatement(
            "SELECT C.DOC_HANDLE, C.DOC_SEGS, C.CMPRS_PRG, C.DOC_NAME, C.DOC_DATE, C.DOC_TIME, C.DOC_LEN, C.LST_UPD_ID, C.LST_UPD_TS, C.DOC_AUTH, C.DOC_SERV "
                + "FROM CWSINT.TSCNTRLT C "
                + "WHERE C.DOC_HANDLE = '0001121506110220*RAMESHA 00001' "
                + "ORDER BY C.DOC_HANDLE FOR READ ONLY WITH UR")
            .executeQuery()) {

          while (rs.next()) {
            final String staffId = rs.getString("LST_UPD_ID");
            System.out.println("staffId = " + staffId);
          }
        }
      }

    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

}

