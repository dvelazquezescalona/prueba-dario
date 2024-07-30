package cu.fcc.pigeon.service;

import cu.fcc.pigeon.Exception.BusinnesException;
import cu.fcc.pigeon.domain.Paradero;
import cu.fcc.pigeon.service.dto.ParaderoDTO;
import cu.fcc.pigeon.service.dto.ResponseDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link cu.fcc.pigeon.domain.Paradero}.
 */
public interface ParaderoService {
    /**
     * Save a paradero.
     *
     * @param paradero the entity to save.
     * @return the persisted entity.
     */
    Paradero save(Paradero paradero);

    /**
     * Updates a paradero.
     *
     * @param paradero the entity to update.
     * @return the persisted entity.
     */
    Paradero update(Paradero paradero);

    /**
     * Partially updates a paradero.
     *
     * @param paradero the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Paradero> partialUpdate(Paradero paradero);

    /**
     * Get all the paraderos.
     *
     * @return the list of entities.
     */
    List<Paradero> findAll();

    /**
     * Get the "id" paradero.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Paradero> findOne(Long id);

    /**
     * Delete the "id" paradero.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    ResponseDTO<ParaderoDTO> agregarParadero(ParaderoDTO paraderoDTO) throws BusinnesException;

    ResponseDTO<ParaderoDTO> actualizarParadero(ParaderoDTO paraderoDTO) throws BusinnesException;

    ResponseDTO<ParaderoDTO> obtenerParadero(Long id) throws BusinnesException;

    ResponseDTO<List<ParaderoDTO>> obtenerParaderos(Long sociedadId) throws BusinnesException;
}
