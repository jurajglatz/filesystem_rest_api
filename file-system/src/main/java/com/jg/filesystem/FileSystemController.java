package com.jg.filesystem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileSystemController {
    //private static final String template = "Hello, %s";

    @GetMapping("/create_dir")
    public HelloWorld helloworld(@RequestParam(value = "absolute_path", defaultValue = "C:\\") String absolutePath, @RequestParam(value = "directory_name", defaultValue = "New Directory") String directoryName) {
        return new HelloWorld(String.format("%s %s", absolutePath, directoryName));
    }
}
