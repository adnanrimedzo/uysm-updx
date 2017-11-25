package org.uysm.zip;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

/**
 * Demonstrates how to remove a file from a zip file
 *
 * @author Srikanth Reddy Lingala
 */
public class RemoveFileFromZipFile {

    public RemoveFileFromZipFile(String fileName, String UDPXDir) {

        try {
            ZipFile zipFile = new ZipFile(UDPXDir);

            zipFile.removeFile(fileName);

            // 2. With the FileHeader
            if (zipFile.getFileHeaders() != null && zipFile.getFileHeaders().size() > 0) {
                zipFile.removeFile((FileHeader) zipFile.getFileHeaders().get(0));
            } else {
                System.out.println("This cannot be demonstrated as zip file does not have any files left");
            }

        } catch (ZipException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String UDPXDir = "/Users/adnanrimedzo/IdeaProjects/udpx/src/test/java/resources/output/test.udpx";
        String ecriptionKey = "1q2w3e4r1q2w3e4r";
        new Thread(() -> new RemoveFileFromZipFile("CHECK_UDPX.txt", UDPXDir)).run();
    }

}
