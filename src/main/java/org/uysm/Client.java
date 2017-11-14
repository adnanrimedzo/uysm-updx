package org.uysm;

import javafx.util.Pair;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.uysm.updx.UPDX;

import java.util.ArrayList;
import java.util.List;

public class Client {
    @Option(name = "-s", usage = "status: generate , decode")
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
        new Client().doMain(args);
        System.exit(0);
    }

    public void doMain(String[] args) throws Exception {
        CmdLineParser parser = new CmdLineParser(this);

        parser.setUsageWidth(80);

        try {
            parser.parseArgument(args);

            if ("decode".equals(status)) {
                UPDX.decodeUPDX(inPath,outPath,key);
            } else if ("generate".equals(status)) {
                UPDX.generateUPDX(outPath,inPath,key);
            }


            if (arguments.isEmpty())
                throw new CmdLineException(parser, "No argument is given");

        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java SampleMain [options...] arguments...");
            parser.printUsage(System.err);
            System.err.println();

            return;
        }

    }
}
