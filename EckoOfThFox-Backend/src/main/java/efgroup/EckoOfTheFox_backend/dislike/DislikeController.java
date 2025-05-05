package efgroup.EckoOfTheFox_backend.dislike;

import efgroup.EckoOfTheFox_backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dislike")
public class DislikeController {
    private final DislikeService dislikeService;

    //get
    /**
     * automatic when getting user, opinion, comment
     */
    //like opinion
    @PostMapping("/opinion")
    public ResponseEntity<?> dislikeOpinion(@AuthenticationPrincipal User user, @PathVariable int opinionID) {
        try {
            DislikeDTO dislikeDTO = DislikeDTO.fromDislike(dislikeService.dislikeOpinion(user, opinionID));
            return ResponseEntity.ok(dislikeDTO);
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
            DislikeDTO dislikeDTO = DislikeDTO.fromDislike(dislikeService.dislikeComment(user, commentID));
            return ResponseEntity.ok(dislikeDTO);
        }  catch (IllegalAccessException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //delete like
    @DeleteMapping
    public ResponseEntity<?> deleteDislike(@AuthenticationPrincipal User user, @PathVariable String dislikeID) {
        try {
            dislikeService.deleteDislike(user, dislikeID);
            return ResponseEntity.ok("You have unliked");
        }  catch (IllegalAccessException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
