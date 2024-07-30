package cu.fcc.pigeon.web.rest;

import cu.fcc.pigeon.domain.Colombofilo;
import cu.fcc.pigeon.repository.ColombofiloRepository;
import cu.fcc.pigeon.service.ColombofiloService;
import cu.fcc.pigeon.service.dto.ColombofiloDTO;
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
 * REST controller for managing {@link cu.fcc.pigeon.domain.Colombofilo}.
 */
@RestController
@RequestMapping("/api/colombofilos")
public class ColombofiloResource {

    private final Logger log = LoggerFactory.getLogger(ColombofiloResource.class);

    private static final String ENTITY_NAME = "colombofilo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ColombofiloService colombofiloService;

    private final ColombofiloRepository colombofiloRepository;

    public ColombofiloResource(ColombofiloService colombofiloService, ColombofiloRepository colombofiloRepository) {
        this.colombofiloService = colombofiloService;
        this.colombofiloRepository = colombofiloRepository;
    }

    /**
     * agregar colombofilo
     * @param colombofiloDTO
     * @return
     */
    @PostMapping("/addcolombofilo")
    public ResponseDTO<ColombofiloDTO> agregarColombofilo(@Valid @RequestBody ColombofiloDTO colombofiloDTO) {
        log.info("REST para agregar un colombofilo {}", colombofiloDTO);
        try {
            colombofiloDTO.setNombre(colombofiloDTO.getNombre().toUpperCase());
            colombofiloDTO.setPrimerApellido(colombofiloDTO.getPrimerApellido().toUpperCase());
            colombofiloDTO.setSegindoApellido(colombofiloDTO.getSegindoApellido().toUpperCase());
            colombofiloDTO.setDireccion(colombofiloDTO.getDireccion().toUpperCase());
            colombofiloDTO.setZona(colombofiloDTO.getZona().toUpperCase());
            colombofiloDTO.setCategoria(colombofiloDTO.getCategoria().toUpperCase());
            return colombofiloService.agregarColombofilo(colombofiloDTO);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    /**
     * actualizar colombofilos
     * @param colombofiloDTO
     * @return
     */
    @PostMapping("/actualizarcolombofilo")
    public ResponseDTO<ColombofiloDTO> actualizarColombofilo(@Valid @RequestBody ColombofiloDTO colombofiloDTO) {
        log.info("REST para actualizar un colombofilo {}", colombofiloDTO);
        try {
            colombofiloDTO.setNombre(colombofiloDTO.getNombre().toUpperCase());
            colombofiloDTO.setPrimerApellido(colombofiloDTO.getPrimerApellido().toUpperCase());
            colombofiloDTO.setSegindoApellido(colombofiloDTO.getSegindoApellido().toUpperCase());
            colombofiloDTO.setDireccion(colombofiloDTO.getDireccion().toUpperCase());
            colombofiloDTO.setZona(colombofiloDTO.getZona().toUpperCase());
            colombofiloDTO.setCategoria(colombofiloDTO.getCategoria().toUpperCase());
            return colombofiloService.actualizarColombofilo(colombofiloDTO);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    /**
     * obtener un colombofilos
     * @param sociedadId
     * @return
     */
    @GetMapping("/obtenercolombofilos/{sociedadId}")
    public ResponseDTO<List<ColombofiloDTO>> obtenerColombofilos(@PathVariable("sociedadId") Long sociedadId) {
        log.info("REST para obtener los colombofilos de la sociedad: {}", sociedadId);
        try {
            return colombofiloService.obtenerColombofilos(sociedadId);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    /**
     * obtener colombofilo
     * @param id
     * @return
     */
    @GetMapping("/obtenercolombofilo/{id}")
    public ResponseDTO<ColombofiloDTO> obtenerColombofilo(@PathVariable("id") Long id) {
        log.info("REST para obtener un paradero por su id: {}", id);
        try {
            return colombofiloService.obtenerColombofilo(id);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    /**
     * {@code POST  /colombofilos} : Create a new colombofilo.
     *
     * @param colombofilo the colombofilo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new colombofilo, or with status {@code 400 (Bad Request)} if the colombofilo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Colombofilo> createColombofilo(@Valid @RequestBody Colombofilo colombofilo) throws URISyntaxException {
        log.debug("REST request to save Colombofilo : {}", colombofilo);
        if (colombofilo.getId() != null) {
            throw new BadRequestAlertException("A new colombofilo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Colombofilo result = colombofiloService.save(colombofilo);
        return ResponseEntity
            .created(new URI("/api/colombofilos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /colombofilos/:id} : Updates an existing colombofilo.
     *
     * @param id the id of the colombofilo to save.
     * @param colombofilo the colombofilo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated colombofilo,
     * or with status {@code 400 (Bad Request)} if the colombofilo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the colombofilo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Colombofilo> updateColombofilo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Colombofilo colombofilo
    ) throws URISyntaxException {
        log.debug("REST request to update Colombofilo : {}, {}", id, colombofilo);
        if (colombofilo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, colombofilo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!colombofiloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Colombofilo result = colombofiloService.update(colombofilo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, colombofilo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /colombofilos/:id} : Partial updates given fields of an existing colombofilo, field will ignore if it is null
     *
     * @param id the id of the colombofilo to save.
     * @param colombofilo the colombofilo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated colombofilo,
     * or with status {@code 400 (Bad Request)} if the colombofilo is not valid,
     * or with status {@code 404 (Not Found)} if the colombofilo is not found,
     * or with status {@code 500 (Internal Server Error)} if the colombofilo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Colombofilo> partialUpdateColombofilo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Colombofilo colombofilo
    ) throws URISyntaxException {
        log.debug("REST request to partial update Colombofilo partially : {}, {}", id, colombofilo);
        if (colombofilo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, colombofilo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!colombofiloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Colombofilo> result = colombofiloService.partialUpdate(colombofilo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, colombofilo.getId().toString())
        );
    }

    /**
     * {@code GET  /colombofilos} : get all the colombofilos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of colombofilos in body.
     */
    @GetMapping("")
    public List<Colombofilo> getAllColombofilos() {
        log.debug("REST request to get all Colombofilos");
        return colombofiloService.findAll();
    }

    /**
     * {@code GET  /colombofilos/:id} : get the "id" colombofilo.
     *
     * @param id the id of the colombofilo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the colombofilo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Colombofilo> getColombofilo(@PathVariable("id") Long id) {
        log.debug("REST request to get Colombofilo : {}", id);
        Optional<Colombofilo> colombofilo = colombofiloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(colombofilo);
    }

    /**
     * {@code DELETE  /colombofilos/:id} : delete the "id" colombofilo.
     *
     * @param id the id of the colombofilo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColombofilo(@PathVariable("id") Long id) {
        log.debug("REST request to delete Colombofilo : {}", id);
        colombofiloService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
