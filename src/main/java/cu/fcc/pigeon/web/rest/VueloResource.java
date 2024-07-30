package cu.fcc.pigeon.web.rest;

import cu.fcc.pigeon.Exception.BusinnesException;
import cu.fcc.pigeon.domain.Vuelo;
import cu.fcc.pigeon.repository.VueloRepository;
import cu.fcc.pigeon.service.VueloService;
import cu.fcc.pigeon.service.dto.ResponseDTO;
import cu.fcc.pigeon.service.dto.VueloDTO;
import cu.fcc.pigeon.service.dto.enumeration.Status;
import cu.fcc.pigeon.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link cu.fcc.pigeon.domain.Vuelo}.
 */
@RestController
@RequestMapping("/api/vuelos")
public class VueloResource {

    private final Logger log = LoggerFactory.getLogger(VueloResource.class);

    private static final String ENTITY_NAME = "vuelo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VueloService vueloService;

    private final VueloRepository vueloRepository;

    public VueloResource(VueloService vueloService, VueloRepository vueloRepository) {
        this.vueloService = vueloService;
        this.vueloRepository = vueloRepository;
    }

    /**
     * agregar un nuevo vuelo
     * @param vueloDTO
     * @return
     */

    @PostMapping("/addvuelo")
    public ResponseDTO<VueloDTO> agregarVuelo(@Valid @RequestBody VueloDTO vueloDTO) {
        log.info("REST para agregar un vuelo {}", vueloDTO);
        try {
            vueloDTO.setCampeonato(vueloDTO.getCampeonato().toUpperCase());
            vueloDTO.setCompetencia(vueloDTO.getCompetencia().toUpperCase());
            vueloDTO.setDescripcion(vueloDTO.getDescripcion().toUpperCase());
            return vueloService.agregarVuelo(vueloDTO);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    /**
     * actualizar un vuelo existente
     * @param vueloDTO
     * @return
     */
    @PostMapping("/actualizarvuelo")
    public ResponseDTO<VueloDTO> actualizarParadero(@Valid @RequestBody VueloDTO vueloDTO) {
        log.info("REST para actualizar un vuelo {}", vueloDTO);
        try {
            vueloDTO.setCampeonato(vueloDTO.getCampeonato().toUpperCase());
            vueloDTO.setCompetencia(vueloDTO.getCompetencia().toUpperCase());
            vueloDTO.setDescripcion(vueloDTO.getDescripcion().toUpperCase());
            return vueloService.actualizarVuelo(vueloDTO);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    /**
     * obtener los vuelos de un paradero
     * @param paraderoId
     * @return
     */
    @GetMapping("/obtenervuelos/{paraderoId}")
    public ResponseDTO<List<VueloDTO>> obtenerParaderos(@PathVariable("paraderoId") Long paraderoId) {
        log.info("REST para obtener los vuelos del paradero: {}", paraderoId);
        try {
            return vueloService.obtenerVuelos(paraderoId);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    /**
     * obtener un vuelo
     * @param id
     * @return
     */
    @GetMapping("/obtenervuelo/{id}")
    public ResponseDTO<VueloDTO> obtenerParadero(@PathVariable("id") Long id) {
        log.info("REST para obtener un vuelo por su id: {}", id);
        try {
            return vueloService.obtenerVuelo(id);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    /**
     * Proceso para realizar los calculos de un vuelo
     * @param id
     * @return
     */
    @PostMapping("/calcularvelocidad/{id}")
    public ResponseDTO<Long> calcularVelocidad(@PathVariable("id") Long id) throws BusinnesException {
        log.debug("REST request to calculate velocity to Vuelo : {}", id);

        return vueloService.calcularVelocidad(id);
    }

    @PostMapping("/calcularpuntuacion/{id}")
    public ResponseDTO<Long> calcularPuntuacion(@PathVariable("id") Long id) throws BusinnesException {
        log.debug("REST request to calculate score to Vuelo : {}", id);

        return vueloService.calcularPuntos(id);
    }

    /**
     * {@code POST  /vuelos} : Create a new vuelo.
     *
     * @param vuelo the vuelo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vuelo, or with status {@code 400 (Bad Request)} if the vuelo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PostMapping("")
    public ResponseEntity<Vuelo> createVuelo(@Valid @RequestBody Vuelo vuelo) throws URISyntaxException {
        log.debug("REST request to save Vuelo : {}", vuelo);
        if (vuelo.getId() != null) {
            throw new BadRequestAlertException("A new vuelo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vuelo result = vueloService.save(vuelo);
        return ResponseEntity
            .created(new URI("/api/vuelos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vuelos/:id} : Updates an existing vuelo.
     *
     * @param id the id of the vuelo to save.
     * @param vuelo the vuelo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vuelo,
     * or with status {@code 400 (Bad Request)} if the vuelo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vuelo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vuelo> updateVuelo(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Vuelo vuelo)
        throws URISyntaxException {
        log.debug("REST request to update Vuelo : {}, {}", id, vuelo);
        if (vuelo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vuelo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vueloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Vuelo result = vueloService.update(vuelo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vuelo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vuelos/:id} : Partial updates given fields of an existing vuelo, field will ignore if it is null
     *
     * @param id the id of the vuelo to save.
     * @param vuelo the vuelo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vuelo,
     * or with status {@code 400 (Bad Request)} if the vuelo is not valid,
     * or with status {@code 404 (Not Found)} if the vuelo is not found,
     * or with status {@code 500 (Internal Server Error)} if the vuelo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vuelo> partialUpdateVuelo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Vuelo vuelo
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vuelo partially : {}, {}", id, vuelo);
        if (vuelo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vuelo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vueloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vuelo> result = vueloService.partialUpdate(vuelo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vuelo.getId().toString())
        );
    }

    /**
     * {@code GET  /vuelos} : get all the vuelos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vuelos in body.
     */
    @GetMapping("")
    public List<Vuelo> getAllVuelos() {
        log.debug("REST request to get all Vuelos");
        return vueloService.findAll();
    }

    /**
     * {@code GET  /vuelos/:id} : get the "id" vuelo.
     *
     * @param id the id of the vuelo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vuelo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vuelo> getVuelo(@PathVariable("id") Long id) {
        log.debug("REST request to get Vuelo : {}", id);
        Optional<Vuelo> vuelo = vueloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vuelo);
    }

    /**
     * {@code DELETE  /vuelos/:id} : delete the "id" vuelo.
     *
     * @param id the id of the vuelo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVuelo(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vuelo : {}", id);
        vueloService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
