package pl.sda.partyka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.sda.partyka.domain.Event;
import pl.sda.partyka.domain.User;
import pl.sda.partyka.dto.EventCreateRequest;
import pl.sda.partyka.dto.EventTableView;
import pl.sda.partyka.error.EventCreationDateException;
import pl.sda.partyka.repository.EventRepository;

import java.time.LocalDate;

import static pl.sda.partyka.utils.Utils.EVENT_START_DATE_FIELD_NAME;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepo;

    public Event addEvent(EventCreateRequest eventToAdd, User author) throws EventCreationDateException {
        LocalDate eventStartingDate = LocalDate.parse(eventToAdd.getStartingDate());
        LocalDate eventEndingDate = LocalDate.parse(eventToAdd.getEndingDate());
        LocalDate today = LocalDate.now();
        if(today.isAfter(eventStartingDate)){
            throw new EventCreationDateException("Event starting date can't be a past date");
        }
        if(today.isAfter(eventEndingDate)){
            throw new EventCreationDateException("Event starting date can't be a past date");
        }
        if(eventStartingDate.isAfter(eventEndingDate)){
            throw new EventCreationDateException("Event starting date can't be later than ending date");
        }
        Event event = Event.builder()
                .title(eventToAdd.getTitle())
                .startingDate(eventStartingDate)
                .endingDate(eventEndingDate)
                .description(eventToAdd.getDescription())
                .eventAuthor(author)
                .build();
        return eventRepo.save(event);
    }

    public Page<EventTableView> showAllEventsFromClosestOne(Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.Direction.ASC, EVENT_START_DATE_FIELD_NAME);
        return eventRepo.findAllByStartingDateIsAfter(LocalDate.now().minusDays(1), pageRequest).map(this::mapEventToEventTableView);
    }

    public Page<EventTableView> searchForEventsByTitleSortedAscendingByStartingDate(String title, Integer range, Integer page){
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.Direction.ASC, EVENT_START_DATE_FIELD_NAME);
        if (range != 0 && range != 1){
            return eventRepo.findAllByTitleContainsIgnoreCase(title, pageRequest).map(this::mapEventToEventTableView);
        } else {
            LocalDate date;
            if (range == 1) {
                date = LocalDate.now().minusDays(1);
            } else {
                date = LocalDate.now();
            }
            return eventRepo.findAllByTitleContainsIgnoreCaseAndStartingDateIsAfter(title, date, pageRequest).map(this::mapEventToEventTableView);
        }
    }

    private EventTableView mapEventToEventTableView(Event event){
        String eventTableViewDescription;
        String description = event.getDescription();
        if (description.length() > 50){
            eventTableViewDescription = description.substring(0, 49);
        } else {
            eventTableViewDescription = description;
        }
        return EventTableView.of(event.getEventId(), event.getTitle(), event.getStartingDate(), event.getEndingDate(), eventTableViewDescription);
    }
}
