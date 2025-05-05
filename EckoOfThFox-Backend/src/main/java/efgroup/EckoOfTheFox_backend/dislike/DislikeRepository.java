package efgroup.EckoOfTheFox_backend.dislike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DislikeRepository extends JpaRepository<Dislike, UUID> {
}
