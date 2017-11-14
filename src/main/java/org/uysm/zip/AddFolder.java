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
            ProgressMonitor progressMonitor = zipFile.getProgressMonitor();

            while (progressMonitor.getState() == ProgressMonitor.STATE_BUSY) {

//                // To get the percentage done
//                System.out.println("Percent Done: " + progressMonitor.getPercentDone());
//
//                // To get the current file being processed
//                System.out.println("File: " + progressMonitor.getFileName());

                ProgressBar.printProgBar(progressMonitor.getPercentDone(),"File: " + progressMonitor.getFileName() );

                switch (progressMonitor.getCurrentOperation()) {
                    case ProgressMonitor.OPERATION_NONE:
                        System.out.println("no operation being performed");
                        break;
                    case ProgressMonitor.OPERATION_ADD:
                        //System.out.println("Add operation");
                        break;
                    case ProgressMonitor.OPERATION_EXTRACT:
                        System.out.println("Extract operation");
                        break;
                    case ProgressMonitor.OPERATION_REMOVE:
                        System.out.println("Remove operation");
                        break;
                    case ProgressMonitor.OPERATION_CALC_CRC:
                        System.out.println("Calcualting CRC");
                        break;
                    case ProgressMonitor.OPERATION_MERGE:
                        System.out.println("Merge operation");
                        break;
                    default:
                        System.out.println("invalid operation");
                        break;
                }
            }

            System.out.println("Result: " + progressMonitor.getResult());

            if (progressMonitor.getResult() == ProgressMonitor.RESULT_ERROR) {
                // Any exception can be retrieved as below:
                if (progressMonitor.getException() != null) {
                    progressMonitor.getException().printStackTrace();
                } else {
                    System.err.println("An error occurred without any exception");
                }
            }

        } catch (ZipException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String folderDir = "/Users/adnanrimedzo/NetbeansProjects";
        String UDPXDir = "/Users/adnanrimedzo/IdeaProjects/udpx/src/test/java/resources/output/deneme.zip";
        String ecriptionKey = "1q2w3e4r1q2w3e4r";
        new AddFolder(folderDir, UDPXDir, ecriptionKey);
    }

}