/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.findDuplicates.UI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import my.findDuplicates.Files.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Kharisov Ruslan
 */
@Controller
public class ChoosePathController {

    private Logger log = LoggerFactory.getLogger(ChoosePathController.class);
    @Autowired
    private ChooseDirectory chooseDirectory;
    @Autowired
    private FindDuplicates findDuplicates;

    @GetMapping("/")
    public String chooseRoots(Model model) {
        model.addAttribute("roots", chooseDirectory.getRoots());
        return "choosePath";
    }

    @PostMapping("path")
    public String chooseDirectory(@RequestParam(value = "selectedRoots", required = false) String roots[], @RequestParam("command") String command, Model model) {
        if (command.compareTo("Start") == 0) {
            if (roots != null) {
                if (roots.length > 0) {
                    findDuplicates.start(roots);
                    return "status";
                }
            }
        }
        if (command.compareTo("..") == 0) {
            return "redirect:/";
        }
        if (command.endsWith("..")) {
            command = (new File(command)).getParentFile().getAbsolutePath();
        }
        List<String> directories = chooseDirectory.getFiles(command);
        File parent = (new File(command)).getParentFile();
        if (parent != null) {
            if (parent.getAbsolutePath().endsWith(File.separator)) {
                directories.add(0, parent.getAbsolutePath() + "..");
            } else {
                directories.add(0, parent.getAbsolutePath() + File.separator + "..");
            }
        } else {
            directories.add(0, "..");
        }
        model.addAttribute("roots", directories);
        return "choosePath";
    }

    @PostMapping("delete")
    public String deleteFiles(@ModelAttribute("removeFileName") String filename, Model model) {
        findDuplicates.getResult().forEach((d) -> {
            boolean isonefile = false;
            for (FileInfo fi : d.getFiles()) {
                if (fi.getPathname().compareTo(filename) == 0) {
                    isonefile = true;
                    try {
                        Files.delete(Paths.get(filename));
                        fi.setRemove(true);
                    } catch (IOException ex) {
                        log.error(ex.getMessage(), ex);
                    }
                }
            }
            if (isonefile) {
                long countDelete = d.getFiles().stream().filter(FileInfo::isRemove).count();
                if ((d.getFiles().size() - countDelete) == 1) {
                    d.getFiles().forEach(fi -> fi.setOnefile(true));
                }
            }
        });
        return "redirect:/result";
    }

    @GetMapping("result")
    public String result(Model model) {
        model.addAttribute("result", findDuplicates.getResult());
        return "result";
    }

    @GetMapping("status")
    public String status(Model model) {
        String status = findDuplicates.status();
        model.addAttribute("status", status);
        return "statusblock::status";
    }
}
