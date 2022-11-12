package com.jg.filesystem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import static org.apache.commons.io.comparator.SizeFileComparator.SIZE_COMPARATOR;


class DirectoryOperationsTest {
    private final String url = "http://localhost:8080/";
    private final String path = "D:/test";
    public String getUrl() { return url; }
    public String getPath() { return path; }

    /*
    * Test for creating new directory passing no values ->
    * -> using default values defined in GET method
    * */
    @Test
    void createDirTest_defaultValues() throws IOException {
        //connecting
        URL url = new URL(getUrl() + "create_dir");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        boolean exists = (new File(String.format("%s/New_Directory", getPath())).exists());

        Assertions.assertTrue(responseCode == HttpURLConnection.HTTP_OK && exists, "Creating new Directory with default values FAILED: Response: " + responseCode);
    }

    /*
     * Test for creating new directory ->
     * -> passing modified values
     * */
    @Test
    void createDirTest_modifiedValues() throws IOException {
        final String urlPlain = getUrl() + "create_dir";
        String name = "newwwwwww";

        String urlFull = String.format("%s?absolute_path=%s&directory_name=%s", urlPlain, getPath(), name);

        //connecting
        URL url = new URL(urlFull);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        //check if the directory was created
        boolean exists = (new File(String.format("%s/%s", getPath(), name)).exists());
        boolean responseValidation = (responseCode == HttpURLConnection.HTTP_OK);

        Assertions.assertTrue(exists && responseValidation, "Creating new Directory with modified values FAILED: Response: " + responseCode);
    }

    /*
     * Test for properly deleting directory
     * */
    @Test
    void deleteDir() throws IOException {
        //creating new file
        final String name = "Directory_we_want_to_delete";
        new File(getPath() +"/"+ name).mkdir();
        final URL url = new URL(getUrl() + "delete_dir" + "?absolute_path=" + getPath() + "/" + name);

        //connecting
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        boolean responseValidation = (responseCode == HttpURLConnection.HTTP_OK);
        //checking if directory still exists
        boolean exists = (new File(String.format("%s/%s", getPath(), name)).exists());

        Assertions.assertTrue(responseValidation && !exists, "Delete folder request failed");
    }

    /*
    * Test for listing content of directory ordered by size
    * Tst is not finished - fully tested only by sending GET requests in browser
    * */
    @Test
    void listDirectoryContentTest() throws IOException {
        File file = new File(String.format("%s/%s", getPath(), "listDirContentTesting"));
        if (!file.exists()) {
            file.mkdir();
        }
        new File(file.getPath() + "/" + "file_n_aaa" + ".txt").createNewFile();
        new File(file.getPath() + "/" + "file_n_zzzzzzzzz" + ".txt").createNewFile();
        new File(file.getPath() + "/" + "file_n_ggggggggggggggggggggggggg" + ".txt").createNewFile();
        new File(file.getPath() + "/" + "file_n_lllllllllllllll" + ".txt").createNewFile();

        //connecting
        final URL url = new URL(getUrl() + "list_dir_content" + "?absolute_path=" + getPath() + "/" + "listDirContentTesting");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        File fileList = new File(String.format("%s/%s", getPath(), "listDirContentTesting"));
        File[] files = fileList.listFiles();
        Arrays.sort(files, SIZE_COMPARATOR);

        int responseCode = connection.getResponseCode();
        Assertions.assertTrue(responseCode == HttpURLConnection.HTTP_OK, "Delete folder request failed");
        //byte[] array = connection.getInputStream().readAllBytes();
        //String output = new String(array, StandardCharsets.UTF_8);


    }
}