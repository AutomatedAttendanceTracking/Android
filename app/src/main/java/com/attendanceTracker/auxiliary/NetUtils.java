package com.attendanceTracker.auxiliary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Methods for http stuff -> download, etc.
 */
public class NetUtils {

    private static final String LOG_TAG = "NetUtils";
    private static final int HTTP_TIMEOUT = 2500;

    /**
     * download the content from an url
     *
     * returns string or null
     */
    public String downloadString(String url, String method) {
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(HTTP_TIMEOUT);
            connection.setRequestMethod(method);

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

}
