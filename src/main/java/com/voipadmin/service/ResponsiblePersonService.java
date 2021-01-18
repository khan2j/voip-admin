package com.voipadmin.service;

import com.voipadmin.service.dto.ResponsiblePersonDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.voipadmin.domain.ResponsiblePerson}.
 */
public interface ResponsiblePersonService {

    /**
     * Save a responsiblePerson.
     *
     * @param responsiblePersonDTO the entity to save.
     * @return the persisted entity.
     */
    ResponsiblePersonDTO save(ResponsiblePersonDTO responsiblePersonDTO);

    /**
     * Get all the responsiblePeople.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResponsiblePersonDTO> findAll(Pageable pageable);


    /**
     * Get the "id" responsiblePerson.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResponsiblePersonDTO> findOne(Long id);

    /**
     * Delete the "id" responsiblePerson.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
