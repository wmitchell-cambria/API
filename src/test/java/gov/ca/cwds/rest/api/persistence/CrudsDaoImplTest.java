package gov.ca.cwds.rest.api.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CrudsDaoImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CrudsDaoImpl<Referral> crudsDaoImpl;
	
	private Referral existing = new Referral("1", "name1", new Date());
	private Referral toDelete = new Referral("todelete", "name-to-delete", new Date());
	private Referral toDelete2 = new Referral("todelete2", "name-to-delete2", new Date());
	private Referral toDelete3 = new Referral("todelete3", "name-to-delete3", new Date());
	private Referral toUpdate = new Referral("toUpdate", "name-to-update", new Date());
	
	@Before
	public void setup() {
		HashMap<String, Referral> dummyData = new HashMap<String, Referral>();
		dummyData.put(existing.getId(), existing);
		dummyData.put(toDelete.getId(), toDelete);
		dummyData.put(toDelete2.getId(), toDelete2);
		dummyData.put(toUpdate.getId(), toUpdate);
		crudsDaoImpl = new CrudsDaoImpl<>(dummyData); 
	}

	@Test
	public void findReturnsNullWhenNotFound() {
		assertThat(crudsDaoImpl.find("2"), is(nullValue()));
	}
	
	@Test
	public void findReturnsCorrectObjectWhenFound() {
		assertThat(crudsDaoImpl.find(existing.getId()).getId(), is(equalTo(existing.getId())));
	}

	@Test
	public void deleteDeletesTheObject() {
		crudsDaoImpl.delete(toDelete.getId());
		assertThat(crudsDaoImpl.find(toDelete.getId()), is(nullValue()));
	}

	@Test
	public void deleteReturnsObjectWhenDeleted() {
		assertThat(crudsDaoImpl.find(toDelete2.getId()).getId(), is(equalTo(toDelete2.getId())));
	}

	@Test
	public void deleteReturnsNullWhenNotFound() {
		assertThat(crudsDaoImpl.find(toDelete3.getId()), is(nullValue()));
	}
	
	@Test
	public void createReturnsNewObjectWithIdWhenCreated() {
		Referral tocreate = new Referral(null, "justcreated", new Date());
		assertThat(crudsDaoImpl.create(tocreate).getId(), is(notNullValue()));
	}

	@Test
	public void createThrowsEntityExistsEceptionWhenAlreadyExists() {
        thrown.expect(EntityExistsException.class);         
    	crudsDaoImpl.create(existing);
	}

	@Test
	public void updatedReturnsUpdatedObject() {
		Referral updated = new Referral( toUpdate.getId(), "updatedname", new Date());
		assertThat(crudsDaoImpl.update(updated).getReferralName(), is(equalTo(updated.getReferralName())));
	}

	@Test
	public void updateThrowsEntityNotFoundEceptionWhenNotExists() {
        thrown.expect(EntityNotFoundException.class);
    	crudsDaoImpl.update(new Referral("IDONTEXIST", "somename", new Date()));
	}

}
