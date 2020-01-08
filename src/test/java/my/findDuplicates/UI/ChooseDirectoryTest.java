/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.findDuplicates.UI;

import java.io.File;
import java.io.IOException;
import java.util.List;
import my.findDuplicates.Files.AnalysisFilesTest;
import my.findDuplicates.FindDuplicatesApplication;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
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
public class ChooseDirectoryTest {

    private Logger log = LoggerFactory.getLogger(AnalysisFilesTest.class);
    @Autowired
    private ApplicationContext context;
    @Autowired
    private ChooseDirectory chooseDirectory;
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    public ChooseDirectoryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws IOException {
        File nf1 = folder.newFolder();
        File nf2 = folder.newFolder();
        File nf3 = folder.newFolder();
        File f1 = folder.newFile();
        File f2 = folder.newFile();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getRoots method, of class ChooseDirectory.
     */
    @Test
    public void testGetRoots() {
        System.out.println("getRoots");
        List<String> roots = chooseDirectory.getRoots();
        assertThat(roots, instanceOf(List.class));
        assertTrue(roots.size() > 0);
    }

    /**
     * Test of getFiles method, of class ChooseDirectory.
     */
    @Test
    public void testGetFiles() {
        System.out.println("getFiles");
        List<String> directories = chooseDirectory.getFiles(folder.getRoot().getAbsolutePath());
        assertThat(directories, instanceOf(List.class));
        assertEquals(3, directories.size());
    }

}
