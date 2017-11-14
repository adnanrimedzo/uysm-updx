package org.uysm.zip;


import java.util.List;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

/**
 * Demonstrates extracting all files from a zip file
 *
 * @author Srikanth Reddy Lingala
 *
 */
public class ExtractAllFiles {

    int progressRate;

    public ExtractAllFiles(String UDPXDir, String folderDir, String ecriptionKey) {

        try {
            // Initiate ZipFile object with the path/name of the zip file.
            ZipFile zipFile = new ZipFile(UDPXDir);

            // Check to see if the zip file is password protected
            if (zipFile.isEncrypted()) {
                // if yes, then set the password for the zip file
                zipFile.setPassword(ecriptionKey);
            }

            // Get the list of file headers from the zip file
            List fileHeaderList = zipFile.getFileHeaders();

            // Loop through the file headers
            for (int i = 0; i < fileHeaderList.size(); i++) {
                FileHeader fileHeader = (FileHeader)fileHeaderList.get(i);
                // Extract the file to the specified destination
                zipFile.extractFile(fileHeader, folderDir);
                progressRate = (100 * i+1) / fileHeaderList.size();
                ProgressBar.printProgBar(progressRate, "File: " + fileHeader.getFileName());
            }

        } catch (ZipException e) {
            e.printStackTrace();
        }

    }



    /**
     * @param args
     */
    public static void main(String[] args) {
        String folderOutDir = "/Users/adnanrimedzo/IdeaProjects/udpx/src/test/java/resources/extract";
        String UDPXDir = "/Users/adnanrimedzo/IdeaProjects/udpx/src/test/java/resources/output/deneme.zip";
        String ecriptionKey = "1q2w3e4r1q2w3e4r";
        new Thread(() -> new ExtractAllFiles(UDPXDir, folderOutDir, ecriptionKey)).run();
    }

}
