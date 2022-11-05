package com.example.board_springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/file")
public class FileController {

    private final static String UPLOAD_DIRECTORY = "upload";

    @PostMapping("/single")
    public String singleFileUpload(@RequestParam("singleFile") MultipartFile singleFile, HttpServletRequest request) {

        log.info("singleFile: {}", singleFile);
        String path = request.getSession().getServletContext().getRealPath("resources");
        log.info("path: {}", path);     // C:\WebStudy\Study\ebrainSoft\board_mybatis\src\main\webapp\resources
        String root = path + File.separator + UPLOAD_DIRECTORY;

        File file = new File(root);

        if (!file.exists()) file.mkdir();

        String originalFilename = singleFile.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uuidFileName = UUID.randomUUID().toString() + ext;

        File changeFile = new File(root + File.separator + uuidFileName);

        try {
            singleFile.transferTo(changeFile);
            log.info("파일업로드 성공");
        } catch (IOException e) {
            log.error("파일업로드 실패");
            e.printStackTrace();
        }
        return "result";
    }

    @PostMapping("/multi")
    public String multiFileUpload(
            @RequestParam("multiFile") List<MultipartFile> multipartFiles,
            HttpServletRequest request
    ) {
        log.info("multipartFiles: {}", multipartFiles);

        String path = request.getSession().getServletContext().getRealPath("resources");
        log.info("path: {}", path);     // C:\WebStudy\Study\ebrainSoft\board_mybatis\src\main\webapp\resources
        String root = path + File.separator + UPLOAD_DIRECTORY;

        File fileCheck = new File(root);

        if(!fileCheck.exists()) fileCheck.mkdirs();

        List<Map<String, String>> fileList = new ArrayList<>();

        for (int i = 0; i < multipartFiles.size(); i++) {
            String originFile = multipartFiles.get(i).getOriginalFilename();
            String ext = originFile.substring(originFile.lastIndexOf("."));
            String changeFile = UUID.randomUUID().toString() + ext;

            Map<String, String> map = new HashMap<>();
            map.put("originFile", originFile);
            map.put("changeFile", changeFile);

            fileList.add(map);
        }

        try {
            for(int i = 0; i < multipartFiles.size(); i++) {
                File uploadFile = new File(root + File.separator + fileList.get(i).get("changeFile"));
                multipartFiles.get(i).transferTo(uploadFile);
            }
            log.info("다중 파일 업로드 성공");
            } catch (IOException e) {
                log.info("다중 파일 업로드 실패");
                e.printStackTrace();
                for(int i = 0; i < multipartFiles.size(); i++) {
                    new File(root + "\\" + fileList.get(i).get("changeFile")).delete();
                }
            }
        return "result";
    }
}
