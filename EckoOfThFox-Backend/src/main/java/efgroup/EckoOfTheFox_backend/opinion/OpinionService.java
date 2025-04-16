package efgroup.EckoOfTheFox_backend.opinion;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpinionService {
    private final OpinionRepository opinionRepository;

    public List<Opinion> getAllOpinions() throws IllegalArgumentException {
        List<Opinion> opinions = opinionRepository.findAll();

        if (opinions.isEmpty()) {
            throw new IllegalArgumentException("No opinions found.");
        }

        return opinions;
    }

    public List<Opinion> findOpinions(String query) {
        List<Opinion> opinions = getAllOpinions(); //TODO: make custom query in repository, this is a quick fix

        opinions = opinions.stream()
                .filter(n -> n.getTitle().equalsIgnoreCase(query))
                .toList();

        if (opinions.isEmpty()) {
            throw new IllegalArgumentException("No opinions with the title: \"" + query + "\" found.");
        }

        return opinions;
    }

    public OpinionDTO saveOpinion(OpinionDTO opinionDTO){
        Opinion opinion = new Opinion(
                opinionDTO.getOpinionID(),
                opinionDTO.getTitle(),
                opinionDTO.getOpinionText(),
                opinionDTO.get
        )
    }
}
