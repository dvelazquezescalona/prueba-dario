package cu.fcc.pigeon.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cu.fcc.pigeon.IntegrationTest;
import cu.fcc.pigeon.domain.Colombofilo;
import cu.fcc.pigeon.repository.ColombofiloRepository;
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
 * Integration tests for the {@link ColombofiloResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ColombofiloResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMER_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_PRIMER_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_SEGINDO_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_SEGINDO_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_CI = "AAAAAAAAAA";
    private static final String UPDATED_CI = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUD = 1D;
    private static final Double UPDATED_LATITUD = 2D;

    private static final Double DEFAULT_LONGITUD = 1D;
    private static final Double UPDATED_LONGITUD = 2D;

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_ZONA = "AAAAAAAAAA";
    private static final String UPDATED_ZONA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/colombofilos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ColombofiloRepository colombofiloRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restColombofiloMockMvc;

    private Colombofilo colombofilo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Colombofilo createEntity(EntityManager em) {
        Colombofilo colombofilo = new Colombofilo()
            .nombre(DEFAULT_NOMBRE)
            .primerApellido(DEFAULT_PRIMER_APELLIDO)
            .segindoApellido(DEFAULT_SEGINDO_APELLIDO)
            .ci(DEFAULT_CI)
            .latitud(DEFAULT_LATITUD)
            .longitud(DEFAULT_LONGITUD)
            .direccion(DEFAULT_DIRECCION)
            .categoria(DEFAULT_CATEGORIA)
            .telefono(DEFAULT_TELEFONO)
            .zona(DEFAULT_ZONA);
        return colombofilo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Colombofilo createUpdatedEntity(EntityManager em) {
        Colombofilo colombofilo = new Colombofilo()
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segindoApellido(UPDATED_SEGINDO_APELLIDO)
            .ci(UPDATED_CI)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .direccion(UPDATED_DIRECCION)
            .categoria(UPDATED_CATEGORIA)
            .telefono(UPDATED_TELEFONO)
            .zona(UPDATED_ZONA);
        return colombofilo;
    }

    @BeforeEach
    public void initTest() {
        colombofilo = createEntity(em);
    }

    //@Test
    @Transactional
    void createColombofilo() throws Exception {
        int databaseSizeBeforeCreate = colombofiloRepository.findAll().size();
        // Create the Colombofilo
        restColombofiloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colombofilo)))
            .andExpect(status().isCreated());

        // Validate the Colombofilo in the database
        List<Colombofilo> colombofiloList = colombofiloRepository.findAll();
        assertThat(colombofiloList).hasSize(databaseSizeBeforeCreate + 1);
        Colombofilo testColombofilo = colombofiloList.get(colombofiloList.size() - 1);
        assertThat(testColombofilo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testColombofilo.getPrimerApellido()).isEqualTo(DEFAULT_PRIMER_APELLIDO);
        assertThat(testColombofilo.getSegindoApellido()).isEqualTo(DEFAULT_SEGINDO_APELLIDO);
        assertThat(testColombofilo.getCi()).isEqualTo(DEFAULT_CI);
        assertThat(testColombofilo.getLatitud()).isEqualTo(DEFAULT_LATITUD);
        assertThat(testColombofilo.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
        assertThat(testColombofilo.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testColombofilo.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testColombofilo.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testColombofilo.getZona()).isEqualTo(DEFAULT_ZONA);
    }

    //@Test
    @Transactional
    void createColombofiloWithExistingId() throws Exception {
        // Create the Colombofilo with an existing ID
        colombofilo.setId(1L);

        int databaseSizeBeforeCreate = colombofiloRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restColombofiloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colombofilo)))
            .andExpect(status().isBadRequest());

        // Validate the Colombofilo in the database
        List<Colombofilo> colombofiloList = colombofiloRepository.findAll();
        assertThat(colombofiloList).hasSize(databaseSizeBeforeCreate);
    }

    //@Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = colombofiloRepository.findAll().size();
        // set the field null
        colombofilo.setNombre(null);

        // Create the Colombofilo, which fails.

        restColombofiloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colombofilo)))
            .andExpect(status().isBadRequest());

        List<Colombofilo> colombofiloList = colombofiloRepository.findAll();
        assertThat(colombofiloList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkPrimerApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = colombofiloRepository.findAll().size();
        // set the field null
        colombofilo.setPrimerApellido(null);

        // Create the Colombofilo, which fails.

        restColombofiloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colombofilo)))
            .andExpect(status().isBadRequest());

        List<Colombofilo> colombofiloList = colombofiloRepository.findAll();
        assertThat(colombofiloList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkSegindoApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = colombofiloRepository.findAll().size();
        // set the field null
        colombofilo.setSegindoApellido(null);

        // Create the Colombofilo, which fails.

        restColombofiloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colombofilo)))
            .andExpect(status().isBadRequest());

        List<Colombofilo> colombofiloList = colombofiloRepository.findAll();
        assertThat(colombofiloList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkCiIsRequired() throws Exception {
        int databaseSizeBeforeTest = colombofiloRepository.findAll().size();
        // set the field null
        colombofilo.setCi(null);

        // Create the Colombofilo, which fails.

        restColombofiloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colombofilo)))
            .andExpect(status().isBadRequest());

        List<Colombofilo> colombofiloList = colombofiloRepository.findAll();
        assertThat(colombofiloList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkLatitudIsRequired() throws Exception {
        int databaseSizeBeforeTest = colombofiloRepository.findAll().size();
        // set the field null
        colombofilo.setLatitud(null);

        // Create the Colombofilo, which fails.

        restColombofiloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colombofilo)))
            .andExpect(status().isBadRequest());

        List<Colombofilo> colombofiloList = colombofiloRepository.findAll();
        assertThat(colombofiloList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkLongitudIsRequired() throws Exception {
        int databaseSizeBeforeTest = colombofiloRepository.findAll().size();
        // set the field null
        colombofilo.setLongitud(null);

        // Create the Colombofilo, which fails.

        restColombofiloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colombofilo)))
            .andExpect(status().isBadRequest());

        List<Colombofilo> colombofiloList = colombofiloRepository.findAll();
        assertThat(colombofiloList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = colombofiloRepository.findAll().size();
        // set the field null
        colombofilo.setDireccion(null);

        // Create the Colombofilo, which fails.

        restColombofiloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colombofilo)))
            .andExpect(status().isBadRequest());

        List<Colombofilo> colombofiloList = colombofiloRepository.findAll();
        assertThat(colombofiloList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void checkCategoriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = colombofiloRepository.findAll().size();
        // set the field null
        colombofilo.setCategoria(null);

        // Create the Colombofilo, which fails.

        restColombofiloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colombofilo)))
            .andExpect(status().isBadRequest());

        List<Colombofilo> colombofiloList = colombofiloRepository.findAll();
        assertThat(colombofiloList).hasSize(databaseSizeBeforeTest);
    }

    //@Test
    @Transactional
    void getAllColombofilos() throws Exception {
        // Initialize the database
        colombofiloRepository.saveAndFlush(colombofilo);

        // Get all the colombofiloList
        restColombofiloMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(colombofilo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].primerApellido").value(hasItem(DEFAULT_PRIMER_APELLIDO)))
            .andExpect(jsonPath("$.[*].segindoApellido").value(hasItem(DEFAULT_SEGINDO_APELLIDO)))
            .andExpect(jsonPath("$.[*].ci").value(hasItem(DEFAULT_CI)))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD.doubleValue())))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD.doubleValue())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].zona").value(hasItem(DEFAULT_ZONA)));
    }

    //@Test
    @Transactional
    void getColombofilo() throws Exception {
        // Initialize the database
        colombofiloRepository.saveAndFlush(colombofilo);

        // Get the colombofilo
        restColombofiloMockMvc
            .perform(get(ENTITY_API_URL_ID, colombofilo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(colombofilo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.primerApellido").value(DEFAULT_PRIMER_APELLIDO))
            .andExpect(jsonPath("$.segindoApellido").value(DEFAULT_SEGINDO_APELLIDO))
            .andExpect(jsonPath("$.ci").value(DEFAULT_CI))
            .andExpect(jsonPath("$.latitud").value(DEFAULT_LATITUD.doubleValue()))
            .andExpect(jsonPath("$.longitud").value(DEFAULT_LONGITUD.doubleValue()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.zona").value(DEFAULT_ZONA));
    }

    //@Test
    @Transactional
    void getNonExistingColombofilo() throws Exception {
        // Get the colombofilo
        restColombofiloMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    //@Test
    @Transactional
    void putExistingColombofilo() throws Exception {
        // Initialize the database
        colombofiloRepository.saveAndFlush(colombofilo);

        int databaseSizeBeforeUpdate = colombofiloRepository.findAll().size();

        // Update the colombofilo
        Colombofilo updatedColombofilo = colombofiloRepository.findById(colombofilo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedColombofilo are not directly saved in db
        em.detach(updatedColombofilo);
        updatedColombofilo
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segindoApellido(UPDATED_SEGINDO_APELLIDO)
            .ci(UPDATED_CI)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .direccion(UPDATED_DIRECCION)
            .categoria(UPDATED_CATEGORIA)
            .telefono(UPDATED_TELEFONO)
            .zona(UPDATED_ZONA);

        restColombofiloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedColombofilo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedColombofilo))
            )
            .andExpect(status().isOk());

        // Validate the Colombofilo in the database
        List<Colombofilo> colombofiloList = colombofiloRepository.findAll();
        assertThat(colombofiloList).hasSize(databaseSizeBeforeUpdate);
        Colombofilo testColombofilo = colombofiloList.get(colombofiloList.size() - 1);
        assertThat(testColombofilo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testColombofilo.getPrimerApellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testColombofilo.getSegindoApellido()).isEqualTo(UPDATED_SEGINDO_APELLIDO);
        assertThat(testColombofilo.getCi()).isEqualTo(UPDATED_CI);
        assertThat(testColombofilo.getLatitud()).isEqualTo(UPDATED_LATITUD);
        assertThat(testColombofilo.getLongitud()).isEqualTo(UPDATED_LONGITUD);
        assertThat(testColombofilo.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testColombofilo.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testColombofilo.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testColombofilo.getZona()).isEqualTo(UPDATED_ZONA);
    }

    //@Test
    @Transactional
    void putNonExistingColombofilo() throws Exception {
        int databaseSizeBeforeUpdate = colombofiloRepository.findAll().size();
        colombofilo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColombofiloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, colombofilo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(colombofilo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colombofilo in the database
        List<Colombofilo> colombofiloList = colombofiloRepository.findAll();
        assertThat(colombofiloList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithIdMismatchColombofilo() throws Exception {
        int databaseSizeBeforeUpdate = colombofiloRepository.findAll().size();
        colombofilo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColombofiloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(colombofilo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colombofilo in the database
        List<Colombofilo> colombofiloList = colombofiloRepository.findAll();
        assertThat(colombofiloList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void putWithMissingIdPathParamColombofilo() throws Exception {
        int databaseSizeBeforeUpdate = colombofiloRepository.findAll().size();
        colombofilo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColombofiloMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colombofilo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Colombofilo in the database
        List<Colombofilo> colombofiloList = colombofiloRepository.findAll();
        assertThat(colombofiloList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void partialUpdateColombofiloWithPatch() throws Exception {
        // Initialize the database
        colombofiloRepository.saveAndFlush(colombofilo);

        int databaseSizeBeforeUpdate = colombofiloRepository.findAll().size();

        // Update the colombofilo using partial update
        Colombofilo partialUpdatedColombofilo = new Colombofilo();
        partialUpdatedColombofilo.setId(colombofilo.getId());

        partialUpdatedColombofilo.direccion(UPDATED_DIRECCION).telefono(UPDATED_TELEFONO).zona(UPDATED_ZONA);

        restColombofiloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedColombofilo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedColombofilo))
            )
            .andExpect(status().isOk());

        // Validate the Colombofilo in the database
        List<Colombofilo> colombofiloList = colombofiloRepository.findAll();
        assertThat(colombofiloList).hasSize(databaseSizeBeforeUpdate);
        Colombofilo testColombofilo = colombofiloList.get(colombofiloList.size() - 1);
        assertThat(testColombofilo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testColombofilo.getPrimerApellido()).isEqualTo(DEFAULT_PRIMER_APELLIDO);
        assertThat(testColombofilo.getSegindoApellido()).isEqualTo(DEFAULT_SEGINDO_APELLIDO);
        assertThat(testColombofilo.getCi()).isEqualTo(DEFAULT_CI);
        assertThat(testColombofilo.getLatitud()).isEqualTo(DEFAULT_LATITUD);
        assertThat(testColombofilo.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
        assertThat(testColombofilo.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testColombofilo.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testColombofilo.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testColombofilo.getZona()).isEqualTo(UPDATED_ZONA);
    }

    //@Test
    @Transactional
    void fullUpdateColombofiloWithPatch() throws Exception {
        // Initialize the database
        colombofiloRepository.saveAndFlush(colombofilo);

        int databaseSizeBeforeUpdate = colombofiloRepository.findAll().size();

        // Update the colombofilo using partial update
        Colombofilo partialUpdatedColombofilo = new Colombofilo();
        partialUpdatedColombofilo.setId(colombofilo.getId());

        partialUpdatedColombofilo
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segindoApellido(UPDATED_SEGINDO_APELLIDO)
            .ci(UPDATED_CI)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .direccion(UPDATED_DIRECCION)
            .categoria(UPDATED_CATEGORIA)
            .telefono(UPDATED_TELEFONO)
            .zona(UPDATED_ZONA);

        restColombofiloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedColombofilo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedColombofilo))
            )
            .andExpect(status().isOk());

        // Validate the Colombofilo in the database
        List<Colombofilo> colombofiloList = colombofiloRepository.findAll();
        assertThat(colombofiloList).hasSize(databaseSizeBeforeUpdate);
        Colombofilo testColombofilo = colombofiloList.get(colombofiloList.size() - 1);
        assertThat(testColombofilo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testColombofilo.getPrimerApellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testColombofilo.getSegindoApellido()).isEqualTo(UPDATED_SEGINDO_APELLIDO);
        assertThat(testColombofilo.getCi()).isEqualTo(UPDATED_CI);
        assertThat(testColombofilo.getLatitud()).isEqualTo(UPDATED_LATITUD);
        assertThat(testColombofilo.getLongitud()).isEqualTo(UPDATED_LONGITUD);
        assertThat(testColombofilo.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testColombofilo.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testColombofilo.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testColombofilo.getZona()).isEqualTo(UPDATED_ZONA);
    }

    //@Test
    @Transactional
    void patchNonExistingColombofilo() throws Exception {
        int databaseSizeBeforeUpdate = colombofiloRepository.findAll().size();
        colombofilo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColombofiloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, colombofilo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(colombofilo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colombofilo in the database
        List<Colombofilo> colombofiloList = colombofiloRepository.findAll();
        assertThat(colombofiloList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithIdMismatchColombofilo() throws Exception {
        int databaseSizeBeforeUpdate = colombofiloRepository.findAll().size();
        colombofilo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColombofiloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(colombofilo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colombofilo in the database
        List<Colombofilo> colombofiloList = colombofiloRepository.findAll();
        assertThat(colombofiloList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void patchWithMissingIdPathParamColombofilo() throws Exception {
        int databaseSizeBeforeUpdate = colombofiloRepository.findAll().size();
        colombofilo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColombofiloMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(colombofilo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Colombofilo in the database
        List<Colombofilo> colombofiloList = colombofiloRepository.findAll();
        assertThat(colombofiloList).hasSize(databaseSizeBeforeUpdate);
    }

    //@Test
    @Transactional
    void deleteColombofilo() throws Exception {
        // Initialize the database
        colombofiloRepository.saveAndFlush(colombofilo);

        int databaseSizeBeforeDelete = colombofiloRepository.findAll().size();

        // Delete the colombofilo
        restColombofiloMockMvc
            .perform(delete(ENTITY_API_URL_ID, colombofilo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Colombofilo> colombofiloList = colombofiloRepository.findAll();
        assertThat(colombofiloList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
