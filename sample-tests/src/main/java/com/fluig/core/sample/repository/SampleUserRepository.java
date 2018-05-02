package com.fluig.core.sample.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fluig.core.sample.domain.SampleUser;

public interface SampleUserRepository extends CrudRepository<SampleUser, String> {

    public List<SampleUser> findByNameContainingIgnoreCase(String name);

    public List<SampleUser> findByLastNameContainingIgnoreCase(String lastName);

}
