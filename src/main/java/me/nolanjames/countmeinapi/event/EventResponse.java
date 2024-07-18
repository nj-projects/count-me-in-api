package me.nolanjames.countmeinapi.event;

public record EventResponse(
        String name,
        String description,
        String date,
        String imageUrl,
        String publicId
) {
}
