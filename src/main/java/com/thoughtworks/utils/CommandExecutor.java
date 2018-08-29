package com.thoughtworks.utils;

import com.thoughtworks.dbdiff.DBDiffException;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public class CommandExecutor {

    public static ExecResult exec(String cmd) {
        try {
            ConsolePrinter.print(cmd);
            Process pro = Runtime.getRuntime().exec(cmd);
            ExecResult result = new ExecResult();
            result.errorMsg = Optional.ofNullable(readContent(pro.getErrorStream()));
            result.msg = Optional.ofNullable(readContent(pro.getInputStream()));
            result.errorMsg.filter(StringUtils::hasText).ifPresent(ConsolePrinter::print);
            return result;
        } catch (IOException e) {
            throw new DBDiffException(e);
        }
    }

    private static String readContent(InputStream is) {
        return new BufferedReader(new InputStreamReader(is)).lines().collect(joining("\n"));
    }

    @Getter
    public static class ExecResult {
        Optional<String> msg = Optional.empty();
        Optional<String> errorMsg = Optional.empty();
    }
}
