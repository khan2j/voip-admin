package com.voipadmin.service;

import com.voipadmin.service.dto.DeviceModelDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.voipadmin.domain.DeviceModel}.
 */
public interface DeviceModelService {

    /**
     * Save a deviceModel.
     *
     * @param deviceModelDTO the entity to save.
     * @return the persisted entity.
     */
    DeviceModelDTO save(DeviceModelDTO deviceModelDTO);

    /**
     * Get all the deviceModels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeviceModelDTO> findAll(Pageable pageable);


    /**
     * Get the "id" deviceModel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeviceModelDTO> findOne(Long id);

    /**
     * Delete the "id" deviceModel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
