package org.blacksmith.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class ImageService {

    private final ImageStorageProperties properties;
    private final ImageMetadataRepository repository;
    private final LocalImageStorageService storageService;

    public ImageService(ImageStorageProperties properties, ImageMetadataRepository repository, LocalImageStorageService storageService) {
        this.properties = properties;
        this.repository = repository;
        this.storageService = storageService;
    }

    public ImageMetadata uploadImage(MultipartFile file, String ownerId) throws IOException {
        validateImage(file);

        String storagePath;
        try(InputStream inputStream = file.getInputStream()) {
            storagePath = storageService.storeFile(inputStream, file.getOriginalFilename());
        }

        ImageMetadata metadata = new ImageMetadata(
                file.getOriginalFilename(),
                storagePath,
                file.getContentType(),
                ownerId,
                file.getSize(),
                Instant.now(),
                ObjectId.get()
        );

        return repository.save(metadata);
    }

    public Resource getImageResource(String imageId) throws IOException {
        ImageMetadata metadata = getImageMetadata(imageId);
        return storageService.getFileResource(metadata.storedName());
    }

    public ImageMetadata getImageMetadata(String imageId) throws IOException {
        ObjectId objectId = new ObjectId(imageId);
        return repository.findById(objectId).orElseThrow(
                () -> new FileNotFoundException("File not found.")
        );
    }

    private void validateImage(MultipartFile file) {
        if(file.isEmpty()) {
            throw new IllegalArgumentException("File is empty.");
        }

        String mimeType = file.getContentType();
        if(mimeType == null || !properties.allowedMimeTypes().contains(mimeType)) {
            throw new IllegalArgumentException("Invalid mime type.");
        }
    }
}
