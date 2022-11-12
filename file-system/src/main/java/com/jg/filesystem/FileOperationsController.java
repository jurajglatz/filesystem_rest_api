package com.jg.filesystem;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.util.ArrayList;


import static com.jg.filesystem.FileOperations.*;

@RestController
public class FileOperationsController {
    private final String defaultPath = "D:/test";
    public String getDefaultPath() { return defaultPath; }

    //controller for calling method fot new file creating
    @GetMapping("/create_file")
    public void createFile(@RequestParam(value = "absolute_path", defaultValue = defaultPath) String absolutePath, @RequestParam(value = "file_name", defaultValue = "New_File") String fileName, @RequestParam(value = "file_extension", defaultValue = "txt") String fileNameExtension) throws IOException {
        FileOperations.createFile(absolutePath, fileName, fileNameExtension);
        //createFile(absolutePath, fileName, fileNameExtension);
    }

    //controller for calling method fot file deleting
    @GetMapping("/delete_file")
    public void deleteFile(@RequestParam(value = "absolute_path") @NonNull String absolutePath) {
        FileOperations.deleteFile(absolutePath);
        //deleteFile(absolutePath);
    }

    //controller for calling method fot file copying from one destination to another
    @GetMapping("/copy_file")
    public void copyFile(@RequestParam(value = "src_path") @NonNull String srcPath, @RequestParam(value = "dest_path") @NonNull String destPath) throws IOException {
        FileOperations.copyFile(srcPath, destPath);
    }

    //controller for calling method for moving file from one destination to another
    @GetMapping("/move_file")
    public void moveFile(@RequestParam(value = "src_path") @NonNull String srcPath, @RequestParam(value = "dest_path") @NonNull String destPath) throws IOException {
        FileOperations.moveFile(srcPath, destPath);
    }

    //controller for calling method for listing all content of file
    @GetMapping("/read_file")
    public ArrayList<String> readFile(@RequestParam(value = "absolute_path") @NonNull String absolutePath) {
        return FileOperations.getContent(absolutePath);
    }

    //encryption method
    @GetMapping("/encrypt")
    public byte[] encrypt(@RequestParam(value = "absolute_path") @NonNull String absolutePath, @RequestParam(value = "key") @NonNull byte[] key) throws Exception {
        return encryptFile(absolutePath, key);
    }

    //decryption method
    @GetMapping("/decrypt")
    public byte[] decrypt(@RequestParam(value = "absolute_path") @NonNull String absolutePath, @RequestParam(value = "key") @NonNull byte[] key) throws Exception {
        return decryptFile(absolutePath, key);
    }





}
