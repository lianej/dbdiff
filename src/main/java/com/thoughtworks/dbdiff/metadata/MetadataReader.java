package com.thoughtworks.dbdiff.metadata;

import java.util.List;

public interface MetadataReader {

  List<DDLData> readDDL(String env);
}
