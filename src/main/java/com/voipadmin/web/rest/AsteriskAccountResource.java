package com.voipadmin.web.rest;

import com.voipadmin.service.AsteriskAccountService;
import com.voipadmin.web.rest.errors.BadRequestAlertException;
import com.voipadmin.service.dto.AsteriskAccountDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.voipadmin.domain.AsteriskAccount}.
 */
@RestController
@RequestMapping("/api")
public class AsteriskAccountResource {

    private final Logger log = LoggerFactory.getLogger(AsteriskAccountResource.class);

    private static final String ENTITY_NAME = "asteriskAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AsteriskAccountService asteriskAccountService;

    public AsteriskAccountResource(AsteriskAccountService asteriskAccountService) {
        this.asteriskAccountService = asteriskAccountService;
    }

    /**
     * {@code POST  /asterisk-accounts} : Create a new asteriskAccount.
     *
     * @param asteriskAccountDTO the asteriskAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new asteriskAccountDTO, or with status {@code 400 (Bad Request)} if the asteriskAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/asterisk-accounts")
    public ResponseEntity<AsteriskAccountDTO> createAsteriskAccount(@RequestBody AsteriskAccountDTO asteriskAccountDTO) throws URISyntaxException {
        log.debug("REST request to save AsteriskAccount : {}", asteriskAccountDTO);
        if (asteriskAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new asteriskAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AsteriskAccountDTO result = asteriskAccountService.save(asteriskAccountDTO);
        return ResponseEntity.created(new URI("/api/asterisk-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /asterisk-accounts} : Updates an existing asteriskAccount.
     *
     * @param asteriskAccountDTO the asteriskAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated asteriskAccountDTO,
     * or with status {@code 400 (Bad Request)} if the asteriskAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the asteriskAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/asterisk-accounts")
    public ResponseEntity<AsteriskAccountDTO> updateAsteriskAccount(@RequestBody AsteriskAccountDTO asteriskAccountDTO) throws URISyntaxException {
        log.debug("REST request to update AsteriskAccount : {}", asteriskAccountDTO);
        if (asteriskAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AsteriskAccountDTO result = asteriskAccountService.save(asteriskAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, asteriskAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /asterisk-accounts} : get all the asteriskAccounts.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of asteriskAccounts in body.
     */
    @GetMapping("/asterisk-accounts")
    public ResponseEntity<List<AsteriskAccountDTO>> getAllAsteriskAccounts(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("voipaccount-is-null".equals(filter)) {
            log.debug("REST request to get all AsteriskAccounts where voipAccount is null");
            return new ResponseEntity<>(asteriskAccountService.findAllWhereVoipAccountIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of AsteriskAccounts");
        Page<AsteriskAccountDTO> page = asteriskAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /asterisk-accounts/:id} : get the "id" asteriskAccount.
     *
     * @param id the id of the asteriskAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the asteriskAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/asterisk-accounts/{id}")
    public ResponseEntity<AsteriskAccountDTO> getAsteriskAccount(@PathVariable Long id) {
        log.debug("REST request to get AsteriskAccount : {}", id);
        Optional<AsteriskAccountDTO> asteriskAccountDTO = asteriskAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(asteriskAccountDTO);
    }

    /**
     * {@code DELETE  /asterisk-accounts/:id} : delete the "id" asteriskAccount.
     *
     * @param id the id of the asteriskAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/asterisk-accounts/{id}")
    public ResponseEntity<Void> deleteAsteriskAccount(@PathVariable Long id) {
        log.debug("REST request to delete AsteriskAccount : {}", id);
        asteriskAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
