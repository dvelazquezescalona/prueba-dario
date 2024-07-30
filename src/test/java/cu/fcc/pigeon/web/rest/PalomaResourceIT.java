package cu.fcc.pigeon.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cu.fcc.pigeon.IntegrationTest;
import cu.fcc.pigeon.domain.Paloma;
import cu.fcc.pigeon.repository.PalomaRepository;
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
 * Integration tests for the {@link PalomaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PalomaResourceIT {

    private static final String DEFAULT_ANILLA = "AAAAAAAAAA";
    private static final String UPDATED_ANILLA = "BBBBBBBBBB";

    private static final String DEFAULT_ANNO = "AAAAAAAAAA";
    private static final String UPDATED_ANNO = "BBBBBBBBBB";

    private static final Long DEFAULT_PAIS = 1L;
    private static final Long UPDATED_PAIS = 2L;

    private static final Boolean DEFAULT_SEXO = false;
    private static final Boolean UPDATED_SEXO = true;

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final String ENTITY_API_URL = "/api/palomas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PalomaRepository palomaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPalomaMockMvc;

    private Paloma paloma;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paloma createEntity(EntityManager em) {
        Paloma paloma = new Paloma().anilla(DEFAULT_ANILLA).anno(DEFAULT_ANNO).pais(DEFAULT_PAIS).sexo(DEFAULT_SEXO).activo(DEFAULT_ACTIVO);
        return paloma;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paloma createUpdatedEntity(EntityManager em) {
        Paloma paloma = new Paloma().anilla(UPDATED_ANILLA).anno(UPDATED_ANNO).pais(UPDATED_PAIS).sexo(UPDATED_SEXO).activo(UPDATED_ACTIVO);
        return paloma;
    }

    @BeforeEach
    public void initTest() {
        paloma = createEntity(em);
    }

    //@Test
    @Transactional
    void createPaloma() throws Exception {
        int databaseSizeBeforeCreate = palomaRepository.findAll().size();
        // Create the Paloma
        restPalomaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paloma)))
            .andExpect(status().isCreated());

        // Validate the Paloma in the database
        List<Paloma> palomaList = palomaRepository.findAll();
        assertThat(palomaList).hasSize(databaseSizeBeforeCreate + 1);
        Paloma testPaloma = palomaList.get(palomaList.size() - 1);
        assertThat(testPaloma.getAnilla()).isEqualTo(DEFAULT_ANILLA);
        assertThat(testPaloma.getAnno()).isEqualTo(DEFAULT_ANNO);
        assertThat(testPaloma.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testPaloma.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testPaloma.getActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    //@Test
    @Transactional
    void createPalomaWithExistingId() throws Exception {
        // Create the Paloma with an existing ID
        paloma.setId(1L);

        int databaseSizeBeforeCreate = palomaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPalomaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paloma)))
            .andExpect(status().isBadRequest());

        // Validate the Paloma in the database
        List<Paloma> palomaList = palomaRepository.findAll();
        assertThat(palomaList).hasSize(databaseSizeBeforeCreate);
    }

    //@Test
    @Transactional
    void checkAnillaIsRequired() throws Exception {
        int databaseSizeBeforeTest = palomaRepository.findAll().size();
        // set the field null
        paloma.setAnilla(null);

        // Create the Paloma, which fails.

        restPalomaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paloma)))
            .andExpect(status().isBadRequest());

        List<Paloma> palomaList = palomaRepository.findAll();
        assertThat(palomaList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkAnnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = palomaRepository.findAll().size();
        // set the field null
        paloma.setAnno(null);

        // Create the Paloma, which fails.

        restPalomaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paloma)))
            .andExpect(status().isBadRequest());

        List<Paloma> palomaList = palomaRepository.findAll();
        assertThat(palomaList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkPaisIsRequired() throws Exception {
        int databaseSizeBeforeTest = palomaRepository.findAll().size();
        // set the field null
        paloma.setPais(null);

        // Create the Paloma, which fails.

        restPalomaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paloma)))
            .andExpect(status().isBadRequest());

        List<Paloma> palomaList = palomaRepository.findAll();
        assertThat(palomaList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = palomaRepository.findAll().size();
        // set the field null
        paloma.setSexo(null);

        // Create the Paloma, which fails.

        restPalomaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paloma)))
            .andExpect(status().isBadRequest());

        List<Paloma> palomaList = palomaRepository.findAll();
        assertThat(palomaList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void getAllPalomas() throws Exception {
        // Initialize the database
        palomaRepository.saveAndFlush(paloma);

        // Get all the palomaList
        restPalomaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paloma.getId().intValue())))
            .andExpect(jsonPath("$.[*].anilla").value(hasItem(DEFAULT_ANILLA)))
            .andExpect(jsonPath("$.[*].anno").value(hasItem(DEFAULT_ANNO)))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.booleanValue())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }

    //@Test
    @Transactional
    void getPaloma() throws Exception {
        // Initialize the database
        palomaRepository.saveAndFlush(paloma);

        // Get the paloma
        restPalomaMockMvc
            .perform(get(ENTITY_API_URL_ID, paloma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paloma.getId().intValue()))
            .andExpect(jsonPath("$.anilla").value(DEFAULT_ANILLA))
            .andExpect(jsonPath("$.anno").value(DEFAULT_ANNO))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.booleanValue()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    //@Test
    @Transactional
    void getNonExistingPaloma() throws Exception {
        // Get the paloma
        restPalomaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    //@Test
    @Transactional
    void putExistingPaloma() throws Exception {
        // Initialize the database
        palomaRepository.saveAndFlush(paloma);

        int databaseSizeBeforeUpdate = palomaRepository.findAll().size();

        // Update the paloma
        Paloma updatedPaloma = palomaRepository.findById(paloma.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPaloma are not directly saved in db
        em.detach(updatedPaloma);
        updatedPaloma.anilla(UPDATED_ANILLA).anno(UPDATED_ANNO).pais(UPDATED_PAIS).sexo(UPDATED_SEXO).activo(UPDATED_ACTIVO);

        restPalomaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaloma.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPaloma))
            )
            .andExpect(status().isOk());

        // Validate the Paloma in the database
        List<Paloma> palomaList = palomaRepository.findAll();
        assertThat(palomaList).hasSize(databaseSizeBeforeUpdate);
        Paloma testPaloma = palomaList.get(palomaList.size() - 1);
        assertThat(testPaloma.getAnilla()).isEqualTo(UPDATED_ANILLA);
        assertThat(testPaloma.getAnno()).isEqualTo(UPDATED_ANNO);
        assertThat(testPaloma.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testPaloma.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testPaloma.getActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    //@Test
    @Transactional
    void putNonExistingPaloma() throws Exception {
        int databaseSizeBeforeUpdate = palomaRepository.findAll().size();
        paloma.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPalomaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paloma.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paloma))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paloma in the database
        List<Paloma> palomaList = palomaRepository.findAll();
        assertThat(palomaList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithIdMismatchPaloma() throws Exception {
        int databaseSizeBeforeUpdate = palomaRepository.findAll().size();
        paloma.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPalomaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paloma))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paloma in the database
        List<Paloma> palomaList = palomaRepository.findAll();
        assertThat(palomaList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithMissingIdPathParamPaloma() throws Exception {
        int databaseSizeBeforeUpdate = palomaRepository.findAll().size();
        paloma.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPalomaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paloma)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paloma in the database
        List<Paloma> palomaList = palomaRepository.findAll();
        assertThat(palomaList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void partialUpdatePalomaWithPatch() throws Exception {
        // Initialize the database
        palomaRepository.saveAndFlush(paloma);

        int databaseSizeBeforeUpdate = palomaRepository.findAll().size();

        // Update the paloma using partial update
        Paloma partialUpdatedPaloma = new Paloma();
        partialUpdatedPaloma.setId(paloma.getId());

        partialUpdatedPaloma.anno(UPDATED_ANNO).pais(UPDATED_PAIS).activo(UPDATED_ACTIVO);

        restPalomaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaloma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaloma))
            )
            .andExpect(status().isOk());

        // Validate the Paloma in the database
        List<Paloma> palomaList = palomaRepository.findAll();
        assertThat(palomaList).hasSize(databaseSizeBeforeUpdate);
        Paloma testPaloma = palomaList.get(palomaList.size() - 1);
        assertThat(testPaloma.getAnilla()).isEqualTo(DEFAULT_ANILLA);
        assertThat(testPaloma.getAnno()).isEqualTo(UPDATED_ANNO);
        assertThat(testPaloma.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testPaloma.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testPaloma.getActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    //@Test
    @Transactional
    void fullUpdatePalomaWithPatch() throws Exception {
        // Initialize the database
        palomaRepository.saveAndFlush(paloma);

        int databaseSizeBeforeUpdate = palomaRepository.findAll().size();

        // Update the paloma using partial update
        Paloma partialUpdatedPaloma = new Paloma();
        partialUpdatedPaloma.setId(paloma.getId());

        partialUpdatedPaloma.anilla(UPDATED_ANILLA).anno(UPDATED_ANNO).pais(UPDATED_PAIS).sexo(UPDATED_SEXO).activo(UPDATED_ACTIVO);

        restPalomaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaloma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaloma))
            )
            .andExpect(status().isOk());

        // Validate the Paloma in the database
        List<Paloma> palomaList = palomaRepository.findAll();
        assertThat(palomaList).hasSize(databaseSizeBeforeUpdate);
        Paloma testPaloma = palomaList.get(palomaList.size() - 1);
        assertThat(testPaloma.getAnilla()).isEqualTo(UPDATED_ANILLA);
        assertThat(testPaloma.getAnno()).isEqualTo(UPDATED_ANNO);
        assertThat(testPaloma.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testPaloma.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testPaloma.getActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    //@Test
    @Transactional
    void patchNonExistingPaloma() throws Exception {
        int databaseSizeBeforeUpdate = palomaRepository.findAll().size();
        paloma.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPalomaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paloma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paloma))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paloma in the database
        List<Paloma> palomaList = palomaRepository.findAll();
        assertThat(palomaList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithIdMismatchPaloma() throws Exception {
        int databaseSizeBeforeUpdate = palomaRepository.findAll().size();
        paloma.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPalomaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paloma))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paloma in the database
        List<Paloma> palomaList = palomaRepository.findAll();
        assertThat(palomaList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithMissingIdPathParamPaloma() throws Exception {
        int databaseSizeBeforeUpdate = palomaRepository.findAll().size();
        paloma.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPalomaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paloma)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paloma in the database
        List<Paloma> palomaList = palomaRepository.findAll();
        assertThat(palomaList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void deletePaloma() throws Exception {
        // Initialize the database
        palomaRepository.saveAndFlush(paloma);

        int databaseSizeBeforeDelete = palomaRepository.findAll().size();

        // Delete the paloma
        restPalomaMockMvc
            .perform(delete(ENTITY_API_URL_ID, paloma.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Paloma> palomaList = palomaRepository.findAll();
        assertThat(palomaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
