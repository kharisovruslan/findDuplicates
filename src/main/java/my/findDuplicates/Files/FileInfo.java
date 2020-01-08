/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.findDuplicates.Files;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kharisov Ruslan
 */
@Component
@Scope("prototype")
@Data
@RequiredArgsConstructor
@ToString
public class FileInfo {

    final private String pathname;
    final private long size;
}
