package com.thoughtworks.dbdiff.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.dbdiff.DBDiffException;
import com.thoughtworks.dbdiff.args.DBDiffArguments;
import com.thoughtworks.dbdiff.metadata.DDLData;
import com.thoughtworks.utils.FileUtils;
import com.thoughtworks.utils.GitCommandExecutor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component("remoteGit")
@Lazy
public class RemoteGitRepository implements DDLRepository {

  @Setter
  public static class GitConfig {
    String type;
    String url;
    String authorization;
  }

  @Autowired private DBDiffArguments arguments;

  private File repoRootDir;

  @PostConstruct
  private void readConfig() {
    try {
      GitConfig config =
          new ObjectMapper().readValue(arguments.getGitConfigFileURL(), GitConfig.class);
      init(config);
    } catch (IOException e) {
      throw new DBDiffException(e);
    }
  }

  private void init(GitConfig config) {

    String tmpdir = System.getProperty("java.io.tmpdir");
    String gitDirName = UUID.randomUUID().toString().replace("-", "");
    this.repoRootDir = FileUtils.createDirIfNecessary(new File(tmpdir), gitDirName);
    GitCommandExecutor executor = new GitCommandExecutor(repoRootDir.getAbsolutePath());
    executor.git("clone " + config.url);
    if ("remote_ssh".equals(config.type)) {
      // todo
    } else if ("remote_http".equals(config.type)) {
      // todo
    } else {
    }
  }

  @Override
  public void storeData(List<DDLData> ddlData) {}
}
