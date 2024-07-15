package me.nolanjames.countmeinapi.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("""
            SELECT event
            FROM Event event
            WHERE event.publicId = :eventId
            """)
    Optional<Event> getEventByPublicId(String eventId);
}
