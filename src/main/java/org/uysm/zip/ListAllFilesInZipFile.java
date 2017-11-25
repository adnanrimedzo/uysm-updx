package org.uysm.zip;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;
import org.uysm.hash.HashCheckSum;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lists all the files in a zip file including the properties of the file
 *
 * @author Srikanth Reddy Lingala
 */
public class ListAllFilesInZipFile {

    public static InputStream getListAllFilesInZipFile(String fileDir, String ecriptionKey, String except)
            throws IOException, NoSuchAlgorithmException {

        StringBuffer hashBuffer = new StringBuffer();

        try {
            // Initiate ZipFile object with the path/name of the zip file.
            ZipFile zipFile = new ZipFile(fileDir);

            // Get the list of file headers from the zip file
            List fileHeaderList = zipFile.getFileHeaders();

            ZipInputStream is = null;

            if (zipFile.isEncrypted()) {
                zipFile.setPassword(ecriptionKey);
            }

            // Loop through the file headers
            for (int i = 0; i < fileHeaderList.size(); i++) {
                FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);

                //Checks if the file is a directory
                if (fileHeader.isDirectory() || (except != null && except.equals(fileHeader.getFileName()))) {
                    continue;
                }

                //Get the InputStream from the ZipFile
                is = zipFile.getInputStream(fileHeader);

                // FileHeader contains all the properties of the file
                System.out.println("****File Details for: " + fileHeader.getFileName() + "*****");
                System.out.println("Name: " + fileHeader.getFileName());
                System.out.println("Compressed Size: " + fileHeader.getCompressedSize());
                System.out.println("Uncompressed Size: " + fileHeader.getUncompressedSize());
                System.out.println("CRC: " + fileHeader.getCrc32());
                //System.out.println("Hash SHA-256: " + HashCheckSum.getSHA256Hash(is));
                System.out.println("************************************************************");

                hashBuffer.append(HashCheckSum.getSHA256Hash(is)+"\n");

                // Various other properties are available in FileHeader. Please have a look at FileHeader
                // class to see all the properties

                if (is != null) {
                    is.close(true);
                    is = null;
                }
            }

        } catch (ZipException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(hashBuffer.toString().getBytes(StandardCharsets.UTF_8.name()));

    }

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        getListAllFilesInZipFile("/Users/adnanrimedzo/IdeaProjects/udpx/src/test/java/resources/output/test.udpx",
                "1q2w3e4r1q2w3e4r", null);
    }

}
