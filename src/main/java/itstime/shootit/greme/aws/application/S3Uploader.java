package itstime.shootit.greme.aws.application;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import itstime.shootit.greme.user.ui.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    private final UserRepository userRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // MultipartFile을 전달받아 File로 전환한 후, S3에 업로드
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        if (dirName.equals("user")) {
            File uploadFile = convertUser(multipartFile)
                    .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
            return upload(uploadFile, dirName);

        } else if (dirName.equals("diary")) {
            File uploadFile = convertDiary(multipartFile)
                    .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
            return upload(uploadFile, dirName);

        }
        return "success : MultipartFile -> File convert";
    }

    // S3로 파일 업로드하기
    public String upload(File uploadFile, String dirName){
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);  // s3로 업로드
        removeNewFile(uploadFile);  // 로컬에 생성된 File 삭제
        return uploadImageUrl;  // 업로드된 파일의 S3 ULR 주소 반환
    }

    // S3로 업로드
    public String putS3(File uploadFile, String fileName){
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장 이미지 지우기
    public void removeNewFile(File targetFile){
        if (targetFile.delete()){
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일을 삭제하지 못했습니다.");
        }
    }

    // 로컬에 파일 업로드하기. convert()메소드에서 로컬 프로젝트에 사진 파일이 생성되지만, removeNewFile()을 통해서 바로 지운다
    private Optional<File> convertUser(MultipartFile file) throws IOException{
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()){  // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)){
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    private Optional<File> convertDiary(MultipartFile file) throws IOException{
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()){  // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)){
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }


}
