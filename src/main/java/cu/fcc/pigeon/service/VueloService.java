package cu.fcc.pigeon.service;

import cu.fcc.pigeon.Exception.BusinnesException;
import cu.fcc.pigeon.domain.Vuelo;
import cu.fcc.pigeon.service.dto.ColombofiloVueloDTO;
import cu.fcc.pigeon.service.dto.ParaderoDTO;
import cu.fcc.pigeon.service.dto.ResponseDTO;
import cu.fcc.pigeon.service.dto.VueloDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link cu.fcc.pigeon.domain.Vuelo}.
 */
public interface VueloService {
    /**
     * Save a vuelo.
     *
     * @param vuelo the entity to save.
     * @return the persisted entity.
     */
    Vuelo save(Vuelo vuelo);

    /**
     * Updates a vuelo.
     *
     * @param vuelo the entity to update.
     * @return the persisted entity.
     */
    Vuelo update(Vuelo vuelo);

    /**
     * Partially updates a vuelo.
     *
     * @param vuelo the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Vuelo> partialUpdate(Vuelo vuelo);

    /**
     * Get all the vuelos.
     *
     * @return the list of entities.
     */
    List<Vuelo> findAll();

    /**
     * Get the "id" vuelo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Vuelo> findOne(Long id);

    /**
     * Delete the "id" vuelo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * calcular el vuelo
     */

    ResponseDTO<Long> calcularVelocidad(Long id);

    ResponseDTO<Long> calcularPuntos(Long id);

    ResponseDTO<ColombofiloVueloDTO> agregarColombofilo(ColombofiloVueloDTO colombofiloVueloDTO);

    ResponseDTO<VueloDTO> agregarVuelo(VueloDTO vueloDTO) throws BusinnesException;

    ResponseDTO<VueloDTO> actualizarVuelo(VueloDTO vueloDTO) throws BusinnesException;

    ResponseDTO<VueloDTO> obtenerVuelo(Long id) throws BusinnesException;

    ResponseDTO<List<VueloDTO>> obtenerVuelos(Long paraderoId) throws BusinnesException;
}
