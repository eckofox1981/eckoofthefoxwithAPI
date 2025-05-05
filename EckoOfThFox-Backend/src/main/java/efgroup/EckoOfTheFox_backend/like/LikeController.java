package efgroup.EckoOfTheFox_backend.like;

import efgroup.EckoOfTheFox_backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {
    private final LikeService likeService;

    //get
    /**
     * automatic when getting user, opinion, comment
     */
    //like opinion
    @PostMapping("/opinion")
    public ResponseEntity<?> likeOpinion(@AuthenticationPrincipal User user, @PathVariable int opinionID) {
        try {
            LikeDTO likeDTO = LikeDTO.fromLike(likeService.likeOpinion(user, opinionID));
            return ResponseEntity.ok(likeDTO);
        }  catch (IllegalAccessException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //like comment
    @PostMapping("/comment")
    public ResponseEntity<?> likeComment(@AuthenticationPrincipal User user, @PathVariable String commentID) {
        try {
            LikeDTO likeDTO = LikeDTO.fromLike(likeService.likeComment(user, commentID));
            return ResponseEntity.ok(likeDTO);
        }  catch (IllegalAccessException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //delete like
    @DeleteMapping
    public ResponseEntity<?> deleteLike(@AuthenticationPrincipal User user, @PathVariable String likeID) {
        try {
            likeService.deleteLike(user, likeID);
            return ResponseEntity.ok("You have unliked");
        }  catch (IllegalAccessException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
