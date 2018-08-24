package com.thoughtworks.utils;

import java.util.StringJoiner;
import java.util.stream.Stream;

import static com.thoughtworks.utils.CommandExecutor.exec;

public class GitCommandExecutor {
  private String gitDir;

  public GitCommandExecutor(String gitDir) {
    this.gitDir = gitDir;
  }

  public void checkout(String branch) {
    CommandExecutor.ExecResult result = git("checkout " + branch);
    if (result.getErrorMsg().filter(msg -> !msg.startsWith("Already on")).isPresent()) {
      git("checkout -b " + branch);
    }
  }

  public void commit(String comments) {
    CommandExecutor.ExecResult result = git("commit -m '" + comments + "'");
    result.getErrorMsg().ifPresent(System.out::println);
    result.getMsg().ifPresent(System.out::println);
  }

  public CommandExecutor.ExecResult git(String cmd) {
    return exec("git -C " + gitDir + " " + cmd);
  }

  public CommandExecutor.ExecResult git(String... cmd) {
    StringJoiner joiner = new StringJoiner(" ");
    Stream.of(cmd).forEach(joiner::add);
    return exec("git -C " + gitDir + " " + joiner.toString());
  }

  public void initIfNecessary() {
    CommandExecutor.ExecResult result = git("status");
    result.getErrorMsg().ifPresent(msg -> git("init"));
  }
}
