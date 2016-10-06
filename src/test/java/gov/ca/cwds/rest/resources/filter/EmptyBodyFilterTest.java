package gov.ca.cwds.rest.resources.filter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Link.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response.StatusType;

import org.junit.Before;
import org.junit.Test;

public class EmptyBodyFilterTest {

	private ContainerResponseContext responseContext;
	
	@Before
	public void setup() {
		responseContext = new ContainerResponseContext() {
			private Object entity;
			private int status;
			
			@Override
			public void setStatusInfo(StatusType statusInfo) {
			}

			@Override
			public void setStatus(int code) {
				this.status = code;
			}

			@Override
			public void setEntityStream(OutputStream outputStream) {
			}

			@Override
			public void setEntity(Object entity, Annotation[] annotations, MediaType mediaType) {
			}

			@Override
			public void setEntity(Object entity) {
				this.entity = entity;
			}

			@Override
			public boolean hasLink(String relation) {
				return false;
			}

			@Override
			public boolean hasEntity() {
				return false;
			}

			@Override
			public MultivaluedMap<String, String> getStringHeaders() {
				return null;
			}

			@Override
			public StatusType getStatusInfo() {
				return null;
			}

			@Override
			public int getStatus() {
				return status;
			}

			@Override
			public MediaType getMediaType() {
				return null;
			}

			@Override
			public URI getLocation() {
				return null;
			}

			@Override
			public Set<Link> getLinks() {
				return null;
			}

			@Override
			public Builder getLinkBuilder(String relation) {
				return null;
			}

			@Override
			public Link getLink(String relation) {
				return null;
			}

			@Override
			public int getLength() {
				return 0;
			}

			@Override
			public Date getLastModified() {
				return null;
			}

			@Override
			public Locale getLanguage() {
				return null;
			}

			@Override
			public MultivaluedMap<String, Object> getHeaders() {
				return null;
			}

			@Override
			public String getHeaderString(String name) {
				return null;
			}

			@Override
			public Type getEntityType() {
				return null;
			}

			@Override
			public EntityTag getEntityTag() {
				return null;
			}

			@Override
			public OutputStream getEntityStream() {
				return null;
			}

			@Override
			public Class<?> getEntityClass() {
				return null;
			}

			@Override
			public Annotation[] getEntityAnnotations() {
				return null;
			}

			@Override
			public Object getEntity() {
				return entity;
			}

			@Override
			public Date getDate() {
				return null;
			}

			@Override
			public Map<String, NewCookie> getCookies() {
				return null;
			}

			@Override
			public Set<String> getAllowedMethods() {
				return null;
			}
		};

	}

	@Test
	public void filterEmptiesBodyOnSuccess() throws Exception {
		responseContext.setStatus(201);
		responseContext.setEntity(new Object());
		EmptyBodyFilter emptyBodyFilter = new EmptyBodyFilter();
		emptyBodyFilter.filter(null, responseContext);
		assertThat(responseContext.getEntity(), is(nullValue()));
	}

	@Test
	public void filterEmptiesBodyOn200() throws Exception {
		responseContext.setStatus(200);
		responseContext.setEntity(new Object());
		EmptyBodyFilter emptyBodyFilter = new EmptyBodyFilter();
		emptyBodyFilter.filter(null, responseContext);
		assertThat(responseContext.getEntity(), is(nullValue()));
	}

	@Test
	public void filterEmptiesBodyOn299() throws Exception {
		responseContext.setStatus(299);
		responseContext.setEntity(new Object());
		EmptyBodyFilter emptyBodyFilter = new EmptyBodyFilter();
		emptyBodyFilter.filter(null, responseContext);
		assertThat(responseContext.getEntity(), is(nullValue()));
	}

	@Test
	public void filterDoesNotEmptyBodyOn199() throws Exception {
		responseContext.setStatus(199);
		responseContext.setEntity(new Object());
		EmptyBodyFilter emptyBodyFilter = new EmptyBodyFilter();
		emptyBodyFilter.filter(null, responseContext);
		assertThat(responseContext.getEntity(), is(notNullValue()));
	}

	@Test
	public void filterDoesNotEmptyBodyOn300() throws Exception {
		responseContext.setStatus(300);
		responseContext.setEntity(new Object());
		EmptyBodyFilter emptyBodyFilter = new EmptyBodyFilter();
		emptyBodyFilter.filter(null, responseContext);
		assertThat(responseContext.getEntity(), is(notNullValue()));
	}


}
