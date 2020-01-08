/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.findDuplicates.UI;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 *
 * @author Kharisov Ruslan
 */
@WebMvcTest(ChoosePathController.class)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ChoosePathControllerTest.ControllerConfigurationTest.class)
public class ChoosePathControllerTest {

    @TestConfiguration
    @ComponentScan(basePackages = {"my"})
    @EnableWebMvc
    static class ControllerConfigurationTest {
    }

    private Logger log = LoggerFactory.getLogger(ChoosePathControllerTest.class);
    @Autowired
    ApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @Autowired
    ChooseDirectory chooseDirectory;

    public ChoosePathControllerTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of chooseRoots method, of class ChoosePath.
     */
    @Test
    public void testChooseRoots() throws Exception {
        System.out.println("chooseRoots");
        String path = chooseDirectory.getRoots().get(0);
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.TEXT_HTML)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString("value=\"" + path)));
    }

    /**
     * Test of chooseDirectory method, of class ChoosePath.
     */
    @Test
    public void testChooseDirectory() throws Exception {
        System.out.println("chooseDirectory");
        String path = chooseDirectory.getRoots().get(0);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("command", path);
        params.add("selectedRoots", "");
        mvc.perform(MockMvcRequestBuilders.post("/path").params(params).accept(MediaType.TEXT_HTML)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString("value=\"" + path)))
                .andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString("value=\"..")));
    }
}
