package com.voipadmin.web.rest;

import com.voipadmin.VoipadminApp;
import com.voipadmin.domain.OtherDeviceType;
import com.voipadmin.repository.OtherDeviceTypeRepository;
import com.voipadmin.service.OtherDeviceTypeService;
import com.voipadmin.service.dto.OtherDeviceTypeDTO;
import com.voipadmin.service.mapper.OtherDeviceTypeMapper;

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
 * Integration tests for the {@link OtherDeviceTypeResource} REST controller.
 */
@SpringBootTest(classes = VoipadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OtherDeviceTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private OtherDeviceTypeRepository otherDeviceTypeRepository;

    @Autowired
    private OtherDeviceTypeMapper otherDeviceTypeMapper;

    @Autowired
    private OtherDeviceTypeService otherDeviceTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOtherDeviceTypeMockMvc;

    private OtherDeviceType otherDeviceType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OtherDeviceType createEntity(EntityManager em) {
        OtherDeviceType otherDeviceType = new OtherDeviceType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return otherDeviceType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OtherDeviceType createUpdatedEntity(EntityManager em) {
        OtherDeviceType otherDeviceType = new OtherDeviceType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return otherDeviceType;
    }

    @BeforeEach
    public void initTest() {
        otherDeviceType = createEntity(em);
    }

    @Test
    @Transactional
    public void createOtherDeviceType() throws Exception {
        int databaseSizeBeforeCreate = otherDeviceTypeRepository.findAll().size();
        // Create the OtherDeviceType
        OtherDeviceTypeDTO otherDeviceTypeDTO = otherDeviceTypeMapper.toDto(otherDeviceType);
        restOtherDeviceTypeMockMvc.perform(post("/api/other-device-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(otherDeviceTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the OtherDeviceType in the database
        List<OtherDeviceType> otherDeviceTypeList = otherDeviceTypeRepository.findAll();
        assertThat(otherDeviceTypeList).hasSize(databaseSizeBeforeCreate + 1);
        OtherDeviceType testOtherDeviceType = otherDeviceTypeList.get(otherDeviceTypeList.size() - 1);
        assertThat(testOtherDeviceType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOtherDeviceType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createOtherDeviceTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = otherDeviceTypeRepository.findAll().size();

        // Create the OtherDeviceType with an existing ID
        otherDeviceType.setId(1L);
        OtherDeviceTypeDTO otherDeviceTypeDTO = otherDeviceTypeMapper.toDto(otherDeviceType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOtherDeviceTypeMockMvc.perform(post("/api/other-device-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(otherDeviceTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OtherDeviceType in the database
        List<OtherDeviceType> otherDeviceTypeList = otherDeviceTypeRepository.findAll();
        assertThat(otherDeviceTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOtherDeviceTypes() throws Exception {
        // Initialize the database
        otherDeviceTypeRepository.saveAndFlush(otherDeviceType);

        // Get all the otherDeviceTypeList
        restOtherDeviceTypeMockMvc.perform(get("/api/other-device-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(otherDeviceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getOtherDeviceType() throws Exception {
        // Initialize the database
        otherDeviceTypeRepository.saveAndFlush(otherDeviceType);

        // Get the otherDeviceType
        restOtherDeviceTypeMockMvc.perform(get("/api/other-device-types/{id}", otherDeviceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(otherDeviceType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingOtherDeviceType() throws Exception {
        // Get the otherDeviceType
        restOtherDeviceTypeMockMvc.perform(get("/api/other-device-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOtherDeviceType() throws Exception {
        // Initialize the database
        otherDeviceTypeRepository.saveAndFlush(otherDeviceType);

        int databaseSizeBeforeUpdate = otherDeviceTypeRepository.findAll().size();

        // Update the otherDeviceType
        OtherDeviceType updatedOtherDeviceType = otherDeviceTypeRepository.findById(otherDeviceType.getId()).get();
        // Disconnect from session so that the updates on updatedOtherDeviceType are not directly saved in db
        em.detach(updatedOtherDeviceType);
        updatedOtherDeviceType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        OtherDeviceTypeDTO otherDeviceTypeDTO = otherDeviceTypeMapper.toDto(updatedOtherDeviceType);

        restOtherDeviceTypeMockMvc.perform(put("/api/other-device-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(otherDeviceTypeDTO)))
            .andExpect(status().isOk());

        // Validate the OtherDeviceType in the database
        List<OtherDeviceType> otherDeviceTypeList = otherDeviceTypeRepository.findAll();
        assertThat(otherDeviceTypeList).hasSize(databaseSizeBeforeUpdate);
        OtherDeviceType testOtherDeviceType = otherDeviceTypeList.get(otherDeviceTypeList.size() - 1);
        assertThat(testOtherDeviceType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOtherDeviceType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingOtherDeviceType() throws Exception {
        int databaseSizeBeforeUpdate = otherDeviceTypeRepository.findAll().size();

        // Create the OtherDeviceType
        OtherDeviceTypeDTO otherDeviceTypeDTO = otherDeviceTypeMapper.toDto(otherDeviceType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOtherDeviceTypeMockMvc.perform(put("/api/other-device-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(otherDeviceTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OtherDeviceType in the database
        List<OtherDeviceType> otherDeviceTypeList = otherDeviceTypeRepository.findAll();
        assertThat(otherDeviceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOtherDeviceType() throws Exception {
        // Initialize the database
        otherDeviceTypeRepository.saveAndFlush(otherDeviceType);

        int databaseSizeBeforeDelete = otherDeviceTypeRepository.findAll().size();

        // Delete the otherDeviceType
        restOtherDeviceTypeMockMvc.perform(delete("/api/other-device-types/{id}", otherDeviceType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OtherDeviceType> otherDeviceTypeList = otherDeviceTypeRepository.findAll();
        assertThat(otherDeviceTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
