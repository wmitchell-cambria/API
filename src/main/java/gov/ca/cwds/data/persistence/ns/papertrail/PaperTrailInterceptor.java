package gov.ca.cwds.data.persistence.ns.papertrail;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.ns.PaperTrailDao;
import gov.ca.cwds.data.persistence.ns.PaperTrail;
import gov.ca.cwds.rest.filters.RequestExecutionContext;
import gov.ca.cwds.rest.filters.RequestExecutionContextCallback;
import gov.ca.cwds.rest.filters.RequestExecutionContextRegistry;

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
 * <td align="justify">Called before the saved, updated or deleted objects are committed to database
 * (usually before postFlush)</td>
 * </tr>
 * <tr>
 * <td align="justify">postFlush</td>
 * <td align="justify">Called after the saved, updated or deleted objects are committed to
 * database</td>
 * </tr>
 * </table>
 * 
 * <p>
 * <strong>INVESTIGATE: audits on read-only operations?</strong>
 * </p>
 * 
 * @author CWDS API Team
 */
public class PaperTrailInterceptor extends EmptyInterceptor
    implements RequestExecutionContextCallback {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(PaperTrailInterceptor.class);

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
  private transient PaperTrailDao paperTrailDao;

  /**
   * Default ctor.
   */
  public PaperTrailInterceptor() {
    // Notify this instance upon request start and end.
    RequestExecutionContextRegistry.registerCallback(this);
  }

  @Override
  public Serializable key() {
    return PaperTrailInterceptor.class.getName();
  }

  @Override
  public void startRequest(RequestExecutionContext ctx) {
    LOGGER.debug("PaperTrailInterceptor.startRequest");
    clearMaps();
  }

  @Override
  public void endRequest(RequestExecutionContext ctx) {
    LOGGER.debug("PaperTrailInterceptor.endRequest");
    clearMaps();
  }

  // Create
  @Override
  public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames,
      Type[] types) {
    final boolean readOnly = RequestExecutionContext.instance().isResourceReadOnly();
    LOGGER.debug("PaperTrailInterceptor.onSave: readOnly: {}", readOnly);

    if (entity instanceof HasPaperTrail) {
      insertsTlMap.get().put(getItemTypeAndId((HasPaperTrail) entity), entity);
    }
    return super.onSave(entity, id, state, propertyNames, types);
  }

  // Update
  @Override
  public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState,
      Object[] previousState, String[] propertyNames, Type[] types) {
    final boolean readOnly = RequestExecutionContext.instance().isResourceReadOnly();
    LOGGER.debug("PaperTrailInterceptor.onFlushDirty: readOnly: {}", readOnly);

    if (entity instanceof HasPaperTrail) {
      updatesTlMap.get().put(getItemTypeAndId((HasPaperTrail) entity), entity);
    }
    return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
  }

  // Delete
  @Override
  public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames,
      Type[] types) {
    final boolean readOnly = RequestExecutionContext.instance().isResourceReadOnly();
    LOGGER.debug("PaperTrailInterceptor.onDelete: readOnly: {}", readOnly);

    if (entity instanceof HasPaperTrail) {
      deletesTlMap.get().put(getItemTypeAndId((HasPaperTrail) entity), entity);
    }
    super.onDelete(entity, id, state, propertyNames, types);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void preFlush(Iterator entities) {
    LOGGER.debug("PaperTrailInterceptor.preFlush");
    processPaperTrail();
    super.preFlush(entities);
  }

  @Override
  public void afterTransactionBegin(Transaction tx) {
    LOGGER.debug("PaperTrailInterceptor.afterTransactionBegin");
    super.afterTransactionBegin(tx);
    clearMaps();
  }

  @Override
  public void afterTransactionCompletion(Transaction tx) {
    LOGGER.debug("PaperTrailInterceptor.afterTransactionCompletion");
    super.afterTransactionCompletion(tx);
    clearMaps();
  }

  protected void processPaperTrail() {
    try {
      for (Entry<String, Object> entry : insertsTlMap.get().entrySet()) {
        createPaperTrail(CREATE, (HasPaperTrail) entry.getValue());
      }

      for (Entry<String, Object> entry : updatesTlMap.get().entrySet()) {
        createPaperTrail(UPDATE, (HasPaperTrail) entry.getValue());
      }

      for (Entry<String, Object> entry : deletesTlMap.get().entrySet()) {
        createPaperTrail(DESTROY, (HasPaperTrail) entry.getValue());
      }

    } finally {
      clearMaps();
    }
  }

  protected void clearMaps() {
    insertsTlMap.get().clear();
    updatesTlMap.get().clear();
    deletesTlMap.get().clear();
  }

  protected String getItemTypeAndId(HasPaperTrail entity) {
    return entity.getClass().getSimpleName().concat("->").concat(entity.getId());
  }

  protected void createPaperTrail(String event, HasPaperTrail entity) {
    final PaperTrail paperTrail =
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
