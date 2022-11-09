package com.example.board_springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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
    
    
    @RequestMapping("download")
	public void fileDownload(HttpServletRequest req, HttpServletResponse res) {
		String filename = req.getParameter("fileName");
		String readFilename = "";
		System.out.println("filename: "+filename);
		
			try {
				String browser = req.getHeader("User-Agent"); // User-Agent 정보 꼭 필요!
				// 파일 인코딩
				if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")) {
				filename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
				
			} else {
					filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
			}
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
			}
			readFilename = "I:\\upload\\" + filename;
			System.out.println("readFilename: "+ readFilename);
			File file = new File(readFilename);
			if(!file.exists()) {
				return ;
		}
		
		// 파일명 지정
		res.setContentType("application/octer-stream");
		res.setHeader("Content-Transfer-Encoding", "binary;");
		res.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");  // Content-Disposition, 그리고 다운로드할 파일명 적어줘야!
		
		try {
			OutputStream os = res.getOutputStream();
			FileInputStream fis = new FileInputStream(readFilename);
			
			int ncount = 0;
			byte[] bytes = new byte[512];
			
			while((ncount = fis.read(bytes)) != -1) {
				os.write(bytes, 0, ncount);
			}
			fis.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
