package gov.ca.cwds.rest.api.persistence.cms;

import java.io.Serializable;

public class PrimaryKey3 implements Serializable {

  private static final long serialVersionUID = 1L;
  private String one;
  private String two;
  private String three;

  public PrimaryKey3() {}

  public PrimaryKey3(String one, String two, String three) {
    this.one = one;
    this.two = two;
    this.three = three;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "one=" + one.trim() + ",two=" + two.trim() + ",three=" + three.trim();
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((one == null) ? 0 : one.hashCode());
    result = prime * result + ((two == null) ? 0 : two.hashCode());
    result = prime * result + ((three == null) ? 0 : three.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;

    PrimaryKey3 other = (PrimaryKey3) obj;

    if (one == null) {
      if (other.one != null)
        return false;
    } else if (!one.equals(other.one))
      return false;

    if (two == null) {
      if (other.two != null)
        return false;
    } else if (!two.equals(other.two))
      return false;

    if (three == null) {
      if (other.three != null)
        return false;
    } else if (!three.equals(other.three))
      return false;

    return true;
  }

}
