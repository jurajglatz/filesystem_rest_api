package com.jg.filesystem;

import java.io.File;

public class Directory {

    static boolean createDirectory(String path, String dirName){
        return new File(String.format("%s/%s",path,dirName)).mkdir();
    }

     static void deleteDirectory(String path) {
         new File(path).delete();
    }


}
