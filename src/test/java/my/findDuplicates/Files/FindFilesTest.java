/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.findDuplicates.Files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import my.findDuplicates.FindDuplicatesApplication;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Kharisov Ruslan
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = FindDuplicatesApplication.class)
public class FindFilesTest {

    private Logger log = LoggerFactory.getLogger(FindFilesTest.class);
    @Autowired
    private ApplicationContext context;
    @Autowired
    private FindFiles findFiles;
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    public FindFilesTest() {
    }

    @Before
    public void setUp() throws IOException {
        Path root = folder.getRoot().toPath();
        List<FileInfo> files = findFiles.getFiles(root);
        System.out.println("files " + files.size());
        File nf = folder.newFolder();
        File f1 = folder.newFile();
        File f2 = folder.newFile();
        File f3 = folder.newFile();
        File f4 = folder.newFile();
        File f5 = folder.newFile();
        File f6 = folder.newFile();
        File f7 = folder.newFile();
        File f8 = folder.newFile();
        File f9 = folder.newFile();
        FileUtils.writeStringToFile(f1, "a", "UTF-8");
        FileUtils.writeStringToFile(f2, "b", "UTF-8");
        FileUtils.writeStringToFile(f3, "c", "UTF-8");
        FileUtils.writeStringToFile(f4, "a", "UTF-8");
        FileUtils.writeStringToFile(f5, "dd", "UTF-8");
        FileUtils.writeStringToFile(f6, "ad", "UTF-8");
        FileUtils.writeStringToFile(f7, "dd", "UTF-8");
        FileUtils.writeStringToFile(f8, "af", "UTF-8");
        FileUtils.writeStringToFile(f9, "an", "UTF-8");
    }

    @After
    public void tearDown() {
        folder.delete();
    }

    /**
     * Test of getFiles method, of class FindFiles.
     */
    @Test
    public void testGetFiles() throws IOException {
        System.out.println("getFiles");
        Path root = folder.getRoot().toPath();
        List<FileInfo> files = findFiles.getFiles(root);
        files.forEach(fi -> System.out.println(fi.getPathname()));
        System.out.println("count: " + files.size());
        assertEquals(9, files.size());
        files.clear();  // because singleton
    }
}
