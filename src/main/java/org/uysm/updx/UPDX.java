package org.uysm.updx;

import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.progress.ProgressMonitor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.uysm.hash.HashCheckSum;
import org.uysm.zip.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class UPDX {

    public static final String VALIDATION_HASH_FILE = "CHECK_UDPX.txt";

    public final static ProgressMonitor generateUPDX(String folderDir, String UDPXDir, String ecriptionKey) {
        AddFolder addFolder = new AddFolder(folderDir, UDPXDir, ecriptionKey);

        new Thread(() -> {
            while (addFolder.getProgressMonitor().getState() == ProgressMonitor.STATE_BUSY) {

                ProgressBar.printProgBar(addFolder.getProgressMonitor().getPercentDone(), "File: " + addFolder.getProgressMonitor().getFileName());

                switch (addFolder.getProgressMonitor().getCurrentOperation()) {
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

            System.out.println("Result: " + addFolder.getProgressMonitor().getResult());

            if (addFolder.getProgressMonitor().getResult() == ProgressMonitor.RESULT_ERROR) {
                // Any exception can be retrieved as below:
                if (addFolder.getProgressMonitor().getException() != null) {
                    addFolder.getProgressMonitor().getException().printStackTrace();
                } else {
                    System.err.println("An error occurred without any exception");
                }
            }

            try {
                new AddStreamToZip(UDPXDir, VALIDATION_HASH_FILE,
                        ListAllFilesInZipFile.getListAllFilesInZipFile(UDPXDir, ecriptionKey, null), ecriptionKey);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        }).start();

        return addFolder.getProgressMonitor();

    }

    public final static ProgressMonitor decodeUPDX(String UDPXDir, String folderDir, String ecriptionKey) {
        ExtractAllFiles extractAllFiles = new ExtractAllFiles(UDPXDir, folderDir, ecriptionKey);
        return extractAllFiles.getProgressMonitor();
    }

    public final static void hashList(String hashFileDir, String folderDir, String ecriptionKey) throws IOException {
        InputStream hashList = HashCheckSum.getHashFromFolder(folderDir);
        File hashFile = new File(hashFileDir);

        FileUtils.copyInputStreamToFile(hashList, hashFile);
    }

    public final static boolean validateUPDX(String UDPXDir, String ecriptionKey) throws IOException, NoSuchAlgorithmException {
        ZipInputStream checkFileOlder = ExtractSingleFile.getSingleFile(VALIDATION_HASH_FILE, UDPXDir,
                ecriptionKey);

        InputStream checkFileNew = ListAllFilesInZipFile.getListAllFilesInZipFile(UDPXDir, ecriptionKey, VALIDATION_HASH_FILE);

        String hashListFromOlder = IOUtils.toString(checkFileOlder, StandardCharsets.UTF_8.name());
        String hashListFromNew = IOUtils.toString(checkFileNew, StandardCharsets.UTF_8.name());

        checkFileOlder.close(true);
        checkFileNew.close();

        if (hashListFromOlder.equals(hashListFromNew)) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String folderDirIn = "/Users/adnanrimedzo/IdeaProjects/udpx/src/test/java/resources/input";
        String folderDirExt = "/Users/adnanrimedzo/IdeaProjects/udpx/src/test/java/resources/extract";
        String UDPXDir = "/Users/adnanrimedzo/IdeaProjects/udpx/src/test/java/resources/output/test.updx";
        String ecriptionKey = "1q2w3e4r1q2w3e4r";
        generateUPDX(folderDirIn, UDPXDir, ecriptionKey);
        decodeUPDX(UDPXDir, folderDirExt, ecriptionKey);
        System.out.print(validateUPDX(UDPXDir, ecriptionKey));
    }

}
