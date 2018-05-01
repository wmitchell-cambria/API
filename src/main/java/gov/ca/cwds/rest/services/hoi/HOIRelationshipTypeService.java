package gov.ca.cwds.rest.services.hoi;

/**
 * <p>
 * This service handles a request to get relationships for the given clientId.
 * <p>
 * 
 * @author CWDS API Team
 *
 */
class HOIRelationshipTypeService {

  private HOIRelationshipTypeService() {
    throw new IllegalStateException("Utility class");
  }

  static boolean isRelationTypeParent(Short type) {
    boolean fatherToChildRelationship = type <= 214 && type >= 203 && type != 206 && type != 212;
    boolean motherToChildRelationship = type <= 254 && type >= 245 && type != 248 && type != 253;
    boolean parentToChildRelationship = type == 243 || type == 5620 || type == 6361;
    return fatherToChildRelationship || motherToChildRelationship || parentToChildRelationship;
  }

  static boolean isRelationTypeChild(Short type) {
    boolean daughterToParentRelationship = type <= 199 && type >= 188 && type != 191 && type != 197;
    boolean sonToParentRelationship = type <= 293 && type >= 283 && type != 286 && type != 292;
    boolean sonToMotherPresumedRelationship = type == 6360;
    boolean childToParentRelationship = type == 301 || type == 242;
    return daughterToParentRelationship || sonToParentRelationship
        || sonToMotherPresumedRelationship || childToParentRelationship;
  }

  static boolean isRelationTypeSibling(Short type) {
    boolean brotherToSiblingRelationship = type <= 184 && type >= 179;
    boolean sisterToSiblingRelationship = type <= 281 && type >= 276;
    return brotherToSiblingRelationship || sisterToSiblingRelationship;
  }

  static boolean isParentChildOrSiblingRelationshipType(Short type) {
    return isRelationTypeParent(type) || isRelationTypeChild(type) || isRelationTypeSibling(type);
  }

}
