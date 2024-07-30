package cu.fcc.pigeon.repository;

import cu.fcc.pigeon.domain.Colombofilo;
import cu.fcc.pigeon.domain.ColombofiloVuelo;
import cu.fcc.pigeon.domain.Vuelo;
import cu.fcc.pigeon.domain.common.Enviadas;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ColombofiloVuelo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ColombofiloVueloRepository extends JpaRepository<ColombofiloVuelo, Long> {
    Optional<ColombofiloVuelo> findByVueloAndColombofilo(Vuelo vuelo, Colombofilo colombofilo);

    @Query(
        value = "SELECT sum(cv.enviadas) as enviadas FROM pigeon_control_mon.colombofilo_vuelo cv WHERE cv.vuelo_id = ?1",
        nativeQuery = true
    )
    Optional<Enviadas> obtenerEnviadasByVuelo(Long vueloId);
}
