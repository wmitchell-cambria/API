package gov.ca.cwds.rest.services.junit.template;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author CWDS API Team
 */
public interface ServiceTestTemplate {

  @Rule
  ExpectedException thrown = ExpectedException.none();

  @Before
  void setup() throws Exception;

  /*
   * JUnit test for find
   */
  @Test
  /*
   * if Service find() method throws Assertion Error
   */
  void testFindThrowsAssertionError() throws Exception;

  @Test
  void testFindReturnsCorrectEntity() throws Exception;

  @Test
  void testFindReturnsNullWhenNotFound() throws Exception;

  @Test
  void testFindThrowsNotImplementedException() throws Exception;

  /*
   * JUnit test for delete
   */
  @Test
  /*
   * if Service delete() method throws Assertion Error
   */
  void testDeleteThrowsAssertionError() throws Exception;

  @Test
  void testDeleteDelegatesToCrudsService() throws Exception;

  @Test
  void testDeleteReturnsNullWhenNotFound() throws Exception;

  @Test
  /*
   * if Service delete() method not implemented
   */
  void testDeleteThrowsNotImplementedException() throws Exception;

  @Test
  void testDeleteReturnsClass() throws Exception;

  /*
   * JUnit test for update
   */
  @Test
  /*
   * if Service update() throws Assertion Error
   */
  void testUpdateThrowsAssertionError() throws Exception;

  @Test
  void testUpdateReturnsDomain() throws Exception;

  @Test
  void testUpdateReturnsCorrectEntity() throws Exception;

  @Test
  void testUpdateThrowsServiceException() throws Exception;

  @Test
  /*
   * if Service update() method throws Exception
   */
  void testUpdateThrowsNotImplementedException() throws Exception;

  /*
   * JUnit test for create
   */
  @Test
  /*
   * if Service create() throws Assertion Error
   */
  void testCreateThrowsAssertionError() throws Exception;

  @Test
  void testCreateReturnsPostedClass() throws Exception;

  @Test
  void testCreateReturnsCorrectEntity() throws Exception;

  @Test
  /*
   * if Service create() throws error for blank ID
   */
  void testCreateBlankIDError() throws Exception;

  @Test
  /*
   * if Service create() throws error for null ID
   */
  void testCreateNullIDError() throws Exception;

  @Test
  /*
   * if Service create() throws error for empty ID
   */
  void testCreateEmptyIDError() throws Exception;

  @Test
  void testCreateThrowsNotImplementedException() throws Exception;

}
