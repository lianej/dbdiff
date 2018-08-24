package com.thoughtworks.dbdiff.datasource;

import java.util.Map;

public class MapWrapper {
  private Map<String, ?> map;

  public MapWrapper(Map<String, ?> map) {
    this.map = map;
  }

  public String getAsString(String key) {
    Object value = map.get(key);
    if (value == null) {
      return null;
    }
    return value.toString();
  }
}
