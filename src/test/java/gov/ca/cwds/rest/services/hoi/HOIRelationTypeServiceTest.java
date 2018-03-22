package gov.ca.cwds.rest.services.hoi;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class HOIRelationTypeServiceTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testIsRelationTypeParentForAllValidParentToChildTypesSuccess() throws Exception {
    List<Integer> parentToChildTypes = new ArrayList<>();
    parentToChildTypes.addAll(Arrays.asList(243, 254, 5620, 6361));
    for (Integer i = 203; i <= 205; i++) {
      parentToChildTypes.add(i);
    }
    for (Integer i = 207; i <= 211; i++) {
      parentToChildTypes.add(i);
    }
    for (Integer i = 213; i <= 214; i++) {
      parentToChildTypes.add(i);
    }
    for (Integer i = 245; i <= 247; i++) {
      parentToChildTypes.add(i);
    }
    for (Integer i = 249; i <= 252; i++) {
      parentToChildTypes.add(i);
    }

    for (Integer parentToChildType : parentToChildTypes) {
      assertEquals(true,
          HOIRelationshipTypeService.isRelationTypeParent(parentToChildType.shortValue()));
    }
  }

  @Test
  public void testIsRelationTypeParentForSomeInvalidParentToChildTypesFails() throws Exception {
    List<Integer> parentToChildInvalidTypes = new ArrayList<>();
    parentToChildInvalidTypes.addAll(Arrays.asList(206, 212, 248, 253));

    for (Integer parentToChildType : parentToChildInvalidTypes) {
      assertEquals(false,
          HOIRelationshipTypeService.isRelationTypeParent(parentToChildType.shortValue()));
    }
  }

  @Test
  public void testIsRelationTypeChildForAllValidChildToParentTypesSuccess() throws Exception {
    List<Integer> childToParentTypes = new ArrayList<>();
    childToParentTypes.addAll(Arrays.asList(198, 199, 242, 293, 301, 6360));
    for (Integer i = 188; i <= 190; i++) {
      childToParentTypes.add(i);
    }
    for (Integer i = 192; i <= 196; i++) {
      childToParentTypes.add(i);
    }
    for (Integer i = 287; i <= 291; i++) {
      childToParentTypes.add(i);
    }
    for (Integer i = 283; i <= 285; i++) {
      childToParentTypes.add(i);
    }
    for (Integer childToParentType : childToParentTypes) {
      assertEquals(true,
          HOIRelationshipTypeService.isRelationTypeChild(childToParentType.shortValue()));
    }
  }

  @Test
  public void testIsRelationTypeChildForSomeInvalidChildToParentTypesFails() throws Exception {
    List<Integer> childToParentInvalidTypes = new ArrayList<>();
    childToParentInvalidTypes.addAll(Arrays.asList(191, 197, 286, 292));

    for (Integer childToParentType : childToParentInvalidTypes) {
      assertEquals(false,
          HOIRelationshipTypeService.isRelationTypeChild(childToParentType.shortValue()));
    }
  }


  @Test
  public void testIsRelationTypeSiblingForAllValidSiblingTypesSuccess() throws Exception {
    List<Integer> siblingTypes = new ArrayList<>();
    for (Integer i = 179; i <= 184; i++) {
      siblingTypes.add(i);
    }
    for (Integer i = 276; i <= 281; i++) {
      siblingTypes.add(i);
    }

    for (Integer siblingType : siblingTypes) {
      assertEquals(true,
          HOIRelationshipTypeService.isRelationTypeSibling(siblingType.shortValue()));
    }
  }

  @Test
  public void testIsParentChildOrSiblingRelationshipTypeForValidTypesSuccess() throws Exception {
    List<Integer> relationshipTypes = new ArrayList<>();
    relationshipTypes.addAll(Arrays.asList(179, 188, 203));
    for (Integer relationshipType : relationshipTypes) {
      assertEquals(true, HOIRelationshipTypeService
          .isParentChildOrSiblingRelationshipType(relationshipType.shortValue()));
    }
  }

}
