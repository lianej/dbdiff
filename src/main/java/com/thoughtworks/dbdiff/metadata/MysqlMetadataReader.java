package com.thoughtworks.dbdiff.metadata;

import com.thoughtworks.dbdiff.datasource.DataSourceManager;
import com.thoughtworks.dbdiff.datasource.MapWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component("mysql")
public class MysqlMetadataReader implements MetadataReader {

  @Autowired private DataSourceManager dataSourceManager;

  @Autowired private List<DDLPostProcessor> ddlPostProcessors;

  @Override
  public List<DDLData> readDDL(String env) {
    return dataSourceManager.findExecutorsByEnv(env).stream()
        .map(
            executor ->
                executor.queryForStringList("show tables").stream()
                    .map(
                        table -> {
                          MapWrapper ddlResult =
                              executor.queryForMap("show create table `" + table + "`");
                          if (ddlResult == null) {
                            return null;
                          }
                          String ddl = ddlResult.getAsString("Create Table");
                          return DDLData.builder()
                              .env(env)
                              .db(executor.getDb())
                              .table(table)
                              .ddl(ddl)
                              .build();
                        })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()))
        .flatMap(List::stream)
        .filter(ddl -> Objects.nonNull(ddl.getDdl()))
        .peek(this::doPostProcess)
        .collect(Collectors.toList());
  }

  private void doPostProcess(DDLData ddlData) {
    String ddl = ddlData.getDdl();
    for (DDLPostProcessor postProcessor : ddlPostProcessors) {
      ddl = postProcessor.process(ddl);
    }
    ddlData.setDdl(ddl);
  }
}
