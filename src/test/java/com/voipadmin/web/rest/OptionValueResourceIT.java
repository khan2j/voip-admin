package com.voipadmin.web.rest;

import com.voipadmin.VoipadminApp;
import com.voipadmin.domain.OptionValue;
import com.voipadmin.repository.OptionValueRepository;
import com.voipadmin.service.OptionValueService;
import com.voipadmin.service.dto.OptionValueDTO;
import com.voipadmin.service.mapper.OptionValueMapper;

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
 * Integration tests for the {@link OptionValueResource} REST controller.
 */
@SpringBootTest(classes = VoipadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OptionValueResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private OptionValueRepository optionValueRepository;

    @Autowired
    private OptionValueMapper optionValueMapper;

    @Autowired
    private OptionValueService optionValueService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOptionValueMockMvc;

    private OptionValue optionValue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OptionValue createEntity(EntityManager em) {
        OptionValue optionValue = new OptionValue()
            .value(DEFAULT_VALUE);
        return optionValue;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OptionValue createUpdatedEntity(EntityManager em) {
        OptionValue optionValue = new OptionValue()
            .value(UPDATED_VALUE);
        return optionValue;
    }

    @BeforeEach
    public void initTest() {
        optionValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createOptionValue() throws Exception {
        int databaseSizeBeforeCreate = optionValueRepository.findAll().size();
        // Create the OptionValue
        OptionValueDTO optionValueDTO = optionValueMapper.toDto(optionValue);
        restOptionValueMockMvc.perform(post("/api/option-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optionValueDTO)))
            .andExpect(status().isCreated());

        // Validate the OptionValue in the database
        List<OptionValue> optionValueList = optionValueRepository.findAll();
        assertThat(optionValueList).hasSize(databaseSizeBeforeCreate + 1);
        OptionValue testOptionValue = optionValueList.get(optionValueList.size() - 1);
        assertThat(testOptionValue.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createOptionValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = optionValueRepository.findAll().size();

        // Create the OptionValue with an existing ID
        optionValue.setId(1L);
        OptionValueDTO optionValueDTO = optionValueMapper.toDto(optionValue);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptionValueMockMvc.perform(post("/api/option-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optionValueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OptionValue in the database
        List<OptionValue> optionValueList = optionValueRepository.findAll();
        assertThat(optionValueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOptionValues() throws Exception {
        // Initialize the database
        optionValueRepository.saveAndFlush(optionValue);

        // Get all the optionValueList
        restOptionValueMockMvc.perform(get("/api/option-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optionValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getOptionValue() throws Exception {
        // Initialize the database
        optionValueRepository.saveAndFlush(optionValue);

        // Get the optionValue
        restOptionValueMockMvc.perform(get("/api/option-values/{id}", optionValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(optionValue.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingOptionValue() throws Exception {
        // Get the optionValue
        restOptionValueMockMvc.perform(get("/api/option-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOptionValue() throws Exception {
        // Initialize the database
        optionValueRepository.saveAndFlush(optionValue);

        int databaseSizeBeforeUpdate = optionValueRepository.findAll().size();

        // Update the optionValue
        OptionValue updatedOptionValue = optionValueRepository.findById(optionValue.getId()).get();
        // Disconnect from session so that the updates on updatedOptionValue are not directly saved in db
        em.detach(updatedOptionValue);
        updatedOptionValue
            .value(UPDATED_VALUE);
        OptionValueDTO optionValueDTO = optionValueMapper.toDto(updatedOptionValue);

        restOptionValueMockMvc.perform(put("/api/option-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optionValueDTO)))
            .andExpect(status().isOk());

        // Validate the OptionValue in the database
        List<OptionValue> optionValueList = optionValueRepository.findAll();
        assertThat(optionValueList).hasSize(databaseSizeBeforeUpdate);
        OptionValue testOptionValue = optionValueList.get(optionValueList.size() - 1);
        assertThat(testOptionValue.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingOptionValue() throws Exception {
        int databaseSizeBeforeUpdate = optionValueRepository.findAll().size();

        // Create the OptionValue
        OptionValueDTO optionValueDTO = optionValueMapper.toDto(optionValue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionValueMockMvc.perform(put("/api/option-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(optionValueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OptionValue in the database
        List<OptionValue> optionValueList = optionValueRepository.findAll();
        assertThat(optionValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOptionValue() throws Exception {
        // Initialize the database
        optionValueRepository.saveAndFlush(optionValue);

        int databaseSizeBeforeDelete = optionValueRepository.findAll().size();

        // Delete the optionValue
        restOptionValueMockMvc.perform(delete("/api/option-values/{id}", optionValue.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OptionValue> optionValueList = optionValueRepository.findAll();
        assertThat(optionValueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
