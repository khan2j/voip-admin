package com.voipadmin.repository;

import com.voipadmin.domain.Option;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Spring Data  repository for the Option entity.
 */
@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

    @Query(value = "select distinct option from Option option left join fetch option.models",
        countQuery = "select count(distinct option) from Option option")
    Page<Option> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct option from Option option left join fetch option.models")
    List<Option> findAllWithEagerRelationships();

    @Query("select option from Option option left join fetch option.models where option.id =:id")
    Optional<Option> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("SELECT DISTINCT option FROM Option option JOIN fetch option.vendors vendors WHERE vendors.id = :vendorId")
    List<Option> findAllByVendor(@Param("vendorId") Long vendorId);
}
