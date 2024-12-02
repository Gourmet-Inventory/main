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

    private final String accessKey = "ASIARMPMV546JBRY5PEC";
    private final String secretKey = "vWYk6zlBYHpWdXkKY7w0pmr156cEO7dJJ6PwK8n0";
    private final String sessionToken = "IQoJb3JpZ2luX2VjEB4aCXVzLXdlc3QtMiJGMEQCIEl683jS1swSa9sJGWrIq2rs71Rj63MHxtOU57DwVUl0AiB+iYe4t3H6GN78dX5mz6XUY69L9K/Z7VfSxFW1VJuszCrAAgjH//////////8BEAAaDDA5NTUyMjUxNjc5NiIME70ph8FDO8igi1PRKpQCBn24GIHdjyyJlHfAEpyHGrbwyVkwD/OngCaUgLBa5BFGCq3cw7zJkLINN08B7oAF6ZpterVrofUFpI1N1FyGDHsMo6yrpk6ec+XAcPI3RUI6PVYzfmz8zV+1D5bLiY4IJnyFvw7u1w+nOqYLY+Hbqq+gT0koyPcNGruW1471NqDrcTBAy3mS8HDmomi6jnGQCe6P50X3tIbZ5VC1iIdxCw+Xh8rQYQ8R39cO4k15v2r7Kukje49bMsGy1xkO8oX+6SGG2V7OIuW3pgWvDWIhoFiG3d8vvRQ18IOt80J5UBidLjg+RhjfbYRZUlRFNo1trCZ8YlilgI+ut19N3Bd4DpbNuj9pamWVnJ0PeKMIPoXVHg8NMIbhuLoGOp4B72PlpOxko7CGKcUYm4WZ6KNpy13V8xkREr8L3NHWdbnDTWZHq11z/oIr2sPbOWXkoRDDH9iB0PqbfDS18oSzDubWgL7gg47gYMTgFs6VUmCfQF/0/yOZkSTlVrdSXI+0YxDJkaYYtPh5s61ulCYOoPFRKT357/95ohklzLHDqaqyzzbFLU40RHuGQi4/aSru4+QSDiP9hlY/OsAZAPs=" ;
    private final String bucketName = "gourmet-s3";
    private final String region = "us-east-1";

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
