package cu.fcc.pigeon.web.rest;

import cu.fcc.pigeon.domain.Provincia;
import cu.fcc.pigeon.repository.ProvinciaRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link cu.fcc.pigeon.domain.Provincia}.
 */
@RestController
@RequestMapping("/api/provincias")
@Transactional
public class ProvinciaResource {

    private final Logger log = LoggerFactory.getLogger(ProvinciaResource.class);

    private static final String ENTITY_NAME = "provincia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProvinciaRepository provinciaRepository;

    public ProvinciaResource(ProvinciaRepository provinciaRepository) {
        this.provinciaRepository = provinciaRepository;
    }

    /**
     * {@code POST  /provincias} : Create a new provincia.
     *
     * @param provincia the provincia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new provincia, or with status {@code 400 (Bad Request)} if the provincia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Provincia> createProvincia(@Valid @RequestBody Provincia provincia) throws URISyntaxException {
        log.debug("REST request to save Provincia : {}", provincia);
        if (provincia.getId() != null) {
            throw new BadRequestAlertException("A new provincia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Provincia result = provinciaRepository.save(provincia);
        return ResponseEntity
            .created(new URI("/api/provincias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /provincias/:id} : Updates an existing provincia.
     *
     * @param id the id of the provincia to save.
     * @param provincia the provincia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated provincia,
     * or with status {@code 400 (Bad Request)} if the provincia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the provincia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Provincia> updateProvincia(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Provincia provincia
    ) throws URISyntaxException {
        log.debug("REST request to update Provincia : {}, {}", id, provincia);
        if (provincia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, provincia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!provinciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Provincia result = provinciaRepository.save(provincia);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, provincia.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /provincias/:id} : Partial updates given fields of an existing provincia, field will ignore if it is null
     *
     * @param id the id of the provincia to save.
     * @param provincia the provincia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated provincia,
     * or with status {@code 400 (Bad Request)} if the provincia is not valid,
     * or with status {@code 404 (Not Found)} if the provincia is not found,
     * or with status {@code 500 (Internal Server Error)} if the provincia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Provincia> partialUpdateProvincia(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Provincia provincia
    ) throws URISyntaxException {
        log.debug("REST request to partial update Provincia partially : {}, {}", id, provincia);
        if (provincia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, provincia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!provinciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Provincia> result = provinciaRepository
            .findById(provincia.getId())
            .map(existingProvincia -> {
                if (provincia.getNombreProvincia() != null) {
                    existingProvincia.setNombreProvincia(provincia.getNombreProvincia());
                }

                return existingProvincia;
            })
            .map(provinciaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, provincia.getId().toString())
        );
    }

    /**
     * {@code GET  /provincias} : get all the provincias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of provincias in body.
     */
    @GetMapping("")
    public List<Provincia> getAllProvincias() {
        log.debug("REST request to get all Provincias");
        return provinciaRepository.findAll();
    }

    /**
     * {@code GET  /provincias/:id} : get the "id" provincia.
     *
     * @param id the id of the provincia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the provincia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Provincia> getProvincia(@PathVariable("id") Long id) {
        log.debug("REST request to get Provincia : {}", id);
        Optional<Provincia> provincia = provinciaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(provincia);
    }

    /**
     * {@code DELETE  /provincias/:id} : delete the "id" provincia.
     *
     * @param id the id of the provincia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvincia(@PathVariable("id") Long id) {
        log.debug("REST request to delete Provincia : {}", id);
        provinciaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
