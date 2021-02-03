package com.voipadmin.web.rest;

import com.voipadmin.service.OptionService;
import com.voipadmin.web.rest.errors.BadRequestAlertException;
import com.voipadmin.service.dto.OptionDTO;

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
 * REST controller for managing {@link com.voipadmin.domain.Option}.
 */
@RestController
@RequestMapping("/api")
public class OptionResource {

    private final Logger log = LoggerFactory.getLogger(OptionResource.class);

    private static final String ENTITY_NAME = "option";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OptionService optionService;

    public OptionResource(OptionService optionService) {
        this.optionService = optionService;
    }

    /**
     * {@code POST  /options} : Create a new option.
     *
     * @param optionDTO the optionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new optionDTO, or with status {@code 400 (Bad Request)} if the option has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/options")
    public ResponseEntity<OptionDTO> createOption(@RequestBody OptionDTO optionDTO) throws URISyntaxException {
        log.debug("REST request to save Option : {}", optionDTO);
        if (optionDTO.getId() != null) {
            throw new BadRequestAlertException("A new option cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OptionDTO result = optionService.save(optionDTO);
        return ResponseEntity.created(new URI("/api/options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /options} : Updates an existing option.
     *
     * @param optionDTO the optionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated optionDTO,
     * or with status {@code 400 (Bad Request)} if the optionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the optionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/options")
    public ResponseEntity<OptionDTO> updateOption(@RequestBody OptionDTO optionDTO) throws URISyntaxException {
        log.debug("REST request to update Option : {}", optionDTO);
        if (optionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OptionDTO result = optionService.save(optionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, optionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /options} : get all the options.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of options in body.
     */
    @GetMapping("/options")
    public ResponseEntity<List<OptionDTO>> getAllOptions(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Options");
        Page<OptionDTO> page;
        if (eagerload) {
            page = optionService.findAllWithEagerRelationships(pageable);
        } else {
            page = optionService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /options/:id} : get the "id" option.
     *
     * @param id the id of the optionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the optionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/options/{id}")
    public ResponseEntity<OptionDTO> getOption(@PathVariable Long id) {
        log.debug("REST request to get Option : {}", id);
        Optional<OptionDTO> optionDTO = optionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(optionDTO);
    }

    /**
     * {@code DELETE  /options/:id} : delete the "id" option.
     *
     * @param id the id of the optionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/options/{id}")
    public ResponseEntity<Void> deleteOption(@PathVariable Long id) {
        log.debug("REST request to delete Option : {}", id);
        optionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/options/byVendor/{vendorId}")
    public ResponseEntity<List<OptionDTO>> getAllOptionsByVendor(@PathVariable Long vendorId) {
        log.debug("REST request to get all Options by vendor");
        List<OptionDTO> optionsOfVendor = optionService.findAllByVendorId(vendorId);
        return ResponseEntity.ok().body(optionsOfVendor);
    }
}
