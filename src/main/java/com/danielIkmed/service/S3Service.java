package com.danielIkmed.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
@Log4j2
public class S3Service {
    private AmazonS3 s3client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public List<S3ObjectSummary> lisFiles(){
        ObjectListing objectListing = s3client.listObjects(bucketName);
        return objectListing.getObjectSummaries();
    }

    public S3Service(AmazonS3 s3client){
        this.s3client = s3client;
    }

    public void uploadFile(String keyName, MultipartFile file)throws IOException{
        var putObjectResult = s3client.putObject(bucketName, keyName, file.getInputStream(), null);
        log.info(putObjectResult.getMetadata());
    }

    public InputStreamResource downloadFile(String fileName){

        return new InputStreamResource(getFile(fileName).getObjectContent());
    }

    public InputStreamResource viewImageFile(String fileNmame){
         return  new InputStreamResource(getFile(fileNmame).getObjectContent());
    }

    public InputStreamResource viewVideoFile(String fileNmame){
        return  new InputStreamResource(getFile(fileNmame).getObjectContent());
    }

    public S3Object getFile(String keyName){
        return  s3client.getObject(bucketName,keyName);
    }
}
