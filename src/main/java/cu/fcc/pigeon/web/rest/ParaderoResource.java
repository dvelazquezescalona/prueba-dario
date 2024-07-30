package cu.fcc.pigeon.web.rest;

import cu.fcc.pigeon.Exception.BusinnesException;
import cu.fcc.pigeon.config.SYSMensaje;
import cu.fcc.pigeon.domain.Paradero;
import cu.fcc.pigeon.repository.ParaderoRepository;
import cu.fcc.pigeon.service.ParaderoService;
import cu.fcc.pigeon.service.dto.ParaderoDTO;
import cu.fcc.pigeon.service.dto.ResponseDTO;
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
 * REST controller for managing {@link cu.fcc.pigeon.domain.Paradero}.
 */
@RestController
@RequestMapping("/api/paraderos")
public class ParaderoResource {

    private final Logger log = LoggerFactory.getLogger(ParaderoResource.class);

    private static final String ENTITY_NAME = "paradero";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParaderoService paraderoService;

    private final ParaderoRepository paraderoRepository;

    public ParaderoResource(ParaderoService paraderoService, ParaderoRepository paraderoRepository) {
        this.paraderoService = paraderoService;
        this.paraderoRepository = paraderoRepository;
    }

    @PostMapping("/addparadero")
    public ResponseDTO<ParaderoDTO> agregarParadero(@Valid @RequestBody ParaderoDTO paraderoDTO) {
        log.info("REST para agregar un paradero {}", paraderoDTO);
        try {
            paraderoDTO.setNombreParadero(paraderoDTO.getNombreParadero().toUpperCase());
            return paraderoService.agregarParadero(paraderoDTO);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    @PostMapping("/actualizarparadero")
    public ResponseDTO<ParaderoDTO> actualizarParadero(@Valid @RequestBody ParaderoDTO paraderoDTO) {
        log.info("REST para actualizar un paradero {}", paraderoDTO);
        try {
            paraderoDTO.setNombreParadero(paraderoDTO.getNombreParadero().toUpperCase());
            return paraderoService.actualizarParadero(paraderoDTO);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    @GetMapping("/obtenerparaderos/{sociedadId}")
    public ResponseDTO<List<ParaderoDTO>> obtenerParaderos(@PathVariable("sociedadId") Long sociedadId) {
        log.info("REST para obtener los paraderos de la sociedad: {}", sociedadId);
        try {
            return paraderoService.obtenerParaderos(sociedadId);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    @GetMapping("/obtenerparadero/{id}")
    public ResponseDTO<ParaderoDTO> obtenerParadero(@PathVariable("id") Long id) {
        log.info("REST para obtener un paradero por su id: {}", id);
        try {
            return paraderoService.obtenerParadero(id);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    /**
     * {@code POST  /paraderos} : Create a new paradero.
     *
     * @param paradero the paradero to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paradero, or with status {@code 400 (Bad Request)} if the paradero has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Paradero> createParadero(@Valid @RequestBody Paradero paradero) throws URISyntaxException {
        log.debug("REST request to save Paradero : {}", paradero);
        if (paradero.getId() != null) {
            throw new BadRequestAlertException("A new paradero cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Paradero result = paraderoService.save(paradero);
        return ResponseEntity
            .created(new URI("/api/paraderos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paraderos/:id} : Updates an existing paradero.
     *
     * @param id the id of the paradero to save.
     * @param paradero the paradero to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paradero,
     * or with status {@code 400 (Bad Request)} if the paradero is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paradero couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Paradero> updateParadero(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Paradero paradero
    ) throws URISyntaxException {
        log.debug("REST request to update Paradero : {}, {}", id, paradero);
        if (paradero.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paradero.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paraderoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Paradero result = paraderoService.update(paradero);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paradero.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /paraderos/:id} : Partial updates given fields of an existing paradero, field will ignore if it is null
     *
     * @param id the id of the paradero to save.
     * @param paradero the paradero to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paradero,
     * or with status {@code 400 (Bad Request)} if the paradero is not valid,
     * or with status {@code 404 (Not Found)} if the paradero is not found,
     * or with status {@code 500 (Internal Server Error)} if the paradero couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Paradero> partialUpdateParadero(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Paradero paradero
    ) throws URISyntaxException {
        log.debug("REST request to partial update Paradero partially : {}, {}", id, paradero);
        if (paradero.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paradero.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paraderoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Paradero> result = paraderoService.partialUpdate(paradero);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paradero.getId().toString())
        );
    }

    /**
     * {@code GET  /paraderos} : get all the paraderos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paraderos in body.
     */
    @GetMapping("")
    public List<Paradero> getAllParaderos() {
        log.debug("REST request to get all Paraderos");
        return paraderoService.findAll();
    }

    /**
     * {@code GET  /paraderos/:id} : get the "id" paradero.
     *
     * @param id the id of the paradero to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paradero, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Paradero> getParadero(@PathVariable("id") Long id) {
        log.debug("REST request to get Paradero : {}", id);
        Optional<Paradero> paradero = paraderoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paradero);
    }

    /**
     * {@code DELETE  /paraderos/:id} : delete the "id" paradero.
     *
     * @param id the id of the paradero to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParadero(@PathVariable("id") Long id) {
        log.debug("REST request to delete Paradero : {}", id);
        paraderoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
