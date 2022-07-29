package com.ariscience.vizhtml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Just some IO Utility Methods
 *
 * (c) 2021 Alamgir Research Inc. Proprietary and Confidential. Protected by US and International intellectual property, copyright and trade secret laws. Do not distribute, show or otherwise provide access without written authorization from the President of Alamgir Research Inc.
 */
public final class VizHTMLUtils {

    /**
     * prevent external instantiation
     */
    private VizHTMLUtils() {
    }

    /**
     * This function reads template file and returns content as String.
     *
     * @param inputStream the input stream to read (and subsequently close)
     * @return template file string content
     * @throws IOException on IO error
     */
    public static String readFullyAndClose(final InputStream inputStream) throws IOException {

        final InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        final BufferedReader br = new BufferedReader(isr);

        StringBuilder content = new StringBuilder(512);

        try {
            String line = null;
            while ((line = br.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }finally{
            br.close();
            isr.close();
            inputStream.close();
        }

        return content.toString();
    }
}
