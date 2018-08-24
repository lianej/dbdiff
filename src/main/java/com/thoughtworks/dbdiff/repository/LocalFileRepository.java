package com.thoughtworks.dbdiff.repository;

import com.thoughtworks.dbdiff.args.DBDiffArguments;
import com.thoughtworks.dbdiff.metadata.DDLData;
import com.thoughtworks.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component("file")
@Lazy
public class LocalFileRepository implements DDLRepository {


  @Autowired private DBDiffArguments arguments;

  @Override
  public void storeData(List<DDLData> ddlData) {
    File rootDir = FileUtils.createDirIfNecessary(arguments.getOutputPath());
    for (DDLData data : ddlData) {
      File envDir = FileUtils.createDirIfNecessary(rootDir, data.getEnv());
      File dbDir = FileUtils.createDirIfNecessary(envDir, data.getDb());
      FileUtils.writeAsFile(dbDir, data.getTable() + ".sql", data.getDdl());
    }
  }
}
