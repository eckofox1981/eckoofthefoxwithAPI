package efgroup.EckoOfTheFox_backend.opinion;

import efgroup.EckoOfTheFox_backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OpinionService {
    private final OpinionRepository opinionRepository;

    public List<Opinion> getAllOpinions() throws NoSuchElementException {
        List<Opinion> opinions = opinionRepository.findAll();

        if (opinions.isEmpty()) {
            throw new NoSuchElementException("No opinions found.");
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

    public Opinion saveOpinion(User user, OpinionDTO opinionDTO) throws IllegalArgumentException {


        Opinion opinion = new Opinion(
                opinionDTO.getOpinionID(),
                opinionDTO.getTitle(),
                opinionDTO.getOpinionText(),
                user
        );
        return opinionRepository.save(opinion);

    }

    public Opinion updateOpinion(User user, OpinionDTO opinionDTO) throws IllegalAccessException, NoSuchElementException {
        Opinion opinionToUpdate = opinionRepository.findById(opinionDTO.getOpinionID()).orElseThrow(() -> new NoSuchElementException("The opinion was not found in the database."));

        if (opinionToUpdate.getAuthor().getUserID() != user.getUserID()) {
            throw new IllegalAccessException("You are not allowed to change this opinion.");
        }

        opinionToUpdate.setTitle(opinionDTO.getTitle());
        opinionToUpdate.setOpinionText(opinionDTO.getOpinionText());

        return opinionRepository.save(opinionToUpdate);
    }

    public void deleteOpinion(User user, int opinionId) throws IllegalAccessException, NoSuchElementException {
        Opinion opinionToDelete = opinionRepository.findById(opinionId).orElseThrow(() -> new NoSuchElementException("The opinion was not found in the database."));

        if (opinionToDelete.getAuthor().getUserID() != user.getUserID()) {
            throw new IllegalAccessException("You are not allowed to change this opinion.");
        }

        opinionRepository.delete(opinionToDelete);
    }
}
