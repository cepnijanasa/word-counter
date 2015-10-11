package com.whatever;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipWriter {

    public static void createZipEntry(ZipOutputStream out, Map<String, Integer> wordMap,
                                      String entryName) throws IOException {

        ZipEntry ze = new ZipEntry(entryName);
        out.putNextEntry(ze);

        for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
            out.write(String.format(entry.getKey() + " " + entry.getValue() + "%n")
                    .getBytes(StandardCharsets.UTF_8));
        }
        out.closeEntry();
    }
}
