package com.jg.filesystem;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Map;

import static com.jg.filesystem.DirectoryOperations.*;

@RestController
public class DirectoryOperationsController {
    private final String defaultPath = "D:/test";
    public String getDefaultPath() { return defaultPath; }

    //Controller for calling method for creating new directory
    @GetMapping("/create_dir")
    public void createDir(@RequestParam(value = "absolute_path", defaultValue = defaultPath) String absolutePath, @RequestParam(value = "directory_name", defaultValue = "New_Directory") String directoryName) {
        createDirectory(absolutePath, directoryName);
    }

    //controller for calling method for deleting directory
    @GetMapping("/delete_dir")
    public void deleteDir(@RequestParam(value = "absolute_path") @NonNull String absolutePath){
        deleteDirectory(absolutePath);
    }

    //controller for calling method that lists all files in directory ordered by size
    @GetMapping("/list_dir_content")
    public File[] listDirectoryContent(@RequestParam(value = "absolute_path", defaultValue = defaultPath) String absolutePath) {
        return listContentOfDirectory(absolutePath);
    }

    //controller for calling method for finding pattern in all files in directory (also ordered by size)
    @GetMapping("/find_pattern")
    public Map<File, Integer> findPatternInFiles(@RequestParam(value = "directory_path") @NonNull String absolutePath, @RequestParam(value = "pattern") @NonNull String pattern) {
        return findPattern(absolutePath, pattern);
    }
}
