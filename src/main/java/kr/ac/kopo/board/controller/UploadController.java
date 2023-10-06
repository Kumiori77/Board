package kr.ac.kopo.board.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {

    @Value("${kr.ac.kopo.upload.path}")
    private String uploadPath;

    @PostMapping("/uploadAjax")
    public void uploadFile(MultipartFile[] uploadFiles) {

        // 들어온 파일들의 이름 출력
        for(MultipartFile uploadFile : uploadFiles) {

            // 이미지 파일인지 확인
            if (uploadFile.getContentType().startsWith("image") == false) {
                log.warn("this file is not image type");
                return;
            }

            String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(
                    originalName.lastIndexOf("\\") + 1);

            log.info("File Name : " + fileName);

            // 날짜 폴더 생성
            String folderPath = makeFolder();

            // 고유한  UUID 생성
            String uuid = UUID.randomUUID().toString();

            // 파일이름과 저장할 경로
            // (업로드 경로/오늘날짜 경로/UUID_파일 이름)
            String saveName = uploadPath + File.separator + folderPath +
                    File.separator + uuid + "_" + fileName;

            // 저장 경로
            Path savePath = Paths.get(saveName);

            try {
                uploadFile.transferTo(savePath);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // 오늘 날짜 폴더 생성하고 경로 반환하는 메소드
    private String makeFolder() {
        String str = LocalDate.now().
                format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/", File.separator);

        // 폴더 생성
        File uploadPathFolder = new File(uploadPath, folderPath);
        if (uploadPathFolder.exists() == false) {
            uploadPathFolder.mkdirs(); // 오늘날짜 폴더가 없으면 생성
        }

        return folderPath;
    }

}
