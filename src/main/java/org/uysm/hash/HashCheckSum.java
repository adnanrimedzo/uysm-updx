package org.uysm.hash;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.InputStream;
import javax.xml.bind.DatatypeConverter;
import java.io.File;

public class HashCheckSum {

    public static InputStream getHashFromFolder(String folder) throws IOException {
        Path source = Paths.get(folder);
        StringBuffer hashBuffer = new StringBuffer();
        Files.walk(source).filter(Files::isRegularFile).forEach(
                f -> {
                    try {
                        hashBuffer.append(f.toAbsolutePath().toString().replace(folder, "")
                                + " ==> " + getSHA256Hash(f.toAbsolutePath().toString()) + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                });

        return new ByteArrayInputStream(hashBuffer.toString().getBytes(StandardCharsets.UTF_8.name()));

    }

    public static InputStream getHashFromFile(String file) throws IOException, NoSuchAlgorithmException {
        StringBuffer hashBuffer = new StringBuffer();
        hashBuffer.append(getSHA256Hash(file));
        return new ByteArrayInputStream(hashBuffer.toString().getBytes(StandardCharsets.UTF_8.name()));
    }

    public static String getSHA256Hash(String fileDir) throws IOException, NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        FileInputStream fis = new FileInputStream(fileDir);

        byte[] data = new byte[1024];
        int read = 0;
        while ((read = fis.read(data)) != -1) {
            sha256.update(data, 0, read);
        }
        ;
        byte[] hashBytes = sha256.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < hashBytes.length; i++) {
            sb.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        String fileHash = sb.toString();

        return fileHash;
    }

    public static String getSHA256Hash(InputStream fis) throws IOException, NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

        byte[] data = new byte[1024];
        int read = 0;
        while ((read = fis.read(data)) != -1) {
            sha256.update(data, 0, read);
        }
        ;
        byte[] hashBytes = sha256.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < hashBytes.length; i++) {
            sb.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        String fileHash = sb.toString();

        return fileHash;
    }

    public static void main(String[] arg) throws IOException {
        getHashFromFolder("/Users/adnanrimedzo/IdeaProjects/deneme");
    }

}
