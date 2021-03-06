package org.uysm.zip;

public class ProgressBar {

    public static void printProgBar(double percent, String message) {

        percent = percent * 100.0;

        StringBuilder bar = new StringBuilder("[");

        for (int i = 0; i < 50; i++) {
            if (i < (percent / 2)) {
                bar.append("=");
            } else if (i == (percent / 2)) {
                bar.append(">");
            } else {
                bar.append(" ");
            }
        }

        bar.append("]   " + percent + "%     ");
        bar.append("    " + message);
        //System.out.print("\r" + bar.toString());
    }

}
