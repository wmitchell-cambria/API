package gov.ca.cwds.data.persistence.ns.papertrail;

import java.io.Serializable;

import javax.inject.Inject;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import gov.ca.cwds.data.ns.PaperTrailDao;
import gov.ca.cwds.data.persistence.ns.PaperTrail;

/**
 * onSave – Called when you save an object, the object is not save into database yet. onFlushDirty –
 * Called when you update an object, the object is not update into database yet. onDelete – Called
 * when you delete an object, the object is not delete into database yet. preFlush – Called before
 * the saved, updated or deleted objects are committed to database (usually before postFlush).
 * postFlush – Called after the saved, updated or deleted objects are committed to database.
 */
public class PaperTrailInterceptor extends EmptyInterceptor {

  private static final long serialVersionUID = 1L;

  private static final String CREATE = "create";
  private static final String UPDATE = "update";
  private static final String DESTROY = "destroy";

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
      createPaperTrail(CREATE, (String) id, (HasPaperTrail) entity);
    }
    return super.onSave(entity, id, state, propertyNames, types);
  }

  // Update
  @Override
  public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState,
      Object[] previousState, String[] propertyNames, Type[] types) {

    if (entity instanceof HasPaperTrail) {
      createPaperTrail(UPDATE, (String) id, (HasPaperTrail) entity);
    }
    return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
  }

  // Delete
  @Override
  public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames,
      Type[] types) {

    if (entity instanceof HasPaperTrail) {
      createPaperTrail(DESTROY, (String) id, (HasPaperTrail) entity);
    }
    super.onDelete(entity, id, state, propertyNames, types);
  }

  private void createPaperTrail(String event, String id, HasPaperTrail entity) {
    PaperTrail paperTrail = new PaperTrail(entity.getClass().getSimpleName(), id, event);
    paperTrailDao.create(paperTrail);
  }

  public PaperTrailDao getPaperTrailDao() {
    return paperTrailDao;
  }

  public void setPaperTrailDao(PaperTrailDao paperTrailDao) {
    this.paperTrailDao = paperTrailDao;
  }

}
