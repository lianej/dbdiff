package com.thoughtworks.dbdiff.datasource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.dbdiff.args.DBDiffArguments;
import com.thoughtworks.dbdiff.EnvNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class DataSourceManager {

  private static final TypeReference<List<DBConfiguration>> TYPE_REFERENCE =
      new TypeReference<List<DBConfiguration>>() {};

  private Map<String, SQLExecutor> datasourceMapping;

  @Autowired private DBDiffArguments arguments;

  @PostConstruct
  private void initDataSources() throws IOException {
    if (datasourceMapping == null) {
      URL url = arguments.getDbConfigFileURL();
      List<DBConfiguration> dbList = new ObjectMapper().readValue(url, TYPE_REFERENCE);
      this.datasourceMapping =
          dbList
              .stream()
              .collect(toMap(cfg -> getDBKey(cfg.getEnv(), cfg.getDb()), SQLExecutor::new));
    }
  }

  private String getDBKey(String env, String db) {
    return String.join("/", env, db);
  }

  public List<SQLExecutor> findExecutorsByEnv(String env) {
    List<SQLExecutor> executors =
        datasourceMapping
            .keySet()
            .stream()
            .filter(key -> key.startsWith(env))
            .map(datasourceMapping::get)
            .collect(toList());
    if (executors.isEmpty()) {
      throw new EnvNotFound("env:[" + env + "] not found");
    }
    return executors;
  }

  public SQLExecutor getExecutor(String env, String db) {
    String dbKey = getDBKey(env, db);
    SQLExecutor executor = datasourceMapping.get(dbKey);
    if (executor == null) {
      throw new NullPointerException("database: " + dbKey + " not found");
    }
    return executor;
  }

  public Set<String> getEnvList() {
    return datasourceMapping.keySet();
  }
}
