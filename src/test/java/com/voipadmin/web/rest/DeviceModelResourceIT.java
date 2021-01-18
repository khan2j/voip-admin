package com.voipadmin.web.rest;

import com.voipadmin.VoipadminApp;
import com.voipadmin.domain.DeviceModel;
import com.voipadmin.repository.DeviceModelRepository;
import com.voipadmin.service.DeviceModelService;
import com.voipadmin.service.dto.DeviceModelDTO;
import com.voipadmin.service.mapper.DeviceModelMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.voipadmin.domain.enumeration.DeviceType;
/**
 * Integration tests for the {@link DeviceModelResource} REST controller.
 */
@SpringBootTest(classes = VoipadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DeviceModelResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_CONFIGURABLE = false;
    private static final Boolean UPDATED_IS_CONFIGURABLE = true;

    private static final Integer DEFAULT_LINES_COUNT = 1;
    private static final Integer UPDATED_LINES_COUNT = 2;

    private static final byte[] DEFAULT_CONFIG_TEMPLATE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONFIG_TEMPLATE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONFIG_TEMPLATE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONFIG_TEMPLATE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_FIRMWARE_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FIRMWARE_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FIRMWARE_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FIRMWARE_FILE_CONTENT_TYPE = "image/png";

    private static final DeviceType DEFAULT_DEVICE_TYPE = DeviceType.IPPHONE;
    private static final DeviceType UPDATED_DEVICE_TYPE = DeviceType.IPGATEWAY;

    @Autowired
    private DeviceModelRepository deviceModelRepository;

    @Autowired
    private DeviceModelMapper deviceModelMapper;

    @Autowired
    private DeviceModelService deviceModelService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeviceModelMockMvc;

    private DeviceModel deviceModel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceModel createEntity(EntityManager em) {
        DeviceModel deviceModel = new DeviceModel()
            .name(DEFAULT_NAME)
            .isConfigurable(DEFAULT_IS_CONFIGURABLE)
            .linesCount(DEFAULT_LINES_COUNT)
            .configTemplate(DEFAULT_CONFIG_TEMPLATE)
            .configTemplateContentType(DEFAULT_CONFIG_TEMPLATE_CONTENT_TYPE)
            .firmwareFile(DEFAULT_FIRMWARE_FILE)
            .firmwareFileContentType(DEFAULT_FIRMWARE_FILE_CONTENT_TYPE)
            .deviceType(DEFAULT_DEVICE_TYPE);
        return deviceModel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceModel createUpdatedEntity(EntityManager em) {
        DeviceModel deviceModel = new DeviceModel()
            .name(UPDATED_NAME)
            .isConfigurable(UPDATED_IS_CONFIGURABLE)
            .linesCount(UPDATED_LINES_COUNT)
            .configTemplate(UPDATED_CONFIG_TEMPLATE)
            .configTemplateContentType(UPDATED_CONFIG_TEMPLATE_CONTENT_TYPE)
            .firmwareFile(UPDATED_FIRMWARE_FILE)
            .firmwareFileContentType(UPDATED_FIRMWARE_FILE_CONTENT_TYPE)
            .deviceType(UPDATED_DEVICE_TYPE);
        return deviceModel;
    }

    @BeforeEach
    public void initTest() {
        deviceModel = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeviceModel() throws Exception {
        int databaseSizeBeforeCreate = deviceModelRepository.findAll().size();
        // Create the DeviceModel
        DeviceModelDTO deviceModelDTO = deviceModelMapper.toDto(deviceModel);
        restDeviceModelMockMvc.perform(post("/api/device-models")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceModelDTO)))
            .andExpect(status().isCreated());

        // Validate the DeviceModel in the database
        List<DeviceModel> deviceModelList = deviceModelRepository.findAll();
        assertThat(deviceModelList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceModel testDeviceModel = deviceModelList.get(deviceModelList.size() - 1);
        assertThat(testDeviceModel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeviceModel.isIsConfigurable()).isEqualTo(DEFAULT_IS_CONFIGURABLE);
        assertThat(testDeviceModel.getLinesCount()).isEqualTo(DEFAULT_LINES_COUNT);
        assertThat(testDeviceModel.getConfigTemplate()).isEqualTo(DEFAULT_CONFIG_TEMPLATE);
        assertThat(testDeviceModel.getConfigTemplateContentType()).isEqualTo(DEFAULT_CONFIG_TEMPLATE_CONTENT_TYPE);
        assertThat(testDeviceModel.getFirmwareFile()).isEqualTo(DEFAULT_FIRMWARE_FILE);
        assertThat(testDeviceModel.getFirmwareFileContentType()).isEqualTo(DEFAULT_FIRMWARE_FILE_CONTENT_TYPE);
        assertThat(testDeviceModel.getDeviceType()).isEqualTo(DEFAULT_DEVICE_TYPE);
    }

    @Test
    @Transactional
    public void createDeviceModelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceModelRepository.findAll().size();

        // Create the DeviceModel with an existing ID
        deviceModel.setId(1L);
        DeviceModelDTO deviceModelDTO = deviceModelMapper.toDto(deviceModel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceModelMockMvc.perform(post("/api/device-models")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceModel in the database
        List<DeviceModel> deviceModelList = deviceModelRepository.findAll();
        assertThat(deviceModelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceModelRepository.findAll().size();
        // set the field null
        deviceModel.setName(null);

        // Create the DeviceModel, which fails.
        DeviceModelDTO deviceModelDTO = deviceModelMapper.toDto(deviceModel);


        restDeviceModelMockMvc.perform(post("/api/device-models")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceModelDTO)))
            .andExpect(status().isBadRequest());

        List<DeviceModel> deviceModelList = deviceModelRepository.findAll();
        assertThat(deviceModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsConfigurableIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceModelRepository.findAll().size();
        // set the field null
        deviceModel.setIsConfigurable(null);

        // Create the DeviceModel, which fails.
        DeviceModelDTO deviceModelDTO = deviceModelMapper.toDto(deviceModel);


        restDeviceModelMockMvc.perform(post("/api/device-models")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceModelDTO)))
            .andExpect(status().isBadRequest());

        List<DeviceModel> deviceModelList = deviceModelRepository.findAll();
        assertThat(deviceModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeviceModels() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get all the deviceModelList
        restDeviceModelMockMvc.perform(get("/api/device-models?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceModel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isConfigurable").value(hasItem(DEFAULT_IS_CONFIGURABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].linesCount").value(hasItem(DEFAULT_LINES_COUNT)))
            .andExpect(jsonPath("$.[*].configTemplateContentType").value(hasItem(DEFAULT_CONFIG_TEMPLATE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].configTemplate").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONFIG_TEMPLATE))))
            .andExpect(jsonPath("$.[*].firmwareFileContentType").value(hasItem(DEFAULT_FIRMWARE_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].firmwareFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_FIRMWARE_FILE))))
            .andExpect(jsonPath("$.[*].deviceType").value(hasItem(DEFAULT_DEVICE_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getDeviceModel() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get the deviceModel
        restDeviceModelMockMvc.perform(get("/api/device-models/{id}", deviceModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deviceModel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.isConfigurable").value(DEFAULT_IS_CONFIGURABLE.booleanValue()))
            .andExpect(jsonPath("$.linesCount").value(DEFAULT_LINES_COUNT))
            .andExpect(jsonPath("$.configTemplateContentType").value(DEFAULT_CONFIG_TEMPLATE_CONTENT_TYPE))
            .andExpect(jsonPath("$.configTemplate").value(Base64Utils.encodeToString(DEFAULT_CONFIG_TEMPLATE)))
            .andExpect(jsonPath("$.firmwareFileContentType").value(DEFAULT_FIRMWARE_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.firmwareFile").value(Base64Utils.encodeToString(DEFAULT_FIRMWARE_FILE)))
            .andExpect(jsonPath("$.deviceType").value(DEFAULT_DEVICE_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDeviceModel() throws Exception {
        // Get the deviceModel
        restDeviceModelMockMvc.perform(get("/api/device-models/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeviceModel() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        int databaseSizeBeforeUpdate = deviceModelRepository.findAll().size();

        // Update the deviceModel
        DeviceModel updatedDeviceModel = deviceModelRepository.findById(deviceModel.getId()).get();
        // Disconnect from session so that the updates on updatedDeviceModel are not directly saved in db
        em.detach(updatedDeviceModel);
        updatedDeviceModel
            .name(UPDATED_NAME)
            .isConfigurable(UPDATED_IS_CONFIGURABLE)
            .linesCount(UPDATED_LINES_COUNT)
            .configTemplate(UPDATED_CONFIG_TEMPLATE)
            .configTemplateContentType(UPDATED_CONFIG_TEMPLATE_CONTENT_TYPE)
            .firmwareFile(UPDATED_FIRMWARE_FILE)
            .firmwareFileContentType(UPDATED_FIRMWARE_FILE_CONTENT_TYPE)
            .deviceType(UPDATED_DEVICE_TYPE);
        DeviceModelDTO deviceModelDTO = deviceModelMapper.toDto(updatedDeviceModel);

        restDeviceModelMockMvc.perform(put("/api/device-models")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceModelDTO)))
            .andExpect(status().isOk());

        // Validate the DeviceModel in the database
        List<DeviceModel> deviceModelList = deviceModelRepository.findAll();
        assertThat(deviceModelList).hasSize(databaseSizeBeforeUpdate);
        DeviceModel testDeviceModel = deviceModelList.get(deviceModelList.size() - 1);
        assertThat(testDeviceModel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeviceModel.isIsConfigurable()).isEqualTo(UPDATED_IS_CONFIGURABLE);
        assertThat(testDeviceModel.getLinesCount()).isEqualTo(UPDATED_LINES_COUNT);
        assertThat(testDeviceModel.getConfigTemplate()).isEqualTo(UPDATED_CONFIG_TEMPLATE);
        assertThat(testDeviceModel.getConfigTemplateContentType()).isEqualTo(UPDATED_CONFIG_TEMPLATE_CONTENT_TYPE);
        assertThat(testDeviceModel.getFirmwareFile()).isEqualTo(UPDATED_FIRMWARE_FILE);
        assertThat(testDeviceModel.getFirmwareFileContentType()).isEqualTo(UPDATED_FIRMWARE_FILE_CONTENT_TYPE);
        assertThat(testDeviceModel.getDeviceType()).isEqualTo(UPDATED_DEVICE_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDeviceModel() throws Exception {
        int databaseSizeBeforeUpdate = deviceModelRepository.findAll().size();

        // Create the DeviceModel
        DeviceModelDTO deviceModelDTO = deviceModelMapper.toDto(deviceModel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceModelMockMvc.perform(put("/api/device-models")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceModel in the database
        List<DeviceModel> deviceModelList = deviceModelRepository.findAll();
        assertThat(deviceModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeviceModel() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        int databaseSizeBeforeDelete = deviceModelRepository.findAll().size();

        // Delete the deviceModel
        restDeviceModelMockMvc.perform(delete("/api/device-models/{id}", deviceModel.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeviceModel> deviceModelList = deviceModelRepository.findAll();
        assertThat(deviceModelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
