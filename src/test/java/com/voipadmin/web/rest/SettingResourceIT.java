package com.voipadmin.web.rest;

import com.voipadmin.VoipadminApp;
import com.voipadmin.domain.Setting;
import com.voipadmin.repository.SettingRepository;
import com.voipadmin.service.SettingService;
import com.voipadmin.service.dto.SettingDTO;
import com.voipadmin.service.mapper.SettingMapper;

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

/**
 * Integration tests for the {@link SettingResource} REST controller.
 */
@SpringBootTest(classes = VoipadminApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SettingResourceIT {

    private static final String DEFAULT_TEXT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_VALUE = "BBBBBBBBBB";

    @Autowired
    private SettingRepository settingRepository;

    @Mock
    private SettingRepository settingRepositoryMock;

    @Autowired
    private SettingMapper settingMapper;

    @Mock
    private SettingService settingServiceMock;

    @Autowired
    private SettingService settingService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSettingMockMvc;

    private Setting setting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Setting createEntity(EntityManager em) {
        Setting setting = new Setting()
            .textValue(DEFAULT_TEXT_VALUE);
        return setting;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Setting createUpdatedEntity(EntityManager em) {
        Setting setting = new Setting()
            .textValue(UPDATED_TEXT_VALUE);
        return setting;
    }

    @BeforeEach
    public void initTest() {
        setting = createEntity(em);
    }

    @Test
    @Transactional
    public void createSetting() throws Exception {
        int databaseSizeBeforeCreate = settingRepository.findAll().size();
        // Create the Setting
        SettingDTO settingDTO = settingMapper.toDto(setting);
        restSettingMockMvc.perform(post("/api/settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isCreated());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeCreate + 1);
        Setting testSetting = settingList.get(settingList.size() - 1);
        assertThat(testSetting.getTextValue()).isEqualTo(DEFAULT_TEXT_VALUE);
    }

    @Test
    @Transactional
    public void createSettingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = settingRepository.findAll().size();

        // Create the Setting with an existing ID
        setting.setId(1L);
        SettingDTO settingDTO = settingMapper.toDto(setting);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSettingMockMvc.perform(post("/api/settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSettings() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList
        restSettingMockMvc.perform(get("/api/settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(setting.getId().intValue())))
            .andExpect(jsonPath("$.[*].textValue").value(hasItem(DEFAULT_TEXT_VALUE)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllSettingsWithEagerRelationshipsIsEnabled() throws Exception {
        when(settingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSettingMockMvc.perform(get("/api/settings?eagerload=true"))
            .andExpect(status().isOk());

        verify(settingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllSettingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(settingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSettingMockMvc.perform(get("/api/settings?eagerload=true"))
            .andExpect(status().isOk());

        verify(settingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getSetting() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get the setting
        restSettingMockMvc.perform(get("/api/settings/{id}", setting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(setting.getId().intValue()))
            .andExpect(jsonPath("$.textValue").value(DEFAULT_TEXT_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingSetting() throws Exception {
        // Get the setting
        restSettingMockMvc.perform(get("/api/settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSetting() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        int databaseSizeBeforeUpdate = settingRepository.findAll().size();

        // Update the setting
        Setting updatedSetting = settingRepository.findById(setting.getId()).get();
        // Disconnect from session so that the updates on updatedSetting are not directly saved in db
        em.detach(updatedSetting);
        updatedSetting
            .textValue(UPDATED_TEXT_VALUE);
        SettingDTO settingDTO = settingMapper.toDto(updatedSetting);

        restSettingMockMvc.perform(put("/api/settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isOk());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
        Setting testSetting = settingList.get(settingList.size() - 1);
        assertThat(testSetting.getTextValue()).isEqualTo(UPDATED_TEXT_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingSetting() throws Exception {
        int databaseSizeBeforeUpdate = settingRepository.findAll().size();

        // Create the Setting
        SettingDTO settingDTO = settingMapper.toDto(setting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSettingMockMvc.perform(put("/api/settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSetting() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        int databaseSizeBeforeDelete = settingRepository.findAll().size();

        // Delete the setting
        restSettingMockMvc.perform(delete("/api/settings/{id}", setting.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
