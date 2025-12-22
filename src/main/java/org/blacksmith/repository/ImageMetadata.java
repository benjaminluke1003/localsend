package org.blacksmith.repository;

import java.time.Instant;

public record ImageMetadata(
        String originalName,
        String storedName,
        String mimeType,
        String ownerId,
        long size,
        Instant createdAt,
        ObjectId id
) {
}
