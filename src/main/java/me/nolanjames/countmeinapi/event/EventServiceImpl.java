package me.nolanjames.countmeinapi.event;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public EventResponse createEvent(EventRequest request) {
        log.info("Request to create event with object: {}", request);

        Event newEvent = eventMapper.toEvent(request);
        newEvent.setPublicId(UUID.randomUUID().toString());
        eventRepository.save(newEvent);

        log.info("Event saved with object: {}", request);
        return eventMapper.toResponse(newEvent);
    }

    @Override
    public List<EventResponse> getEvents() {
        log.info("Request for all events");
        List<Event> events = eventRepository.findAll();

        return events.stream()
                .map(eventMapper::toResponse)
                .toList();
    }

    @Override
    public EventResponse getEvent(String eventId) {
        log.info("Request to get event with public ID: {}", eventId);

        return eventRepository.getEventByPublicId(eventId)
                .map(eventMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("No Event found with ID: " + eventId));
    }

    @Override
    @Transactional
    public EventResponse updateEvent(String eventId, EventRequest request) {
        log.info("Request to update event with public ID: {} and object: {}", eventId, request);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Event event = eventRepository.getEventByPublicId(eventId)
                .orElseThrow(() -> new EntityNotFoundException("No Event found with ID: " + eventId));

        event.setName(request.name());
        event.setDescription(request.description());
        event.setDate(LocalDate.parse(request.date(), formatter));
        event.setImageUrl(request.imageUrl());

        eventRepository.save(event);
        log.info("Event updated with public ID: {} ", eventId);

        return eventMapper.toResponse(event);
    }

    @Override
    public void deleteEvent(String eventId) {
        log.info("Request to delete event with public ID: {}", eventId);

        Event event = eventRepository.getEventByPublicId(eventId)
                .orElseThrow(() -> new EntityNotFoundException("No Event found with ID: " + eventId));
        eventRepository.delete(event);
        log.info("Event deleted with public ID: {}", eventId);
    }
}
