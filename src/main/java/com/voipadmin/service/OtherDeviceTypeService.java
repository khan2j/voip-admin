package com.voipadmin.service;

import com.voipadmin.service.dto.OtherDeviceTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.voipadmin.domain.OtherDeviceType}.
 */
public interface OtherDeviceTypeService {

    /**
     * Save a otherDeviceType.
     *
     * @param otherDeviceTypeDTO the entity to save.
     * @return the persisted entity.
     */
    OtherDeviceTypeDTO save(OtherDeviceTypeDTO otherDeviceTypeDTO);

    /**
     * Get all the otherDeviceTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OtherDeviceTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" otherDeviceType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OtherDeviceTypeDTO> findOne(Long id);

    /**
     * Delete the "id" otherDeviceType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
