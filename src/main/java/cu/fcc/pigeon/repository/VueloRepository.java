package cu.fcc.pigeon.repository;

import cu.fcc.pigeon.domain.Vuelo;
import cu.fcc.pigeon.domain.common.Enviadas;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vuelo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Long> {}
