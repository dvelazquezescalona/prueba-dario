package cu.fcc.pigeon.web.rest;

import cu.fcc.pigeon.domain.ColombofiloVuelo;
import cu.fcc.pigeon.repository.ColombofiloVueloRepository;
import cu.fcc.pigeon.service.ColombofiloVueloService;
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
 * REST controller for managing {@link cu.fcc.pigeon.domain.ColombofiloVuelo}.
 */
@RestController
@RequestMapping("/api/colombofilo-vuelos")
public class ColombofiloVueloResource {

    private final Logger log = LoggerFactory.getLogger(ColombofiloVueloResource.class);

    private static final String ENTITY_NAME = "colombofiloVuelo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ColombofiloVueloService colombofiloVueloService;

    private final ColombofiloVueloRepository colombofiloVueloRepository;

    public ColombofiloVueloResource(
        ColombofiloVueloService colombofiloVueloService,
        ColombofiloVueloRepository colombofiloVueloRepository
    ) {
        this.colombofiloVueloService = colombofiloVueloService;
        this.colombofiloVueloRepository = colombofiloVueloRepository;
    }

    /**
     * {@code POST  /colombofilo-vuelos} : Create a new colombofiloVuelo.
     *
     * @param colombofiloVuelo the colombofiloVuelo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new colombofiloVuelo, or with status {@code 400 (Bad Request)} if the colombofiloVuelo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ColombofiloVuelo> createColombofiloVuelo(@Valid @RequestBody ColombofiloVuelo colombofiloVuelo)
        throws URISyntaxException {
        log.debug("REST request to save ColombofiloVuelo : {}", colombofiloVuelo);
        if (colombofiloVuelo.getId() != null) {
            throw new BadRequestAlertException("A new colombofiloVuelo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ColombofiloVuelo result = colombofiloVueloService.save(colombofiloVuelo);
        return ResponseEntity
            .created(new URI("/api/colombofilo-vuelos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /colombofilo-vuelos/:id} : Updates an existing colombofiloVuelo.
     *
     * @param id the id of the colombofiloVuelo to save.
     * @param colombofiloVuelo the colombofiloVuelo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated colombofiloVuelo,
     * or with status {@code 400 (Bad Request)} if the colombofiloVuelo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the colombofiloVuelo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ColombofiloVuelo> updateColombofiloVuelo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ColombofiloVuelo colombofiloVuelo
    ) throws URISyntaxException {
        log.debug("REST request to update ColombofiloVuelo : {}, {}", id, colombofiloVuelo);
        if (colombofiloVuelo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, colombofiloVuelo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!colombofiloVueloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ColombofiloVuelo result = colombofiloVueloService.update(colombofiloVuelo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, colombofiloVuelo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /colombofilo-vuelos/:id} : Partial updates given fields of an existing colombofiloVuelo, field will ignore if it is null
     *
     * @param id the id of the colombofiloVuelo to save.
     * @param colombofiloVuelo the colombofiloVuelo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated colombofiloVuelo,
     * or with status {@code 400 (Bad Request)} if the colombofiloVuelo is not valid,
     * or with status {@code 404 (Not Found)} if the colombofiloVuelo is not found,
     * or with status {@code 500 (Internal Server Error)} if the colombofiloVuelo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ColombofiloVuelo> partialUpdateColombofiloVuelo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ColombofiloVuelo colombofiloVuelo
    ) throws URISyntaxException {
        log.debug("REST request to partial update ColombofiloVuelo partially : {}, {}", id, colombofiloVuelo);
        if (colombofiloVuelo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, colombofiloVuelo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!colombofiloVueloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ColombofiloVuelo> result = colombofiloVueloService.partialUpdate(colombofiloVuelo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, colombofiloVuelo.getId().toString())
        );
    }

    /**
     * {@code GET  /colombofilo-vuelos} : get all the colombofiloVuelos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of colombofiloVuelos in body.
     */
    @GetMapping("")
    public List<ColombofiloVuelo> getAllColombofiloVuelos() {
        log.debug("REST request to get all ColombofiloVuelos");
        return colombofiloVueloService.findAll();
    }

    /**
     * {@code GET  /colombofilo-vuelos/:id} : get the "id" colombofiloVuelo.
     *
     * @param id the id of the colombofiloVuelo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the colombofiloVuelo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ColombofiloVuelo> getColombofiloVuelo(@PathVariable("id") Long id) {
        log.debug("REST request to get ColombofiloVuelo : {}", id);
        Optional<ColombofiloVuelo> colombofiloVuelo = colombofiloVueloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(colombofiloVuelo);
    }

    /**
     * {@code DELETE  /colombofilo-vuelos/:id} : delete the "id" colombofiloVuelo.
     *
     * @param id the id of the colombofiloVuelo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColombofiloVuelo(@PathVariable("id") Long id) {
        log.debug("REST request to delete ColombofiloVuelo : {}", id);
        colombofiloVueloService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
