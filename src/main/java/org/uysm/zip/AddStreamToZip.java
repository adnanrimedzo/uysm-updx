package org.uysm.zip;

import java.io.*;
import java.nio.charset.StandardCharsets;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class AddStreamToZip {

    public AddStreamToZip(String zipDir, String fileName, InputStream stream, String ecriptionKey) {

        try {
            ZipFile zipFile = new ZipFile(zipDir);

            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);

            // Set the compression level
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

            if(ecriptionKey != null) {
                parameters.setEncryptFiles(true);
                parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
                parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
                parameters.setPassword(ecriptionKey);
            }

            parameters.setFileNameInZip(fileName);

            parameters.setSourceExternalStream(true);

            zipFile.addStream(stream, parameters);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String UDPXDir = "/Users/adnanrimedzo/IdeaProjects/udpx/src/test/java/resources/output/test.udpx";
        String ecriptionKey = "1q2w3e4r1q2w3e4r";
        new AddStreamToZip(UDPXDir, "test.txt",new ByteArrayInputStream("test additional file".getBytes(StandardCharsets.UTF_8.name())), ecriptionKey);
    }

}
