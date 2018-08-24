package com.thoughtworks.dbdiff.args;

import lombok.Getter;
import lombok.ToString;

import java.net.URL;
import java.util.List;

@Getter
@ToString
public class DBDiffArguments {
  String dbType = "mysql";
  String repoType = "file";
  List<String> env;
  URL dbConfigFileURL;
  URL gitConfigFileURL;
  String outputPath = ".";
}
