package com.voipadmin.service;

import com.voipadmin.service.dto.AsteriskAccountDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.voipadmin.domain.AsteriskAccount}.
 */
public interface AsteriskAccountService {

    /**
     * Save a asteriskAccount.
     *
     * @param asteriskAccountDTO the entity to save.
     * @return the persisted entity.
     */
    AsteriskAccountDTO save(AsteriskAccountDTO asteriskAccountDTO);

    /**
     * Get all the asteriskAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AsteriskAccountDTO> findAll(Pageable pageable);
    /**
     * Get all the AsteriskAccountDTO where VoipAccount is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<AsteriskAccountDTO> findAllWhereVoipAccountIsNull();


    /**
     * Get the "id" asteriskAccount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AsteriskAccountDTO> findOne(Long id);

    /**
     * Delete the "id" asteriskAccount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
