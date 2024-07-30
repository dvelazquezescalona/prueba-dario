package cu.fcc.pigeon.service;

import cu.fcc.pigeon.Exception.BusinnesException;
import cu.fcc.pigeon.domain.Colombofilo;
import cu.fcc.pigeon.service.dto.ColombofiloDTO;
import cu.fcc.pigeon.service.dto.ParaderoDTO;
import cu.fcc.pigeon.service.dto.ResponseDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link cu.fcc.pigeon.domain.Colombofilo}.
 */
public interface ColombofiloService {
    /**
     * Save a colombofilo.
     *
     * @param colombofilo the entity to save.
     * @return the persisted entity.
     */
    Colombofilo save(Colombofilo colombofilo);

    /**
     * Updates a colombofilo.
     *
     * @param colombofilo the entity to update.
     * @return the persisted entity.
     */
    Colombofilo update(Colombofilo colombofilo);

    /**
     * Partially updates a colombofilo.
     *
     * @param colombofilo the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Colombofilo> partialUpdate(Colombofilo colombofilo);

    /**
     * Get all the colombofilos.
     *
     * @return the list of entities.
     */
    List<Colombofilo> findAll();

    /**
     * Get the "id" colombofilo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Colombofilo> findOne(Long id);

    /**
     * Delete the "id" colombofilo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    ResponseDTO<ColombofiloDTO> agregarColombofilo(ColombofiloDTO colombofiloDTO) throws BusinnesException;

    ResponseDTO<ColombofiloDTO> actualizarColombofilo(ColombofiloDTO colombofiloDTO) throws BusinnesException;

    ResponseDTO<ColombofiloDTO> obtenerColombofilo(Long id) throws BusinnesException;

    ResponseDTO<List<ColombofiloDTO>> obtenerColombofilos(Long sociedadId) throws BusinnesException;
}
