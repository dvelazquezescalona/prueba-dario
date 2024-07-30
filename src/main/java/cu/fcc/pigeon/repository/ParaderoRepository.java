package cu.fcc.pigeon.repository;

import cu.fcc.pigeon.domain.Paradero;
import cu.fcc.pigeon.domain.Sociedad;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Paradero entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParaderoRepository extends JpaRepository<Paradero, Long> {
    Optional<Paradero> findByNombreParaderoAndSociedad(String nombreParadero, Sociedad sociedad);

    List<Paradero> findBySociedad(Sociedad sociedad);
}
