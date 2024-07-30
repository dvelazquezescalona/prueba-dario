package cu.fcc.pigeon.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cu.fcc.pigeon.IntegrationTest;
import cu.fcc.pigeon.domain.Sociedad;
import cu.fcc.pigeon.repository.SociedadRepository;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link SociedadResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SociedadResourceIT {

    private static final String DEFAULT_NOMBRE_SOCIEDAD = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_SOCIEDAD = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUD = 1D;
    private static final Double UPDATED_LATITUD = 2D;

    private static final Double DEFAULT_LONGITUD = 1D;
    private static final Double UPDATED_LONGITUD = 2D;

    private static final String ENTITY_API_URL = "/api/sociedads";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SociedadRepository sociedadRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSociedadMockMvc;

    private Sociedad sociedad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sociedad createEntity(EntityManager em) {
        Sociedad sociedad = new Sociedad().nombreSociedad(DEFAULT_NOMBRE_SOCIEDAD).latitud(DEFAULT_LATITUD).longitud(DEFAULT_LONGITUD);
        return sociedad;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sociedad createUpdatedEntity(EntityManager em) {
        Sociedad sociedad = new Sociedad().nombreSociedad(UPDATED_NOMBRE_SOCIEDAD).latitud(UPDATED_LATITUD).longitud(UPDATED_LONGITUD);
        return sociedad;
    }

    @BeforeEach
    public void initTest() {
        sociedad = createEntity(em);
    }

    //@Test
    @Transactional
    void createSociedad() throws Exception {
        int databaseSizeBeforeCreate = sociedadRepository.findAll().size();
        // Create the Sociedad
        restSociedadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sociedad)))
            .andExpect(status().isCreated());

        // Validate the Sociedad in the database
        List<Sociedad> sociedadList = sociedadRepository.findAll();
        assertThat(sociedadList).hasSize(databaseSizeBeforeCreate + 1);
        Sociedad testSociedad = sociedadList.get(sociedadList.size() - 1);
        assertThat(testSociedad.getNombreSociedad()).isEqualTo(DEFAULT_NOMBRE_SOCIEDAD);
        assertThat(testSociedad.getLatitud()).isEqualTo(DEFAULT_LATITUD);
        assertThat(testSociedad.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
    }

    //@Test
    @Transactional
    void createSociedadWithExistingId() throws Exception {
        // Create the Sociedad with an existing ID
        sociedad.setId(1L);

        int databaseSizeBeforeCreate = sociedadRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSociedadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sociedad)))
            .andExpect(status().isBadRequest());

        // Validate the Sociedad in the database
        List<Sociedad> sociedadList = sociedadRepository.findAll();
        assertThat(sociedadList).hasSize(databaseSizeBeforeCreate);
    }

    //@Test
    @Transactional
    void checkNombreSociedadIsRequired() throws Exception {
        int databaseSizeBeforeTest = sociedadRepository.findAll().size();
        // set the field null
        sociedad.setNombreSociedad(null);

        // Create the Sociedad, which fails.

        restSociedadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sociedad)))
            .andExpect(status().isBadRequest());

        List<Sociedad> sociedadList = sociedadRepository.findAll();
        assertThat(sociedadList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkLatitudIsRequired() throws Exception {
        int databaseSizeBeforeTest = sociedadRepository.findAll().size();
        // set the field null
        sociedad.setLatitud(null);

        // Create the Sociedad, which fails.

        restSociedadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sociedad)))
            .andExpect(status().isBadRequest());

        List<Sociedad> sociedadList = sociedadRepository.findAll();
        assertThat(sociedadList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkLongitudIsRequired() throws Exception {
        int databaseSizeBeforeTest = sociedadRepository.findAll().size();
        // set the field null
        sociedad.setLongitud(null);

        // Create the Sociedad, which fails.

        restSociedadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sociedad)))
            .andExpect(status().isBadRequest());

        List<Sociedad> sociedadList = sociedadRepository.findAll();
        assertThat(sociedadList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void getAllSociedads() throws Exception {
        // Initialize the database
        sociedadRepository.saveAndFlush(sociedad);

        // Get all the sociedadList
        restSociedadMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sociedad.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreSociedad").value(hasItem(DEFAULT_NOMBRE_SOCIEDAD)))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD.doubleValue())))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD.doubleValue())));
    }

    //@Test
    @Transactional
    void getSociedad() throws Exception {
        // Initialize the database
        sociedadRepository.saveAndFlush(sociedad);

        // Get the sociedad
        restSociedadMockMvc
            .perform(get(ENTITY_API_URL_ID, sociedad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sociedad.getId().intValue()))
            .andExpect(jsonPath("$.nombreSociedad").value(DEFAULT_NOMBRE_SOCIEDAD))
            .andExpect(jsonPath("$.latitud").value(DEFAULT_LATITUD.doubleValue()))
            .andExpect(jsonPath("$.longitud").value(DEFAULT_LONGITUD.doubleValue()));
    }

    //@Test
    @Transactional
    void getNonExistingSociedad() throws Exception {
        // Get the sociedad
        restSociedadMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    //@Test
    @Transactional
    void putExistingSociedad() throws Exception {
        // Initialize the database
        sociedadRepository.saveAndFlush(sociedad);

        int databaseSizeBeforeUpdate = sociedadRepository.findAll().size();

        // Update the sociedad
        Sociedad updatedSociedad = sociedadRepository.findById(sociedad.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSociedad are not directly saved in db
        em.detach(updatedSociedad);
        updatedSociedad.nombreSociedad(UPDATED_NOMBRE_SOCIEDAD).latitud(UPDATED_LATITUD).longitud(UPDATED_LONGITUD);

        restSociedadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSociedad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSociedad))
            )
            .andExpect(status().isOk());

        // Validate the Sociedad in the database
        List<Sociedad> sociedadList = sociedadRepository.findAll();
        assertThat(sociedadList).hasSize(databaseSizeBeforeUpdate);
        Sociedad testSociedad = sociedadList.get(sociedadList.size() - 1);
        assertThat(testSociedad.getNombreSociedad()).isEqualTo(UPDATED_NOMBRE_SOCIEDAD);
        assertThat(testSociedad.getLatitud()).isEqualTo(UPDATED_LATITUD);
        assertThat(testSociedad.getLongitud()).isEqualTo(UPDATED_LONGITUD);
    }

    //@Test
    @Transactional
    void putNonExistingSociedad() throws Exception {
        int databaseSizeBeforeUpdate = sociedadRepository.findAll().size();
        sociedad.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSociedadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sociedad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sociedad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sociedad in the database
        List<Sociedad> sociedadList = sociedadRepository.findAll();
        assertThat(sociedadList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithIdMismatchSociedad() throws Exception {
        int databaseSizeBeforeUpdate = sociedadRepository.findAll().size();
        sociedad.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSociedadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sociedad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sociedad in the database
        List<Sociedad> sociedadList = sociedadRepository.findAll();
        assertThat(sociedadList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithMissingIdPathParamSociedad() throws Exception {
        int databaseSizeBeforeUpdate = sociedadRepository.findAll().size();
        sociedad.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSociedadMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sociedad)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sociedad in the database
        List<Sociedad> sociedadList = sociedadRepository.findAll();
        assertThat(sociedadList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void partialUpdateSociedadWithPatch() throws Exception {
        // Initialize the database
        sociedadRepository.saveAndFlush(sociedad);

        int databaseSizeBeforeUpdate = sociedadRepository.findAll().size();

        // Update the sociedad using partial update
        Sociedad partialUpdatedSociedad = new Sociedad();
        partialUpdatedSociedad.setId(sociedad.getId());

        restSociedadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSociedad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSociedad))
            )
            .andExpect(status().isOk());

        // Validate the Sociedad in the database
        List<Sociedad> sociedadList = sociedadRepository.findAll();
        assertThat(sociedadList).hasSize(databaseSizeBeforeUpdate);
        Sociedad testSociedad = sociedadList.get(sociedadList.size() - 1);
        assertThat(testSociedad.getNombreSociedad()).isEqualTo(DEFAULT_NOMBRE_SOCIEDAD);
        assertThat(testSociedad.getLatitud()).isEqualTo(DEFAULT_LATITUD);
        assertThat(testSociedad.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
    }

    //@Test
    @Transactional
    void fullUpdateSociedadWithPatch() throws Exception {
        // Initialize the database
        sociedadRepository.saveAndFlush(sociedad);

        int databaseSizeBeforeUpdate = sociedadRepository.findAll().size();

        // Update the sociedad using partial update
        Sociedad partialUpdatedSociedad = new Sociedad();
        partialUpdatedSociedad.setId(sociedad.getId());

        partialUpdatedSociedad.nombreSociedad(UPDATED_NOMBRE_SOCIEDAD).latitud(UPDATED_LATITUD).longitud(UPDATED_LONGITUD);

        restSociedadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSociedad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSociedad))
            )
            .andExpect(status().isOk());

        // Validate the Sociedad in the database
        List<Sociedad> sociedadList = sociedadRepository.findAll();
        assertThat(sociedadList).hasSize(databaseSizeBeforeUpdate);
        Sociedad testSociedad = sociedadList.get(sociedadList.size() - 1);
        assertThat(testSociedad.getNombreSociedad()).isEqualTo(UPDATED_NOMBRE_SOCIEDAD);
        assertThat(testSociedad.getLatitud()).isEqualTo(UPDATED_LATITUD);
        assertThat(testSociedad.getLongitud()).isEqualTo(UPDATED_LONGITUD);
    }

    //@Test
    @Transactional
    void patchNonExistingSociedad() throws Exception {
        int databaseSizeBeforeUpdate = sociedadRepository.findAll().size();
        sociedad.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSociedadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sociedad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sociedad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sociedad in the database
        List<Sociedad> sociedadList = sociedadRepository.findAll();
        assertThat(sociedadList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithIdMismatchSociedad() throws Exception {
        int databaseSizeBeforeUpdate = sociedadRepository.findAll().size();
        sociedad.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSociedadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sociedad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sociedad in the database
        List<Sociedad> sociedadList = sociedadRepository.findAll();
        assertThat(sociedadList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithMissingIdPathParamSociedad() throws Exception {
        int databaseSizeBeforeUpdate = sociedadRepository.findAll().size();
        sociedad.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSociedadMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sociedad)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sociedad in the database
        List<Sociedad> sociedadList = sociedadRepository.findAll();
        assertThat(sociedadList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void deleteSociedad() throws Exception {
        // Initialize the database
        sociedadRepository.saveAndFlush(sociedad);

        int databaseSizeBeforeDelete = sociedadRepository.findAll().size();

        // Delete the sociedad
        restSociedadMockMvc
            .perform(delete(ENTITY_API_URL_ID, sociedad.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sociedad> sociedadList = sociedadRepository.findAll();
        assertThat(sociedadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
