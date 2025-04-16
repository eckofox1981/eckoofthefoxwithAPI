package efgroup.EckoOfTheFox_backend.opinion;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        } catch (Exception e) {
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
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    //post
    @PostMapping("post-opinion")
    public ResponseEntity<?> submitOpinion(@RequestBody OpinionDTO opinionDTO) {

    }

    //update

    //delete

}
