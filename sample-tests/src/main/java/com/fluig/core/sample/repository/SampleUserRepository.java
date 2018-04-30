package com.fluig.core.sample.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.fluig.core.sample.domain.SampleUser;

public interface SampleUserRepository extends PagingAndSortingRepository<SampleUser, String> {


}
