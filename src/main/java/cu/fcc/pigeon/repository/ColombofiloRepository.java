package cu.fcc.pigeon.repository;

import cu.fcc.pigeon.domain.Colombofilo;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Colombofilo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ColombofiloRepository extends JpaRepository<Colombofilo, Long> {
    Optional<Colombofilo> findByCi(String ci);
}
