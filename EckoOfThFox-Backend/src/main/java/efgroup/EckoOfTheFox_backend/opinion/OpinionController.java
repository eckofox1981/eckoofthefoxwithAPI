package efgroup.EckoOfTheFox_backend.opinion;

import efgroup.EckoOfTheFox_backend.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/opinion")
public class OpinionController {
    private final OpinionService opinionService;

    //getAll
    @GetMapping
    public ResponseEntity<?> getAllOpinion() {
        try {
            List<OpinionDTO> opinionDTOS = opinionService.getAllOpinions()
                    .stream()
                    .map(OpinionDTO::fromOpinion)
                    .toList();
            return ResponseEntity.ok(opinionDTOS);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //getsearch
    @GetMapping("/search-opinion")
    public ResponseEntity<?> findOpinions(@PathVariable String query) {
        try {
            List<OpinionDTO> opinionDTOS = opinionService.findOpinions(query)
                    .stream()
                    .map(OpinionDTO::fromOpinion)
                    .toList();
            return ResponseEntity.ok(opinionDTOS);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //post
    @PostMapping("/post")
    public ResponseEntity<?> submitOpinion(@AuthenticationPrincipal User user, @RequestBody OpinionDTO opinionDTO) {
        try {
            System.out.println("OPinionDTO:" + opinionDTO);
            OpinionDTO savedOpinionDTO = OpinionDTO.fromOpinion(opinionService.saveOpinion(user, opinionDTO));
            return ResponseEntity.ok(savedOpinionDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(405).body(e.getMessage());
        }
    }

    //update
    @PutMapping("/edit")
    public ResponseEntity<?> updateOpinion(@AuthenticationPrincipal User user, @RequestBody OpinionDTO opinionDTO) {
        try {
            Opinion updatedOpinion = opinionService.updateOpinion(user, opinionDTO);
            return ResponseEntity.ok(updatedOpinion);
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //delete
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteOpinion(@AuthenticationPrincipal User user, @PathVariable int opinionID) {
        try {
            opinionService.deleteOpinion(user, opinionID);
            return ResponseEntity.status(204).body("Opinion deleted.");
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
