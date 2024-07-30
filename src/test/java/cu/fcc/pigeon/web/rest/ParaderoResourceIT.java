package cu.fcc.pigeon.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cu.fcc.pigeon.IntegrationTest;
import cu.fcc.pigeon.domain.Paradero;
import cu.fcc.pigeon.repository.ParaderoRepository;
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
 * Integration tests for the {@link ParaderoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParaderoResourceIT {

    private static final String DEFAULT_NOMBRE_PARADERO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_PARADERO = "BBBBBBBBBB";

    private static final Integer DEFAULT_DISTANCIA_MEDIA = 1;
    private static final Integer UPDATED_DISTANCIA_MEDIA = 2;

    private static final Double DEFAULT_LATITUD = 1D;
    private static final Double UPDATED_LATITUD = 2D;

    private static final Double DEFAULT_LONGITUD = 1D;
    private static final Double UPDATED_LONGITUD = 2D;

    private static final String ENTITY_API_URL = "/api/paraderos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ParaderoRepository paraderoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParaderoMockMvc;

    private Paradero paradero;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paradero createEntity(EntityManager em) {
        Paradero paradero = new Paradero()
            .nombreParadero(DEFAULT_NOMBRE_PARADERO)
            .distanciaMedia(DEFAULT_DISTANCIA_MEDIA)
            .latitud(DEFAULT_LATITUD)
            .longitud(DEFAULT_LONGITUD);
        return paradero;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paradero createUpdatedEntity(EntityManager em) {
        Paradero paradero = new Paradero()
            .nombreParadero(UPDATED_NOMBRE_PARADERO)
            .distanciaMedia(UPDATED_DISTANCIA_MEDIA)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD);
        return paradero;
    }

    @BeforeEach
    public void initTest() {
        paradero = createEntity(em);
    }

    //@Test
    @Transactional
    void createParadero() throws Exception {
        int databaseSizeBeforeCreate = paraderoRepository.findAll().size();
        // Create the Paradero
        restParaderoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paradero)))
            .andExpect(status().isCreated());

        // Validate the Paradero in the database
        List<Paradero> paraderoList = paraderoRepository.findAll();
        assertThat(paraderoList).hasSize(databaseSizeBeforeCreate + 1);
        Paradero testParadero = paraderoList.get(paraderoList.size() - 1);
        assertThat(testParadero.getNombreParadero()).isEqualTo(DEFAULT_NOMBRE_PARADERO);
        assertThat(testParadero.getDistanciaMedia()).isEqualTo(DEFAULT_DISTANCIA_MEDIA);
        assertThat(testParadero.getLatitud()).isEqualTo(DEFAULT_LATITUD);
        assertThat(testParadero.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
    }

    //@Test
    @Transactional
    void createParaderoWithExistingId() throws Exception {
        // Create the Paradero with an existing ID
        paradero.setId(1L);

        int databaseSizeBeforeCreate = paraderoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParaderoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paradero)))
            .andExpect(status().isBadRequest());

        // Validate the Paradero in the database
        List<Paradero> paraderoList = paraderoRepository.findAll();
        assertThat(paraderoList).hasSize(databaseSizeBeforeCreate);
    }

    //@Test
    @Transactional
    void checkNombreParaderoIsRequired() throws Exception {
        int databaseSizeBeforeTest = paraderoRepository.findAll().size();
        // set the field null
        paradero.setNombreParadero(null);

        // Create the Paradero, which fails.

        restParaderoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paradero)))
            .andExpect(status().isBadRequest());

        List<Paradero> paraderoList = paraderoRepository.findAll();
        assertThat(paraderoList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkDistanciaMediaIsRequired() throws Exception {
        int databaseSizeBeforeTest = paraderoRepository.findAll().size();
        // set the field null
        paradero.setDistanciaMedia(null);

        // Create the Paradero, which fails.

        restParaderoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paradero)))
            .andExpect(status().isBadRequest());

        List<Paradero> paraderoList = paraderoRepository.findAll();
        assertThat(paraderoList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkLatitudIsRequired() throws Exception {
        int databaseSizeBeforeTest = paraderoRepository.findAll().size();
        // set the field null
        paradero.setLatitud(null);

        // Create the Paradero, which fails.

        restParaderoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paradero)))
            .andExpect(status().isBadRequest());

        List<Paradero> paraderoList = paraderoRepository.findAll();
        assertThat(paraderoList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkLongitudIsRequired() throws Exception {
        int databaseSizeBeforeTest = paraderoRepository.findAll().size();
        // set the field null
        paradero.setLongitud(null);

        // Create the Paradero, which fails.

        restParaderoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paradero)))
            .andExpect(status().isBadRequest());

        List<Paradero> paraderoList = paraderoRepository.findAll();
        assertThat(paraderoList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void getAllParaderos() throws Exception {
        // Initialize the database
        paraderoRepository.saveAndFlush(paradero);

        // Get all the paraderoList
        restParaderoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paradero.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreParadero").value(hasItem(DEFAULT_NOMBRE_PARADERO)))
            .andExpect(jsonPath("$.[*].distanciaMedia").value(hasItem(DEFAULT_DISTANCIA_MEDIA)))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD.doubleValue())))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD.doubleValue())));
    }

    //@Test
    @Transactional
    void getParadero() throws Exception {
        // Initialize the database
        paraderoRepository.saveAndFlush(paradero);

        // Get the paradero
        restParaderoMockMvc
            .perform(get(ENTITY_API_URL_ID, paradero.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paradero.getId().intValue()))
            .andExpect(jsonPath("$.nombreParadero").value(DEFAULT_NOMBRE_PARADERO))
            .andExpect(jsonPath("$.distanciaMedia").value(DEFAULT_DISTANCIA_MEDIA))
            .andExpect(jsonPath("$.latitud").value(DEFAULT_LATITUD.doubleValue()))
            .andExpect(jsonPath("$.longitud").value(DEFAULT_LONGITUD.doubleValue()));
    }

    //@Test
    @Transactional
    void getNonExistingParadero() throws Exception {
        // Get the paradero
        restParaderoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    //@Test
    @Transactional
    void putExistingParadero() throws Exception {
        // Initialize the database
        paraderoRepository.saveAndFlush(paradero);

        int databaseSizeBeforeUpdate = paraderoRepository.findAll().size();

        // Update the paradero
        Paradero updatedParadero = paraderoRepository.findById(paradero.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedParadero are not directly saved in db
        em.detach(updatedParadero);
        updatedParadero
            .nombreParadero(UPDATED_NOMBRE_PARADERO)
            .distanciaMedia(UPDATED_DISTANCIA_MEDIA)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD);

        restParaderoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedParadero.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedParadero))
            )
            .andExpect(status().isOk());

        // Validate the Paradero in the database
        List<Paradero> paraderoList = paraderoRepository.findAll();
        assertThat(paraderoList).hasSize(databaseSizeBeforeUpdate);
        Paradero testParadero = paraderoList.get(paraderoList.size() - 1);
        assertThat(testParadero.getNombreParadero()).isEqualTo(UPDATED_NOMBRE_PARADERO);
        assertThat(testParadero.getDistanciaMedia()).isEqualTo(UPDATED_DISTANCIA_MEDIA);
        assertThat(testParadero.getLatitud()).isEqualTo(UPDATED_LATITUD);
        assertThat(testParadero.getLongitud()).isEqualTo(UPDATED_LONGITUD);
    }

    //@Test
    @Transactional
    void putNonExistingParadero() throws Exception {
        int databaseSizeBeforeUpdate = paraderoRepository.findAll().size();
        paradero.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParaderoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paradero.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paradero))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paradero in the database
        List<Paradero> paraderoList = paraderoRepository.findAll();
        assertThat(paraderoList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithIdMismatchParadero() throws Exception {
        int databaseSizeBeforeUpdate = paraderoRepository.findAll().size();
        paradero.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParaderoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paradero))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paradero in the database
        List<Paradero> paraderoList = paraderoRepository.findAll();
        assertThat(paraderoList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithMissingIdPathParamParadero() throws Exception {
        int databaseSizeBeforeUpdate = paraderoRepository.findAll().size();
        paradero.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParaderoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paradero)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paradero in the database
        List<Paradero> paraderoList = paraderoRepository.findAll();
        assertThat(paraderoList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void partialUpdateParaderoWithPatch() throws Exception {
        // Initialize the database
        paraderoRepository.saveAndFlush(paradero);

        int databaseSizeBeforeUpdate = paraderoRepository.findAll().size();

        // Update the paradero using partial update
        Paradero partialUpdatedParadero = new Paradero();
        partialUpdatedParadero.setId(paradero.getId());

        partialUpdatedParadero.distanciaMedia(UPDATED_DISTANCIA_MEDIA).longitud(UPDATED_LONGITUD);

        restParaderoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParadero.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParadero))
            )
            .andExpect(status().isOk());

        // Validate the Paradero in the database
        List<Paradero> paraderoList = paraderoRepository.findAll();
        assertThat(paraderoList).hasSize(databaseSizeBeforeUpdate);
        Paradero testParadero = paraderoList.get(paraderoList.size() - 1);
        assertThat(testParadero.getNombreParadero()).isEqualTo(DEFAULT_NOMBRE_PARADERO);
        assertThat(testParadero.getDistanciaMedia()).isEqualTo(UPDATED_DISTANCIA_MEDIA);
        assertThat(testParadero.getLatitud()).isEqualTo(DEFAULT_LATITUD);
        assertThat(testParadero.getLongitud()).isEqualTo(UPDATED_LONGITUD);
    }

    //@Test
    @Transactional
    void fullUpdateParaderoWithPatch() throws Exception {
        // Initialize the database
        paraderoRepository.saveAndFlush(paradero);

        int databaseSizeBeforeUpdate = paraderoRepository.findAll().size();

        // Update the paradero using partial update
        Paradero partialUpdatedParadero = new Paradero();
        partialUpdatedParadero.setId(paradero.getId());

        partialUpdatedParadero
            .nombreParadero(UPDATED_NOMBRE_PARADERO)
            .distanciaMedia(UPDATED_DISTANCIA_MEDIA)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD);

        restParaderoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParadero.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParadero))
            )
            .andExpect(status().isOk());

        // Validate the Paradero in the database
        List<Paradero> paraderoList = paraderoRepository.findAll();
        assertThat(paraderoList).hasSize(databaseSizeBeforeUpdate);
        Paradero testParadero = paraderoList.get(paraderoList.size() - 1);
        assertThat(testParadero.getNombreParadero()).isEqualTo(UPDATED_NOMBRE_PARADERO);
        assertThat(testParadero.getDistanciaMedia()).isEqualTo(UPDATED_DISTANCIA_MEDIA);
        assertThat(testParadero.getLatitud()).isEqualTo(UPDATED_LATITUD);
        assertThat(testParadero.getLongitud()).isEqualTo(UPDATED_LONGITUD);
    }

    //@Test
    @Transactional
    void patchNonExistingParadero() throws Exception {
        int databaseSizeBeforeUpdate = paraderoRepository.findAll().size();
        paradero.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParaderoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paradero.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paradero))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paradero in the database
        List<Paradero> paraderoList = paraderoRepository.findAll();
        assertThat(paraderoList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithIdMismatchParadero() throws Exception {
        int databaseSizeBeforeUpdate = paraderoRepository.findAll().size();
        paradero.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParaderoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paradero))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paradero in the database
        List<Paradero> paraderoList = paraderoRepository.findAll();
        assertThat(paraderoList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithMissingIdPathParamParadero() throws Exception {
        int databaseSizeBeforeUpdate = paraderoRepository.findAll().size();
        paradero.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParaderoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paradero)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paradero in the database
        List<Paradero> paraderoList = paraderoRepository.findAll();
        assertThat(paraderoList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void deleteParadero() throws Exception {
        // Initialize the database
        paraderoRepository.saveAndFlush(paradero);

        int databaseSizeBeforeDelete = paraderoRepository.findAll().size();

        // Delete the paradero
        restParaderoMockMvc
            .perform(delete(ENTITY_API_URL_ID, paradero.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Paradero> paraderoList = paraderoRepository.findAll();
        assertThat(paraderoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
