package org.uysm.updx;

import net.lingala.zip4j.io.ZipInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.uysm.hash.HashCheckSum;
import org.uysm.zip.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class UPDX {

    public static final String VALIDATION_HASH_FILE = "CHECK_UDPX.txt";

    public final static void generateUPDX(String folderDir, String UDPXDir, String ecriptionKey) throws IOException, NoSuchAlgorithmException {
        new AddFolder(folderDir, UDPXDir, ecriptionKey);
        new AddStreamToZip(UDPXDir, VALIDATION_HASH_FILE,
                ListAllFilesInZipFile.getListAllFilesInZipFile(UDPXDir, ecriptionKey, null), ecriptionKey);
    }

    public final static void decodeUPDX(String UDPXDir, String folderDir, String ecriptionKey) {
        new Thread(() -> new ExtractAllFiles(UDPXDir, folderDir, ecriptionKey)).run();
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
        String UDPXDir = "/Users/adnanrimedzo/IdeaProjects/udpx/src/test/java/resources/output/test.udpx";
        String ecriptionKey = "1q2w3e4r1q2w3e4r";
        generateUPDX(folderDirIn, UDPXDir, ecriptionKey);
        decodeUPDX(UDPXDir, folderDirExt, ecriptionKey);
        System.out.print(validateUPDX(UDPXDir, ecriptionKey));
    }

}
