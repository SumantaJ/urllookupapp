package com.stylight.urllookupapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stylight.urllookupapp.domain.UrlMappingDetails;

@Repository
public interface UrlLookupRepository extends JpaRepository<UrlMappingDetails, Long> {

}
