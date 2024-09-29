package project.gourmetinventoryproject.service;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;

import jakarta.annotation.PostConstruct;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.exception.AWSInvalid;
import project.gourmetinventoryproject.repository.PratoRepository;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.internal.signing.DefaultS3Presigner;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Service
public class S3Service {

    private S3Client s3Client;

    private final String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
    private final String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
    private final String sessionToken = System.getenv("AWS_SESSION_TOKEN");
    private final String bucketName = System.getenv("AWS_S3_BUCKET");
    private final String region = System.getenv("AWS_REGION");

    @Autowired
    private PratoRepository pratoRepository;

    @PostConstruct
    public void init() {
        System.out.println(System.getenv("AWS_SESSION_TOKEN"));
        if (accessKey == null || secretKey == null || sessionToken == null || bucketName == null || region == null) {
            System.out.println("Credenciais vazias");
        }
        else {
            AwsSessionCredentials awsCreds = AwsSessionCredentials.create(accessKey, secretKey, sessionToken);
            this.s3Client = S3Client.builder()
                    .region(Region.of(region))
                    .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                    .build();
        }
    }

    public String uploadFile(MultipartFile file,Prato prato) throws IOException {
        String key = file.getOriginalFilename();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        prato.setFoto("https://gourmet-inventory-bucket.s3.amazonaws.com/" + file.getOriginalFilename());
        pratoRepository.save(prato);
        return key;
    }

    public void deleteFile(String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }

    public String updateFile(String existingKey, MultipartFile file, Prato prato) throws IOException {
        deleteFile(existingKey);
        return uploadFile(file,prato);
    }
}
