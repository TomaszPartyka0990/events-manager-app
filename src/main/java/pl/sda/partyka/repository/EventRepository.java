package pl.sda.partyka.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.partyka.domain.Event;

import java.time.LocalDate;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findAllByStartingDateIsAfter(LocalDate date, Pageable pageable);
    Page<Event> findAllByTitleContainsIgnoreCase(String titleFragment, Pageable pageable);
    Page<Event> findAllByTitleContainsIgnoreCaseAndStartingDateIsAfter(String titleFragment, LocalDate date, Pageable pageable);
}
