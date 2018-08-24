package com.thoughtworks.dbdiff.metadata;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

@Component
public class DDLReorderingProcessor implements DDLPostProcessor {

  @Override
  public String process(String ddl) {
    return new ProcessUnit(ddl).doProcess();
  }

  private static class ProcessUnit {
    String[] lines;

    ProcessUnit(String ddl) {
      this.lines = ddl.split("\n");
    }

    String doProcess() {
      Stream.of(
              buildReorderOperators("UNIQUE KEY"),
              buildReorderOperators("KEY"),
              buildReorderOperators("CONSTRAINT"))
          .filter(Objects::nonNull)
          .forEach(ReorderingOperator::reorder);
      return String.join("\n", lines);
    }

    ReorderingOperator buildReorderOperators(String searchWord) {
      int firstIdx = findFirst(searchWord);
      if (firstIdx < 0) {
        return null;
      }
      int lastIdx = findLast(searchWord, firstIdx);
      return new ReorderingOperator(firstIdx, lastIdx);
    }

    int findFirst(String searchKeyWord) {
      for (int i = 0; i < lines.length; i++) {
        if (lines[i].trim().toUpperCase().startsWith(searchKeyWord)) {
          return i;
        }
      }
      return -1;
    }

    int findLast(String searchKeyWord, int firstIndex) {
      for (int i = firstIndex + 1; i < lines.length; i++) {
        if (!lines[i].trim().toUpperCase().startsWith(searchKeyWord)) {
          return i - 1;
        }
      }
      return lines.length;
    }

    class ReorderingOperator {
      int beginIndex;
      int endIndex;

      ReorderingOperator(int beginIndex, int endIndex) {
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
      }

      void reorder() {
        String[] sorted =
            Arrays.stream(lines, beginIndex, endIndex).sorted().toArray(String[]::new);
        for (int i = beginIndex, j = 0; i < endIndex; i++, j++) {
          lines[i] = sorted[j];
        }
      }
    }
  }
}
