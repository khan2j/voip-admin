package com.voipadmin.service;

import com.voipadmin.service.dto.OptionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.voipadmin.domain.Option}.
 */
public interface OptionService {

    /**
     * Save a option.
     *
     * @param optionDTO the entity to save.
     * @return the persisted entity.
     */
    OptionDTO save(OptionDTO optionDTO);

    /**
     * Get all the options.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OptionDTO> findAll(Pageable pageable);

    /**
     * Get all the options with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<OptionDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" option.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OptionDTO> findOne(Long id);

    /**
     * Delete the "id" option.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<OptionDTO> findAllByVendorId(Long vendorId);

    List<OptionDTO> findAllByModelId(Long modelId);

}
