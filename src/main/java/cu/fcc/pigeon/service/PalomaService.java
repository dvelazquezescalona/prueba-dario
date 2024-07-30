package cu.fcc.pigeon.service;

import cu.fcc.pigeon.Exception.BusinnesException;
import cu.fcc.pigeon.domain.Paloma;
import cu.fcc.pigeon.service.dto.PalomaDTO;
import cu.fcc.pigeon.service.dto.ResponseDTO;
import cu.fcc.pigeon.service.dto.VueloDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link cu.fcc.pigeon.domain.Paloma}.
 */
public interface PalomaService {
    /**
     * Save a paloma.
     *
     * @param paloma the entity to save.
     * @return the persisted entity.
     */
    Paloma save(Paloma paloma);

    /**
     * Updates a paloma.
     *
     * @param paloma the entity to update.
     * @return the persisted entity.
     */
    Paloma update(Paloma paloma);

    /**
     * Partially updates a paloma.
     *
     * @param paloma the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Paloma> partialUpdate(Paloma paloma);

    /**
     * Get all the palomas.
     *
     * @return the list of entities.
     */
    List<Paloma> findAll();

    /**
     * Get the "id" paloma.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Paloma> findOne(Long id);

    /**
     * Delete the "id" paloma.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    ResponseDTO<PalomaDTO> agregarPaloma(PalomaDTO palomaDTO);

    ResponseDTO<PalomaDTO> actualizarPaloma(PalomaDTO palomaDTO);

    ResponseDTO<PalomaDTO> obtenerPaloma(Long id);

    ResponseDTO<List<PalomaDTO>> obtenerPalomasByColombofilo(Long colombofiloId);
}
