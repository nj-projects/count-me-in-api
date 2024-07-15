package me.nolanjames.countmeinapi.event;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class EventMapper {
    public Event toEvent(EventRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return Event.builder()
                .name(request.name())
                .description(request.description())
                .date(LocalDate.parse(request.date(), formatter))
                .build();
    }

    public EventResponse toResponse(Event newEvent) {
        return new EventResponse(
                newEvent.getName(),
                newEvent.getDescription(),
                newEvent.getDate().toString()
        );
    }
}
