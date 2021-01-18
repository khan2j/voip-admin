package com.voipadmin.service.impl;

import com.voipadmin.service.AsteriskAccountService;
import com.voipadmin.domain.AsteriskAccount;
import com.voipadmin.repository.AsteriskAccountRepository;
import com.voipadmin.service.dto.AsteriskAccountDTO;
import com.voipadmin.service.mapper.AsteriskAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link AsteriskAccount}.
 */
@Service
@Transactional
public class AsteriskAccountServiceImpl implements AsteriskAccountService {

    private final Logger log = LoggerFactory.getLogger(AsteriskAccountServiceImpl.class);

    private final AsteriskAccountRepository asteriskAccountRepository;

    private final AsteriskAccountMapper asteriskAccountMapper;

    public AsteriskAccountServiceImpl(AsteriskAccountRepository asteriskAccountRepository, AsteriskAccountMapper asteriskAccountMapper) {
        this.asteriskAccountRepository = asteriskAccountRepository;
        this.asteriskAccountMapper = asteriskAccountMapper;
    }

    @Override
    public AsteriskAccountDTO save(AsteriskAccountDTO asteriskAccountDTO) {
        log.debug("Request to save AsteriskAccount : {}", asteriskAccountDTO);
        AsteriskAccount asteriskAccount = asteriskAccountMapper.toEntity(asteriskAccountDTO);
        asteriskAccount = asteriskAccountRepository.save(asteriskAccount);
        return asteriskAccountMapper.toDto(asteriskAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AsteriskAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AsteriskAccounts");
        return asteriskAccountRepository.findAll(pageable)
            .map(asteriskAccountMapper::toDto);
    }



    /**
     *  Get all the asteriskAccounts where VoipAccount is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<AsteriskAccountDTO> findAllWhereVoipAccountIsNull() {
        log.debug("Request to get all asteriskAccounts where VoipAccount is null");
        return StreamSupport
            .stream(asteriskAccountRepository.findAll().spliterator(), false)
            .filter(asteriskAccount -> asteriskAccount.getVoipAccount() == null)
            .map(asteriskAccountMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AsteriskAccountDTO> findOne(Long id) {
        log.debug("Request to get AsteriskAccount : {}", id);
        return asteriskAccountRepository.findById(id)
            .map(asteriskAccountMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AsteriskAccount : {}", id);
        asteriskAccountRepository.deleteById(id);
    }
}
