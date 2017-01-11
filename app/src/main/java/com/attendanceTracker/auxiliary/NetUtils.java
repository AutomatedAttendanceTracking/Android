package com.attendanceTracker.auxiliary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * Methods for http stuff -> download, etc.
 */
public class NetUtils {

    // TODO: set the url
    public static final String DOWNLOAD_URL = "http://10.0.2.2/testString";

    private static final String LOG_TAG = "NetUtils";
    private static final int HTTP_TIMEOUT = 2500;

    /**
     * download the content from an url with additional params
     *
     * returns string or null
     */
    public String downloadString(String url, Map<String, String> params) {
        String formatedUrl = formatUrl(url, params);
        if (formatedUrl == null) {
            return null;
        }

        return downloadString(formatedUrl);
    }

    /**
     * download the content from an url
     *
     * returns string or null
     */
    public String downloadString(String url) {
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(HTTP_TIMEOUT);

        } catch (IOException e) {
            Log.e(LOG_TAG, "URL not well formed.");
            return null;
        }

        try {
            InputStream input = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            return builder.toString();

        } catch (IOException e) {
            Log.e(LOG_TAG, "Could not read input stream.");
            return null;

        } finally {
            connection.disconnect();
        }
    }

    /**
     * download an image (qr-code) from an url with additional params
     *
     * returns image or null
     */
    public Bitmap downloadImage(String url, Map<String, String> params) {
        String formatedUrl = formatUrl(url, params);
        if (formatedUrl == null) {
            return null;
        }

        return downloadImage(formatedUrl);
    }

    /**
     * download an image (qr-code) from an url
     *
     * returns image or null
     */
    public Bitmap downloadImage(String url) {
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(HTTP_TIMEOUT);

        } catch (IOException e) {
            Log.e(LOG_TAG, "URL not well formed.");
            return null;
        }

        try {
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Could not read input stream.");
            return null;

        } finally {
            connection.disconnect();
        }
    }

    /**
     * formats an url with params to an URI string
     */
    private String formatUrl(String url, Map<String, String> params) {
        Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
        StringBuilder sb = new StringBuilder("?");
        while (iter.hasNext()) {
            Map.Entry<String, String> e = iter.next();
            try {
                sb.append(String.format("%s=%s",
                        URLEncoder.encode(e.getKey(), "UTF-8"),
                        URLEncoder.encode(e.getValue(), "UTF-8")));
            } catch (UnsupportedEncodingException ex) {
                Log.e(LOG_TAG, "UTF-8 encoding not supported. Bailing out.");
                return null;
            }
            if (iter.hasNext())
                sb.append("&");
        }
        return url + sb.toString();
    }

}
