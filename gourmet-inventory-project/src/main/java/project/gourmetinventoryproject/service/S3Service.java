package project.gourmetinventoryproject.service;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.dto.prato.PratoConsultaDto;
import project.gourmetinventoryproject.repository.PratoRepository;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Service
public class S3Service {

    private S3Client s3Client;
    private S3Presigner s3Presigner;

    private final String accessKey = System.getenv("aws_access_key_id");
    private final String secretKey = System.getenv("aws_secret_access_key");
    private final String sessionToken = System.getenv("aws_session_token");
    private final String bucketName = System.getenv("aws_s3_bucket");
    private final String region = System.getenv("aws_region");

    @Autowired
    private PratoRepository pratoRepository;
    protected Boolean aws = true;

    @PostConstruct
    public void init() {
        if (aws){
            if (accessKey == null || secretKey == null || sessionToken == null || bucketName == null || region == null) {
                System.out.println("Credenciais vazias");
            } else {
                AwsSessionCredentials awsCreds = AwsSessionCredentials.create(accessKey, secretKey, sessionToken);
                AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(awsCreds);

                this.s3Client = S3Client.builder()
                        .region(Region.of(region))
                        .credentialsProvider(credentialsProvider)
                        .build();

                this.s3Presigner = S3Presigner.builder()
                        .region(Region.of(region))
                        .credentialsProvider(credentialsProvider)
                        .build();
            }
        }
    }

    public String generatePresignedUrl(String key) {
        Duration duration = Duration.ofDays(1); // Tempo de expiração da URL
        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(r -> r
                .signatureDuration(duration)
                .getObjectRequest(g -> g
                        .bucket(bucketName)
                        .key(key)
                        .responseCacheControl("no-cache") // Para evitar cache
                        .responseContentType("image/jpeg") // Define o tipo de conteúdo
                        .responseContentDisposition("inline"))); // Define como inline para exibição

        return presignedRequest.url().toString();
    }

    public Prato uploadFile(MultipartFile file, Prato prato) throws IOException {
        String key = file.getOriginalFilename();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        prato.setFoto("https://"+bucketName+".s3.amazonaws.com/" + key);

        return pratoRepository.save(prato);
    }


    public Prato updateFile(String existingKey, MultipartFile file, Prato prato) throws IOException {
        deleteFile(existingKey);
        return uploadFile(file,prato);
    }

    public void deleteFile(String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }
}