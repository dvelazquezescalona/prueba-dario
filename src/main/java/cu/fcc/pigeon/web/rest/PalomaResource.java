package cu.fcc.pigeon.web.rest;

import cu.fcc.pigeon.domain.Paloma;
import cu.fcc.pigeon.repository.PalomaRepository;
import cu.fcc.pigeon.service.PalomaService;
import cu.fcc.pigeon.service.dto.PalomaDTO;
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
 * REST controller for managing {@link cu.fcc.pigeon.domain.Paloma}.
 */
@RestController
@RequestMapping("/api/palomas")
public class PalomaResource {

    private final Logger log = LoggerFactory.getLogger(PalomaResource.class);

    private static final String ENTITY_NAME = "paloma";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PalomaService palomaService;

    private final PalomaRepository palomaRepository;

    public PalomaResource(PalomaService palomaService, PalomaRepository palomaRepository) {
        this.palomaService = palomaService;
        this.palomaRepository = palomaRepository;
    }

    @PostMapping("/addpaloma")
    public ResponseDTO<PalomaDTO> agregarPaloma(@Valid @RequestBody PalomaDTO palomaDTO) {
        log.info("REST para agregar una paloma {}", palomaDTO);
        try {
            return palomaService.agregarPaloma(palomaDTO);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    /**
     *
     * @param palomaDTO
     * @return
     */

    @PostMapping("/actualizarpaloma")
    public ResponseDTO<PalomaDTO> actualizarPaloma(@Valid @RequestBody PalomaDTO palomaDTO) {
        log.info("REST para actualizar una paloma {}", palomaDTO);
        try {
            return palomaService.actualizarPaloma(palomaDTO);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    /**
     * obtener los vuelos de un paradero
     * @param colombofiloId
     * @return
     */
    @GetMapping("/obtenerpalomas/{colombofiloId}")
    public ResponseDTO<List<PalomaDTO>> obtenerPalomas(@PathVariable("colombofiloId") Long colombofiloId) {
        log.info("REST para obtener las palomas del colombofilo: {}", colombofiloId);
        try {
            return palomaService.obtenerPalomasByColombofilo(colombofiloId);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    /**
     * obtener un vuelo
     * @param id
     * @return
     */
    @GetMapping("/obtenerpaloma/{id}")
    public ResponseDTO<PalomaDTO> obtenerPaloma(@PathVariable("id") Long id) {
        log.info("REST para obtener una paloma por su id: {}", id);
        try {
            return palomaService.obtenerPaloma(id);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    /**
     * {@code POST  /palomas} : Create a new paloma.
     *
     * @param paloma the paloma to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paloma, or with status {@code 400 (Bad Request)} if the paloma has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Paloma> createPaloma(@Valid @RequestBody Paloma paloma) throws URISyntaxException {
        log.debug("REST request to save Paloma : {}", paloma);
        if (paloma.getId() != null) {
            throw new BadRequestAlertException("A new paloma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Paloma result = palomaService.save(paloma);
        return ResponseEntity
            .created(new URI("/api/palomas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /palomas/:id} : Updates an existing paloma.
     *
     * @param id the id of the paloma to save.
     * @param paloma the paloma to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paloma,
     * or with status {@code 400 (Bad Request)} if the paloma is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paloma couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Paloma> updatePaloma(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Paloma paloma
    ) throws URISyntaxException {
        log.debug("REST request to update Paloma : {}, {}", id, paloma);
        if (paloma.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paloma.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!palomaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Paloma result = palomaService.update(paloma);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paloma.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /palomas/:id} : Partial updates given fields of an existing paloma, field will ignore if it is null
     *
     * @param id the id of the paloma to save.
     * @param paloma the paloma to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paloma,
     * or with status {@code 400 (Bad Request)} if the paloma is not valid,
     * or with status {@code 404 (Not Found)} if the paloma is not found,
     * or with status {@code 500 (Internal Server Error)} if the paloma couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Paloma> partialUpdatePaloma(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Paloma paloma
    ) throws URISyntaxException {
        log.debug("REST request to partial update Paloma partially : {}, {}", id, paloma);
        if (paloma.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paloma.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!palomaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Paloma> result = palomaService.partialUpdate(paloma);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paloma.getId().toString())
        );
    }

    /**
     * {@code GET  /palomas} : get all the palomas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of palomas in body.
     */
    @GetMapping("")
    public List<Paloma> getAllPalomas() {
        log.debug("REST request to get all Palomas");
        return palomaService.findAll();
    }

    /**
     * {@code GET  /palomas/:id} : get the "id" paloma.
     *
     * @param id the id of the paloma to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paloma, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Paloma> getPaloma(@PathVariable("id") Long id) {
        log.debug("REST request to get Paloma : {}", id);
        Optional<Paloma> paloma = palomaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paloma);
    }

    /**
     * {@code DELETE  /palomas/:id} : delete the "id" paloma.
     *
     * @param id the id of the paloma to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaloma(@PathVariable("id") Long id) {
        log.debug("REST request to delete Paloma : {}", id);
        palomaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
