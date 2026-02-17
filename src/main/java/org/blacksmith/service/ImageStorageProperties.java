package org.blacksmith.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@ConfigurationProperties(prefix = "app.image-storage")
@Component
public record ImageStorageProperties(
        String basePath,
        Set<String> allowedMimeTypes
) {
    public ImageStorageProperties() {
        this(
                "/home/blacksmithoffire/Pictures/localsendPictures",
                Set.of(
                        "image/jpeg",
                        "image/png",
                        "image/webp",
                        "image/gif"
                )
        );
    }
}
