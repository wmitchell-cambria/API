package io.swagger.util;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
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
      LOGGER.info("Dynamic LOV: {}", allowableValues);

      if (SystemCodeCache.global() != null) {
        final String category = allowableValues.substring(1);
        final Set<SystemCode> sysCodes = SystemCodeCache.global().getSystemCodesForMeta(category);

        if (sysCodes != null && !sysCodes.isEmpty()) {
          items.addAll(sysCodes.stream().map(c -> c.getSystemId()).sorted().map(s -> s.toString())
              .collect(Collectors.toList()));
        } else {
          LOGGER.warn("NO SYSTEM CODES FOR CATEGORY {}", category);
        }
      } else {
        LOGGER.warn("system code cache not initialized at this point ...");

        items.add("Barney");
        items.add("Buzz");
        items.add("Candace");
        items.add("Dora");
        items.add("Ferb");
        items.add("Neutron");
        items.add("Perry");
        items.add("Phineas");
      }

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
