package me.nolanjames.countmeinapi.event;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public EventResponse createEvent(EventRequest request) {
        Event newEvent = eventMapper.toEvent(request);
        newEvent.setPublicId(UUID.randomUUID().toString());
        eventRepository.save(newEvent);

        return eventMapper.toResponse(newEvent);
    }

    @Override
    public List<EventResponse> getEvents() {
        List<Event> events = eventRepository.findAll();

        return events.stream()
                .map(eventMapper::toResponse)
                .toList();

    }

    @Override
    public EventResponse getEvent(String eventId) {
        Optional<Event> event = eventRepository.getEventByPublicId(eventId);

        return event.map(eventMapper::toResponse).orElse(null);
    }

    @Override
    @Transactional
    public EventResponse updateEvent(String eventId, EventRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Optional<Event> event = eventRepository.getEventByPublicId(eventId);
        if (event.isPresent()) {
            Event eventToUpdate = event.get();
            eventToUpdate.setName(request.name());
            eventToUpdate.setDescription(request.description());
            eventToUpdate.setDate(LocalDate.parse(request.date(), formatter));

            eventRepository.save(eventToUpdate);

            return eventMapper.toResponse(eventToUpdate);
        }
        // Todo: update when add error handling
        return null;
    }
}
