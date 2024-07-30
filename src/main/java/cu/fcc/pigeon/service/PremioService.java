package cu.fcc.pigeon.service;

import cu.fcc.pigeon.domain.Premio;
import cu.fcc.pigeon.service.dto.PalomaDTO;
import cu.fcc.pigeon.service.dto.PremioDTO;
import cu.fcc.pigeon.service.dto.ReporteVueloDTO;
import cu.fcc.pigeon.service.dto.ResponseDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

/**
 * Service Interface for managing {@link cu.fcc.pigeon.domain.Premio}.
 */
public interface PremioService {
    /**
     * Save a premio.
     *
     * @param premio the entity to save.
     * @return the persisted entity.
     */
    Premio save(Premio premio);

    /**
     * Updates a premio.
     *
     * @param premio the entity to update.
     * @return the persisted entity.
     */
    Premio update(Premio premio);

    /**
     * Partially updates a premio.
     *
     * @param premio the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Premio> partialUpdate(Premio premio);

    /**
     * Get all the premios.
     *
     * @return the list of entities.
     */
    List<Premio> findAll();

    /**
     * Get the "id" premio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Premio> findOne(Long id);

    /**
     * Delete the "id" premio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    ResponseDTO<PremioDTO> agregarPremio(PremioDTO premioDTO);

    ResponseDTO<PremioDTO> actualizarPremio(PremioDTO premioDTO);

    ResponseDTO<PremioDTO> obtenerPremio(Long id);

    ResponseDTO<List<ReporteVueloDTO>> obtenerPremiosByVuelo(Long vueloId, Long colombofiloId, Long sociedadId);

    ResponseDTO<List<PremioDTO>> obtenerPremiosByPaloma(Long palomaId);
    //Page<PremioDTO>
}
