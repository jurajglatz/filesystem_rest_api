package com.jg.filesystem;


import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;


import static org.junit.jupiter.api.Assertions.*;

class CreateNewDirectoryTest {

    @Test
    void isCreatedTest(){
        final String path = System.getProperty("user.home");
        final String name = "newww_create_directory_test";
        CreateNewDirectory dir = new CreateNewDirectory(path, name);
        if (dir.getAbsolutePath().equals(path) && dir.getDirectoryName().equals(name)){
            assertTrue(Files.isDirectory(Path.of(String.format("%s\\%s", path, name))));
        } else{
            assertFalse(false);
        }
    }

}