package gov.ca.cwds.data.ns;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.ns.IntakeLOVCodeEntity;
import gov.ca.cwds.inject.NsSessionFactory;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class IntakeLOVCodeDao extends BaseDaoImpl<IntakeLOVCodeEntity> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public IntakeLOVCodeDao(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find map of IntakeLOVCodeEntity by Set of intake codes
   *
   * @param intakeCodes Set of intake codes
   * @return map where key is an intake code and value is an IntakeLOVCodeEntity
   */
  @SuppressWarnings("unchecked")
  public Map<String, IntakeLOVCodeEntity> findIntakeLOVCodesByIntakeCodes(Set<String> intakeCodes) {
    final Query<IntakeLOVCodeEntity> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(constructNamedQueryName("findIntakeLOVCodesByIntakeCodes"))
        .setParameter("intakeCodes", intakeCodes);
    return query.list().stream()
        .collect(Collectors.toMap(IntakeLOVCodeEntity::getIntakeCode, c -> c));
  }
}
