package cu.fcc.pigeon.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cu.fcc.pigeon.IntegrationTest;
import cu.fcc.pigeon.domain.Color;
import cu.fcc.pigeon.repository.ColorRepository;
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
 * Integration tests for the {@link ColorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ColorResourceIT {

    private static final String DEFAULT_NOMBRE_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_COLOR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/colors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restColorMockMvc;

    private Color color;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Color createEntity(EntityManager em) {
        Color color = new Color().nombreColor(DEFAULT_NOMBRE_COLOR);
        return color;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Color createUpdatedEntity(EntityManager em) {
        Color color = new Color().nombreColor(UPDATED_NOMBRE_COLOR);
        return color;
    }

    @BeforeEach
    public void initTest() {
        color = createEntity(em);
    }

    //@Test
    @Transactional
    void createColor() throws Exception {
        int databaseSizeBeforeCreate = colorRepository.findAll().size();
        // Create the Color
        restColorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(color)))
            .andExpect(status().isCreated());

        // Validate the Color in the database
        List<Color> colorList = colorRepository.findAll();
        assertThat(colorList).hasSize(databaseSizeBeforeCreate + 1);
        Color testColor = colorList.get(colorList.size() - 1);
        assertThat(testColor.getNombreColor()).isEqualTo(DEFAULT_NOMBRE_COLOR);
    }

    //@Test
    @Transactional
    void createColorWithExistingId() throws Exception {
        // Create the Color with an existing ID
        color.setId(1L);

        int databaseSizeBeforeCreate = colorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restColorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(color)))
            .andExpect(status().isBadRequest());

        // Validate the Color in the database
        List<Color> colorList = colorRepository.findAll();
        assertThat(colorList).hasSize(databaseSizeBeforeCreate);
    }

    //@Test
    @Transactional
    void checkNombreColorIsRequired() throws Exception {
        int databaseSizeBeforeTest = colorRepository.findAll().size();
        // set the field null
        color.setNombreColor(null);

        // Create the Color, which fails.

        restColorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(color)))
            .andExpect(status().isBadRequest());

        List<Color> colorList = colorRepository.findAll();
        assertThat(colorList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void getAllColors() throws Exception {
        // Initialize the database
        colorRepository.saveAndFlush(color);

        // Get all the colorList
        restColorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(color.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreColor").value(hasItem(DEFAULT_NOMBRE_COLOR)));
    }

    //@Test
    @Transactional
    void getColor() throws Exception {
        // Initialize the database
        colorRepository.saveAndFlush(color);

        // Get the color
        restColorMockMvc
            .perform(get(ENTITY_API_URL_ID, color.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(color.getId().intValue()))
            .andExpect(jsonPath("$.nombreColor").value(DEFAULT_NOMBRE_COLOR));
    }

    //@Test
    @Transactional
    void getNonExistingColor() throws Exception {
        // Get the color
        restColorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    //@Test
    @Transactional
    void putExistingColor() throws Exception {
        // Initialize the database
        colorRepository.saveAndFlush(color);

        int databaseSizeBeforeUpdate = colorRepository.findAll().size();

        // Update the color
        Color updatedColor = colorRepository.findById(color.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedColor are not directly saved in db
        em.detach(updatedColor);
        updatedColor.nombreColor(UPDATED_NOMBRE_COLOR);

        restColorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedColor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedColor))
            )
            .andExpect(status().isOk());

        // Validate the Color in the database
        List<Color> colorList = colorRepository.findAll();
        assertThat(colorList).hasSize(databaseSizeBeforeUpdate);
        Color testColor = colorList.get(colorList.size() - 1);
        assertThat(testColor.getNombreColor()).isEqualTo(UPDATED_NOMBRE_COLOR);
    }

    //@Test
    @Transactional
    void putNonExistingColor() throws Exception {
        int databaseSizeBeforeUpdate = colorRepository.findAll().size();
        color.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, color.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(color))
            )
            .andExpect(status().isBadRequest());

        // Validate the Color in the database
        List<Color> colorList = colorRepository.findAll();
        assertThat(colorList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithIdMismatchColor() throws Exception {
        int databaseSizeBeforeUpdate = colorRepository.findAll().size();
        color.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(color))
            )
            .andExpect(status().isBadRequest());

        // Validate the Color in the database
        List<Color> colorList = colorRepository.findAll();
        assertThat(colorList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithMissingIdPathParamColor() throws Exception {
        int databaseSizeBeforeUpdate = colorRepository.findAll().size();
        color.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(color)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Color in the database
        List<Color> colorList = colorRepository.findAll();
        assertThat(colorList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void partialUpdateColorWithPatch() throws Exception {
        // Initialize the database
        colorRepository.saveAndFlush(color);

        int databaseSizeBeforeUpdate = colorRepository.findAll().size();

        // Update the color using partial update
        Color partialUpdatedColor = new Color();
        partialUpdatedColor.setId(color.getId());

        restColorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedColor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedColor))
            )
            .andExpect(status().isOk());

        // Validate the Color in the database
        List<Color> colorList = colorRepository.findAll();
        assertThat(colorList).hasSize(databaseSizeBeforeUpdate);
        Color testColor = colorList.get(colorList.size() - 1);
        assertThat(testColor.getNombreColor()).isEqualTo(DEFAULT_NOMBRE_COLOR);
    }

    //@Test
    @Transactional
    void fullUpdateColorWithPatch() throws Exception {
        // Initialize the database
        colorRepository.saveAndFlush(color);

        int databaseSizeBeforeUpdate = colorRepository.findAll().size();

        // Update the color using partial update
        Color partialUpdatedColor = new Color();
        partialUpdatedColor.setId(color.getId());

        partialUpdatedColor.nombreColor(UPDATED_NOMBRE_COLOR);

        restColorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedColor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedColor))
            )
            .andExpect(status().isOk());

        // Validate the Color in the database
        List<Color> colorList = colorRepository.findAll();
        assertThat(colorList).hasSize(databaseSizeBeforeUpdate);
        Color testColor = colorList.get(colorList.size() - 1);
        assertThat(testColor.getNombreColor()).isEqualTo(UPDATED_NOMBRE_COLOR);
    }

    //@Test
    @Transactional
    void patchNonExistingColor() throws Exception {
        int databaseSizeBeforeUpdate = colorRepository.findAll().size();
        color.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, color.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(color))
            )
            .andExpect(status().isBadRequest());

        // Validate the Color in the database
        List<Color> colorList = colorRepository.findAll();
        assertThat(colorList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithIdMismatchColor() throws Exception {
        int databaseSizeBeforeUpdate = colorRepository.findAll().size();
        color.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(color))
            )
            .andExpect(status().isBadRequest());

        // Validate the Color in the database
        List<Color> colorList = colorRepository.findAll();
        assertThat(colorList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithMissingIdPathParamColor() throws Exception {
        int databaseSizeBeforeUpdate = colorRepository.findAll().size();
        color.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(color)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Color in the database
        List<Color> colorList = colorRepository.findAll();
        assertThat(colorList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void deleteColor() throws Exception {
        // Initialize the database
        colorRepository.saveAndFlush(color);

        int databaseSizeBeforeDelete = colorRepository.findAll().size();

        // Delete the color
        restColorMockMvc
            .perform(delete(ENTITY_API_URL_ID, color.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Color> colorList = colorRepository.findAll();
        assertThat(colorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
