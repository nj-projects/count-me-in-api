package me.nolanjames.countmeinapi.event;

import java.util.List;

public interface EventService {
    EventResponse createEvent(EventRequest request);

    List<EventResponse> getEvents();

    EventResponse getEvent(String eventId);
}
