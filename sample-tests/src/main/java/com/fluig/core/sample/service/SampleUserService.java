package com.fluig.core.sample.service;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fluig.core.sample.domain.SampleUser;
import com.fluig.core.sample.exception.UserException;
import com.fluig.core.sample.repository.SampleUserRepository;

@Service
public class SampleUserService {

    @Autowired
    private SampleUserRepository repository;

    public Iterable<SampleUser> findAll() {
        return repository.findAll();
    }

    public SampleUser create(SampleUser user) throws UserException {
        validateUser(user);

        if (user.getId() != null) {
            throw new UserException("id must be null!!");
        }
        user.setId(UUID.randomUUID().toString());
        return repository.save(user);

    }

    public SampleUser update(SampleUser user) throws Exception {

        if (user.getId() == null || user.getId().isEmpty()) {
            throw new UserException("id can not be null!!");
        }

        checkExistingUser(user.getId());

        validateUser(user);

        return repository.save(user);

    }

    private void validateUser(SampleUser user) throws UserException {
        if (user.getAge() <= 0) {
            throw new UserException("Invalid age!!!");
        }
        if (user.getName() == null || user.getName().isEmpty() || user.getName().length() <= 3) {
            throw new UserException("Invalid user name!!!");
        }
        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new UserException("Invalid user last name!!!");
        }
    }


    public void delete(String id) throws Exception {
        if (id == null || id.isEmpty()) {
            throw new UserException("id can not be null!!");
        }
        checkExistingUser(id);

        repository.deleteById(id);

    }

    private void checkExistingUser(String id) throws Exception {
        findById(id);
    }

    public SampleUser findById(String id) {
        SampleUser user = null;
        try {
            user = repository.findById(id).get();
        } catch (Exception e) {
        }
        if (user == null) {
            throw new EntityNotFoundException("user not found with id " + id);
        }
        return user;
    }

    public List<SampleUser> findByNameContainingIgnoreCase(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    public List<SampleUser> findByLastNameContainingIgnoreCase(String name) {
        return repository.findByLastNameContainingIgnoreCase(name);
    }
}
