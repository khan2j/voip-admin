package com.voipadmin.service.impl;

import com.voipadmin.service.ResponsiblePersonService;
import com.voipadmin.domain.ResponsiblePerson;
import com.voipadmin.repository.ResponsiblePersonRepository;
import com.voipadmin.service.dto.ResponsiblePersonDTO;
import com.voipadmin.service.mapper.ResponsiblePersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ResponsiblePerson}.
 */
@Service
@Transactional
public class ResponsiblePersonServiceImpl implements ResponsiblePersonService {

    private final Logger log = LoggerFactory.getLogger(ResponsiblePersonServiceImpl.class);

    private final ResponsiblePersonRepository responsiblePersonRepository;

    private final ResponsiblePersonMapper responsiblePersonMapper;

    public ResponsiblePersonServiceImpl(ResponsiblePersonRepository responsiblePersonRepository, ResponsiblePersonMapper responsiblePersonMapper) {
        this.responsiblePersonRepository = responsiblePersonRepository;
        this.responsiblePersonMapper = responsiblePersonMapper;
    }

    @Override
    public ResponsiblePersonDTO save(ResponsiblePersonDTO responsiblePersonDTO) {
        log.debug("Request to save ResponsiblePerson : {}", responsiblePersonDTO);
        ResponsiblePerson responsiblePerson = responsiblePersonMapper.toEntity(responsiblePersonDTO);
        responsiblePerson = responsiblePersonRepository.save(responsiblePerson);
        return responsiblePersonMapper.toDto(responsiblePerson);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsiblePersonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResponsiblePeople");
        return responsiblePersonRepository.findAll(pageable)
            .map(responsiblePersonMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ResponsiblePersonDTO> findOne(Long id) {
        log.debug("Request to get ResponsiblePerson : {}", id);
        return responsiblePersonRepository.findById(id)
            .map(responsiblePersonMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResponsiblePerson : {}", id);
        responsiblePersonRepository.deleteById(id);
    }
}
