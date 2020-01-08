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
import java.util.Map;
import my.findDuplicates.FindDuplicatesApplication;
import my.findDuplicates.UI.Duplicates;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
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
public class AnalysisFilesTest {

    private Logger log = LoggerFactory.getLogger(AnalysisFilesTest.class);
    @Autowired
    private ApplicationContext context;
    @Autowired
    private FindFiles findFiles;
    @Autowired
    private AnalysisFiles analysisFiles;
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    public AnalysisFilesTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws IOException {
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
     * Test of analisysSize method, of class AnalysisFiles.
     */
    @Test
    public void testAnalisysSizeShaResult() throws IOException {

        System.out.println("analisys size and sha and result");
        Path root = folder.getRoot().toPath();
        List<FileInfo> files = findFiles.getFiles(root);
        assertEquals(9, files.size());
        analysisFiles.execute(files);
        Map<Long, List<FileInfo>> fileSizeMap = analysisFiles.getFileSizeMap();
        assertEquals(4, fileSizeMap.get(Long.valueOf(1)).size());
        assertEquals(5, fileSizeMap.get(Long.valueOf(2)).size());
        Map<String, List<FileInfo>> resultSha = analysisFiles.getFileShaMap();
        assertEquals(2, resultSha.size());
        List<Duplicates> result = analysisFiles.getResult();
        assertEquals(2, result.size());
        files.clear();  // because singleton
    }
}
