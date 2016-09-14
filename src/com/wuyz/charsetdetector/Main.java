package com.wuyz.charsetdetector;

import org.mozilla.intl.chardet.CharsetDetector;

import java.io.File;
import java.io.FilenameFilter;

public class Main {

    public static void main(String argv[]) throws Exception {

        if (argv.length != 1) {
            System.out.println("Usage: CharsetDetector dir");
            return;
        }

        File file = new File(argv[0]);
        if (!file.exists() || file.isFile())
            return;

        FilenameFilter filter = (dir, filename) -> {
            // System.out.println(dir.getAbsolutePath() + " " + filename);
            String s = filename.toLowerCase();
            if (s.endsWith(".java") || s.endsWith(".aidl") || s.endsWith(".cpp") || s.endsWith(".c"))
                return true;
            File f = new File(dir, filename);
            return f.isDirectory();
        };
        listFile(file, filter);
        System.out.println("ok");
    }

    private static void listFile(File file, FilenameFilter filter) throws Exception {
        File[] files = file.listFiles(filter);
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    CharsetDetector.getCharset(f);
                } else {
                    listFile(f, filter);
                }
            }
        }
    }
}
