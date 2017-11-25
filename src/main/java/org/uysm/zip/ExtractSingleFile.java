package org.uysm.zip;


import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;

/**
 * Demonstrates extraction of a single file from the zip file
 *
 * @author Srikanth Reddy Lingala
 */

public class ExtractSingleFile {

    public static ZipInputStream getSingleFile(String fileName, String UDPXDir, String ecriptionKey) {

        ZipInputStream is = null;

        try {
            // Initiate ZipFile object with the path/name of the zip file.
            ZipFile zipFile = new ZipFile(UDPXDir);

            // Check to see if the zip file is password protected
            if (zipFile.isEncrypted()) {
                // if yes, then set the password for the zip file
                zipFile.setPassword(ecriptionKey);
            }

            FileHeader fileHeader = zipFile.getFileHeader(fileName);

            if (fileHeader != null) {

                if (fileHeader.isDirectory()) {
                    return null;
                }

                //Get the InputStream from the ZipFile
                is = zipFile.getInputStream(fileHeader);
                //Initialize the output stream
            }
        } catch (ZipException e) {
            e.printStackTrace();
        }

        return is;

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String folderOutDir = "/Users/adnanrimedzo/IdeaProjects/udpx/src/test/java/resources/extract";
        String UDPXDir = "/Users/adnanrimedzo/IdeaProjects/udpx/src/test/java/resources/output/test.udpx";
        String ecriptionKey = "1q2w3e4r1q2w3e4r";
        getSingleFile("CHECK_UDPX.txt", UDPXDir, ecriptionKey);
    }

}
