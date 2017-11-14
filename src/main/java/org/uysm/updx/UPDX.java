package org.uysm.updx;

import org.uysm.zip.AddFolder;
import org.uysm.zip.ExtractAllFiles;

public class UPDX {

    public final static void generateUPDX(String folderDir, String UDPXDir, String ecriptionKey){
        new AddFolder(folderDir, UDPXDir, ecriptionKey);
    }

    public final static void decodeUPDX(String UDPXDir, String folderDir, String ecriptionKey){
        new Thread(() -> new ExtractAllFiles(UDPXDir, folderDir, ecriptionKey)).run();
    }

}
