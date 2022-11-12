package com.jg.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileOperations {

    static void createFile(String path, String dirName, String fileNameExtension) throws IOException {
        File file = new File(String.format("%s\\%s.%s", path,dirName, fileNameExtension));
        if (!file.exists()) {
            file.createNewFile();
        }
    }

     static void deleteFile(String path) {
         File file = new File(path);
         if (file.exists()) {
             file.delete();
         }
    }

    static void copyFile(String oldPath, String newPath) throws IOException{
        Path src = Paths.get(oldPath);
        //InputStream in = (new File(oldPath));
        Path dest = Paths.get(newPath);
        Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
    }

    static void moveFile(String oldPath, String newPath) throws IOException{
        Path src = Paths.get(oldPath);
        //InputStream in = (new File(oldPath));
        Path dest = Paths.get(newPath);
        Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
    }


}
