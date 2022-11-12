package com.jg.filesystem;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;

public class FileOperations {

    /*
    * Method for File creating
    * */
    static void createFile(String path, String dirName, String fileNameExtension) throws IOException {
        File file = new File(String.format("%s\\%s.%s", path,dirName, fileNameExtension));
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    /*
    * Method for File deleting
    * */
     static void deleteFile(String path) {
         File file = new File(path);
         if (file.exists()) {
             file.delete();
         }
     }

     /*
     * Method for copying file from one destination to another
     * */
    static void copyFile(String oldPath, String newPath) throws IOException{
        Path src = Paths.get(oldPath);
        //InputStream in = (new File(oldPath));
        Path dest = Paths.get(newPath);
        Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
    }

    /*
    * Method for moving file from one destination to another
    * */
    static void moveFile(String oldPath, String newPath) throws IOException{
        Path src = Paths.get(oldPath);
        Path dest = Paths.get(newPath);
        Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
    }

    /*
    * Method for listing all content of file
    * */
    static ArrayList<String> getContent(String path){
        ArrayList<String> content = new ArrayList<String>();

        try {
            File file = new File(path);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                content.add(line);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return content;
    }

    /*
    * Method for encrypting data using symmetric encryption (AES)
    * */
    static byte[] encryptFile(String path, byte[] key) throws Exception {
        byte[] data = Files.readAllBytes(Paths.get(path));

        //AES for symmetric encryption/decryption
        Cipher cip = Cipher.getInstance("AES");
        SecretKeySpec k = new SecretKeySpec(key, "AES");
        cip.init(Cipher.ENCRYPT_MODE, k);
        byte[] encryptedData = cip.doFinal(data);
        //write encrypted data to file
        Files.write(Paths.get(path), encryptedData);

        return encryptedData;
    }

    /*
    * Method for decrypting file using AES
    * */
    static byte[] decryptFile(String path, byte[] key) throws Exception {
        //byte[] key = //... secret sequence of bytes
        byte[] data = Files.readAllBytes(Paths.get(path));

        Cipher cip = Cipher.getInstance("AES");
        SecretKeySpec k = new SecretKeySpec(key, "AES");
        cip.init(Cipher.DECRYPT_MODE, k);
        byte[] decryptedData = cip.doFinal(data);
        Files.write(Paths.get(path), decryptedData);

        return decryptedData;
    }


}
