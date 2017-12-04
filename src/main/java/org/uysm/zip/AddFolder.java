package org.uysm.zip;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * Demonstrated adding a folder to zip file
 *
 * @author Srikanth Reddy Lingala
 */
public class AddFolder {

    private ProgressMonitor progressMonitor = null;

    public AddFolder(String folderDir, String UDPXDir, String ecriptionKey) {

        try {
            // Initiate ZipFile object with the path/name of the zip file.
            ZipFile zipFile = new ZipFile(UDPXDir);

            zipFile.setRunInThread(true);

            // Initiate Zip Parameters which define various properties such
            // as compression method, etc.
            ZipParameters parameters = new ZipParameters();

            // set compression method to store compression
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);

            // Set the compression level
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            parameters.setPassword(ecriptionKey);

            // Add folder to the zip file
            zipFile.addFolder(folderDir, parameters);

            // Get progress monitor from ZipFile
            progressMonitor = zipFile.getProgressMonitor();

        } catch (ZipException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String folderDir = "/Users/adnanrimedzo/IdeaProjects/udpx/src/test/java/resources/input";
        String UDPXDir = "/Users/adnanrimedzo/IdeaProjects/udpx/src/test/java/resources/output/test.udpx";
        String ecriptionKey = "1q2w3e4r1q2w3e4r";
        new AddFolder(folderDir, UDPXDir, ecriptionKey);
    }

    public ProgressMonitor getProgressMonitor() {
        return progressMonitor;
    }

}