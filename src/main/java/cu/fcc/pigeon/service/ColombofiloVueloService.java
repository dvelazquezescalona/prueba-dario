package cu.fcc.pigeon.service;

import cu.fcc.pigeon.domain.ColombofiloVuelo;
import cu.fcc.pigeon.service.dto.ColombofiloVueloDTO;
import cu.fcc.pigeon.service.dto.ResponseDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link cu.fcc.pigeon.domain.ColombofiloVuelo}.
 */
public interface ColombofiloVueloService {
    /**
     * Save a colombofiloVuelo.
     *
     * @param colombofiloVuelo the entity to save.
     * @return the persisted entity.
     */
    ColombofiloVuelo save(ColombofiloVuelo colombofiloVuelo);

    /**
     * Updates a colombofiloVuelo.
     *
     * @param colombofiloVuelo the entity to update.
     * @return the persisted entity.
     */
    ColombofiloVuelo update(ColombofiloVuelo colombofiloVuelo);

    /**
     * Partially updates a colombofiloVuelo.
     *
     * @param colombofiloVuelo the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ColombofiloVuelo> partialUpdate(ColombofiloVuelo colombofiloVuelo);

    /**
     * Get all the colombofiloVuelos.
     *
     * @return the list of entities.
     */
    List<ColombofiloVuelo> findAll();

    /**
     * Get the "id" colombofiloVuelo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ColombofiloVuelo> findOne(Long id);

    /**
     * Delete the "id" colombofiloVuelo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    ResponseDTO<ColombofiloVueloDTO> agregarColombofiloVuelo(ColombofiloVueloDTO colombofiloVueloDTO);
    ResponseDTO<ColombofiloVueloDTO> actualizarColombofiloVuelo(ColombofiloVueloDTO colombofiloVueloDTO);
    ResponseDTO<ColombofiloVueloDTO> obtenerColombofiloVuelo(Long id);
    ResponseDTO<ColombofiloVueloDTO> obtenerColombofiloVuelo(Long vueloId, Long colombofiloId);
}
