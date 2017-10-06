/**
 * <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017
 * Chen Chao.
 */
package io.swagger.util;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.models.properties.PropertyBuilder;

/**
 * Stolen from Swagger 1.5.9. Display LOV values at runtime without compiling enums or Strings in
 * advance.
 * 
 * @author CWDS API Team
 */
public class AllowableEnumValues implements AllowableValues {

  private static final Logger LOGGER = LoggerFactory.getLogger(AllowableEnumValues.class);

  private final List<String> items;

  private AllowableEnumValues(List<String> items) {
    this.items = items;
  }

  public static AllowableEnumValues create(String allowableValues) {
    final List<String> items = new ArrayList<>();

    if (allowableValues.startsWith("$")) {
      LOGGER.info("Dynamic LOV!");
      items.add("Barney");
      items.add("Buzz");
      items.add("Candace");
      items.add("Dora");
      items.add("Ferb");
      items.add("Neutron");
      items.add("Perry");
      items.add("Phineas");
    } else {
      LOGGER.info(allowableValues);
      for (String value : allowableValues.split(",")) {
        final String trimmed = value.trim();
        if (!"".equals(trimmed)) {
          items.add(trimmed);
        }
      }
    }

    return items.isEmpty() ? null : new AllowableEnumValues(items);
  }

  public List<String> getItems() {
    return items;
  }

  @Override
  public Map<PropertyBuilder.PropertyId, Object> asPropertyArguments() {
    final Map<PropertyBuilder.PropertyId, Object> map =
        new EnumMap<>(PropertyBuilder.PropertyId.class);
    map.put(PropertyBuilder.PropertyId.ENUM, items);
    return map;
  }

}
