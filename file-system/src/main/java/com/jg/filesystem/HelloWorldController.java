package com.jg.filesystem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    private static final String template = "Hello, %s";

    @GetMapping("/greeting")
    public HelloWorld helloworld(@RequestParam(value = "name", defaultValue = "world") String name) {
        return new HelloWorld(String.format(template, name));
    }
}
