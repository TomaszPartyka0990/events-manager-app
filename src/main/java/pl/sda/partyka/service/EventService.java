package pl.sda.partyka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.partyka.domain.Event;
import pl.sda.partyka.domain.User;
import pl.sda.partyka.dto.EventCreateRequest;
import pl.sda.partyka.repository.EventRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepo;

    public Event addEvent(EventCreateRequest eventToAdd, User author){
        Event event = Event.builder()
                .title(eventToAdd.getTitle())
                .startingDate(LocalDate.parse(eventToAdd.getStartingDate()))
                .endingDate(LocalDate.parse(eventToAdd.getEndingDate()))
                .description(eventToAdd.getDescription())
                .eventAuthor(author)
                .build();
        return eventRepo.save(event);
    }
}
