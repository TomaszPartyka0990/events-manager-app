package pl.sda.partyka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.partyka.domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
