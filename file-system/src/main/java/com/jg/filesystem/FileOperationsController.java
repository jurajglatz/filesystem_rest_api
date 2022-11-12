package com.jg.filesystem;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.jg.filesystem.FileOperations.createFile;
import static com.jg.filesystem.FileOperations.deleteFile;

@RestController
public class FileOperationsController {
    private final String defaultPath = "C:\\";
    public String getDefaultPath() { return defaultPath; }

    @GetMapping("/create_file")
    public void createFile(@RequestParam(value = "absolute_path", defaultValue = defaultPath) String absolutePath, @RequestParam(value = "file_name", defaultValue = "New_File") String fileName, @RequestParam(value = "file_extension", defaultValue = "txt") String fileNameExtension) throws IOException {
        FileOperations.createFile(absolutePath, fileName, fileNameExtension);
        //createFile(absolutePath, fileName, fileNameExtension);
    }

    @GetMapping("/delete_file")
    public void deleteFile(@RequestParam(value = "absolute_path") @NonNull String absolutePath) throws IOException {
        FileOperations.deleteFile(absolutePath);
        //deleteFile(absolutePath);
    }

    @GetMapping("/copy_file")
    public void copyFile(@RequestParam(value = "src_path") @NonNull String srcPath, @RequestParam(value = "dest_path") @NonNull String destPath) throws IOException {
        FileOperations.copyFile(srcPath, destPath);
    }

    @GetMapping("/move_file")
    public void moveFile(@RequestParam(value = "src_path") @NonNull String srcPath, @RequestParam(value = "dest_path") @NonNull String destPath) throws IOException {
        FileOperations.moveFile(srcPath, destPath);

    }
}
