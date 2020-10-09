package com.example.zotern.rest_service.application;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;



@Entity
public class Application {
    private @Id @GeneratedValue Long id;
    private String fullName;
    private String phoneNumber;
    private String typeOfTransport;
    private Integer idOfTransport;
    private Status status;

    Application() {}

    Application(String fullName,String phoneNumber, String typeOfTransport, Status status) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.typeOfTransport = typeOfTransport;
        this.status = status;
    }

    public Long getId() {
        return this.id;
    }

    public String getFullName(){

        return this.fullName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getTypeOfTransport() {
        return this.typeOfTransport;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setTypeOfTransport(String typeOfTransport) {
        this.typeOfTransport = typeOfTransport;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Application))
            return false;
        Application application = (Application) o;
        return Objects.equals(this.id, application.id) && Objects.equals(this.fullName, application.fullName)
                && Objects.equals(this.phoneNumber, application.phoneNumber) && Objects.equals(this.typeOfTransport, application.typeOfTransport)
                && this.status == application.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.fullName, this.phoneNumber, this.typeOfTransport, this.status);
    }

    @Override
    public String toString() {
        return "Application{" + "id=" + this.id + ", full name='" + this.fullName + '\'' +
                ", phone number='" + this.phoneNumber + '\'' + ", type of transport='" +
                this.typeOfTransport + '\'' + "status=" + this.status +'}';
    }

}