package com.thoughtworks.dbdiff.metadata;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class DDLData {
  private String env;
  private String db;
  private String table;
  private String ddl;
}
