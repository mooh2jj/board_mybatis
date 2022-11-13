package com.example.board_springboot.common.scheduler;

import com.example.board_springboot.domain.AttachVO;
import com.example.board_springboot.mapper.AttachMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileCheckTask {

    @Value("${file.upload.folder}")
    private String uploadFolder;
    private final AttachMapper attachMapper;

    @Scheduled(cron = "15 * * * * ?")        // 새벽 2시에 진행 sec min hour day month, week, year
    public void checkFiles() {
        log.warn("File Check Task run..........");
        log.warn("File Check Time: {}", new Date());
        List<AttachVO> oldFolders = attachMapper.findOldFolders();

        List<Path> pathList = oldFolders.stream()
                .map(folder -> Paths.get(uploadFolder, folder.getUploadPath(),
                        folder.getUuid() + "_" + folder.getFileName()))
                .collect(Collectors.toList());
        log.warn("=================================================");

        pathList.forEach(path -> log.warn("path: {}", path));

        File targetDir = Paths.get(uploadFolder, getFolderYesterDay()).toFile();
        File[] removeFiles = targetDir.listFiles(file -> !pathList.contains(file.toPath()));
        log.warn("------------------remove file 시작-----------------------");
        for (File file : removeFiles) {
            log.warn(file.getAbsolutePath());
            file.delete();
        }
    }

    private String getFolderYesterDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String str = sdf.format(cal.getTime());
        return str.replace("-", File.separator);
    }

}
