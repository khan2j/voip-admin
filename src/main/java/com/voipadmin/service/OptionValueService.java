package com.voipadmin.service;

import com.voipadmin.service.dto.OptionValueDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.voipadmin.domain.OptionValue}.
 */
public interface OptionValueService {

    /**
     * Save a optionValue.
     *
     * @param optionValueDTO the entity to save.
     * @return the persisted entity.
     */
    OptionValueDTO save(OptionValueDTO optionValueDTO);

    /**
     * Get all the optionValues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OptionValueDTO> findAll(Pageable pageable);


    /**
     * Get the "id" optionValue.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OptionValueDTO> findOne(Long id);

    /**
     * Delete the "id" optionValue.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
