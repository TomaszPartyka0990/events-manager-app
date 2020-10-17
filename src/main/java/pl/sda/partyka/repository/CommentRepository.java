package pl.sda.partyka.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.partyka.domain.Comment;
import pl.sda.partyka.domain.Event;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByConnectedEvent(Event event, Pageable pageable);
}
