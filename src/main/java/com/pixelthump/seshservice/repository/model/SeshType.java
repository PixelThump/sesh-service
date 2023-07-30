package com.pixelthump.seshservice.repository.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SeshType {

    @Id
    @Column(name = "name", nullable = false)
    private String name;

    public SeshType(String name) {

        this.name = name;
    }

    public SeshType() {

    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }
}
