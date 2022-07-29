package com.ariscience.vizhtml;

/**
 * <pre>
 * Encapsulates version of VizHTML project.
 * </pre>
 *
 * (c) 2018-2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 */
public class AppVersion {

    /**
     * Package Name
     */
    public static final String VERSION_PACKAGE_NAME = "ARI VizHTML";


    //if any of the following two variables are changed, a corresponding change needs to happen in mf_* variables in build.xml
    /**
     * the major build number
     */
    private static final String BUILD_MAJOR = "1";

    /**
     * the minor build number
     */
    private static final String BUILD_MINOR = "0.1";

    /**
     * @return BioTD Version, Build Data and Build Platform information
     */
    public static String getVersion() {
        StringBuilder sb = new StringBuilder(200);
        sb.append(VERSION_PACKAGE_NAME);
        sb.append("_");
        sb.append(getBuildMajor());
        sb.append(".");
        sb.append(getBuildMinor());
        return sb.toString();
    }

    /**
     * @return the major version
     */
    public static String getBuildMajor() {
        return BUILD_MAJOR;
    }

    /**
     * @return the minor version
     */
    public static String getBuildMinor() {
        return BUILD_MINOR;
    }

    /**
     * Write version to System.out
     *
     * @param args takes in no arguments
     */
    public static void main(String[] args) {
        System.out.println("V.M - VizHTML.Version..:" + getVersion());
    }

}

