package com.voipadmin.web.rest;

import com.voipadmin.VoipadminApp;
import com.voipadmin.domain.ResponsiblePerson;
import com.voipadmin.repository.ResponsiblePersonRepository;
import com.voipadmin.service.ResponsiblePersonService;
import com.voipadmin.service.dto.ResponsiblePersonDTO;
import com.voipadmin.service.mapper.ResponsiblePersonMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ResponsiblePersonResource} REST controller.
 */
@SpringBootTest(classes = VoipadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ResponsiblePersonResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_ROOM = "AAAAAAAAAA";
    private static final String UPDATED_ROOM = "BBBBBBBBBB";

    @Autowired
    private ResponsiblePersonRepository responsiblePersonRepository;

    @Autowired
    private ResponsiblePersonMapper responsiblePersonMapper;

    @Autowired
    private ResponsiblePersonService responsiblePersonService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResponsiblePersonMockMvc;

    private ResponsiblePerson responsiblePerson;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponsiblePerson createEntity(EntityManager em) {
        ResponsiblePerson responsiblePerson = new ResponsiblePerson()
            .code(DEFAULT_CODE)
            .firstName(DEFAULT_FIRST_NAME)
            .secondName(DEFAULT_SECOND_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .position(DEFAULT_POSITION)
            .room(DEFAULT_ROOM);
        return responsiblePerson;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponsiblePerson createUpdatedEntity(EntityManager em) {
        ResponsiblePerson responsiblePerson = new ResponsiblePerson()
            .code(UPDATED_CODE)
            .firstName(UPDATED_FIRST_NAME)
            .secondName(UPDATED_SECOND_NAME)
            .lastName(UPDATED_LAST_NAME)
            .position(UPDATED_POSITION)
            .room(UPDATED_ROOM);
        return responsiblePerson;
    }

    @BeforeEach
    public void initTest() {
        responsiblePerson = createEntity(em);
    }

    @Test
    @Transactional
    public void createResponsiblePerson() throws Exception {
        int databaseSizeBeforeCreate = responsiblePersonRepository.findAll().size();
        // Create the ResponsiblePerson
        ResponsiblePersonDTO responsiblePersonDTO = responsiblePersonMapper.toDto(responsiblePerson);
        restResponsiblePersonMockMvc.perform(post("/api/responsible-people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(responsiblePersonDTO)))
            .andExpect(status().isCreated());

        // Validate the ResponsiblePerson in the database
        List<ResponsiblePerson> responsiblePersonList = responsiblePersonRepository.findAll();
        assertThat(responsiblePersonList).hasSize(databaseSizeBeforeCreate + 1);
        ResponsiblePerson testResponsiblePerson = responsiblePersonList.get(responsiblePersonList.size() - 1);
        assertThat(testResponsiblePerson.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testResponsiblePerson.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testResponsiblePerson.getSecondName()).isEqualTo(DEFAULT_SECOND_NAME);
        assertThat(testResponsiblePerson.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testResponsiblePerson.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testResponsiblePerson.getRoom()).isEqualTo(DEFAULT_ROOM);
    }

    @Test
    @Transactional
    public void createResponsiblePersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = responsiblePersonRepository.findAll().size();

        // Create the ResponsiblePerson with an existing ID
        responsiblePerson.setId(1L);
        ResponsiblePersonDTO responsiblePersonDTO = responsiblePersonMapper.toDto(responsiblePerson);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsiblePersonMockMvc.perform(post("/api/responsible-people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(responsiblePersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResponsiblePerson in the database
        List<ResponsiblePerson> responsiblePersonList = responsiblePersonRepository.findAll();
        assertThat(responsiblePersonList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsiblePersonRepository.findAll().size();
        // set the field null
        responsiblePerson.setCode(null);

        // Create the ResponsiblePerson, which fails.
        ResponsiblePersonDTO responsiblePersonDTO = responsiblePersonMapper.toDto(responsiblePerson);


        restResponsiblePersonMockMvc.perform(post("/api/responsible-people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(responsiblePersonDTO)))
            .andExpect(status().isBadRequest());

        List<ResponsiblePerson> responsiblePersonList = responsiblePersonRepository.findAll();
        assertThat(responsiblePersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsiblePersonRepository.findAll().size();
        // set the field null
        responsiblePerson.setFirstName(null);

        // Create the ResponsiblePerson, which fails.
        ResponsiblePersonDTO responsiblePersonDTO = responsiblePersonMapper.toDto(responsiblePerson);


        restResponsiblePersonMockMvc.perform(post("/api/responsible-people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(responsiblePersonDTO)))
            .andExpect(status().isBadRequest());

        List<ResponsiblePerson> responsiblePersonList = responsiblePersonRepository.findAll();
        assertThat(responsiblePersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsiblePersonRepository.findAll().size();
        // set the field null
        responsiblePerson.setLastName(null);

        // Create the ResponsiblePerson, which fails.
        ResponsiblePersonDTO responsiblePersonDTO = responsiblePersonMapper.toDto(responsiblePerson);


        restResponsiblePersonMockMvc.perform(post("/api/responsible-people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(responsiblePersonDTO)))
            .andExpect(status().isBadRequest());

        List<ResponsiblePerson> responsiblePersonList = responsiblePersonRepository.findAll();
        assertThat(responsiblePersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeople() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList
        restResponsiblePersonMockMvc.perform(get("/api/responsible-people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsiblePerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].secondName").value(hasItem(DEFAULT_SECOND_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].room").value(hasItem(DEFAULT_ROOM)));
    }
    
    @Test
    @Transactional
    public void getResponsiblePerson() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get the responsiblePerson
        restResponsiblePersonMockMvc.perform(get("/api/responsible-people/{id}", responsiblePerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(responsiblePerson.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.secondName").value(DEFAULT_SECOND_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.room").value(DEFAULT_ROOM));
    }
    @Test
    @Transactional
    public void getNonExistingResponsiblePerson() throws Exception {
        // Get the responsiblePerson
        restResponsiblePersonMockMvc.perform(get("/api/responsible-people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResponsiblePerson() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        int databaseSizeBeforeUpdate = responsiblePersonRepository.findAll().size();

        // Update the responsiblePerson
        ResponsiblePerson updatedResponsiblePerson = responsiblePersonRepository.findById(responsiblePerson.getId()).get();
        // Disconnect from session so that the updates on updatedResponsiblePerson are not directly saved in db
        em.detach(updatedResponsiblePerson);
        updatedResponsiblePerson
            .code(UPDATED_CODE)
            .firstName(UPDATED_FIRST_NAME)
            .secondName(UPDATED_SECOND_NAME)
            .lastName(UPDATED_LAST_NAME)
            .position(UPDATED_POSITION)
            .room(UPDATED_ROOM);
        ResponsiblePersonDTO responsiblePersonDTO = responsiblePersonMapper.toDto(updatedResponsiblePerson);

        restResponsiblePersonMockMvc.perform(put("/api/responsible-people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(responsiblePersonDTO)))
            .andExpect(status().isOk());

        // Validate the ResponsiblePerson in the database
        List<ResponsiblePerson> responsiblePersonList = responsiblePersonRepository.findAll();
        assertThat(responsiblePersonList).hasSize(databaseSizeBeforeUpdate);
        ResponsiblePerson testResponsiblePerson = responsiblePersonList.get(responsiblePersonList.size() - 1);
        assertThat(testResponsiblePerson.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testResponsiblePerson.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testResponsiblePerson.getSecondName()).isEqualTo(UPDATED_SECOND_NAME);
        assertThat(testResponsiblePerson.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testResponsiblePerson.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testResponsiblePerson.getRoom()).isEqualTo(UPDATED_ROOM);
    }

    @Test
    @Transactional
    public void updateNonExistingResponsiblePerson() throws Exception {
        int databaseSizeBeforeUpdate = responsiblePersonRepository.findAll().size();

        // Create the ResponsiblePerson
        ResponsiblePersonDTO responsiblePersonDTO = responsiblePersonMapper.toDto(responsiblePerson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsiblePersonMockMvc.perform(put("/api/responsible-people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(responsiblePersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResponsiblePerson in the database
        List<ResponsiblePerson> responsiblePersonList = responsiblePersonRepository.findAll();
        assertThat(responsiblePersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResponsiblePerson() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        int databaseSizeBeforeDelete = responsiblePersonRepository.findAll().size();

        // Delete the responsiblePerson
        restResponsiblePersonMockMvc.perform(delete("/api/responsible-people/{id}", responsiblePerson.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResponsiblePerson> responsiblePersonList = responsiblePersonRepository.findAll();
        assertThat(responsiblePersonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
