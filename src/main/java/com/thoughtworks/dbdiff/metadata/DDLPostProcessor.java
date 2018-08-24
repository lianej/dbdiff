package com.thoughtworks.dbdiff.metadata;

public interface DDLPostProcessor {

    String process(String ddl);

}
