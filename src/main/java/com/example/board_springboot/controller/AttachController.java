package com.example.board_springboot.controller;

import com.example.board_springboot.domain.AttachVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class AttachController {

    @Value("${file.upload.path}")
    private String uploadPath;

    /**
     * ajax방식
     * (다중)파일업로드
     * @param uploadFile
     * @return 첨부파일 리스트
     */
    @PostMapping("/uploadAjaxAction")
    public ResponseEntity<List<AttachVO>> uploadAjaxPost(MultipartFile[] uploadFile) {
        log.info("uploadAjaxAction multipartFile[]: {}", uploadFile);
        List<AttachVO> attachList = new ArrayList<>();

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

                if (checkImageType(saveFile)) {
                    attachVO.setImage(true);
                }

                attachList.add(attachVO);
            } catch (Exception e) {
                log.error(e.getMessage());
            } // end catch
        } // end for

        return new ResponseEntity<>(attachList, HttpStatus.OK);
    }

    /**
     * 이미지 파일인지 유무 판단 메서드
     * @param file
     * @return boolean
     */
    private boolean checkImageType(File file) {

        try {
            String contentType = Files.probeContentType(file.toPath());
            log.info("checkImageType: {}", contentType);

            return contentType.startsWith("image");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 이미지 표시
     * @param fileName
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName) throws UnsupportedEncodingException {

        log.info("fileName: " + fileName);

        File file = new File(URLDecoder.decode(fileName, "UTF-8"));
        log.info("file: " + file);

        ResponseEntity<byte[]> result = null;
        try {
            HttpHeaders header = new HttpHeaders();

            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 파일 업로드 후 다운로드
     * @param userAgent
     * @param fileName
     * @param request
     * @return resource, headers
     * @throws UnsupportedEncodingException
     */
    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName, HttpServletRequest request) throws UnsupportedEncodingException {

        Resource resource = new FileSystemResource(URLDecoder.decode(fileName, "UTF-8"));

        if (!resource.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String resourceName = resource.getFilename();
        // remove UUID
        String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);

        HttpHeaders headers = new HttpHeaders();
        try {

            boolean checkIE = (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1);
            String downloadName = null;

            if (checkIE) {
//                downloadName = URLEncoder.encode(resourceOriginalName, "UTF8").replaceAll("\\+", " ");
                downloadName = resourceOriginalName.replaceAll("\\+", " ");
            } else {
                downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
            }
            headers.add("Content-Disposition", "attachment; filename=" + downloadName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    /**
     * 업로드폴더 파일 삭제
     * @param fileName
     * @param type
     * @return String "deleted"
     */
    @PostMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(String fileName, String type) {

        log.info("deleteFile: " + fileName);
        File file;
        try {
            file = new File(URLDecoder.decode(fileName, "UTF-8"));

            file.delete();

            if (type.equals("image")) {

                String largeFileName = file.getAbsolutePath().replace("s_", "");
                log.info("largeFileName: " + largeFileName);
                file = new File(largeFileName);

                file.delete();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }

}
