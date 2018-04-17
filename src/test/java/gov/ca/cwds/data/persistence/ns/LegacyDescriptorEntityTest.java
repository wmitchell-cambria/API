package gov.ca.cwds.data.persistence.ns;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LegacyDescriptorEntityTest {

	private Long id = null;
	private String legacyId = "1234567ABC";
	private String legacyUiId = "0X5";
	private String legacyTableName = "CLIENT_T";
	private String legacyTableDescription = "Client";
	private String legacyLastUpdated = "";
	private String describableType = LegacyDescriptorEntity.DESCRIBABLE_TYPE_PARTICIPANT;
	private Long describableId = 1L;

	@Test
	public void testEmptyConstructor() throws Exception {
	  assertThat(LegacyDescriptorEntity.class.newInstance(), is(notNullValue()));
	}
	
	@Test
	public void testConstructor() throws Exception {
	  LegacyDescriptorEntity descriptor = new LegacyDescriptorEntity(legacyId, legacyUiId,
		  legacyTableName, legacyTableDescription, legacyLastUpdated, describableType, describableId);
	  assertThat(descriptor.getId(), is(equalTo(id)));
	  assertThat(descriptor.getPrimaryKey(), is(equalTo(id)));
	  assertThat(descriptor.getLegacyId(), is(equalTo(legacyId)));
	  assertThat(descriptor.getLegacyUiId(), is(equalTo(legacyUiId)));
	  assertThat(descriptor.getLegacyTableDescription(), is(equalTo(legacyTableDescription)));
	  assertThat(descriptor.getLegacyTableName(), is(equalTo(legacyTableName)));
	  assertThat(descriptor.getLegacyLastUpdated(), is(equalTo(legacyLastUpdated)));
	  assertThat(descriptor.getDescribableId(), is(equalTo(describableId)));
	  assertThat(descriptor.getDescribableType(), is(equalTo(describableType)));
	}
	
	@Test
	public void equalsShouldBeTrueWhenSameObject() throws Exception {
	  LegacyDescriptorEntity descriptor = new LegacyDescriptorEntity();	  
	  assertTrue(descriptor.equals(descriptor));
	}
	  
	@Test
	public void shouldHaveEqualsHashcode() throws Exception {
	  LegacyDescriptorEntity descriptor = new LegacyDescriptorEntity(legacyId, legacyUiId,
		  legacyTableName, legacyTableDescription, legacyLastUpdated, describableType, describableId);
	  LegacyDescriptorEntity descriptor1 = new LegacyDescriptorEntity(legacyId, legacyUiId,
		  legacyTableName, legacyTableDescription, legacyLastUpdated, describableType, describableId);
	  assertEquals("Expecting legacy descriptor to have same hash code", descriptor.hashCode(), descriptor1.hashCode());
	}
}
