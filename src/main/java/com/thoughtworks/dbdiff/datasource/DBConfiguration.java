package com.thoughtworks.dbdiff.datasource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class DBConfiguration {
  private String env;
  private String db;
  private String url;
  private String username;
  private String password;
}
