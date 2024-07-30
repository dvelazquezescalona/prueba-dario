package cu.fcc.pigeon.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cu.fcc.pigeon.IntegrationTest;
import cu.fcc.pigeon.domain.ColombofiloVuelo;
import cu.fcc.pigeon.repository.ColombofiloVueloRepository;
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
 * Integration tests for the {@link ColombofiloVueloResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ColombofiloVueloResourceIT {

    private static final Integer DEFAULT_ENVIDAS = 1;
    private static final Integer UPDATED_ENVIDAS = 2;

    private static final Double DEFAULT_DISTANCIA = 1D;
    private static final Double UPDATED_DISTANCIA = 2D;

    private static final String ENTITY_API_URL = "/api/colombofilo-vuelos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ColombofiloVueloRepository colombofiloVueloRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restColombofiloVueloMockMvc;

    private ColombofiloVuelo colombofiloVuelo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ColombofiloVuelo createEntity(EntityManager em) {
        ColombofiloVuelo colombofiloVuelo = new ColombofiloVuelo().enviadas(DEFAULT_ENVIDAS).distancia(DEFAULT_DISTANCIA);
        return colombofiloVuelo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ColombofiloVuelo createUpdatedEntity(EntityManager em) {
        ColombofiloVuelo colombofiloVuelo = new ColombofiloVuelo().enviadas(UPDATED_ENVIDAS).distancia(UPDATED_DISTANCIA);
        return colombofiloVuelo;
    }

    @BeforeEach
    public void initTest() {
        colombofiloVuelo = createEntity(em);
    }

    //@Test
    @Transactional
    void createColombofiloVuelo() throws Exception {
        int databaseSizeBeforeCreate = colombofiloVueloRepository.findAll().size();
        // Create the ColombofiloVuelo
        restColombofiloVueloMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colombofiloVuelo))
            )
            .andExpect(status().isCreated());

        // Validate the ColombofiloVuelo in the database
        List<ColombofiloVuelo> colombofiloVueloList = colombofiloVueloRepository.findAll();
        assertThat(colombofiloVueloList).hasSize(databaseSizeBeforeCreate + 1);
        ColombofiloVuelo testColombofiloVuelo = colombofiloVueloList.get(colombofiloVueloList.size() - 1);
        assertThat(testColombofiloVuelo.getEnviadas()).isEqualTo(DEFAULT_ENVIDAS);
        assertThat(testColombofiloVuelo.getDistancia()).isEqualTo(DEFAULT_DISTANCIA);
    }

    //@Test
    @Transactional
    void createColombofiloVueloWithExistingId() throws Exception {
        // Create the ColombofiloVuelo with an existing ID
        colombofiloVuelo.setId(1L);

        int databaseSizeBeforeCreate = colombofiloVueloRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restColombofiloVueloMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colombofiloVuelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ColombofiloVuelo in the database
        List<ColombofiloVuelo> colombofiloVueloList = colombofiloVueloRepository.findAll();
        assertThat(colombofiloVueloList).hasSize(databaseSizeBeforeCreate);
    }

    //@Test
    @Transactional
    void checkEnvidasIsRequired() throws Exception {
        int databaseSizeBeforeTest = colombofiloVueloRepository.findAll().size();
        // set the field null
        colombofiloVuelo.setEnviadas(null);

        // Create the ColombofiloVuelo, which fails.

        restColombofiloVueloMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colombofiloVuelo))
            )
            .andExpect(status().isBadRequest());

        List<ColombofiloVuelo> colombofiloVueloList = colombofiloVueloRepository.findAll();
        assertThat(colombofiloVueloList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkDistanciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = colombofiloVueloRepository.findAll().size();
        // set the field null
        colombofiloVuelo.setDistancia(null);

        // Create the ColombofiloVuelo, which fails.

        restColombofiloVueloMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colombofiloVuelo))
            )
            .andExpect(status().isBadRequest());

        List<ColombofiloVuelo> colombofiloVueloList = colombofiloVueloRepository.findAll();
        assertThat(colombofiloVueloList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void getAllColombofiloVuelos() throws Exception {
        // Initialize the database
        colombofiloVueloRepository.saveAndFlush(colombofiloVuelo);

        // Get all the colombofiloVueloList
        restColombofiloVueloMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(colombofiloVuelo.getId().intValue())))
            .andExpect(jsonPath("$.[*].envidas").value(hasItem(DEFAULT_ENVIDAS)))
            .andExpect(jsonPath("$.[*].distancia").value(hasItem(DEFAULT_DISTANCIA.doubleValue())));
    }

    //@Test
    @Transactional
    void getColombofiloVuelo() throws Exception {
        // Initialize the database
        colombofiloVueloRepository.saveAndFlush(colombofiloVuelo);

        // Get the colombofiloVuelo
        restColombofiloVueloMockMvc
            .perform(get(ENTITY_API_URL_ID, colombofiloVuelo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(colombofiloVuelo.getId().intValue()))
            .andExpect(jsonPath("$.envidas").value(DEFAULT_ENVIDAS))
            .andExpect(jsonPath("$.distancia").value(DEFAULT_DISTANCIA.doubleValue()));
    }

    //@Test
    @Transactional
    void getNonExistingColombofiloVuelo() throws Exception {
        // Get the colombofiloVuelo
        restColombofiloVueloMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    //@Test
    @Transactional
    void putExistingColombofiloVuelo() throws Exception {
        // Initialize the database
        colombofiloVueloRepository.saveAndFlush(colombofiloVuelo);

        int databaseSizeBeforeUpdate = colombofiloVueloRepository.findAll().size();

        // Update the colombofiloVuelo
        ColombofiloVuelo updatedColombofiloVuelo = colombofiloVueloRepository.findById(colombofiloVuelo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedColombofiloVuelo are not directly saved in db
        em.detach(updatedColombofiloVuelo);
        updatedColombofiloVuelo.enviadas(UPDATED_ENVIDAS).distancia(UPDATED_DISTANCIA);

        restColombofiloVueloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedColombofiloVuelo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedColombofiloVuelo))
            )
            .andExpect(status().isOk());

        // Validate the ColombofiloVuelo in the database
        List<ColombofiloVuelo> colombofiloVueloList = colombofiloVueloRepository.findAll();
        assertThat(colombofiloVueloList).hasSize(databaseSizeBeforeUpdate);
        ColombofiloVuelo testColombofiloVuelo = colombofiloVueloList.get(colombofiloVueloList.size() - 1);
        assertThat(testColombofiloVuelo.getEnviadas()).isEqualTo(UPDATED_ENVIDAS);
        assertThat(testColombofiloVuelo.getDistancia()).isEqualTo(UPDATED_DISTANCIA);
    }

    //@Test
    @Transactional
    void putNonExistingColombofiloVuelo() throws Exception {
        int databaseSizeBeforeUpdate = colombofiloVueloRepository.findAll().size();
        colombofiloVuelo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColombofiloVueloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, colombofiloVuelo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(colombofiloVuelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ColombofiloVuelo in the database
        List<ColombofiloVuelo> colombofiloVueloList = colombofiloVueloRepository.findAll();
        assertThat(colombofiloVueloList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithIdMismatchColombofiloVuelo() throws Exception {
        int databaseSizeBeforeUpdate = colombofiloVueloRepository.findAll().size();
        colombofiloVuelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColombofiloVueloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(colombofiloVuelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ColombofiloVuelo in the database
        List<ColombofiloVuelo> colombofiloVueloList = colombofiloVueloRepository.findAll();
        assertThat(colombofiloVueloList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithMissingIdPathParamColombofiloVuelo() throws Exception {
        int databaseSizeBeforeUpdate = colombofiloVueloRepository.findAll().size();
        colombofiloVuelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColombofiloVueloMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colombofiloVuelo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ColombofiloVuelo in the database
        List<ColombofiloVuelo> colombofiloVueloList = colombofiloVueloRepository.findAll();
        assertThat(colombofiloVueloList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void partialUpdateColombofiloVueloWithPatch() throws Exception {
        // Initialize the database
        colombofiloVueloRepository.saveAndFlush(colombofiloVuelo);

        int databaseSizeBeforeUpdate = colombofiloVueloRepository.findAll().size();

        // Update the colombofiloVuelo using partial update
        ColombofiloVuelo partialUpdatedColombofiloVuelo = new ColombofiloVuelo();
        partialUpdatedColombofiloVuelo.setId(colombofiloVuelo.getId());

        restColombofiloVueloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedColombofiloVuelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedColombofiloVuelo))
            )
            .andExpect(status().isOk());

        // Validate the ColombofiloVuelo in the database
        List<ColombofiloVuelo> colombofiloVueloList = colombofiloVueloRepository.findAll();
        assertThat(colombofiloVueloList).hasSize(databaseSizeBeforeUpdate);
        ColombofiloVuelo testColombofiloVuelo = colombofiloVueloList.get(colombofiloVueloList.size() - 1);
        assertThat(testColombofiloVuelo.getEnviadas()).isEqualTo(DEFAULT_ENVIDAS);
        assertThat(testColombofiloVuelo.getDistancia()).isEqualTo(DEFAULT_DISTANCIA);
    }

    //@Test
    @Transactional
    void fullUpdateColombofiloVueloWithPatch() throws Exception {
        // Initialize the database
        colombofiloVueloRepository.saveAndFlush(colombofiloVuelo);

        int databaseSizeBeforeUpdate = colombofiloVueloRepository.findAll().size();

        // Update the colombofiloVuelo using partial update
        ColombofiloVuelo partialUpdatedColombofiloVuelo = new ColombofiloVuelo();
        partialUpdatedColombofiloVuelo.setId(colombofiloVuelo.getId());

        partialUpdatedColombofiloVuelo.enviadas(UPDATED_ENVIDAS).distancia(UPDATED_DISTANCIA);

        restColombofiloVueloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedColombofiloVuelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedColombofiloVuelo))
            )
            .andExpect(status().isOk());

        // Validate the ColombofiloVuelo in the database
        List<ColombofiloVuelo> colombofiloVueloList = colombofiloVueloRepository.findAll();
        assertThat(colombofiloVueloList).hasSize(databaseSizeBeforeUpdate);
        ColombofiloVuelo testColombofiloVuelo = colombofiloVueloList.get(colombofiloVueloList.size() - 1);
        assertThat(testColombofiloVuelo.getEnviadas()).isEqualTo(UPDATED_ENVIDAS);
        assertThat(testColombofiloVuelo.getDistancia()).isEqualTo(UPDATED_DISTANCIA);
    }

    //@Test
    @Transactional
    void patchNonExistingColombofiloVuelo() throws Exception {
        int databaseSizeBeforeUpdate = colombofiloVueloRepository.findAll().size();
        colombofiloVuelo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColombofiloVueloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, colombofiloVuelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(colombofiloVuelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ColombofiloVuelo in the database
        List<ColombofiloVuelo> colombofiloVueloList = colombofiloVueloRepository.findAll();
        assertThat(colombofiloVueloList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithIdMismatchColombofiloVuelo() throws Exception {
        int databaseSizeBeforeUpdate = colombofiloVueloRepository.findAll().size();
        colombofiloVuelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColombofiloVueloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(colombofiloVuelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ColombofiloVuelo in the database
        List<ColombofiloVuelo> colombofiloVueloList = colombofiloVueloRepository.findAll();
        assertThat(colombofiloVueloList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithMissingIdPathParamColombofiloVuelo() throws Exception {
        int databaseSizeBeforeUpdate = colombofiloVueloRepository.findAll().size();
        colombofiloVuelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColombofiloVueloMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(colombofiloVuelo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ColombofiloVuelo in the database
        List<ColombofiloVuelo> colombofiloVueloList = colombofiloVueloRepository.findAll();
        assertThat(colombofiloVueloList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void deleteColombofiloVuelo() throws Exception {
        // Initialize the database
        colombofiloVueloRepository.saveAndFlush(colombofiloVuelo);

        int databaseSizeBeforeDelete = colombofiloVueloRepository.findAll().size();

        // Delete the colombofiloVuelo
        restColombofiloVueloMockMvc
            .perform(delete(ENTITY_API_URL_ID, colombofiloVuelo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ColombofiloVuelo> colombofiloVueloList = colombofiloVueloRepository.findAll();
        assertThat(colombofiloVueloList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
