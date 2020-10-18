package pl.sda.partyka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.sda.partyka.domain.Comment;
import pl.sda.partyka.domain.Event;
import pl.sda.partyka.domain.User;
import pl.sda.partyka.dto.CommentCreateRequest;
import pl.sda.partyka.repository.CommentRepository;

import static pl.sda.partyka.utils.Utils.COMMENT_CREATE_DATE_FIELD_NAME;

@Service
@RequiredArgsConstructor
public class CommentService{

    private final CommentRepository commentRepo;

    public Comment addNewComment(CommentCreateRequest commentToAdd, User author, Event event){
        Comment comment = Comment.builder()
                .content(commentToAdd.getContent())
                .commentAuthor(author)
                .connectedEvent(event)
                .build();
        return commentRepo.save(comment);
    }

    public Page<Comment> showAllCommentsForSpecifiedEventOrderedByCreationDateDesc(Event event, Integer page){
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.Direction.DESC, COMMENT_CREATE_DATE_FIELD_NAME);
        return commentRepo.findAllByConnectedEvent(event, pageRequest);
    }
}
