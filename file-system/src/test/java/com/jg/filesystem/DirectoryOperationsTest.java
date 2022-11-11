package com.jg.filesystem;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


class DirectoryOperationsTest {
    private final String url = "http://localhost:8080/";
    private final String path = "C:/";
    public String getUrl() { return url; }
    public String getPath() { return path; }

    @Test
    void createDirTest_defaultValues() throws IOException {
        URL url = new URL(getUrl() + "create_dir");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        boolean exists = (new File(String.format("C:/New Directory")).exists());

        Assertions.assertTrue(responseCode == HttpURLConnection.HTTP_OK, "Creating new Directory with default values FAILED: Response: " + responseCode);
    }
    @Test
    void createDirTest_modifiedValues() throws IOException {
        final String urlPlain = getUrl() + "create_dir";
        String name = "newwwwwww";

        String urlFull = String.format("%s?absolute_path=%s&directory_name=%s", urlPlain, getPath(), name);

        URL url = new URL(urlFull);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        boolean exists = (new File(String.format("%s/%s", getPath(), name)).exists());
        boolean responseValidation = (responseCode == HttpURLConnection.HTTP_OK);

        Assertions.assertTrue(exists && responseValidation, "Creating new Directory with modified values FAILED: Response: " + responseCode);
    }

    @Test
    void deleteDir() throws IOException {
        final String name = "Directory_we_want_to_delete";
        new File(getPath() + name).mkdir();
        final URL url = new URL(getUrl() + "delete_dir" + "?absolute_path=" + getPath() + name);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        boolean responseValidation = (responseCode == HttpURLConnection.HTTP_OK);
        boolean exists = (new File(String.format("%s/%s", getPath(), name)).exists());

        Assertions.assertTrue(responseValidation && !exists, "Delete folder request failed");
    }
}