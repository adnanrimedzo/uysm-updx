package org.uysm.fileoperation;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileOperations {

    public final static List<String>  readLines(String path) throws IOException {
        File f = new File(path);
        List<String> lines = FileUtils.readLines(f, "UTF-8");
        return lines;
    }

    public final static void  writeLines(String path, List<String> lines) throws IOException {
        File f = new File(path);
        FileUtils.writeLines(f, "UTF-8",lines, "\n",false);
    }

    public final static void  writeLine(String path, String line) throws IOException {
        File f = new File(path);
        FileUtils.write(f, line, "UTF-8",false);
    }

}
