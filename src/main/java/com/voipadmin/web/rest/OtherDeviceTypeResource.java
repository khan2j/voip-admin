package com.voipadmin.web.rest;

import com.voipadmin.service.OtherDeviceTypeService;
import com.voipadmin.web.rest.errors.BadRequestAlertException;
import com.voipadmin.service.dto.OtherDeviceTypeDTO;

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
 * REST controller for managing {@link com.voipadmin.domain.OtherDeviceType}.
 */
@RestController
@RequestMapping("/api")
public class OtherDeviceTypeResource {

    private final Logger log = LoggerFactory.getLogger(OtherDeviceTypeResource.class);

    private static final String ENTITY_NAME = "otherDeviceType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OtherDeviceTypeService otherDeviceTypeService;

    public OtherDeviceTypeResource(OtherDeviceTypeService otherDeviceTypeService) {
        this.otherDeviceTypeService = otherDeviceTypeService;
    }

    /**
     * {@code POST  /other-device-types} : Create a new otherDeviceType.
     *
     * @param otherDeviceTypeDTO the otherDeviceTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new otherDeviceTypeDTO, or with status {@code 400 (Bad Request)} if the otherDeviceType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/other-device-types")
    public ResponseEntity<OtherDeviceTypeDTO> createOtherDeviceType(@RequestBody OtherDeviceTypeDTO otherDeviceTypeDTO) throws URISyntaxException {
        log.debug("REST request to save OtherDeviceType : {}", otherDeviceTypeDTO);
        if (otherDeviceTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new otherDeviceType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OtherDeviceTypeDTO result = otherDeviceTypeService.save(otherDeviceTypeDTO);
        return ResponseEntity.created(new URI("/api/other-device-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /other-device-types} : Updates an existing otherDeviceType.
     *
     * @param otherDeviceTypeDTO the otherDeviceTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated otherDeviceTypeDTO,
     * or with status {@code 400 (Bad Request)} if the otherDeviceTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the otherDeviceTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/other-device-types")
    public ResponseEntity<OtherDeviceTypeDTO> updateOtherDeviceType(@RequestBody OtherDeviceTypeDTO otherDeviceTypeDTO) throws URISyntaxException {
        log.debug("REST request to update OtherDeviceType : {}", otherDeviceTypeDTO);
        if (otherDeviceTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OtherDeviceTypeDTO result = otherDeviceTypeService.save(otherDeviceTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, otherDeviceTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /other-device-types} : get all the otherDeviceTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of otherDeviceTypes in body.
     */
    @GetMapping("/other-device-types")
    public ResponseEntity<List<OtherDeviceTypeDTO>> getAllOtherDeviceTypes(Pageable pageable) {
        log.debug("REST request to get a page of OtherDeviceTypes");
        Page<OtherDeviceTypeDTO> page = otherDeviceTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /other-device-types/:id} : get the "id" otherDeviceType.
     *
     * @param id the id of the otherDeviceTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the otherDeviceTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/other-device-types/{id}")
    public ResponseEntity<OtherDeviceTypeDTO> getOtherDeviceType(@PathVariable Long id) {
        log.debug("REST request to get OtherDeviceType : {}", id);
        Optional<OtherDeviceTypeDTO> otherDeviceTypeDTO = otherDeviceTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(otherDeviceTypeDTO);
    }

    /**
     * {@code DELETE  /other-device-types/:id} : delete the "id" otherDeviceType.
     *
     * @param id the id of the otherDeviceTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/other-device-types/{id}")
    public ResponseEntity<Void> deleteOtherDeviceType(@PathVariable Long id) {
        log.debug("REST request to delete OtherDeviceType : {}", id);
        otherDeviceTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
