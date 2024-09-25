package com.uit.backendapi.api.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "QuiDinh")
public class QuiDinh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaQuiDinh", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "TenQuiDinh", nullable = false, length = 100)
    private String tenQuiDinh;

    @Nationalized
    @Column(name = "NoiDung", nullable = false, length = 1000)
    private String noiDung;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenQuiDinh() {
        return tenQuiDinh;
    }

    public void setTenQuiDinh(String tenQuiDinh) {
        this.tenQuiDinh = tenQuiDinh;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

}