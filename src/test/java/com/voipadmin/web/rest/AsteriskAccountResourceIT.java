package com.voipadmin.web.rest;

import com.voipadmin.VoipadminApp;
import com.voipadmin.domain.AsteriskAccount;
import com.voipadmin.repository.AsteriskAccountRepository;
import com.voipadmin.service.AsteriskAccountService;
import com.voipadmin.service.dto.AsteriskAccountDTO;
import com.voipadmin.service.mapper.AsteriskAccountMapper;

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
 * Integration tests for the {@link AsteriskAccountResource} REST controller.
 */
@SpringBootTest(classes = VoipadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AsteriskAccountResourceIT {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_ASTERISK_ID = "AAAAAAAAAA";
    private static final String UPDATED_ASTERISK_ID = "BBBBBBBBBB";

    @Autowired
    private AsteriskAccountRepository asteriskAccountRepository;

    @Autowired
    private AsteriskAccountMapper asteriskAccountMapper;

    @Autowired
    private AsteriskAccountService asteriskAccountService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAsteriskAccountMockMvc;

    private AsteriskAccount asteriskAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AsteriskAccount createEntity(EntityManager em) {
        AsteriskAccount asteriskAccount = new AsteriskAccount()
            .username(DEFAULT_USERNAME)
            .asteriskId(DEFAULT_ASTERISK_ID);
        return asteriskAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AsteriskAccount createUpdatedEntity(EntityManager em) {
        AsteriskAccount asteriskAccount = new AsteriskAccount()
            .username(UPDATED_USERNAME)
            .asteriskId(UPDATED_ASTERISK_ID);
        return asteriskAccount;
    }

    @BeforeEach
    public void initTest() {
        asteriskAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createAsteriskAccount() throws Exception {
        int databaseSizeBeforeCreate = asteriskAccountRepository.findAll().size();
        // Create the AsteriskAccount
        AsteriskAccountDTO asteriskAccountDTO = asteriskAccountMapper.toDto(asteriskAccount);
        restAsteriskAccountMockMvc.perform(post("/api/asterisk-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(asteriskAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the AsteriskAccount in the database
        List<AsteriskAccount> asteriskAccountList = asteriskAccountRepository.findAll();
        assertThat(asteriskAccountList).hasSize(databaseSizeBeforeCreate + 1);
        AsteriskAccount testAsteriskAccount = asteriskAccountList.get(asteriskAccountList.size() - 1);
        assertThat(testAsteriskAccount.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testAsteriskAccount.getAsteriskId()).isEqualTo(DEFAULT_ASTERISK_ID);
    }

    @Test
    @Transactional
    public void createAsteriskAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = asteriskAccountRepository.findAll().size();

        // Create the AsteriskAccount with an existing ID
        asteriskAccount.setId(1L);
        AsteriskAccountDTO asteriskAccountDTO = asteriskAccountMapper.toDto(asteriskAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAsteriskAccountMockMvc.perform(post("/api/asterisk-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(asteriskAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AsteriskAccount in the database
        List<AsteriskAccount> asteriskAccountList = asteriskAccountRepository.findAll();
        assertThat(asteriskAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAsteriskAccounts() throws Exception {
        // Initialize the database
        asteriskAccountRepository.saveAndFlush(asteriskAccount);

        // Get all the asteriskAccountList
        restAsteriskAccountMockMvc.perform(get("/api/asterisk-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asteriskAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].asteriskId").value(hasItem(DEFAULT_ASTERISK_ID)));
    }
    
    @Test
    @Transactional
    public void getAsteriskAccount() throws Exception {
        // Initialize the database
        asteriskAccountRepository.saveAndFlush(asteriskAccount);

        // Get the asteriskAccount
        restAsteriskAccountMockMvc.perform(get("/api/asterisk-accounts/{id}", asteriskAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(asteriskAccount.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.asteriskId").value(DEFAULT_ASTERISK_ID));
    }
    @Test
    @Transactional
    public void getNonExistingAsteriskAccount() throws Exception {
        // Get the asteriskAccount
        restAsteriskAccountMockMvc.perform(get("/api/asterisk-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAsteriskAccount() throws Exception {
        // Initialize the database
        asteriskAccountRepository.saveAndFlush(asteriskAccount);

        int databaseSizeBeforeUpdate = asteriskAccountRepository.findAll().size();

        // Update the asteriskAccount
        AsteriskAccount updatedAsteriskAccount = asteriskAccountRepository.findById(asteriskAccount.getId()).get();
        // Disconnect from session so that the updates on updatedAsteriskAccount are not directly saved in db
        em.detach(updatedAsteriskAccount);
        updatedAsteriskAccount
            .username(UPDATED_USERNAME)
            .asteriskId(UPDATED_ASTERISK_ID);
        AsteriskAccountDTO asteriskAccountDTO = asteriskAccountMapper.toDto(updatedAsteriskAccount);

        restAsteriskAccountMockMvc.perform(put("/api/asterisk-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(asteriskAccountDTO)))
            .andExpect(status().isOk());

        // Validate the AsteriskAccount in the database
        List<AsteriskAccount> asteriskAccountList = asteriskAccountRepository.findAll();
        assertThat(asteriskAccountList).hasSize(databaseSizeBeforeUpdate);
        AsteriskAccount testAsteriskAccount = asteriskAccountList.get(asteriskAccountList.size() - 1);
        assertThat(testAsteriskAccount.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testAsteriskAccount.getAsteriskId()).isEqualTo(UPDATED_ASTERISK_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingAsteriskAccount() throws Exception {
        int databaseSizeBeforeUpdate = asteriskAccountRepository.findAll().size();

        // Create the AsteriskAccount
        AsteriskAccountDTO asteriskAccountDTO = asteriskAccountMapper.toDto(asteriskAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAsteriskAccountMockMvc.perform(put("/api/asterisk-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(asteriskAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AsteriskAccount in the database
        List<AsteriskAccount> asteriskAccountList = asteriskAccountRepository.findAll();
        assertThat(asteriskAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAsteriskAccount() throws Exception {
        // Initialize the database
        asteriskAccountRepository.saveAndFlush(asteriskAccount);

        int databaseSizeBeforeDelete = asteriskAccountRepository.findAll().size();

        // Delete the asteriskAccount
        restAsteriskAccountMockMvc.perform(delete("/api/asterisk-accounts/{id}", asteriskAccount.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AsteriskAccount> asteriskAccountList = asteriskAccountRepository.findAll();
        assertThat(asteriskAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
