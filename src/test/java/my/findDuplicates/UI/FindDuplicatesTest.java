/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.findDuplicates.UI;

import java.io.File;
import java.io.IOException;
import java.util.List;
import my.findDuplicates.Files.FindFiles;
import my.findDuplicates.FindDuplicatesApplication;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
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
public class FindDuplicatesTest {

    private Logger log = LoggerFactory.getLogger(FindDuplicatesTest.class);
    @Autowired
    private ApplicationContext context;
    @Autowired
    private FindFiles findFiles;
    @Autowired
    private FindDuplicates findDuplicates;
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    public FindDuplicatesTest() {
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

    /**
     * Test of start method, of class FindDuplicates.
     */
    @Test
    public void testStart() throws Exception {
        System.out.println("start");
        findDuplicates.start(new String[]{folder.getRoot().getAbsolutePath()});
        while (!findDuplicates.isReady()) {
            Thread.sleep(900);
        }
        List<Duplicates> result = findDuplicates.getResult();
        Assert.assertEquals(2, result.size());
        Assert.assertEquals("ready", findDuplicates.status());
    }
}
