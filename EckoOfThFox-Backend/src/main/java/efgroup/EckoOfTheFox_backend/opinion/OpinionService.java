package efgroup.EckoOfTheFox_backend.opinion;

import efgroup.EckoOfTheFox_backend.user.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
        if (opinionDTO.getImgType().equalsIgnoreCase("jpg") ||
                opinionDTO.getImgType().equalsIgnoreCase("jpeg") ||
                opinionDTO.getImgType().equalsIgnoreCase("png")) {
            Opinion opinion = new Opinion(
                    opinionDTO.getOpinionID(),
                    opinionDTO.getTitle(),
                    opinionDTO.getOpinionText(),
                    opinionDTO.getImgName(),
                    opinionDTO.getImgContent(),
                    opinionDTO.getImgType(),
                    user
            );
            return opinionRepository.save(opinion);
        } else {
            throw new IllegalArgumentException("The image file type is not allowed.");
        }
    }

    public Opinion updateOpinion(User user, OpinionDTO opinionDTO) throws IllegalAccessException, NoSuchElementException {
        Opinion opinionToUpdate = opinionRepository.findById(opinionDTO.getOpinionID()).orElseThrow(() -> new NoSuchElementException("The opinon was not found in the database."));
        if (opinionToUpdate.getAuthor().getUserID() != user.getUserID()) {
            throw new IllegalAccessException("You are not allowed to change this opinion.");
        }

        opinionToUpdate.setTitle(opinionDTO.getTitle());
        opinionToUpdate.setOpinionText(opinionDTO.getOpinionText());
        opinionToUpdate.setImgName(opinionToUpdate.getImgName());
        opinionToUpdate.setImgContent(opinionDTO.getImgContent());
        opinionToUpdate.setImgType(opinionDTO.getImgType());

        //TODO finish
    }
}
