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

  /**
   * Construct an AllowableEnumValues from allowed values annotation.
   * 
   * @param allowableValues allowable values for a field on Swagger page
   * @return AllowableEnumValues instance
   */
  public static AllowableEnumValues create(String allowableValues) {
    final List<String> items = new ArrayList<>();

    final boolean isSysCodeId = allowableValues.startsWith("$ID:");
    final boolean isSysCodeLogical = allowableValues.startsWith("$LG:");

    if (isSysCodeId || isSysCodeLogical) {
      LOGGER.info("Dynamic LOV: {}", allowableValues);

      addSysCodeItems(allowableValues, items, isSysCodeId);
    } else {
      LOGGER.info(allowableValues);
      addNonSysCodeItems(allowableValues, items);
    }

    return items.isEmpty() ? null : new AllowableEnumValues(items);
  }

  private static void addNonSysCodeItems(String allowableValues, List<String> items) {
    for (String value : allowableValues.split(",")) {
      final String trimmed = value.trim();
      if (!"".equals(trimmed)) {
        items.add(trimmed);
      }
    }
  }

  private static void addSysCodeItems(String allowableValues, List<String> items,
      boolean isSysCodeId) {
    if (SystemCodeCache.global() != null) {
      final String category = allowableValues.substring(4);
      final Set<SystemCode> sysCodes = SystemCodeCache.global().getSystemCodesForMeta(category);

      if (sysCodes != null && !sysCodes.isEmpty()) {
        items.addAll(sysCodes.stream().map(c -> isSysCodeId ? c.getSystemId() : c.getLogicalId())
            .sorted().map(Object::toString).collect(Collectors.toList()));
      } else {
        LOGGER.warn("NO SYSTEM CODES FOR CATEGORY {}", category);
      }
    }
  }

  @SuppressWarnings("javadoc")
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
