package gov.ca.cwds.data.persistence.ns.papertrail;

import gov.ca.cwds.data.ns.PaperTrailDao;
import gov.ca.cwds.data.persistence.ns.PaperTrail;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

/**
 * Synthetic triggers for PostgreSQL via Hibernate interceptor.
 *
 * <p>
 * Parameter Defaults
 * </p>
 * <table summary="Event Method">
 * <tr>
 * <th align="justify">Method</th>
 * <th align="justify">Description</th>
 * </tr>
 * <tr>
 * <td align="justify">onSave</td>
 * <td align="justify">Called when you save an object, the object is not yet saved to the
 * database</td>
 * </tr>
 * <tr>
 * <td align="justify">onFlushDirty</td>
 * <td align="justify">Called when you update an object, the object is not yet saved to the
 * database</td>
 * </tr>
 * <tr>
 * <td align="justify">onDelete</td>
 * <td align="justify">Called when you delete an object, the object is not yet saved to the
 * database</td>
 * </tr>
 * <tr>
 * <td align="justify">preFlush</td>
 * <td align="justify">Called before the saved, updated or deleted objects are committed to
 * database
 * (usually before postFlush)</td>
 * </tr>
 * <tr>
 * <td align="justify">postFlush</td>
 * <td align="justify">Called after the saved, updated or deleted objects are committed to
 * database</td>
 * </tr>
 * </table>
 *
 * @author Intake Team 4
 */
public class PaperTrailInterceptor extends EmptyInterceptor {

  public static final String STR_ARROW = "->";
  private static final long serialVersionUID = 1L;
  private static final String CREATE = "create";
  private static final String UPDATE = "update";
  private static final String DESTROY = "destroy";
  private static final ThreadLocal<Set<String>> insertsTlSet =
      ThreadLocal.withInitial(HashSet::new);
  private static final ThreadLocal<Set<String>> updatesTlSet =
      ThreadLocal.withInitial(HashSet::new);
  private static final ThreadLocal<Set<String>> deletesTlSet =
      ThreadLocal.withInitial(HashSet::new);
  @Inject
  private transient PaperTrailDao paperTrailDao;

  public PaperTrailInterceptor() {
    // Default ctor.
  }

  // Create
  @Override
  public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames,
      Type[] types) {

    if (entity instanceof HasPaperTrail) {
      insertsTlSet.get().add(getItemTypeAndId((HasPaperTrail) entity));
    }
    return super.onSave(entity, id, state, propertyNames, types);
  }

  // Update
  @Override
  public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState,
      Object[] previousState, String[] propertyNames, Type[] types) {

    if (entity instanceof HasPaperTrail) {
      updatesTlSet.get().add(getItemTypeAndId((HasPaperTrail) entity));
    }
    return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
  }

  // Delete
  @Override
  public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames,
      Type[] types) {

    if (entity instanceof HasPaperTrail) {
      deletesTlSet.get().add(getItemTypeAndId((HasPaperTrail) entity));
    }
    super.onDelete(entity, id, state, propertyNames, types);
  }

  @Override
  public void preFlush(Iterator entities) {
    processPaperTrail();
    super.preFlush(entities);
  }

  private void processPaperTrail() {
    try {
      insertsTlSet.get()
          .forEach(typeAndId -> paperTrailDao.create(createPaperTrail(typeAndId, CREATE)));
      updatesTlSet.get()
          .forEach(typeAndId -> paperTrailDao.create(createPaperTrail(typeAndId, UPDATE)));
      deletesTlSet.get()
          .forEach(typeAndId -> paperTrailDao.create(createPaperTrail(typeAndId, DESTROY)));

    } finally {
      clearSets();
    }
  }

  private void clearSets() {
    insertsTlSet.get().clear();
    updatesTlSet.get().clear();
    deletesTlSet.get().clear();
  }

  private String getItemTypeAndId(HasPaperTrail entity) {
    return entity.getClass().getSimpleName().concat(STR_ARROW).concat(entity.getId());
  }

  private PaperTrail createPaperTrail(String typeAndId, String event) {
    return new PaperTrail(StringUtils.split(typeAndId, STR_ARROW, 2)[0],
        StringUtils.split(typeAndId, STR_ARROW, 2)[1], event);
  }

  public PaperTrailDao getPaperTrailDao() {
    return paperTrailDao;
  }

  public void setPaperTrailDao(PaperTrailDao paperTrailDao) {
    this.paperTrailDao = paperTrailDao;
  }

  @Override
  public String toString() {
    return "PaperTrailInterceptor [paperTrailDao=" + paperTrailDao + "]";
  }

  @Override
  public void afterTransactionBegin(Transaction tx) {
    super.afterTransactionBegin(tx);
    clearSets();
  }

  @Override
  public void afterTransactionCompletion(Transaction tx) {
    super.afterTransactionCompletion(tx);
    clearSets();
  }

}
