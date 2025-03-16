package gr.hua.it21774.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.http.Method;
import io.minio.messages.Item;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class MinioService {

    private final MinioClient minioClient;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public void uploadRequestFile(MultipartFile file, String bucketName, String folderName, String fileName)
            throws Exception {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object("/" + folderName + "/" + fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build());
    }

    public String getSignedUrl(String bucketName, String folderName, String fileName) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object("/" + folderName + "/" + fileName)
                        .expiry(60, TimeUnit.SECONDS)
                        .build());
    }

    public void uploadMultipleFiles(List<MultipartFile> files, String bucketName, String folderName) throws Exception {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object("/" + folderName + "/" + fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
        }
    }

    public void deleteAllFilesInFolder(String bucketName, String folderName) throws Exception {
        Iterable<Result<Item>> objects = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .prefix(folderName)
                        .recursive(true)
                        .build());

        Iterator<Result<Item>> iterator = objects.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next().get();
            String filePath = item.objectName();

            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filePath)
                            .build());
        }
    }

    public void deleteFiles(String bucketName, String folderName, Set<String> fileNames) throws Exception {
        for (String fileName : fileNames) {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(folderName + "/" + fileName)
                            .build());
        }
    }

}
