package efgroup.EckoOfTheFox_backend.comment;

import efgroup.EckoOfTheFox_backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    //get

    //post
    @PostMapping("/post")
    public ResponseEntity<?> commentOpinion(@AuthenticationPrincipal User user,
                                            @PathVariable int opinionID,
                                            @RequestBody CommentDTO commentDTO) {
        try {
            CommentDTO savedCommentDTO = CommentDTO.fromComment(commentService.commentOpinion(user, opinionID, commentDTO));
            return ResponseEntity.ok(savedCommentDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //update
    @PutMapping("/edit")
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
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComment(@AuthenticationPrincipal User user, @PathVariable String commentID) {
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
