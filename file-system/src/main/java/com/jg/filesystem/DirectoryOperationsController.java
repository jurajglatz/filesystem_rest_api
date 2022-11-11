package com.jg.filesystem;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.jg.filesystem.DirectoryOperations.createDirectory;
import static com.jg.filesystem.DirectoryOperations.deleteDirectory;

@RestController
public class DirectoryOperationsController {
    private final String defaultPath = "C:\\";
    public String getDefaultPath() { return defaultPath; }

    @GetMapping("/create_dir")
    public void createDir(@RequestParam(value = "absolute_path", defaultValue = defaultPath) String absolutePath, @RequestParam(value = "directory_name", defaultValue = "New Directory") String directoryName) {
        createDirectory(absolutePath, directoryName);
    }

    @GetMapping("/delete_dir")
    public void deleteDir(@RequestParam(value = "absolute_path") @NonNull String absolutePath) throws IOException {
        deleteDirectory(absolutePath);
    }
}
