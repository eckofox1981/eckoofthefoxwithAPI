package efgroup.EckoOfTheFox_backend.opinion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Integer> {

    Optional<Opinion> findByTitle(String title);
}
