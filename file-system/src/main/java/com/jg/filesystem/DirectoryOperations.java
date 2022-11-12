package com.jg.filesystem;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.jg.filesystem.FileOperations.getContent;
import static org.apache.commons.io.comparator.SizeFileComparator.SIZE_COMPARATOR;

public class DirectoryOperations {

    /*
    * Method for creating new Directories
    *
    * 1.check if the file we want to create already exists.
    * 2.if it doesnt, it creates new one
    * */
    static void createDirectory(String path, String dirName){
        File file = new File(String.format("%s/%s", path,dirName));
        if (!file.exists()) {
            file.mkdir();
        }
    }

    /*
     * Method for deleting existing Directories
     *
     * 1.check if the file we want to delete exists.
     * 2.if it does, it is deleted
     * */
     static void deleteDirectory(String path) {
         File file = new File(path);
         if (file.exists()) {
             file.delete();
         }
    }

    /*
    * Method for listing all content of directory sorted by size
    *
    * 1. list files of directory
    * 2. sort it properly
    * */
    static File[] listContentOfDirectory(String path){
        File file = new File(path);
        //1.
        File[] files = file.listFiles();

        //2.
        Arrays.sort(files, SIZE_COMPARATOR);
        //FileSortBySize.displayFileOrder(files, false);

        return files;
    }

    /*
    * Method for finding pattern in all files in directory from argument
    *
    * 1. Hashmap is created for saving pairs of File:line number (where the pattern occurs)
    * 2. Iterate through all files and all lines of files and looking for pattern from argument
    * */
    static Map<File, Integer> findPattern(String path, String pattern){
        //1.
        Map<File, Integer> map;
        map = new HashMap<File, Integer>();
        File[] files = listContentOfDirectory(path);

        //2.
        for (Integer i = 0; i < files.length; i++){
            ArrayList<String> content = new ArrayList<String>();
            content = getContent(files[i].getPath());
            for (Integer j = 0; j < content.size(); j++){
                if (content.get(j).contains(pattern)){
                    map.put(files[i], j);
                }
            }

        }
        return map;
    }


}
