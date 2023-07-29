package com.pixelthump.seshservice.repository.model;
import jakarta.persistence.*;

@Entity
public class Sesh {

    @Id
    @Column(name = "sesh_code", nullable = false)
    private String seshCode;
    @ManyToOne
    @JoinColumn(name = "sesh_type_name")
    private SeshType seshType;

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
