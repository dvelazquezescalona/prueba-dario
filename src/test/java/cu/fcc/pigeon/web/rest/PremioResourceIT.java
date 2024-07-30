package cu.fcc.pigeon.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cu.fcc.pigeon.IntegrationTest;
import cu.fcc.pigeon.domain.Premio;
import cu.fcc.pigeon.repository.PremioRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PremioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PremioResourceIT {

    private static final String DEFAULT_DESIGNADA = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNADA = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_ARRIBO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_ARRIBO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_TIEMPO_VUELO = 1D;
    private static final Double UPDATED_TIEMPO_VUELO = 2D;

    private static final Double DEFAULT_VELOCIDAD = 1D;
    private static final Double UPDATED_VELOCIDAD = 2D;

    private static final Integer DEFAULT_LUGAR = 1;
    private static final Integer UPDATED_LUGAR = 2;

    private static final Double DEFAULT_PUNTOS = 1D;
    private static final Double UPDATED_PUNTOS = 2D;

    private static final String ENTITY_API_URL = "/api/premios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PremioRepository premioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPremioMockMvc;

    private Premio premio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Premio createEntity(EntityManager em) {
        Premio premio = new Premio()
            .designada(DEFAULT_DESIGNADA)
            .fechaArribo(DEFAULT_FECHA_ARRIBO)
            .tiempoVuelo(DEFAULT_TIEMPO_VUELO)
            .velocidad(DEFAULT_VELOCIDAD)
            .lugar(DEFAULT_LUGAR)
            .puntos(DEFAULT_PUNTOS);
        return premio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Premio createUpdatedEntity(EntityManager em) {
        Premio premio = new Premio()
            .designada(UPDATED_DESIGNADA)
            .fechaArribo(UPDATED_FECHA_ARRIBO)
            .tiempoVuelo(UPDATED_TIEMPO_VUELO)
            .velocidad(UPDATED_VELOCIDAD)
            .lugar(UPDATED_LUGAR)
            .puntos(UPDATED_PUNTOS);
        return premio;
    }

    @BeforeEach
    public void initTest() {
        premio = createEntity(em);
    }

    //@Test
    @Transactional
    void createPremio() throws Exception {
        int databaseSizeBeforeCreate = premioRepository.findAll().size();
        // Create the Premio
        restPremioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(premio)))
            .andExpect(status().isCreated());

        // Validate the Premio in the database
        List<Premio> premioList = premioRepository.findAll();
        assertThat(premioList).hasSize(databaseSizeBeforeCreate + 1);
        Premio testPremio = premioList.get(premioList.size() - 1);
        assertThat(testPremio.getDesignada()).isEqualTo(DEFAULT_DESIGNADA);
        assertThat(testPremio.getFechaArribo()).isEqualTo(DEFAULT_FECHA_ARRIBO);
        assertThat(testPremio.getTiempoVuelo()).isEqualTo(DEFAULT_TIEMPO_VUELO);
        assertThat(testPremio.getVelocidad()).isEqualTo(DEFAULT_VELOCIDAD);
        assertThat(testPremio.getLugar()).isEqualTo(DEFAULT_LUGAR);
        assertThat(testPremio.getPuntos()).isEqualTo(DEFAULT_PUNTOS);
    }

    //@Test
    @Transactional
    void createPremioWithExistingId() throws Exception {
        // Create the Premio with an existing ID
        premio.setId(1L);

        int databaseSizeBeforeCreate = premioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPremioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(premio)))
            .andExpect(status().isBadRequest());

        // Validate the Premio in the database
        List<Premio> premioList = premioRepository.findAll();
        assertThat(premioList).hasSize(databaseSizeBeforeCreate);
    }

    //@Test
    @Transactional
    void checkDesignadaIsRequired() throws Exception {
        int databaseSizeBeforeTest = premioRepository.findAll().size();
        // set the field null
        premio.setDesignada(null);

        // Create the Premio, which fails.

        restPremioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(premio)))
            .andExpect(status().isBadRequest());

        List<Premio> premioList = premioRepository.findAll();
        assertThat(premioList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkFechaArriboIsRequired() throws Exception {
        int databaseSizeBeforeTest = premioRepository.findAll().size();
        // set the field null
        premio.setFechaArribo(null);

        // Create the Premio, which fails.

        restPremioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(premio)))
            .andExpect(status().isBadRequest());

        List<Premio> premioList = premioRepository.findAll();
        assertThat(premioList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void getAllPremios() throws Exception {
        // Initialize the database
        premioRepository.saveAndFlush(premio);

        // Get all the premioList
        restPremioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(premio.getId().intValue())))
            .andExpect(jsonPath("$.[*].designada").value(hasItem(DEFAULT_DESIGNADA)))
            .andExpect(jsonPath("$.[*].fechaArribo").value(hasItem(DEFAULT_FECHA_ARRIBO.toString())))
            .andExpect(jsonPath("$.[*].tiempoVuelo").value(hasItem(DEFAULT_TIEMPO_VUELO)))
            .andExpect(jsonPath("$.[*].velocidad").value(hasItem(DEFAULT_VELOCIDAD.doubleValue())))
            .andExpect(jsonPath("$.[*].lugar").value(hasItem(DEFAULT_LUGAR)))
            .andExpect(jsonPath("$.[*].puntos").value(hasItem(DEFAULT_PUNTOS.doubleValue())));
    }

    //@Test
    @Transactional
    void getPremio() throws Exception {
        // Initialize the database
        premioRepository.saveAndFlush(premio);

        // Get the premio
        restPremioMockMvc
            .perform(get(ENTITY_API_URL_ID, premio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(premio.getId().intValue()))
            .andExpect(jsonPath("$.designada").value(DEFAULT_DESIGNADA))
            .andExpect(jsonPath("$.fechaArribo").value(DEFAULT_FECHA_ARRIBO.toString()))
            .andExpect(jsonPath("$.tiempoVuelo").value(DEFAULT_TIEMPO_VUELO))
            .andExpect(jsonPath("$.velocidad").value(DEFAULT_VELOCIDAD.doubleValue()))
            .andExpect(jsonPath("$.lugar").value(DEFAULT_LUGAR))
            .andExpect(jsonPath("$.puntos").value(DEFAULT_PUNTOS.doubleValue()));
    }

    //@Test
    @Transactional
    void getNonExistingPremio() throws Exception {
        // Get the premio
        restPremioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    //@Test
    @Transactional
    void putExistingPremio() throws Exception {
        // Initialize the database
        premioRepository.saveAndFlush(premio);

        int databaseSizeBeforeUpdate = premioRepository.findAll().size();

        // Update the premio
        Premio updatedPremio = premioRepository.findById(premio.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPremio are not directly saved in db
        em.detach(updatedPremio);
        updatedPremio
            .designada(UPDATED_DESIGNADA)
            .fechaArribo(UPDATED_FECHA_ARRIBO)
            .tiempoVuelo(UPDATED_TIEMPO_VUELO)
            .velocidad(UPDATED_VELOCIDAD)
            .lugar(UPDATED_LUGAR)
            .puntos(UPDATED_PUNTOS);

        restPremioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPremio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPremio))
            )
            .andExpect(status().isOk());

        // Validate the Premio in the database
        List<Premio> premioList = premioRepository.findAll();
        assertThat(premioList).hasSize(databaseSizeBeforeUpdate);
        Premio testPremio = premioList.get(premioList.size() - 1);
        assertThat(testPremio.getDesignada()).isEqualTo(UPDATED_DESIGNADA);
        assertThat(testPremio.getFechaArribo()).isEqualTo(UPDATED_FECHA_ARRIBO);
        assertThat(testPremio.getTiempoVuelo()).isEqualTo(UPDATED_TIEMPO_VUELO);
        assertThat(testPremio.getVelocidad()).isEqualTo(UPDATED_VELOCIDAD);
        assertThat(testPremio.getLugar()).isEqualTo(UPDATED_LUGAR);
        assertThat(testPremio.getPuntos()).isEqualTo(UPDATED_PUNTOS);
    }

    //@Test
    @Transactional
    void putNonExistingPremio() throws Exception {
        int databaseSizeBeforeUpdate = premioRepository.findAll().size();
        premio.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPremioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, premio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(premio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Premio in the database
        List<Premio> premioList = premioRepository.findAll();
        assertThat(premioList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithIdMismatchPremio() throws Exception {
        int databaseSizeBeforeUpdate = premioRepository.findAll().size();
        premio.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPremioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(premio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Premio in the database
        List<Premio> premioList = premioRepository.findAll();
        assertThat(premioList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithMissingIdPathParamPremio() throws Exception {
        int databaseSizeBeforeUpdate = premioRepository.findAll().size();
        premio.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPremioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(premio)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Premio in the database
        List<Premio> premioList = premioRepository.findAll();
        assertThat(premioList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void partialUpdatePremioWithPatch() throws Exception {
        // Initialize the database
        premioRepository.saveAndFlush(premio);

        int databaseSizeBeforeUpdate = premioRepository.findAll().size();

        // Update the premio using partial update
        Premio partialUpdatedPremio = new Premio();
        partialUpdatedPremio.setId(premio.getId());

        partialUpdatedPremio.velocidad(UPDATED_VELOCIDAD);

        restPremioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPremio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPremio))
            )
            .andExpect(status().isOk());

        // Validate the Premio in the database
        List<Premio> premioList = premioRepository.findAll();
        assertThat(premioList).hasSize(databaseSizeBeforeUpdate);
        Premio testPremio = premioList.get(premioList.size() - 1);
        assertThat(testPremio.getDesignada()).isEqualTo(DEFAULT_DESIGNADA);
        assertThat(testPremio.getFechaArribo()).isEqualTo(DEFAULT_FECHA_ARRIBO);
        assertThat(testPremio.getTiempoVuelo()).isEqualTo(DEFAULT_TIEMPO_VUELO);
        assertThat(testPremio.getVelocidad()).isEqualTo(UPDATED_VELOCIDAD);
        assertThat(testPremio.getLugar()).isEqualTo(DEFAULT_LUGAR);
        assertThat(testPremio.getPuntos()).isEqualTo(DEFAULT_PUNTOS);
    }

    //@Test
    @Transactional
    void fullUpdatePremioWithPatch() throws Exception {
        // Initialize the database
        premioRepository.saveAndFlush(premio);

        int databaseSizeBeforeUpdate = premioRepository.findAll().size();

        // Update the premio using partial update
        Premio partialUpdatedPremio = new Premio();
        partialUpdatedPremio.setId(premio.getId());

        partialUpdatedPremio
            .designada(UPDATED_DESIGNADA)
            .fechaArribo(UPDATED_FECHA_ARRIBO)
            .tiempoVuelo(UPDATED_TIEMPO_VUELO)
            .velocidad(UPDATED_VELOCIDAD)
            .lugar(UPDATED_LUGAR)
            .puntos(UPDATED_PUNTOS);

        restPremioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPremio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPremio))
            )
            .andExpect(status().isOk());

        // Validate the Premio in the database
        List<Premio> premioList = premioRepository.findAll();
        assertThat(premioList).hasSize(databaseSizeBeforeUpdate);
        Premio testPremio = premioList.get(premioList.size() - 1);
        assertThat(testPremio.getDesignada()).isEqualTo(UPDATED_DESIGNADA);
        assertThat(testPremio.getFechaArribo()).isEqualTo(UPDATED_FECHA_ARRIBO);
        assertThat(testPremio.getTiempoVuelo()).isEqualTo(UPDATED_TIEMPO_VUELO);
        assertThat(testPremio.getVelocidad()).isEqualTo(UPDATED_VELOCIDAD);
        assertThat(testPremio.getLugar()).isEqualTo(UPDATED_LUGAR);
        assertThat(testPremio.getPuntos()).isEqualTo(UPDATED_PUNTOS);
    }

    //@Test
    @Transactional
    void patchNonExistingPremio() throws Exception {
        int databaseSizeBeforeUpdate = premioRepository.findAll().size();
        premio.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPremioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, premio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(premio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Premio in the database
        List<Premio> premioList = premioRepository.findAll();
        assertThat(premioList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithIdMismatchPremio() throws Exception {
        int databaseSizeBeforeUpdate = premioRepository.findAll().size();
        premio.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPremioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(premio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Premio in the database
        List<Premio> premioList = premioRepository.findAll();
        assertThat(premioList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithMissingIdPathParamPremio() throws Exception {
        int databaseSizeBeforeUpdate = premioRepository.findAll().size();
        premio.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPremioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(premio)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Premio in the database
        List<Premio> premioList = premioRepository.findAll();
        assertThat(premioList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void deletePremio() throws Exception {
        // Initialize the database
        premioRepository.saveAndFlush(premio);

        int databaseSizeBeforeDelete = premioRepository.findAll().size();

        // Delete the premio
        restPremioMockMvc
            .perform(delete(ENTITY_API_URL_ID, premio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Premio> premioList = premioRepository.findAll();
        assertThat(premioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
