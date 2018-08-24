package com.thoughtworks.dbdiff.repository;

import com.thoughtworks.dbdiff.DBDiffException;
import com.thoughtworks.dbdiff.args.DBDiffArguments;
import com.thoughtworks.dbdiff.metadata.DDLData;
import com.thoughtworks.utils.CommandExecutor;
import com.thoughtworks.utils.FileUtils;
import com.thoughtworks.utils.GitCommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;

@Repository("localGit")
@Lazy
public class LocalGitRepository implements DDLRepository {

  @Autowired private DBDiffArguments arguments;

  private GitCommandExecutor gitCommandExecutor;

  private File repoRootDir;

  private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HH_mm_ss");

  @PostConstruct
  private void init() {
    this.repoRootDir = new File(arguments.getOutputPath());
    if (repoRootDir.exists() || repoRootDir.mkdirs()) {
      this.gitCommandExecutor = new GitCommandExecutor(repoRootDir.getAbsolutePath());
    } else {
      throw new DBDiffException("repo [" + arguments.getOutputPath() + "] not found");
    }
  }

  @Override
  public void storeData(List<DDLData> ddlData) {
    String now = dateFormatter.format(LocalDateTime.now());
    CommandExecutor.exec("rm -rf " + repoRootDir.getAbsolutePath() + "/*.sql");
    ddlData
        .stream()
        .collect(groupingBy(DDLData::getEnv))
        .forEach(
            (env, data) -> {
              this.gitCommandExecutor.initIfNecessary();
              this.gitCommandExecutor.checkout(env);
              String comments = env + "_" + now;
              String fileName = env + ".txt";
              File descriptionFile = FileUtils.writeAsFile(this.repoRootDir, fileName, comments);
              this.gitCommandExecutor.git("add", descriptionFile.getAbsolutePath());
              for (DDLData ddl : data) {
                File dbDir = FileUtils.createDirIfNecessary(this.repoRootDir, ddl.getDb());
                File file = FileUtils.writeAsFile(dbDir, ddl.getTable() + ".sql", ddl.getDdl());
                this.gitCommandExecutor.git("add", file.getAbsolutePath());
              }
              this.gitCommandExecutor.commit(comments);
            });
  }
}
