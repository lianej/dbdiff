package com.thoughtworks.dbdiff.datasource;

import com.mysql.jdbc.Driver;
import com.thoughtworks.utils.ConsolePrinter;
import lombok.Getter;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class SQLExecutor {

  private final JdbcTemplate jdbcTemplate;
  @Getter private final String env;
  @Getter private final String db;

  public SQLExecutor(DBConfiguration dbConfiguration) {
    DataSource ds = new DataSource();
    ds.setUrl(dbConfiguration.getUrl());
    ds.setUsername(dbConfiguration.getUsername());
    ds.setPassword(dbConfiguration.getPassword());
    ds.setDriverClassName(Driver.class.getName());
    this.jdbcTemplate = new JdbcTemplate(ds);
    this.env = dbConfiguration.getEnv();
    this.db = dbConfiguration.getDb();
  }

  public List<String> queryForStringList(String sql) {
    return jdbcTemplate.queryForList(sql, String.class);
  }

  public List<Map<String, Object>> queryForList(String sql) {
    return jdbcTemplate.queryForList(sql);
  }

  public MapWrapper queryForMap(String sql) {
    try{
      ConsolePrinter.print("exec sql:", sql);
      return new MapWrapper(jdbcTemplate.queryForMap(sql));

    }catch (Exception e){
      e.printStackTrace();
      return null;
    }
  }
}
