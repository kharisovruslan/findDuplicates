/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.findDuplicates.UI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kharisov Ruslan
 */
@Component
public class ChooseDirectory {

    private Logger log = LoggerFactory.getLogger(ChooseDirectory.class);

    public List<String> getRoots() {
        File lroot[] = File.listRoots();
        List<String> r = new ArrayList<>();
        for (File f : lroot) {
            if (f.exists()) {
                String sd = f.getAbsolutePath();
                r.add(sd);
            }
        }
        return r;
    }

    public List<String> getFiles(String stringroot) {
        File root = new File(stringroot);
        File lroot[] = root.listFiles();
        List<String> r = new ArrayList<>();
        for (File f : lroot) {
            if (f.isDirectory()) {
                String sd = f.getAbsolutePath();
                r.add(sd);
            }
        }
        return r;
    }
}
