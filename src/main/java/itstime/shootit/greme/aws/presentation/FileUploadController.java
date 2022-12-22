package itstime.shootit.greme.aws.presentation;

import itstime.shootit.greme.aws.application.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class FileUploadController {

    private final S3Uploader s3Uploader;

    @PostMapping("/oauth2/images")
    public List<String> uploadUser(@RequestParam("images") List<MultipartFile> multipartFile) throws IOException {
        return s3Uploader.uploadFile(multipartFile);
    }

}
