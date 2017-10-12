package gov.ca.cwds.rest.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * * @author CWDS API Team
 *
 */
public class SysIdShortToStringSerializer extends JsonSerializer<Short> {
  @Override
  public void serialize(Short sysCode, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    final String sysCodeString = sysCode.toString();
    jsonGenerator.writeString(sysCodeString);
  }

}
