package controller;

import com.amazonaws.auth.policy.Resource;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.S3Service;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1")
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

}
