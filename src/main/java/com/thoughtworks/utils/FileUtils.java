package com.thoughtworks.utils;

import com.thoughtworks.dbdiff.DBDiffException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    public static File writeAsFile(File dir, String fileName, String content){
        try{
            File file = new File(dir, fileName);
            if (file.exists() || file.createNewFile()) {
                try (FileWriter writer = new FileWriter(file)){
                    writer.write(content);
                    writer.flush();
                }
            }
            return file;
        } catch (IOException e){
            throw new DBDiffException("throws ex", e);
        }
    }

    public static File createDirIfNecessary(File parentDir, String dirName){
        File dir = new File(parentDir, dirName);
        if (dir.exists() || dir.mkdir()) {
            return dir;
        }
        throw new DBDiffException("create dir failed");

    }

    public static File createDirIfNecessary(String fullPath){
        File dir = new File(fullPath);
        if (dir.exists() || dir.mkdir()) {
            return dir;
        }
        throw new DBDiffException("create dir failed");
    }

}
