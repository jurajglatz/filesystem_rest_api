package com.jg.filesystem;

import java.io.File;

public class DirectoryOperations {

    static void createDirectory(String path, String dirName){
        if (!(new File(String.format("%s/%s", path,dirName)).exists())) {
            new File(String.format("%s/%s", path, dirName)).mkdir();
        }
    }

     static void deleteDirectory(String path) {
         File file = new File(path);
         if (file.exists()) {
             file.delete();
         }
         //new File(path).delete();
    }


}
