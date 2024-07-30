package cu.fcc.pigeon.service;

import cu.fcc.pigeon.service.dto.PaisDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link cu.fcc.pigeon.domain.Pais}.
 */
public interface PaisService {
    /**
     * Save a pais.
     *
     * @param paisDTO the entity to save.
     * @return the persisted entity.
     */
    PaisDTO save(PaisDTO paisDTO);

    /**
     * Updates a pais.
     *
     * @param paisDTO the entity to update.
     * @return the persisted entity.
     */
    PaisDTO update(PaisDTO paisDTO);

    /**
     * Partially updates a pais.
     *
     * @param paisDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PaisDTO> partialUpdate(PaisDTO paisDTO);

    /**
     * Get all the pais.
     *
     * @return the list of entities.
     */
    List<PaisDTO> findAll();

    /**
     * Get the "id" pais.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaisDTO> findOne(Long id);

    /**
     * Delete the "id" pais.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
