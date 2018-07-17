package org.uysm.zip;


import java.util.List;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.progress.ProgressMonitor;

/**
 * Demonstrates extracting all files from a zip file
 *
 * @author Srikanth Reddy Lingala
 *
 */
public class ExtractAllFiles {

    private ProgressMonitor progressMonitor = new ProgressMonitor();

    public ExtractAllFiles(String UDPXDir, String folderDir, String ecriptionKey) {
        new Thread(() -> {
            try {

                progressMonitor.setResult(1);
                // Initiate ZipFile object with the path/name of the zip file.
                ZipFile zipFile = new ZipFile(UDPXDir);

                // Check to see if the zip file is password protected
                if (zipFile.isEncrypted()) {
                    // if yes, then set the password for the zip file
                    zipFile.setPassword(ecriptionKey);
                }

                // Get the list of file headers from the zip file
                List fileHeaderList = zipFile.getFileHeaders();

                progressMonitor.setTotalWork(zipFile.getFile().length());

                // Loop through the file headers
                for (int i = 0; i < fileHeaderList.size(); i++) {
                    FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);
                    // Extract the file to the specified destination
                    zipFile.extractFile(fileHeader, folderDir);
                    progressMonitor.setFileName(fileHeader.getFileName());
                    progressMonitor.setState(1);
                    progressMonitor.updateWorkCompleted(fileHeader.getCompressedSize());
                }

                progressMonitor.setResult(0);

            } catch (ZipException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public static void main(String[] args) {
        String folderOutDir = "/Users/adnanrimedzo/IdeaProjects/udpx/src/test/java/resources/extract";
        String UDPXDir = "/Users/adnanrimedzo/IdeaProjects/udpx/src/test/java/resources/output/test.udpx";
        String ecriptionKey = "1q2w3e4r1q2w3e4r";
        new Thread(() -> new ExtractAllFiles(UDPXDir, folderOutDir, ecriptionKey)).run();
    }

    public ProgressMonitor getProgressMonitor() {
        return progressMonitor;
    }
}
