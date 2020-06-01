package com.thoughtworks.dbdiff.metadata;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class IgnoreCollateProcessor implements DDLPostProcessor {

  private Pattern charsetPattern = Pattern.compile(" COLLATE[ =]\\w+");

  @Override
  public String process(String ddl) {
    return charsetPattern.matcher(ddl).replaceAll("");
  }
}
