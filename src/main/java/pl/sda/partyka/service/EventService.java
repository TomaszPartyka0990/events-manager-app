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
import pl.sda.partyka.error.EventNotFoundException;
import pl.sda.partyka.repository.EventRepository;
import pl.sda.partyka.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static pl.sda.partyka.utils.Utils.EVENT_START_DATE_FIELD_NAME;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepo;
    private final UserRepository userRepository;

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

    public EventTableView getEventViewById(Long eventId) throws EventNotFoundException{
        Optional<Event> eventById = eventRepo.findById(eventId);
        if (eventById.isPresent()){
            return mapEventToEventTableView(eventById.get());
        } else {
            throw new EventNotFoundException("Event not found");
        }
    }

    public Event getEventById(Long eventId) throws EventNotFoundException{
        Optional<Event> eventById = eventRepo.findById(eventId);
        if (eventById.isPresent()){
            return eventById.get();
        } else {
            throw new EventNotFoundException("Event not found");
        }
    }

    public void signToEvent(Event event, User user){
        List<User> signedUsers = event.getSignedUsers();
        List<Event> eventsSignedTo = user.getEventsSignedTo();
        if (!signedUsers.contains(user) && !eventsSignedTo.contains(event)) {
            signedUsers.add(user);
            eventRepo.save(event);
            eventsSignedTo.add(event);
            userRepository.save(user);
        }
    }

    public void unsignFromEvent(Event event, User user){
        List<User> signedUsers = event.getSignedUsers();
        List<Event> eventsSignedTo = user.getEventsSignedTo();
        signedUsers.remove(user);
        eventsSignedTo.remove(event);
        eventRepo.save(event);
        userRepository.save(user);
    }

    public boolean isUserAlreadySigned(Event event, User user){
        if (event == null || user == null){
            return false;
        }
        List<User> signedUsers = event.getSignedUsers();
        return signedUsers.contains(user);
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
