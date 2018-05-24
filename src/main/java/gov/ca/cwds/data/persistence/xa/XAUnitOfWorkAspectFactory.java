package gov.ca.cwds.data.persistence.xa;

@FunctionalInterface
public interface XAUnitOfWorkAspectFactory {

  XAUnitOfWorkAspect make();

}
