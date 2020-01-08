/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.findDuplicates.Files;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import my.findDuplicates.UI.Duplicates;
import org.apache.commons.codec.digest.DigestUtils;
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
public class AnalysisFiles {

    private Logger log = LoggerFactory.getLogger(AnalysisFiles.class);

    private Map<String, List<FileInfo>> fileShaMap = new HashMap<>();

    private Map<Long, List<FileInfo>> fileSizeMap = new HashMap<>();

    private List<Duplicates> result = new ArrayList<>();

    @Autowired
    private ApplicationContext context;

    public void analisysSize(List<FileInfo> files) {
        files.forEach(fi -> {
            List<FileInfo> lfiles;
            if (fileSizeMap.containsKey(fi.getSize())) {
                lfiles = fileSizeMap.get(fi.getSize());
            } else {
                lfiles = new ArrayList<>();
                fileSizeMap.put(Long.valueOf(fi.getSize()), lfiles);
            }
            lfiles.add(fi);
        });
    }

    private Optional<String> getSha(String filename) {
        Optional<String> sha = Optional.empty();
        try (FileInputStream fis = new FileInputStream(filename)) {
            sha = Optional.of(DigestUtils.sha1Hex(fis));
        } catch (FileNotFoundException ex) {
            log.error(ex.getMessage() + " file: " + filename, ex);
        } catch (IOException ex) {
            log.error(ex.getMessage() + " file: " + filename, ex);
        }
        return sha;
    }

    private void analisysSha() {
        Set<Long> keys = fileSizeMap.keySet();
        keys.forEach(k -> {
            List<FileInfo> files = fileSizeMap.get(k);
            files.forEach(fi -> {
                String sha = getSha(fi.getPathname()).orElse("");
                List<FileInfo> lfiles = new ArrayList<>();
                if (fileShaMap.containsKey(sha)) {
                    lfiles = fileShaMap.get(sha);
                } else {
                    lfiles = new ArrayList<>();
                    fileShaMap.put(sha, lfiles);
                }
                lfiles.add(fi);
            });
        });
        fileShaMap = fileShaMap.entrySet().stream().filter(e -> e.getValue().size() > 1)
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    private void analisysResult() {
        Set<String> keys = fileShaMap.keySet();
        for (String k : keys) {
            List<FileInfo> files = fileShaMap.get(k);
            // because in list several result we can use files.get(0)
            Duplicates fd = context.getBean(Duplicates.class, k, files.get(0).getSize(), files);
            result.add(fd);
        }
    }

    public void execute(List<FileInfo> files) {
        fileShaMap.clear();
        fileSizeMap.clear();
        result.clear();
        analisysSize(files);
        analisysSha();
        analisysResult();
    }

    public List<Duplicates> getResult() {
        return result;
    }

    public Map<String, List<FileInfo>> getFileShaMap() {
        return fileShaMap;
    }

    public void setFileShaMap(Map<String, List<FileInfo>> fileShaMap) {
        this.fileShaMap = fileShaMap;
    }

    public Map<Long, List<FileInfo>> getFileSizeMap() {
        return fileSizeMap;
    }
}
