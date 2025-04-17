package efgroup.EckoOfTheFox_backend.comment;

import efgroup.EckoOfTheFox_backend.opinion.Opinion;
import efgroup.EckoOfTheFox_backend.opinion.OpinionDTO;
import efgroup.EckoOfTheFox_backend.opinion.OpinionRepository;
import efgroup.EckoOfTheFox_backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class CommentService {
    private final CommentRepository commentRepository;
    private final OpinionRepository opinionRepository;

    //get
    /**
     * behövs det? hämtas i samband med opinions och users...
     */

    //post

    public Comment commentOpinion(User user, UUID opinionID, CommentDTO commentDTO) throws NoSuchElementException {
        Opinion opinion = opinionRepository.findById(opinionID).orElseThrow(() -> new NoSuchElementException("Opinion not found."));
        Comment comment = new Comment(UUID.randomUUID(), new Date(), commentDTO.getCommentText(), user, opinion);
        return commentRepository.save(comment);
    }

    //update
    public Comment updateComment(User user, CommentDTO commentDTO) throws NoSuchElementException, IllegalAccessException {
            Comment comment = commentRepository.findById(commentDTO.getCommentID()).orElseThrow(() -> new NoSuchElementException("Opinion not found."));
            if (comment.getAuthor().getUserID() != user.getUserID()) {
                throw new IllegalAccessException("You are nor authorized to edit this comment");
            }
            comment.setCommentText(commentDTO.getCommentText());
            return commentRepository.save(comment);
    }

    //delete
    public void deleteComment(User user, String commentID) throws NoSuchElementException, IllegalAccessException {
        Comment comment = commentRepository.findById(UUID.fromString(commentID)).orElseThrow(() -> new NoSuchElementException("Opinion not found."));
        if (comment.getAuthor().getUserID() != user.getUserID()) {
            throw new IllegalAccessException("You are nor authorized to delete this comment");
        }

        commentRepository.delete(comment);
    }

}
