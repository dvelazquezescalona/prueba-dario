package cu.fcc.pigeon.repository;

import cu.fcc.pigeon.domain.Municipio;
import cu.fcc.pigeon.domain.Sociedad;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sociedad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SociedadRepository extends JpaRepository<Sociedad, Long> {
    Optional<Sociedad> findByNombreSociedadAndMunicipio(String nombreSociedad, Municipio municipio);

    List<Sociedad> findByMunicipio(Municipio municipio);
}
