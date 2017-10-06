/**
 * <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017
 * Chen Chao.
 */
package io.swagger.util;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import io.swagger.models.properties.PropertyBuilder;

public class AllowableEnumValues implements AllowableValues {

  private final List<String> items;

  private AllowableEnumValues(List<String> items) {
    this.items = items;
  }

  public static AllowableEnumValues create(String allowableValues) {
    System.out.println("Dave is so cool");
    final List<String> items = new ArrayList<>();
    for (String value : allowableValues.split(",")) {
      final String trimmed = value.trim();
      if (!trimmed.equals("")) {
        items.add(trimmed);
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
        new EnumMap<PropertyBuilder.PropertyId, Object>(PropertyBuilder.PropertyId.class);
    map.put(PropertyBuilder.PropertyId.ENUM, items);
    return map;
  }

}
