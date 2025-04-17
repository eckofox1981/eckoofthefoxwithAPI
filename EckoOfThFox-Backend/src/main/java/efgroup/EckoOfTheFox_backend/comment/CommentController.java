package efgroup.EckoOfTheFox_backend.comment;

import efgroup.EckoOfTheFox_backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //get

    //post
    @PostMapping("/add-comment")
    public ResponseEntity<?> commentOpinion(@AuthenticationPrincipal User user,
                                            @PathVariable UUID opinionID,
                                            @RequestBody CommentDTO commentDTO) {
        try {
            CommentDTO savedCommentDTO = CommentDTO.fromComment(commentService.commentOpinion(user, opinionID, commentDTO));
            return ResponseEntity.ok(savedCommentDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //update

    public ResponseEntity<?> editComment(@AuthenticationPrincipal User user, @RequestBody CommentDTO commentDTO) {
        try {
            CommentDTO updatedCommentDTO = CommentDTO.fromComment(commentService.updateComment(user, commentDTO));
            return ResponseEntity.ok(updatedCommentDTO);
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //delete
    public ResponseEntity<?> deleteComment(@AuthenticationPrincipal User user, String commentID) {
        try {
            commentService.deleteComment(user, commentID);
            return ResponseEntity.ok("Comment deleted.");
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
