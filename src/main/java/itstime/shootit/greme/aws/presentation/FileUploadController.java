package itstime.shootit.greme.aws.presentation;

import itstime.shootit.greme.aws.application.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class FileUploadController {

    private final S3Uploader s3Uploader;

    @PostMapping("/oauth2/images")
    public String uploadUser(@RequestParam("images")MultipartFile multipartFile) throws IOException {
        s3Uploader.upload(multipartFile, "user");
        return "userImage";
    }

    @PostMapping("/oauth2/diary")
    public String uploadDiary(@RequestParam("images")MultipartFile multipartFile) throws IOException {
        s3Uploader.upload(multipartFile, "diary");
        return "diaryImage";
    }
}
