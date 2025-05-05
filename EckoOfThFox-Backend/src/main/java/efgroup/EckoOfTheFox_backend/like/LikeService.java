package efgroup.EckoOfTheFox_backend.like;

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
public class LikeService {
    private final LikeRepository likeRepository;
    private final OpinionRepository opinionRepository;
    private final CommentRepository commentRepository;

    //like opinion
    public Like likeOpinion(User user, int opinionID) throws IllegalAccessException, NoSuchElementException {
        Opinion opinion = opinionRepository.findById(opinionID).orElseThrow(() -> new NoSuchElementException("Opinion not found."));

        if (!canComment(user, opinion.getLikes())) {
            throw new IllegalAccessException("You have already liked this opinion");
        }

        return likeRepository.save(new Like(UUID.randomUUID(), new Date(), null, opinion, user));
    }

    //like comment
    public Like likeComment(User user, String commentID) throws IllegalAccessException, NoSuchElementException {
        Comment comment = commentRepository.findById(UUID.fromString(commentID)).orElseThrow(() -> new NoSuchElementException("Comment not found."));

        if (!canComment(user, comment.getLikes())) {
            throw new IllegalAccessException("You have already liked this opinion");
        }

        return likeRepository.save(new Like(UUID.randomUUID(), new Date(), comment, null, user));
    }

    //delete like
    public void deleteLike(User user, String likeID) throws IllegalAccessException, NoSuchElementException {
        Like like = likeRepository.findById(UUID.fromString(likeID)).orElseThrow(() -> new NoSuchElementException("Dislike not found"));

        if (user != like.getUser()) {
            throw new IllegalAccessException("You are not authorized to remove this like");
        }

        likeRepository.delete(like);
    }

    private boolean canComment(User user, List<Like> listOfLikes) {
        for (int i = 0; i < listOfLikes.size(); i++) {
            if (listOfLikes.get(i).getUser() == user) {
                return false;
            }
        }
        return true;

    }
}
