package com.thoughtworks.dbdiff.metadata;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class IgnoreAutoIncreamentProcessor implements DDLPostProcessor {

  private Pattern idSequencePattern = Pattern.compile(" AUTO_INCREMENT=\\d+");

  @Override
  public String process(String ddl) {
    return idSequencePattern.matcher(ddl).replaceAll("");
  }
}
