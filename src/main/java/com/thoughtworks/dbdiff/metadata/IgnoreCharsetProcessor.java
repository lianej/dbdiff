package com.thoughtworks.dbdiff.metadata;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class IgnoreCharsetProcessor implements DDLPostProcessor {

  private Pattern charsetPattern = Pattern.compile("CHARSET=\\w+");

  @Override
  public String process(String ddl) {
    return charsetPattern.matcher(ddl).replaceAll("CHARSET=utf8/*handled*/");
  }
}
