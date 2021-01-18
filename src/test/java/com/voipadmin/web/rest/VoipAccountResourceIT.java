package com.voipadmin.web.rest;

import com.voipadmin.VoipadminApp;
import com.voipadmin.domain.VoipAccount;
import com.voipadmin.repository.VoipAccountRepository;
import com.voipadmin.service.VoipAccountService;
import com.voipadmin.service.dto.VoipAccountDTO;
import com.voipadmin.service.mapper.VoipAccountMapper;

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
 * Integration tests for the {@link VoipAccountResource} REST controller.
 */
@SpringBootTest(classes = VoipadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VoipAccountResourceIT {

    private static final Boolean DEFAULT_MANUALLY_CREATED = false;
    private static final Boolean UPDATED_MANUALLY_CREATED = true;

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_SIP_SERVER = "AAAAAAAAAA";
    private static final String UPDATED_SIP_SERVER = "BBBBBBBBBB";

    private static final String DEFAULT_SIP_PORT = "AAAAAAAAAA";
    private static final String UPDATED_SIP_PORT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LINE_ENABLE = false;
    private static final Boolean UPDATED_LINE_ENABLE = true;

    private static final String DEFAULT_LINE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_LINE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private VoipAccountRepository voipAccountRepository;

    @Autowired
    private VoipAccountMapper voipAccountMapper;

    @Autowired
    private VoipAccountService voipAccountService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoipAccountMockMvc;

    private VoipAccount voipAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoipAccount createEntity(EntityManager em) {
        VoipAccount voipAccount = new VoipAccount()
            .manuallyCreated(DEFAULT_MANUALLY_CREATED)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .sipServer(DEFAULT_SIP_SERVER)
            .sipPort(DEFAULT_SIP_PORT)
            .lineEnable(DEFAULT_LINE_ENABLE)
            .lineNumber(DEFAULT_LINE_NUMBER);
        return voipAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoipAccount createUpdatedEntity(EntityManager em) {
        VoipAccount voipAccount = new VoipAccount()
            .manuallyCreated(UPDATED_MANUALLY_CREATED)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .sipServer(UPDATED_SIP_SERVER)
            .sipPort(UPDATED_SIP_PORT)
            .lineEnable(UPDATED_LINE_ENABLE)
            .lineNumber(UPDATED_LINE_NUMBER);
        return voipAccount;
    }

    @BeforeEach
    public void initTest() {
        voipAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoipAccount() throws Exception {
        int databaseSizeBeforeCreate = voipAccountRepository.findAll().size();
        // Create the VoipAccount
        VoipAccountDTO voipAccountDTO = voipAccountMapper.toDto(voipAccount);
        restVoipAccountMockMvc.perform(post("/api/voip-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voipAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the VoipAccount in the database
        List<VoipAccount> voipAccountList = voipAccountRepository.findAll();
        assertThat(voipAccountList).hasSize(databaseSizeBeforeCreate + 1);
        VoipAccount testVoipAccount = voipAccountList.get(voipAccountList.size() - 1);
        assertThat(testVoipAccount.isManuallyCreated()).isEqualTo(DEFAULT_MANUALLY_CREATED);
        assertThat(testVoipAccount.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testVoipAccount.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testVoipAccount.getSipServer()).isEqualTo(DEFAULT_SIP_SERVER);
        assertThat(testVoipAccount.getSipPort()).isEqualTo(DEFAULT_SIP_PORT);
        assertThat(testVoipAccount.isLineEnable()).isEqualTo(DEFAULT_LINE_ENABLE);
        assertThat(testVoipAccount.getLineNumber()).isEqualTo(DEFAULT_LINE_NUMBER);
    }

    @Test
    @Transactional
    public void createVoipAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voipAccountRepository.findAll().size();

        // Create the VoipAccount with an existing ID
        voipAccount.setId(1L);
        VoipAccountDTO voipAccountDTO = voipAccountMapper.toDto(voipAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoipAccountMockMvc.perform(post("/api/voip-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voipAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VoipAccount in the database
        List<VoipAccount> voipAccountList = voipAccountRepository.findAll();
        assertThat(voipAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = voipAccountRepository.findAll().size();
        // set the field null
        voipAccount.setUsername(null);

        // Create the VoipAccount, which fails.
        VoipAccountDTO voipAccountDTO = voipAccountMapper.toDto(voipAccount);


        restVoipAccountMockMvc.perform(post("/api/voip-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voipAccountDTO)))
            .andExpect(status().isBadRequest());

        List<VoipAccount> voipAccountList = voipAccountRepository.findAll();
        assertThat(voipAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVoipAccounts() throws Exception {
        // Initialize the database
        voipAccountRepository.saveAndFlush(voipAccount);

        // Get all the voipAccountList
        restVoipAccountMockMvc.perform(get("/api/voip-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voipAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].manuallyCreated").value(hasItem(DEFAULT_MANUALLY_CREATED.booleanValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].sipServer").value(hasItem(DEFAULT_SIP_SERVER)))
            .andExpect(jsonPath("$.[*].sipPort").value(hasItem(DEFAULT_SIP_PORT)))
            .andExpect(jsonPath("$.[*].lineEnable").value(hasItem(DEFAULT_LINE_ENABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].lineNumber").value(hasItem(DEFAULT_LINE_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getVoipAccount() throws Exception {
        // Initialize the database
        voipAccountRepository.saveAndFlush(voipAccount);

        // Get the voipAccount
        restVoipAccountMockMvc.perform(get("/api/voip-accounts/{id}", voipAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voipAccount.getId().intValue()))
            .andExpect(jsonPath("$.manuallyCreated").value(DEFAULT_MANUALLY_CREATED.booleanValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.sipServer").value(DEFAULT_SIP_SERVER))
            .andExpect(jsonPath("$.sipPort").value(DEFAULT_SIP_PORT))
            .andExpect(jsonPath("$.lineEnable").value(DEFAULT_LINE_ENABLE.booleanValue()))
            .andExpect(jsonPath("$.lineNumber").value(DEFAULT_LINE_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingVoipAccount() throws Exception {
        // Get the voipAccount
        restVoipAccountMockMvc.perform(get("/api/voip-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoipAccount() throws Exception {
        // Initialize the database
        voipAccountRepository.saveAndFlush(voipAccount);

        int databaseSizeBeforeUpdate = voipAccountRepository.findAll().size();

        // Update the voipAccount
        VoipAccount updatedVoipAccount = voipAccountRepository.findById(voipAccount.getId()).get();
        // Disconnect from session so that the updates on updatedVoipAccount are not directly saved in db
        em.detach(updatedVoipAccount);
        updatedVoipAccount
            .manuallyCreated(UPDATED_MANUALLY_CREATED)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .sipServer(UPDATED_SIP_SERVER)
            .sipPort(UPDATED_SIP_PORT)
            .lineEnable(UPDATED_LINE_ENABLE)
            .lineNumber(UPDATED_LINE_NUMBER);
        VoipAccountDTO voipAccountDTO = voipAccountMapper.toDto(updatedVoipAccount);

        restVoipAccountMockMvc.perform(put("/api/voip-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voipAccountDTO)))
            .andExpect(status().isOk());

        // Validate the VoipAccount in the database
        List<VoipAccount> voipAccountList = voipAccountRepository.findAll();
        assertThat(voipAccountList).hasSize(databaseSizeBeforeUpdate);
        VoipAccount testVoipAccount = voipAccountList.get(voipAccountList.size() - 1);
        assertThat(testVoipAccount.isManuallyCreated()).isEqualTo(UPDATED_MANUALLY_CREATED);
        assertThat(testVoipAccount.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testVoipAccount.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testVoipAccount.getSipServer()).isEqualTo(UPDATED_SIP_SERVER);
        assertThat(testVoipAccount.getSipPort()).isEqualTo(UPDATED_SIP_PORT);
        assertThat(testVoipAccount.isLineEnable()).isEqualTo(UPDATED_LINE_ENABLE);
        assertThat(testVoipAccount.getLineNumber()).isEqualTo(UPDATED_LINE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingVoipAccount() throws Exception {
        int databaseSizeBeforeUpdate = voipAccountRepository.findAll().size();

        // Create the VoipAccount
        VoipAccountDTO voipAccountDTO = voipAccountMapper.toDto(voipAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoipAccountMockMvc.perform(put("/api/voip-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voipAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VoipAccount in the database
        List<VoipAccount> voipAccountList = voipAccountRepository.findAll();
        assertThat(voipAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVoipAccount() throws Exception {
        // Initialize the database
        voipAccountRepository.saveAndFlush(voipAccount);

        int databaseSizeBeforeDelete = voipAccountRepository.findAll().size();

        // Delete the voipAccount
        restVoipAccountMockMvc.perform(delete("/api/voip-accounts/{id}", voipAccount.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VoipAccount> voipAccountList = voipAccountRepository.findAll();
        assertThat(voipAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
