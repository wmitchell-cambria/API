package gov.ca.cwds.data.persistence.hibernate.dialect;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL9Dialect;

/**
 * Wrap default PostgreSQL9Dialect with 'json' type.
 *
 * @author Intake Team 4
 */
public class JsonPostgreSQLDialect extends PostgreSQL9Dialect {

  public JsonPostgreSQLDialect() {
    super();
    this.registerColumnType(Types.JAVA_OBJECT, "json");
  }

}
