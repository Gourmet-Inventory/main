package project.gourmetinventoryproject.service;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import project.gourmetinventoryproject.domain.Prato;
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

    public String uploadFile(MultipartFile file, Prato prato) throws IOException {
        String key = file.getOriginalFilename();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        prato.setFoto("https://gourmet-inventory-bucket.s3.amazonaws.com/" + key);
        pratoRepository.save(prato);
        return key;
    }


    public String updateFile(String existingKey, MultipartFile file, Prato prato) throws IOException {
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