package com.voipadmin.repository;

import com.voipadmin.domain.ResponsiblePerson;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ResponsiblePerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResponsiblePersonRepository extends JpaRepository<ResponsiblePerson, Long> {
}
