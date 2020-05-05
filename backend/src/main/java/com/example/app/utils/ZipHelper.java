package com.example.app.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class ZipHelper {

    private ZipHelper() {
    }

    public static void unzip(byte[] data, String dirName) throws IOException {
        File destDir = new File(dirName);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(data));
        ZipEntry entry = zipIn.getNextEntry();

        while (entry != null) {
            String filePath = dirName + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                FileTools.extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

}
