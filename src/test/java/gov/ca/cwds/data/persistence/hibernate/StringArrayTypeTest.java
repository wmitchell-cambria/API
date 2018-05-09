package gov.ca.cwds.data.persistence.hibernate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class StringArrayTypeTest extends Doofenshmirtz<Client> {

  StringArrayType target;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    target = new StringArrayType();
  }

  @Test
  public void type() throws Exception {
    assertThat(StringArrayType.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void sqlTypes_A$() throws Exception {
    int[] actual = target.sqlTypes();
    int[] expected = {2003};
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void returnedClass_A$() throws Exception {
    Class actual = target.returnedClass();
    Class expected = String[].class;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void equals_A$Object$Object() throws Exception {
    Object x = null;
    Object y = null;
    boolean actual = target.equals(x, y);
    boolean expected = true;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void hashCode_A$Object() throws Exception {
    Object x = "hello world";
    int actual = target.hashCode(x);
    int expected = 0;
    assertThat(actual, is(not(expected)));
  }

  @Test
  public void nullSafeGet_A$ResultSet$StringArray$SharedSessionContractImplementor$Object()
      throws Exception {
    String[] names = new String[] {};
    SharedSessionContractImplementor session = mock(SharedSessionContractImplementor.class);
    Object owner = null;
    Object actual = target.nullSafeGet(rs, names, session, owner);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = SQLException.class)
  public void nullSafeGet_A$ResultSet$StringArray$SharedSessionContractImplementor$Object_T$SQLException()
      throws Exception {
    String[] names = new String[] {"hello", "world"};
    SharedSessionContractImplementor session = mock(SharedSessionContractImplementor.class);
    Object owner = null;

    when(rs.getArray(any(String.class))).thenThrow(SQLException.class);
    target.nullSafeGet(rs, names, session, owner);
  }

  @Test
  public void nullSafeSet_A$PreparedStatement$Object$int$SharedSessionContractImplementor()
      throws Exception {
    PreparedStatement st = mock(PreparedStatement.class);
    Object value = null;
    int index = 0;
    SharedSessionContractImplementor session = mock(SharedSessionContractImplementor.class);
    target.nullSafeSet(st, value, index, session);
  }

  @Test(expected = SQLException.class)
  public void nullSafeSet_A$PreparedStatement$Object$int$SharedSessionContractImplementor_T$SQLException()
      throws Exception {
    PreparedStatement st = mock(PreparedStatement.class);
    String[] value = {"junk"};
    int index = 0;
    SharedSessionContractImplementor session = mock(SharedSessionContractImplementor.class);
    when(session.connection()).thenThrow(SQLException.class);
    target.nullSafeSet(st, value, index, session);
  }

  @Test
  public void deepCopy_A$Object() throws Exception {
    Object value = null;
    Object actual = target.deepCopy(value);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void isMutable_A$() throws Exception {
    boolean actual = target.isMutable();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void disassemble_A$Object() throws Exception {
    Object value = null;
    Serializable actual = target.disassemble(value);
    Serializable expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void assemble_A$Serializable$Object() throws Exception {
    Serializable cached = DEFAULT_CLIENT_ID;
    Object owner = null;
    Object actual = target.assemble(cached, owner);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void replace_A$Object$Object$Object() throws Exception {
    Object original = null;
    Object target_ = null;
    Object owner = null;
    Object actual = target.replace(original, target_, owner);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
