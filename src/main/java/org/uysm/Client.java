package org.uysm;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.uysm.updx.UPDX;

import java.util.ArrayList;
import java.util.List;

public class Client {
    @Option(name = "-s", usage = "status: generate , decode, validate, hashlist")
    private String status = "generate";

    @Option(name = "-key", usage = "input key")
    private String key = "";

    @Option(name = "-out", usage = "outh path")
    public String outPath = "";

    @Option(name = "-in", usage = "input path")
    public String inPath = "";

    @Argument
    private List<String> arguments = new ArrayList<String>();

    public static void main(String[] args) throws Exception {
        boolean result = new Client().doMain(args);
        if (result) {
            System.exit(0);
        } else {
            System.exit(-1);
        }
    }

    public boolean doMain(String[] args) throws Exception {
        CmdLineParser parser = new CmdLineParser(this);

        parser.setUsageWidth(80);

        try {
            parser.parseArgument(args);

            if ("decode".equals(status)) {
                UPDX.decodeUPDX(inPath, outPath, key);
            } else if ("generate".equals(status)) {
                UPDX.generateUPDX(inPath, outPath, key);
            } else if ("validate".equals(status)) {
                return UPDX.validateUPDX(inPath, key);
            }else if ("hashlist".equals(status)) {
                UPDX.hashList(outPath, inPath, key);
            }

        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java SampleMain [options...] arguments...");
            parser.printUsage(System.err);
            System.err.println();

            return true;
        }

        return true;

    }
}
