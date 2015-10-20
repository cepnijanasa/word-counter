package com.whatever;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipWriter {

    private static void createZipEntry(ZipOutputStream out, Map<String, Integer> wordMap,
                                       String entryName) throws IOException {

        ZipEntry ze = new ZipEntry(entryName);
        out.putNextEntry(ze);

        for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
            out.write(String.format(entry.getKey() + " " + entry.getValue() + "%n")
                    .getBytes(StandardCharsets.UTF_8));
        }
        out.closeEntry();
    }

    public static byte[] createZip(WordCollection container) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(bos))) {
            createZipEntry(zos, container.getWordsAG(), "A_G.txt");
            createZipEntry(zos, container.getWordsHN(), "H_N.txt");
            createZipEntry(zos, container.getWordsOU(), "O_U.txt");
            createZipEntry(zos, container.getWordsVZ(), "V_Z.txt");
        }
        return bos.toByteArray();
    }
}
