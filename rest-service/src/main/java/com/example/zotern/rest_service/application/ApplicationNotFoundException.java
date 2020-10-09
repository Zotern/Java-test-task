package com.example.zotern.rest_service.application;

class ApplicationNotFoundException extends RuntimeException {

    ApplicationNotFoundException(Long id) {
        super("Could not find application " + id);
    }
}
