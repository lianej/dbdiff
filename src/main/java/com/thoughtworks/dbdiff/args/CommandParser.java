package com.thoughtworks.dbdiff.args;

import com.thoughtworks.dbdiff.DBDiffException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Arrays;

public class CommandParser {

  private Options buildOptions() {
    return new Options()
        .addOption("e", "env-list", true, "db env list, split by comma")
        .addOption("r", "repository-type", true, "repository type")
        .addOption("d", "db-config-file", true, "db config file(json)")
        .addOption("o", "file-output-path", true, "file output path")
        .addOption("g", "git-config-file", true, "git config file(json)");
  }

  public DBDiffArguments parse(String[] args) {
    try {
      CommandLine line = new DefaultParser().parse(buildOptions(), args);
      DBDiffArguments option = new DBDiffArguments();
      String envList = line.getOptionValue("env-list");
      if (envList != null) {
        option.env = Arrays.asList(envList.split(","));
      }
      option.repoType = line.getOptionValue("r","file");
      option.outputPath = line.getOptionValue("o");
      option.dbConfigFileURL =
          Thread.currentThread()
              .getContextClassLoader()
              .getResource(line.getOptionValue("db", "db.json"));
      option.gitConfigFileURL =
          Thread.currentThread()
              .getContextClassLoader()
              .getResource(line.getOptionValue("git", "git.json"));
      return option;
    } catch (ParseException e) {
      throw new DBDiffException("parse command args error, args=" + Arrays.toString(args), e);
    }
  }
}
