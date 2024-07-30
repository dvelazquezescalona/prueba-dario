package cu.fcc.pigeon.service;

import cu.fcc.pigeon.Exception.BusinnesException;
import cu.fcc.pigeon.domain.Sociedad;
import cu.fcc.pigeon.service.dto.ParaderoDTO;
import cu.fcc.pigeon.service.dto.ResponseDTO;
import cu.fcc.pigeon.service.dto.SociedadDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link cu.fcc.pigeon.domain.Sociedad}.
 */
public interface SociedadService {
    /**
     * Save a sociedad.
     *
     * @param sociedad the entity to save.
     * @return the persisted entity.
     */
    Sociedad save(Sociedad sociedad);

    /**
     * Updates a sociedad.
     *
     * @param sociedad the entity to update.
     * @return the persisted entity.
     */
    Sociedad update(Sociedad sociedad);

    /**
     * Partially updates a sociedad.
     *
     * @param sociedad the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Sociedad> partialUpdate(Sociedad sociedad);

    /**
     * Get all the sociedads.
     *
     * @return the list of entities.
     */
    List<Sociedad> findAll();

    /**
     * Get the "id" sociedad.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Sociedad> findOne(Long id);

    /**
     * Delete the "id" sociedad.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    ResponseDTO<SociedadDTO> agregarSociedad(SociedadDTO sociedadDTO) throws BusinnesException;

    ResponseDTO<SociedadDTO> actualizarSociedad(SociedadDTO sociedadDTO) throws BusinnesException;

    ResponseDTO<SociedadDTO> obtenerSociedad(Long id) throws BusinnesException;

    ResponseDTO<List<SociedadDTO>> obtenerSociedades(Long municipioId) throws BusinnesException;
}
