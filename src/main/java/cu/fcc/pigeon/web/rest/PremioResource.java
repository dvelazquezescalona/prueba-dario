package cu.fcc.pigeon.web.rest;

import cu.fcc.pigeon.domain.Premio;
import cu.fcc.pigeon.repository.PremioRepository;
import cu.fcc.pigeon.service.PremioService;
import cu.fcc.pigeon.service.dto.*;
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
 * REST controller for managing {@link cu.fcc.pigeon.domain.Premio}.
 */
@RestController
@RequestMapping("/api/premios")
public class PremioResource {

    private final Logger log = LoggerFactory.getLogger(PremioResource.class);

    private static final String ENTITY_NAME = "premio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PremioService premioService;

    private final PremioRepository premioRepository;

    public PremioResource(PremioService premioService, PremioRepository premioRepository) {
        this.premioService = premioService;
        this.premioRepository = premioRepository;
    }

    @PostMapping("/addPremio")
    public ResponseDTO<PremioDTO> agregarPremio(@Valid @RequestBody PremioDTO premioDTO) {
        log.info("REST para agregar un premio {}", premioDTO);
        try {
            return premioService.agregarPremio(premioDTO);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    /**
     *
     * @param premioDTO
     * @return
     */

    @PostMapping("/actualizarPremio")
    public ResponseDTO<PremioDTO> actualizarPremio(@Valid @RequestBody PremioDTO premioDTO) {
        log.info("REST para actualizar un premio {}", premioDTO);
        try {
            return premioService.actualizarPremio(premioDTO);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    @PostMapping("/obtenerPremiosByVuelo")
    public ResponseDTO<List<ReporteVueloDTO>> obtenerPremiosByVuelo(@RequestBody RequestReporteVueloDTO requestReporteVueloDTO) {
        log.info("REST para obtener los premios del vuelo: {}", requestReporteVueloDTO);
        try {
            if (requestReporteVueloDTO.getColombofiloId() < 1) {
                requestReporteVueloDTO.setColombofiloId(null);
            }
            if (requestReporteVueloDTO.getSociedadId() < 1) {
                requestReporteVueloDTO.setSociedadId(null);
            }
            return premioService.obtenerPremiosByVuelo(
                requestReporteVueloDTO.getVueloId(),
                requestReporteVueloDTO.getColombofiloId(),
                requestReporteVueloDTO.getSociedadId()
            );
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    /**
     * obtener los vuelos de un paradero
     * @param palomaId
     * @return
     */
    @GetMapping("/obtenerpremiosbypaloma/{palomaId}")
    public ResponseDTO<List<PremioDTO>> obtenerPremiosByPaloma(@PathVariable("palomaId") Long palomaId) {
        log.info("REST para obtener los premios de la paloma: {}", palomaId);
        try {
            return premioService.obtenerPremiosByPaloma(palomaId);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    /**
     * obtener un vuelo
     * @param id
     * @return
     */
    @GetMapping("/obtenerpremio/{id}")
    public ResponseDTO<PremioDTO> obtenerPremio(@PathVariable("id") Long id) {
        log.info("REST para obtener un premio por su id: {}", id);
        try {
            return premioService.obtenerPremio(id);
        } catch (Exception e) {
            return new ResponseDTO<>(e.getMessage(), Status.ERROR, null);
        }
    }

    /**
     * {@code POST  /premios} : Create a new premio.
     *
     * @param premio the premio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new premio, or with status {@code 400 (Bad Request)} if the premio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Premio> createPremio(@Valid @RequestBody Premio premio) throws URISyntaxException {
        log.debug("REST request to save Premio : {}", premio);
        if (premio.getId() != null) {
            throw new BadRequestAlertException("A new premio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Premio result = premioService.save(premio);
        return ResponseEntity
            .created(new URI("/api/premios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /premios/:id} : Updates an existing premio.
     *
     * @param id the id of the premio to save.
     * @param premio the premio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated premio,
     * or with status {@code 400 (Bad Request)} if the premio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the premio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Premio> updatePremio(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Premio premio
    ) throws URISyntaxException {
        log.debug("REST request to update Premio : {}, {}", id, premio);
        if (premio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, premio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!premioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Premio result = premioService.update(premio);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, premio.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /premios/:id} : Partial updates given fields of an existing premio, field will ignore if it is null
     *
     * @param id the id of the premio to save.
     * @param premio the premio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated premio,
     * or with status {@code 400 (Bad Request)} if the premio is not valid,
     * or with status {@code 404 (Not Found)} if the premio is not found,
     * or with status {@code 500 (Internal Server Error)} if the premio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Premio> partialUpdatePremio(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Premio premio
    ) throws URISyntaxException {
        log.debug("REST request to partial update Premio partially : {}, {}", id, premio);
        if (premio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, premio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!premioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Premio> result = premioService.partialUpdate(premio);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, premio.getId().toString())
        );
    }

    /**
     * {@code GET  /premios} : get all the premios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of premios in body.
     */
    @GetMapping("")
    public List<Premio> getAllPremios() {
        log.debug("REST request to get all Premios");
        return premioService.findAll();
    }

    /**
     * {@code GET  /premios/:id} : get the "id" premio.
     *
     * @param id the id of the premio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the premio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Premio> getPremio(@PathVariable("id") Long id) {
        log.debug("REST request to get Premio : {}", id);
        Optional<Premio> premio = premioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(premio);
    }

    /**
     * {@code DELETE  /premios/:id} : delete the "id" premio.
     *
     * @param id the id of the premio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePremio(@PathVariable("id") Long id) {
        log.debug("REST request to delete Premio : {}", id);
        premioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
