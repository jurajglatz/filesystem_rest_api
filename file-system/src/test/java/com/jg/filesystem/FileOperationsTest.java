package com.jg.filesystem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


class FileOperationsTest {
    //address where is api running
    public String getUrl() {
        String url = "http://localhost:8080/";
        return url; }
    //path to folder for creating and editing files in test methods
    public String getPath() {
        String path = "D:/test";
        return path; }
    String name = "new_file_createFileTest";

    @Test
    void createFileTest() throws Exception {
        //connect + create new file
        URL url = new URL(String.format("%s%s?absolute_path=%s&file_name=%s&file_extension=%s", getUrl(), "create_file", getPath(), name, "txt"));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        //checking if the file was created
        int responseCode = connection.getResponseCode();
        boolean exists = (new File(String.format("%s/%s.txt", getPath(), name)).exists());
        boolean responseValidation = (responseCode == HttpURLConnection.HTTP_OK);

        Assertions.assertTrue(exists && responseValidation, "Creating new file with extension .txt FAILED: Response: " + responseCode);
        connection.disconnect();
    }

    @Test
    void deleteFileTest() throws Exception {
        //connecting + deleting file
        URL url = new URL(String.format("%s%s?absolute_path=%s/%s.txt", getUrl(), "delete_file", getPath(), name));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        //checking if the file is deleted
        boolean deleted = !(new File(String.format("%s/%s.txt", getPath(), name)).exists());
        boolean responseValidation = (responseCode == HttpURLConnection.HTTP_OK);

        Assertions.assertTrue(deleted && responseValidation, "Deleting file with extension .txt FAILED: Response: " + responseCode);
        connection.disconnect();
    }

    @Test
    void copyFileTest() throws IOException {
        //creating 2 files, 1 as source and 1 as destination
        boolean copyTestingFrom = new File((getPath() + "/copy_testing_from.txt")).createNewFile();
        boolean copyTestingTo = new File((getPath() + "/copy_testing_to.txt")).createNewFile();

        //editing source file for copying
        String text = "ABC DEF GHI 123 456 789 JKL MNO";
        FileWriter myWriter = new FileWriter(getPath() + "/copy_testing_from.txt");
        myWriter.write(text);
        myWriter.close();

        //connecting
        URL url = new URL(String.format("%s%s?src_path=%s&dest_path=%s", getUrl(), "copy_file", getPath() + "/copy_testing_from.txt", getPath() + "/copy_testing_to.txt"));
        //URL url = new URL("http://localhost:8080/copy_file?src_path=D:/test/copy_testing_from.txt&dest_path=D:/test/copy_testing_to.txt");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        //reading if also all file content was copied from src to dest
        File file = new File(getPath() + "/copy_testing_to.txt");
        Scanner myReader = new Scanner(file);
        String data = "";
        if (myReader.hasNextLine()) {
            data = myReader.nextLine();
        }
        myReader.close();

        int responseCode = connection.getResponseCode();

        boolean copied = data.equals(text);
        boolean responseValidation = (responseCode == HttpURLConnection.HTTP_OK);

        Assertions.assertTrue(copied && responseValidation, "Copying file FAILED: Response: " + responseCode);

    }


    @Test
    void moveFileTest() throws IOException {
        //creating file, we want to move later
        boolean moveTestingFrom = new File((getPath() + "/move_testing_from.txt")).createNewFile();
        URL url = new URL(String.format("%s%s?src_path=%s&dest_path=%s", getUrl(), "move_file", getPath() + "/move_testing_from.txt", getPath() + "/move_testing_final.txt"));

        //connecting
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        //checking if the file was moved properly
        int responseCode = connection.getResponseCode();
        boolean moved = (new File(String.format("%s//move_testing_final.txt", getPath())).exists());
        boolean responseValidation = (responseCode == HttpURLConnection.HTTP_OK);

        Assertions.assertTrue(moved && responseValidation, "Moving file FAILED: Response: " + responseCode);
    }

    @Test
    void readFileTest() throws IOException {
        //creting new file
        File file = new File((getPath() + "/reading_file.txt"));
        file.createNewFile();
        String text = "ABC DEF GHI 123 456 7skjcbclnaiosc liascbilyscblisb89 JKL MNO liacb alkjcb akcj bs ajcsn";

        //writing something in new file. Later it will be read by called method
        FileWriter myWriter = new FileWriter(getPath() + "/reading_file.txt");
        myWriter.write(String.valueOf(text));
        myWriter.close();

        //connecting
        URL url = new URL(String.format("%s%s?absolute_path=%s", getUrl(), "read_file", getPath() + "/reading_file.txt"));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();

        //make readable string from return value from GET request
        byte[] dataFromFile  = connection.getInputStream().readAllBytes();
        String textFromFile = new String(dataFromFile, StandardCharsets.UTF_8);

        boolean equals = textFromFile.contains(text);
        boolean responseValidation = (responseCode == HttpURLConnection.HTTP_OK);
        Assertions.assertTrue(equals && responseValidation, "Reading file FAILED: Response: " + responseCode);
    }

    @Test
    void encryptDecryptFileTest() throws IOException {
        //creting file to test encryption
        File file = new File((getPath() + "/encrypt_decrypt_file.txt"));
        file.createNewFile();
        String text = "abc123def456ghi789jkl";

        //writing text in the file
        FileWriter myWriter = new FileWriter(getPath() + "/encrypt_decrypt_file.txt");
        myWriter.write(String.valueOf(text));
        myWriter.close();

        //generated KEY for symmetric encryption
        String key = "4528482B4D6251655368566D59713374";

        //connecting
        URL url = new URL(String.format("%s%s?absolute_path=%s&key=%s", getUrl(), "encrypt", getPath() + "/encrypt_decrypt_file.txt", key));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();

        //make readable string from textfile. The message should be encrypted so we cant read it
        byte[] dataFromFile  = connection.getInputStream().readAllBytes();
        String textFromFile = new String(dataFromFile, StandardCharsets.UTF_8);

        if (responseCode != HttpURLConnection.HTTP_OK || textFromFile.contains(text)) {
            assert false;
        }
        URL url2 = new URL(String.format("%s%s?absolute_path=%s&key=%s", getUrl(), "decrypt", getPath() + "/encrypt_decrypt_file.txt", key));
        HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
        connection2.setRequestMethod("GET");
        int responseCode2 = connection2.getResponseCode();

        //make readable string from textfile. Message should be as at the start
        byte[] dataFromFileAfterDecrypting  = connection2.getInputStream().readAllBytes();
        String textFromFileAfterDecrypting = new String(dataFromFileAfterDecrypting, StandardCharsets.UTF_8);

        if (responseCode2 == HttpURLConnection.HTTP_OK && textFromFileAfterDecrypting.contains(text)){
            assert true;
        } else {
            assert false;
        }

    }


}