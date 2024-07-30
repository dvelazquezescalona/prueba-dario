package cu.fcc.pigeon.web.rest;

import cu.fcc.pigeon.Exception.BusinnesException;
import cu.fcc.pigeon.domain.Sociedad;
import cu.fcc.pigeon.repository.SociedadRepository;
import cu.fcc.pigeon.service.SociedadService;
import cu.fcc.pigeon.service.dto.ParaderoDTO;
import cu.fcc.pigeon.service.dto.ResponseDTO;
import cu.fcc.pigeon.service.dto.SociedadDTO;
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
 * REST controller for managing {@link cu.fcc.pigeon.domain.Sociedad}.
 */
@RestController
@RequestMapping("/api/sociedades")
public class SociedadResource {

    private final Logger log = LoggerFactory.getLogger(SociedadResource.class);

    private static final String ENTITY_NAME = "sociedad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SociedadService sociedadService;

    private final SociedadRepository sociedadRepository;

    public SociedadResource(SociedadService sociedadService, SociedadRepository sociedadRepository) {
        this.sociedadService = sociedadService;
        this.sociedadRepository = sociedadRepository;
    }

    /**
     * Add nueva sociedad
     * @param sociedadDTO
     * @return Retorna un ResponseDTO, que contiene un mensaje, Status y el objeto Sociedad
     *
     */
    @PostMapping("/addsociedad")
    public ResponseDTO<SociedadDTO> agregarSociedad(@Valid @RequestBody SociedadDTO sociedadDTO) {
        log.info("REST para agregar una sociedad {}", sociedadDTO);
        try {
            sociedadDTO.setNombreSociedad(sociedadDTO.getNombreSociedad().toUpperCase());
            return sociedadService.agregarSociedad(sociedadDTO);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    /**
     * actualizar una sociedad
     * @param sociedadDTO
     * @return Retorna un ResponseDTO, que contiene un mensaje, Status y el objeto Sociedad
     *
     */
    @PostMapping("/actualizarsociedad")
    public ResponseDTO<SociedadDTO> actualizarSociedad(@Valid @RequestBody SociedadDTO sociedadDTO) {
        log.info("REST para actualizar una sociedad {}", sociedadDTO);
        try {
            sociedadDTO.setNombreSociedad(sociedadDTO.getNombreSociedad().toUpperCase());
            return sociedadService.actualizarSociedad(sociedadDTO);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    /**
     *
     * @param municipioId
     * @return Retorna un ResponseDTO, que contiene un mensaje, Status y una lista de sociedades del municipio con id = municipioId
     */
    @GetMapping("/obtenersociedades/{municipioId}")
    public ResponseDTO<List<SociedadDTO>> obtenerSociedades(@PathVariable("municipioId") Long municipioId) {
        log.info("REST para obtener las sociedades del municipio: {}", municipioId);
        try {
            return sociedadService.obtenerSociedades(municipioId);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    /**
     *
     * @param id
     * @return  Retorna un ResponseDTO, que contiene un mensaje, Status y el objeto Sociedad
     */
    @GetMapping("/obtenersociedad/{id}")
    public ResponseDTO<SociedadDTO> obtenerParadero(@PathVariable("id") Long id) {
        log.info("REST para obtener un paradero por su id: {}", id);
        try {
            return sociedadService.obtenerSociedad(id);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    /**
     * {@code POST  /sociedads} : Create a new sociedad.
     *
     * @param sociedad the sociedad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sociedad, or with status {@code 400 (Bad Request)} if the sociedad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Sociedad> createSociedad(@Valid @RequestBody Sociedad sociedad) throws URISyntaxException {
        log.debug("REST request to save Sociedad : {}", sociedad);
        if (sociedad.getId() != null) {
            throw new BadRequestAlertException("A new sociedad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sociedad result = sociedadService.save(sociedad);
        return ResponseEntity
            .created(new URI("/api/sociedades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sociedads/:id} : Updates an existing sociedad.
     *
     * @param id the id of the sociedad to save.
     * @param sociedad the sociedad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sociedad,
     * or with status {@code 400 (Bad Request)} if the sociedad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sociedad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sociedad> updateSociedad(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Sociedad sociedad
    ) throws URISyntaxException {
        log.debug("REST request to update Sociedad : {}, {}", id, sociedad);
        if (sociedad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sociedad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sociedadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Sociedad result = sociedadService.update(sociedad);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sociedad.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sociedads/:id} : Partial updates given fields of an existing sociedad, field will ignore if it is null
     *
     * @param id the id of the sociedad to save.
     * @param sociedad the sociedad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sociedad,
     * or with status {@code 400 (Bad Request)} if the sociedad is not valid,
     * or with status {@code 404 (Not Found)} if the sociedad is not found,
     * or with status {@code 500 (Internal Server Error)} if the sociedad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sociedad> partialUpdateSociedad(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Sociedad sociedad
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sociedad partially : {}, {}", id, sociedad);
        if (sociedad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sociedad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sociedadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sociedad> result = sociedadService.partialUpdate(sociedad);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sociedad.getId().toString())
        );
    }

    /**
     * {@code GET  /sociedads} : get all the sociedads.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sociedads in body.
     */
    @GetMapping("")
    public List<Sociedad> getAllSociedads() {
        log.debug("REST request to get all Sociedads");
        return sociedadService.findAll();
    }

    /**
     * {@code GET  /sociedads/:id} : get the "id" sociedad.
     *
     * @param id the id of the sociedad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sociedad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sociedad> getSociedad(@PathVariable("id") Long id) {
        log.debug("REST request to get Sociedad : {}", id);
        Optional<Sociedad> sociedad = sociedadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sociedad);
    }

    /**
     * {@code DELETE  /sociedads/:id} : delete the "id" sociedad.
     *
     * @param id the id of the sociedad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSociedad(@PathVariable("id") Long id) {
        log.debug("REST request to delete Sociedad : {}", id);
        sociedadService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
