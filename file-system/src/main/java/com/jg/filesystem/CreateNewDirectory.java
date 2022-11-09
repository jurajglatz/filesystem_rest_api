package com.jg.filesystem;


import java.io.File;

public class CreateNewDirectory {
    private String absolutePath;
    private String directoryName;
    private String status = "not created";

    public CreateNewDirectory(String path, String directoryName) {
        setAbsolutePath(path);
        setDirectoryName(directoryName);
        new File(String.format("%s\\%s",path,directoryName)).mkdir();
    }

    public String getAbsolutePath() {
        return this.absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }
}
