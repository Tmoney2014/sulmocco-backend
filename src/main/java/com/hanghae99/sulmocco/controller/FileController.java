package com.hanghae99.sulmocco.controller;

import com.hanghae99.sulmocco.dto.UploadResponseDto;
import com.hanghae99.sulmocco.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final S3Service s3Service;

    // 이미지 업로드 V1 - List<UploadResponseDto>
    @PostMapping("/api/images")
    public ResponseEntity<?> uploadImageV1(@RequestPart(value = "file", required = false) List<MultipartFile> files) throws IOException {
        return s3Service.uploadImageV1(files);
    }

//    // 이미지 업로드 V2 - List<String>
//    @PostMapping("/api/images/uploadV2")
//    public List<String> uploadImageV2(@RequestPart(value = "file", required = false) List<MultipartFile> files) throws IOException {
//        return s3Service.uploadImageV2(files);
//    }

    // 이미지 삭제
    @DeleteMapping("/api/images")
    public List<String> deleteImages(@RequestBody List<String> filenames){
        return s3Service.deleteImages(filenames);
    }
}
