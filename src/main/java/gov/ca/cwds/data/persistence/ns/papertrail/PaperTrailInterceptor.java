package gov.ca.cwds.data.persistence.ns.papertrail;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import gov.ca.cwds.data.ns.PaperTrailDao;
import gov.ca.cwds.data.persistence.ns.PaperTrail;

/**
 * @author Intake Team 4
 *
 *         onSave - Called when you save an object, the object is not save into database yet.
 *         onFlushDirty - Called when you update an object, the object is not update into database
 *         yet. onDelete - Called when you delete an object, the object is not delete into database
 *         yet. preFlush - Called before the saved, updated or deleted objects are committed to
 *         database (usually before postFlush). postFlush - Called after the saved, updated or
 *         deleted objects are committed to database.
 */
public class PaperTrailInterceptor extends EmptyInterceptor {

  private static final long serialVersionUID = 1L;

  private static final String CREATE = "create";
  private static final String UPDATE = "update";
  private static final String DESTROY = "destroy";

  private static final ThreadLocal<Map<String, Object>> insertsTlMap =
      ThreadLocal.withInitial(HashMap::new);
  private static final ThreadLocal<Map<String, Object>> updatesTlMap =
      ThreadLocal.withInitial(HashMap::new);
  private static final ThreadLocal<Map<String, Object>> deletesTlMap =
      ThreadLocal.withInitial(HashMap::new);


  @Inject
  private PaperTrailDao paperTrailDao;

  public PaperTrailInterceptor() {
    // Default ctor.
  }

  // Create
  @Override
  public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames,
      Type[] types) {

    if (entity instanceof HasPaperTrail) {
      insertsTlMap.get().put(getItemTypeAndId((HasPaperTrail) entity), entity);
    }
    return super.onSave(entity, id, state, propertyNames, types);
  }

  // Update
  @Override
  public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState,
      Object[] previousState, String[] propertyNames, Type[] types) {

    if (entity instanceof HasPaperTrail) {
      updatesTlMap.get().put(getItemTypeAndId((HasPaperTrail) entity), entity);
    }
    return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
  }

  // Delete
  @Override
  public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames,
      Type[] types) {

    if (entity instanceof HasPaperTrail) {
      deletesTlMap.get().put(getItemTypeAndId((HasPaperTrail) entity), entity);
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

      for (Entry<String, Object> entry : insertsTlMap.get().entrySet()) {
        HasPaperTrail entity = (HasPaperTrail) entry.getValue();
        createPaperTrail(CREATE, entity);
      }

      for (Entry<String, Object> entry : updatesTlMap.get().entrySet()) {
        HasPaperTrail entity = (HasPaperTrail) entry.getValue();
        createPaperTrail(UPDATE, entity);
      }

      for (Entry<String, Object> entry : deletesTlMap.get().entrySet()) {
        HasPaperTrail entity = (HasPaperTrail) entry.getValue();
        createPaperTrail(DESTROY, entity);
      }

    } finally {
      insertsTlMap.get().clear();
      updatesTlMap.get().clear();
      deletesTlMap.get().clear();
    }

  }

  private String getItemTypeAndId(HasPaperTrail entity) {
    return entity.getClass().getSimpleName().concat("->").concat(entity.getId());
  }

  private void createPaperTrail(String event, HasPaperTrail entity) {
    PaperTrail paperTrail =
        new PaperTrail(entity.getClass().getSimpleName(), entity.getId(), event);
    paperTrailDao.create(paperTrail);
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

}
