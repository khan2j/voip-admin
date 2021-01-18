package com.voipadmin.service;

import com.voipadmin.service.dto.VoipAccountDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.voipadmin.domain.VoipAccount}.
 */
public interface VoipAccountService {

    /**
     * Save a voipAccount.
     *
     * @param voipAccountDTO the entity to save.
     * @return the persisted entity.
     */
    VoipAccountDTO save(VoipAccountDTO voipAccountDTO);

    /**
     * Get all the voipAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VoipAccountDTO> findAll(Pageable pageable);


    /**
     * Get the "id" voipAccount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VoipAccountDTO> findOne(Long id);

    /**
     * Delete the "id" voipAccount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
