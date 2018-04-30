package com.fluig.core.sample.controller;


import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fluig.core.sample.domain.SampleUser;
import com.fluig.core.sample.exception.UserException;
import com.fluig.core.sample.service.SampleUserService;

@RestController
@RequestMapping("/users")
public class SampleController {

    @Autowired
    private SampleUserService service;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Iterable<SampleUser> getUsers() {
        return service.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public @ResponseBody
    SampleUser getUserById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody SampleUser user) {
        try {
            SampleUser createUser = service.create(user);
            return ResponseEntity.created(new URI("/users/" + createUser.getId())).body(createUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") String id, @RequestBody SampleUser user) {
        try {
            user.setId(id);
            SampleUser update = service.update(user);
            return ResponseEntity.ok(update);
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") String id) {
        try {
            service.delete(id);
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
