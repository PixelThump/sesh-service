package com.pixelthump.seshservice.repository.model;
import jakarta.persistence.*;

@Entity
public class Sesh {

    @ManyToOne
    @JoinColumn(name = "sesh_type_name")
    private SeshType seshType;
    @Id
    @Column(name = "sesh_code", nullable = false)
    private String seshCode;

    public Sesh() {

    }

    public Sesh(SeshType seshType, String seshCode) {

        this.seshType = seshType;
        this.seshCode = seshCode;
    }

    public SeshType getSeshType() {

        return seshType;
    }

    public void setSeshType(SeshType seshType) {

        this.seshType = seshType;
    }

    public String getSeshCode() {

        return seshCode;
    }

    public void setSeshCode(String seshCode) {

        this.seshCode = seshCode;
    }

}
