/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.findDuplicates.UI;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import my.findDuplicates.Files.FileInfo;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kharisov Ruslan
 */
@ToString
@Getter
@Setter
@RequiredArgsConstructor
@Component
@Scope("prototype")
public class Duplicates implements Comparable<Duplicates> {

    final private String sha;
    final private long size;
    final private List<FileInfo> files;

    public String getSizeHuman() {
        return org.apache.commons.io.FileUtils.byteCountToDisplaySize(size);
    }

    @Override
    public int compareTo(Duplicates o) {
        return Long.compare(this.getSize(), o.getSize());
    }
}
