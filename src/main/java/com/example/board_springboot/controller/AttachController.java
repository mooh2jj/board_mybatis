package com.example.board_springboot.controller;

import com.example.board_springboot.domain.AttachVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class AttachController {

    private final static String UPLOAD_DIRECTORY = "upload";

    @PostMapping("/uploadAjaxAction")
    public ResponseEntity<List<AttachVO>> uploadAjaxPost(MultipartFile[] uploadFile, HttpServletRequest request) {
        log.info("uploadAjaxAction multipartFile[]: {}", uploadFile);
        List<AttachVO> attachList = new ArrayList<>();

        String path = request.getSession().getServletContext().getRealPath("resources");
        log.info("path: {}", path);     // C:\WebStudy\Study\ebrainSoft\board_mybatis\src\main\webapp\resources
        String uploadPath = path + File.separator + UPLOAD_DIRECTORY;

        for (MultipartFile multipartFile : uploadFile) {

            String uploadFileName = multipartFile.getOriginalFilename();
            long size = multipartFile.getSize();
            log.info("-------------------------------------");
            log.info("Upload File Name: " + uploadFileName);
            log.info("Upload File Size: " + size);

            AttachVO attachVO = new AttachVO();

            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
            log.info("only file name: " + uploadFileName);
            attachVO.setFileName(uploadFileName);

            UUID uuid = UUID.randomUUID();

            uploadFileName = uuid.toString() + "_" + uploadFileName;

            try {
                File saveFile = new File(uploadPath, uploadFileName);
                multipartFile.transferTo(saveFile);

                attachVO.setUuid(uuid.toString());
                attachVO.setUploadPath(uploadPath);

                attachList.add(attachVO);
            } catch (Exception e) {
                log.error(e.getMessage());
            } // end catch
        } // end for

        return new ResponseEntity<>(attachList, HttpStatus.OK);
    }

}
