package com.board.api.utils;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Component
public class FileUtils {

    private final Path tempDir;

    public FileUtils() throws IOException {

        tempDir = Files.createTempDirectory("tmpDir");
    }

    public Path createPath(String uid, String fileName) throws IOException {

        Path dirPath = Files.createDirectories(Paths.get(tempDir.toAbsolutePath().toString(),
                uid, String.valueOf(new Date().getTime())));
        return Files.createFile(Paths.get(dirPath.toAbsolutePath().toString(), fileName));
    }

    @PreDestroy
    public void tearDown() throws IOException {

        org.apache.commons.io.FileUtils.deleteDirectory(tempDir.toFile());
    }
}
