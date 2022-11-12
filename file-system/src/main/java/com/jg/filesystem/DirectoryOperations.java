package com.jg.filesystem;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.jg.filesystem.FileOperations.getContent;
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

    static Map<File, Integer> findPattern(String path, String pattern){
        Map<File, Integer> map;
        map = new HashMap<File, Integer>();
        File[] files = listContentOfDirectory(path);

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
