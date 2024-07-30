package cu.fcc.pigeon.repository;

import cu.fcc.pigeon.domain.Paloma;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Paloma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PalomaRepository extends JpaRepository<Paloma, Long> {
    Optional<Paloma> findByAnillaAndAnnoAndPais(String anilla, String anno, Long pais);
}
