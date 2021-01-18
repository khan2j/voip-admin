package com.voipadmin.web.rest;

import com.voipadmin.service.OptionValueService;
import com.voipadmin.web.rest.errors.BadRequestAlertException;
import com.voipadmin.service.dto.OptionValueDTO;

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

/**
 * REST controller for managing {@link com.voipadmin.domain.OptionValue}.
 */
@RestController
@RequestMapping("/api")
public class OptionValueResource {

    private final Logger log = LoggerFactory.getLogger(OptionValueResource.class);

    private static final String ENTITY_NAME = "optionValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OptionValueService optionValueService;

    public OptionValueResource(OptionValueService optionValueService) {
        this.optionValueService = optionValueService;
    }

    /**
     * {@code POST  /option-values} : Create a new optionValue.
     *
     * @param optionValueDTO the optionValueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new optionValueDTO, or with status {@code 400 (Bad Request)} if the optionValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/option-values")
    public ResponseEntity<OptionValueDTO> createOptionValue(@RequestBody OptionValueDTO optionValueDTO) throws URISyntaxException {
        log.debug("REST request to save OptionValue : {}", optionValueDTO);
        if (optionValueDTO.getId() != null) {
            throw new BadRequestAlertException("A new optionValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OptionValueDTO result = optionValueService.save(optionValueDTO);
        return ResponseEntity.created(new URI("/api/option-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /option-values} : Updates an existing optionValue.
     *
     * @param optionValueDTO the optionValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated optionValueDTO,
     * or with status {@code 400 (Bad Request)} if the optionValueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the optionValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/option-values")
    public ResponseEntity<OptionValueDTO> updateOptionValue(@RequestBody OptionValueDTO optionValueDTO) throws URISyntaxException {
        log.debug("REST request to update OptionValue : {}", optionValueDTO);
        if (optionValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OptionValueDTO result = optionValueService.save(optionValueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, optionValueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /option-values} : get all the optionValues.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of optionValues in body.
     */
    @GetMapping("/option-values")
    public ResponseEntity<List<OptionValueDTO>> getAllOptionValues(Pageable pageable) {
        log.debug("REST request to get a page of OptionValues");
        Page<OptionValueDTO> page = optionValueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /option-values/:id} : get the "id" optionValue.
     *
     * @param id the id of the optionValueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the optionValueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/option-values/{id}")
    public ResponseEntity<OptionValueDTO> getOptionValue(@PathVariable Long id) {
        log.debug("REST request to get OptionValue : {}", id);
        Optional<OptionValueDTO> optionValueDTO = optionValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(optionValueDTO);
    }

    /**
     * {@code DELETE  /option-values/:id} : delete the "id" optionValue.
     *
     * @param id the id of the optionValueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/option-values/{id}")
    public ResponseEntity<Void> deleteOptionValue(@PathVariable Long id) {
        log.debug("REST request to delete OptionValue : {}", id);
        optionValueService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
