package com.danielIkmed.controller;



import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.danielIkmed.service.S3Service;

import java.util.List;


//ToDo Criação de cursos/ iniciar implementação de front end com react

@RestController
@RequestMapping("/s3")
public class S3Controller {
    private final S3Service s3Service;

    public S3Controller(S3Service s3Service){
        this.s3Service = s3Service;
    }

    @GetMapping("/list")
    public ResponseEntity<List<S3ObjectSummary>> listFiles(){
        List<S3ObjectSummary> files = s3Service.lisFiles();

        return ResponseEntity.ok(files);

    }
    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String uploadFile(@RequestParam("file") MultipartFile file)throws Exception{
        s3Service.uploadFile(file.getOriginalFilename(), file);
        return "file uploaded";
    }

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
    @GetMapping("/download/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String fileName){
        InputStreamResource file = s3Service.downloadFile(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

    @GetMapping("/view/image/{fileName}")
    public ResponseEntity<InputStreamResource> viewImageFile(@PathVariable String fileName){
        InputStreamResource file = s3Service.viewImageFile(fileName);
        return ResponseEntity.ok()
                         .contentType(MediaType.IMAGE_PNG)
                         .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+fileName+"\"")
                         .body(file);
    }

    @GetMapping("/view/video/{fileName}")
    public ResponseEntity<InputStreamResource> viewVideoFile(@PathVariable String fileName){
        InputStreamResource file = s3Service.viewVideoFile(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("video/mp4"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+fileName+"\"")
                .body(file);
    }



}
