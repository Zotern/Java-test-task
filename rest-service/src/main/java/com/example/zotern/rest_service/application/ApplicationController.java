package com.example.zotern.rest_service.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ApplicationController {

    private final ApplicationRepository repository;

    private final ApplicationModelAssembler assembler;

    ApplicationController(ApplicationRepository repository, ApplicationModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/applications")
    CollectionModel<EntityModel<Application>> all() {

        List<EntityModel<Application>> applications = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(applications, linkTo(methodOn(ApplicationController.class).all()).withSelfRel());
    }

    @PostMapping("/applications")
    ResponseEntity<?> newApplication(@RequestBody Application newApplication) {

        newApplication.setStatus(Status.NEW);
        EntityModel<Application> entityModel = assembler.toModel(repository.save(newApplication));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/applications/{id}")
    EntityModel<Application> one(@PathVariable Long id) {
        Application application = repository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));

        return assembler.toModel(application);
    }

    @PutMapping("/applications/{id}")
    ResponseEntity<?> replaceEmployee(@RequestBody Application newApplication, @PathVariable Long id) {

        Application updatedApplication = repository.findById(id) //
                .map(application -> {
                    application.setId(newApplication.getId());
                    application.setFullName(newApplication.getFullName());
                    application.setPhoneNumber(newApplication.getPhoneNumber());
                    application.setTypeOfTransport(newApplication.getTypeOfTransport());
                    return repository.save(application);
                }) //
                .orElseGet(() -> {
                    newApplication.setId(id);
                    return repository.save(newApplication);
                });

        EntityModel<Application> entityModel = assembler.toModel(updatedApplication);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/applications/{id}")
    ResponseEntity<?> deleteApplication(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}