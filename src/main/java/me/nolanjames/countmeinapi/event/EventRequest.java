package me.nolanjames.countmeinapi.event;

public record EventRequest(
        String name,
        String description,
        String date
) {
}
