package efgroup.EckoOfTheFox_backend.dislike;

import efgroup.EckoOfTheFox_backend.comment.Comment;
import efgroup.EckoOfTheFox_backend.comment.CommentRepository;
import efgroup.EckoOfTheFox_backend.opinion.Opinion;
import efgroup.EckoOfTheFox_backend.opinion.OpinionRepository;
import efgroup.EckoOfTheFox_backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DislikeService {
    private final DislikeRepository dislikeRepository;
    private final OpinionRepository opinionRepository;
    private final CommentRepository commentRepository;

    //dislike opinion
    public Dislike dislikeOpinion(User user, int opinionID) throws IllegalAccessException, NoSuchElementException {
        Opinion opinion = opinionRepository.findById(opinionID).orElseThrow(() -> new NoSuchElementException("Opinion not found."));

        if (!canComment(user, opinion.getDislikes())) {
            throw new IllegalAccessException("You have already liked this opinion");
        }

        return dislikeRepository.save(new Dislike(UUID.randomUUID(), new Date(), null, opinion, user));
    }

    //dislike comment
    public Dislike dislikeComment(User user, String commentID) throws IllegalAccessException, NoSuchElementException {
        Comment comment = commentRepository.findById(UUID.fromString(commentID)).orElseThrow(() -> new NoSuchElementException("Comment not found."));

        if (!canComment(user, comment.getDislikes())) {
            throw new IllegalAccessException("You have already liked this opinion");
        }

        return dislikeRepository.save(new Dislike(UUID.randomUUID(), new Date(), comment, null, user));
    }

    //delete dislike
    public void deleteDislike(User user, String likeID) throws IllegalAccessException, NoSuchElementException {
        Dislike dislike = dislikeRepository.findById(UUID.fromString(likeID)).orElseThrow(() -> new NoSuchElementException("Dislike not found"));

        if (user != dislike.getUser()) {
            throw new IllegalAccessException("You are not authorized to remove this dislike");
        }

        dislikeRepository.delete(dislike);
    }

    private boolean canComment(User user, List<Dislike> listOfDislikes) {
        for (int i = 0; i < listOfDislikes.size(); i++) {
            if (listOfDislikes.get(i).getUser() == user) {
                return false;
            }
        }
        return true;

    }
}
