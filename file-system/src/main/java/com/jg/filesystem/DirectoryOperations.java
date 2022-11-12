package com.jg.filesystem;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

import static org.apache.commons.io.comparator.SizeFileComparator.SIZE_COMPARATOR;

public class DirectoryOperations {


    static void createDirectory(String path, String dirName){
        File file = new File(String.format("%s/%s", path,dirName));
        if (!file.exists()) {
            file.mkdir();
        }
    }

     static void deleteDirectory(String path) {
         File file = new File(path);
         if (file.exists()) {
             file.delete();
         }
    }

    static File[] listContentOfDirectory(String path){
        File file = new File(path);
        File[] files = file.listFiles();

        Arrays.sort(files, SIZE_COMPARATOR);
        //FileSortBySize.displayFileOrder(files, false);

        return files;
    }


}
