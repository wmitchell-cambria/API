package gov.ca.cwds.data.persistence.ns.papertrail;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.io.Serializable;
import java.util.Iterator;

import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.type.Type;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.ns.PaperTrailDao;
import gov.ca.cwds.data.persistence.ns.PaperTrail;
import gov.ca.cwds.data.persistence.ns.ParticipantEntity;
import gov.ca.cwds.rest.util.Doofenshmirtz;

public class PaperTrailInterceptorTest extends Doofenshmirtz<PaperTrail> {

  PaperTrailDao paperTrailDao;
  PaperTrailInterceptor target;
  Query<PaperTrail> q;

  @Before
  @Override
  public void setup() throws Exception {
    super.setup();
    paperTrailDao = new PaperTrailDao(sessionFactory);
    target = new PaperTrailInterceptor();
    target.setPaperTrailDao(paperTrailDao);
    q = queryInator();
  }

  @Test
  public void type() throws Exception {
    assertThat(PaperTrailInterceptor.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  protected boolean save() throws Exception {
    final ParticipantEntity entity = new ParticipantEntity();
    entity.setId(DEFAULT_PARTICIPANT_ID);
    final Serializable id = DEFAULT_PARTICIPANT_ID;

    Object[] state = new Object[] {};
    String[] propertyNames = new String[] {};
    Type[] types = new Type[] {};
    return target.onSave(entity, id, state, propertyNames, types);
  }

  protected void delete() throws Exception {
    final ParticipantEntity entity = new ParticipantEntity();
    entity.setId(DEFAULT_PARTICIPANT_ID);
    final Serializable id = DEFAULT_PARTICIPANT_ID;

    Object[] state = new Object[] {};
    String[] propertyNames = new String[] {};
    Type[] types = new Type[] {};
    target.onDelete(entity, id, state, propertyNames, types);
  }

  protected boolean update() throws Exception {
    final ParticipantEntity entity = new ParticipantEntity();
    entity.setId(DEFAULT_PARTICIPANT_ID);
    final Serializable id = DEFAULT_PARTICIPANT_ID;

    Object[] currentState = new Object[] {};
    Object[] previousState = new Object[] {};
    String[] propertyNames = new String[] {};
    Type[] types = new Type[] {};
    return target.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
  }

  @Test
  public void onSave_A$Object$Serializable$ObjectArray$StringArray$TypeArray() throws Exception {
    delete();
    update();
    boolean actual = save();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void onFlushDirty_A$Object$Serializable$ObjectArray$ObjectArray$StringArray$TypeArray()
      throws Exception {
    boolean actual = update();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void onDelete_A$Object$Serializable$ObjectArray$StringArray$TypeArray() throws Exception {
    delete();
  }

  @Test
  public void preFlush_A$Iterator() throws Exception {
    save();
    Iterator entities = mock(Iterator.class);
    target.preFlush(entities);
  }

  @Test
  public void getPaperTrailDao_A$() throws Exception {
    PaperTrailDao actual = target.getPaperTrailDao();
    PaperTrailDao expected = paperTrailDao;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPaperTrailDao_A$PaperTrailDao() throws Exception {
    target.setPaperTrailDao(paperTrailDao);
  }

  @Test
  public void toString_A$() throws Exception {
    String actual = target.toString();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void afterTransactionBegin_A$Transaction() throws Exception {
    Transaction tx = mock(Transaction.class);
    target.afterTransactionBegin(tx);
  }

  @Test
  public void afterTransactionCompletion_A$Transaction() throws Exception {
    Transaction tx = mock(Transaction.class);
    target.afterTransactionCompletion(tx);
  }

}
