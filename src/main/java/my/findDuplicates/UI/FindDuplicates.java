/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.findDuplicates.UI;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import my.findDuplicates.Files.AnalysisFiles;
import my.findDuplicates.Files.FindFiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kharisov Ruslan
 */
@Component
public class FindDuplicates {

    private Logger log = LoggerFactory.getLogger(FindDuplicates.class);
    @Autowired
    private FindFiles findFiles;
    @Autowired
    private AnalysisFiles analysisFiles;
    @Autowired
    private ApplicationContext context;
    private String statusmessage = "";
    private List<Duplicates> result;
    private AtomicBoolean ready = new AtomicBoolean(false);

    @Async
    public void start(String[] roots) {
        setStatus("find files");
        ready.set(false);
        for (String root : roots) {
            try {
                setStatus("find files " + root);
                findFiles.getFiles(Paths.get(root));
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
                setStatus("ex.getMessage()");
            }
        }
        setStatus("analysis duplicates");

        analysisFiles.execute(findFiles.getResult());
        findFiles.getResult().clear();
        result = analysisFiles.getResult();
        Collections.sort(result, Collections.reverseOrder());
        setStatus("ready");
        ready.set(true);
    }

    public boolean isReady() {
        return ready.get();
    }

    public List<Duplicates> getResult() {
        return result;
    }

    private synchronized void setStatus(String status) {
        statusmessage = status;
    }

    public synchronized String status() {
        return statusmessage;
    }
}
