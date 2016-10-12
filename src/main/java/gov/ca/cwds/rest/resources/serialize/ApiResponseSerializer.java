package gov.ca.cwds.rest.resources.serialize;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import gov.ca.cwds.rest.api.domain.ApiResponse;

/**
 *  Serializer for {@link ApiResponse}
 * 
 * @author CWDS API Team
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class ApiResponseSerializer extends StdSerializer<ApiResponse> {

    public ApiResponseSerializer() {
        this(null);
    }
   
    public ApiResponseSerializer(Class<ApiResponse> t) {
        super(t);
    }
 
    @Override
    public void serialize(ApiResponse value, JsonGenerator jgen, SerializerProvider provider) 
      throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeStringField("id", value.getId());
        jgen.writeObjectField(value.getObject().getClass().getSimpleName(), value.getObject());
        jgen.writeEndObject();
    }
}