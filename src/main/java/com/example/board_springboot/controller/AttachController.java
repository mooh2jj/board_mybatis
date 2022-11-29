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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class AttachController {

    @Value("${file.upload.folder}")
    private String uploadFolder;

    /**
     * ajax방식
     * (다중)파일업로드
     * @param uploadFile (다중)파일
     * @return 첨부파일 리스트
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/uploadAjaxAction")
    public ResponseEntity<List<AttachVO>> uploadAjaxPost(MultipartFile[] uploadFile) {

        List<AttachVO> attachList = new ArrayList<>();
        // 년/월/일 폴더 생성
        String uploadFolderPath = getFolder();
        log.info("uploadFolderPath: {}", uploadFolderPath);

        File uploadPath = new File(uploadFolder, uploadFolderPath);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

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
                attachVO.setUploadPath(uploadFolderPath);

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
     * 년/월/일 폴더 생성
     * @return 년/월/일 폴더
     */
    private String getFolder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);
        return str.replace("-", File.separator);
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
     * @param fileName 파일이름
     * @return 바이트타입으로 변환한 파일
     */
    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName) {

        log.info("fileName: " + fileName);

        File file = new File(uploadFolder + File.separator + fileName);
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
     * @param userAgent 해당 브라우저를 알기위한 파라미터
     * @param fileName 파일이름
     * @return resource, headers
     */
    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName) {
        log.info("downloadFile run................");
        Resource resource = new FileSystemResource(uploadFolder + File.separator + fileName);

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
     * @param fileName 파일이름
     * @param type 이미지 or 그외
     * @return String "deleted"
     */
    @PreAuthorize("isAuthenticated()")
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
