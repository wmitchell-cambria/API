package gov.ca.cwds.data.ns;

import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.ns.IntakeLOVCodeEntity;
import gov.ca.cwds.inject.NsSessionFactory;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class IntakeLOVCodeDao extends CrudsDaoImpl<IntakeLOVCodeEntity> {

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
   * Find IntakeLOVCodeEntity object by intakeCode.
   *
   * @param intakeCode intakeCode
   * @return IntakeLOVCodeEntity
   */
  public IntakeLOVCodeEntity findIntakeLOVCodeByIntakeCode(String intakeCode) {
    @SuppressWarnings("unchecked")
    final Query<IntakeLOVCodeEntity> query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(
            "gov.ca.cwds.data.persistence.ns.IntakeLOVCodeEntity.findIntakeLOVCodeByIntakeCode")
        .setParameter("intakeCode", intakeCode);
    List<IntakeLOVCodeEntity> codes = query.list();
    return codes.isEmpty() ? null : codes.get(0);
  }
}
