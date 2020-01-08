/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.findDuplicates.Files;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kharisov Ruslan
 */
@Component
public class FindFiles extends SimpleFileVisitor<Path> {

    private Logger log = LoggerFactory.getLogger(FindFiles.class);
    private List<FileInfo> files;

    @Autowired
    private ApplicationContext context;

    public FindFiles() {
        files = new ArrayList<>();
    }

    public List<FileInfo> getFiles(Path findPath) throws IOException {
        Files.walkFileTree(findPath, this);
        return files;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        log.error("Path " + file.getFileName() + " " + exc.getMessage(), exc);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        FileInfo fi = context.getBean(FileInfo.class, file.toString(), attrs.size());
        files.add(fi);
        return FileVisitResult.CONTINUE;
    }

    public List<FileInfo> getResult() {
        return files;
    }
}
