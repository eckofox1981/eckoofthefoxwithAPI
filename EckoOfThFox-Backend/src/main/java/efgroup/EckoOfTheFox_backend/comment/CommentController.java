package efgroup.EckoOfTheFox_backend.comment;

import efgroup.EckoOfTheFox_backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    }

    //update

    //delete
}
