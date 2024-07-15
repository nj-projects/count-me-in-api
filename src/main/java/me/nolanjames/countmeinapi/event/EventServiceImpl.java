package me.nolanjames.countmeinapi.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public EventResponse createEvent(EventRequest request) {
        Event newEvent = eventMapper.toEvent(request);
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
}
