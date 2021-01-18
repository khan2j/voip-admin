package com.voipadmin.web.rest;

import com.voipadmin.VoipadminApp;
import com.voipadmin.domain.Option;
import com.voipadmin.repository.OptionRepository;
import com.voipadmin.service.OptionService;
import com.voipadmin.service.dto.OptionDTO;
import com.voipadmin.service.mapper.OptionMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.voipadmin.domain.enumeration.OptionValueType;
/**
 * Integration tests for the {@link OptionResource} REST controller.
 */
@SpringBootTest(classes = VoipadminApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class OptionResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCR = "AAAAAAAAAA";
    private static final String UPDATED_DESCR = "BBBBBBBBBB";

    private static final OptionValueType DEFAULT_VALUE_TYPE = OptionValueType.TEXT;
    private static final OptionValueType UPDATED_VALUE_TYPE = OptionValueType.SELECT;

    private static final Boolean DEFAULT_MULTIPLE = false;
    private static final Boolean UPDATED_MULTIPLE = true;

    @Autowired
    private OptionRepository optionRepository;

    @Mock
    private OptionRepository optionRepositoryMock;

    @Autowired
    private OptionMapper optionMapper;

    @Mock
    private OptionService optionServiceMock;

    @Autowired
    private OptionService optionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOptionMockMvc;

    private Option option;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Option createEntity(EntityManager em) {
        Option option = new Option()
            .code(DEFAULT_CODE)
            .descr(DEFAULT_DESCR)
            .valueType(DEFAULT_VALUE_TYPE)
            .multiple(DEFAULT_MULTIPLE);
        return option;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Option createUpdatedEntity(EntityManager em) {
        Option option = new Option()
            .code(UPDATED_CODE)
            .descr(UPDATED_DESCR)
            .valueType(UPDATED_VALUE_TYPE)
            .multiple(UPDATED_MULTIPLE);
        return option;
    }

    @BeforeEach
    public void initTest() {
        option = createEntity(em);
    }

    @Test
    @Transactional
    public void createOption() throws Exception {
        int databaseSizeBeforeCreate = optionRepository.findAll().size();
        // Create the Option
        OptionDTO optionDTO = optionMapper.toDto(option);
        restOptionMockMvc.perform(post("/api/options")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optionDTO)))
            .andExpect(status().isCreated());

        // Validate the Option in the database
        List<Option> optionList = optionRepository.findAll();
        assertThat(optionList).hasSize(databaseSizeBeforeCreate + 1);
        Option testOption = optionList.get(optionList.size() - 1);
        assertThat(testOption.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOption.getDescr()).isEqualTo(DEFAULT_DESCR);
        assertThat(testOption.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testOption.isMultiple()).isEqualTo(DEFAULT_MULTIPLE);
    }

    @Test
    @Transactional
    public void createOptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = optionRepository.findAll().size();

        // Create the Option with an existing ID
        option.setId(1L);
        OptionDTO optionDTO = optionMapper.toDto(option);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptionMockMvc.perform(post("/api/options")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Option in the database
        List<Option> optionList = optionRepository.findAll();
        assertThat(optionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOptions() throws Exception {
        // Initialize the database
        optionRepository.saveAndFlush(option);

        // Get all the optionList
        restOptionMockMvc.perform(get("/api/options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(option.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].descr").value(hasItem(DEFAULT_DESCR)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].multiple").value(hasItem(DEFAULT_MULTIPLE.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllOptionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(optionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOptionMockMvc.perform(get("/api/options?eagerload=true"))
            .andExpect(status().isOk());

        verify(optionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllOptionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(optionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOptionMockMvc.perform(get("/api/options?eagerload=true"))
            .andExpect(status().isOk());

        verify(optionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getOption() throws Exception {
        // Initialize the database
        optionRepository.saveAndFlush(option);

        // Get the option
        restOptionMockMvc.perform(get("/api/options/{id}", option.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(option.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.descr").value(DEFAULT_DESCR))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE.toString()))
            .andExpect(jsonPath("$.multiple").value(DEFAULT_MULTIPLE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingOption() throws Exception {
        // Get the option
        restOptionMockMvc.perform(get("/api/options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOption() throws Exception {
        // Initialize the database
        optionRepository.saveAndFlush(option);

        int databaseSizeBeforeUpdate = optionRepository.findAll().size();

        // Update the option
        Option updatedOption = optionRepository.findById(option.getId()).get();
        // Disconnect from session so that the updates on updatedOption are not directly saved in db
        em.detach(updatedOption);
        updatedOption
            .code(UPDATED_CODE)
            .descr(UPDATED_DESCR)
            .valueType(UPDATED_VALUE_TYPE)
            .multiple(UPDATED_MULTIPLE);
        OptionDTO optionDTO = optionMapper.toDto(updatedOption);

        restOptionMockMvc.perform(put("/api/options")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optionDTO)))
            .andExpect(status().isOk());

        // Validate the Option in the database
        List<Option> optionList = optionRepository.findAll();
        assertThat(optionList).hasSize(databaseSizeBeforeUpdate);
        Option testOption = optionList.get(optionList.size() - 1);
        assertThat(testOption.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOption.getDescr()).isEqualTo(UPDATED_DESCR);
        assertThat(testOption.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testOption.isMultiple()).isEqualTo(UPDATED_MULTIPLE);
    }

    @Test
    @Transactional
    public void updateNonExistingOption() throws Exception {
        int databaseSizeBeforeUpdate = optionRepository.findAll().size();

        // Create the Option
        OptionDTO optionDTO = optionMapper.toDto(option);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionMockMvc.perform(put("/api/options")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Option in the database
        List<Option> optionList = optionRepository.findAll();
        assertThat(optionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOption() throws Exception {
        // Initialize the database
        optionRepository.saveAndFlush(option);

        int databaseSizeBeforeDelete = optionRepository.findAll().size();

        // Delete the option
        restOptionMockMvc.perform(delete("/api/options/{id}", option.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Option> optionList = optionRepository.findAll();
        assertThat(optionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
