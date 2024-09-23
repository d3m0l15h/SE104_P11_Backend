package com.uit.backendapi.api.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;

@Entity
public class DoiBong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDoi", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "TenDoi", nullable = false, length = 100)
    private String tenDoi;

    @Nationalized
    @Column(name = "SanNha", nullable = false, length = 100)
    private String sanNha;

    @Nationalized
    @Column(name = "DiaChi", nullable = false, length = 200)
    private String diaChi;

    @Nationalized
    @Column(name = "DienThoai", nullable = false, length = 20)
    private String dienThoai;

    @Nationalized
    @Column(name = "Email", nullable = false, length = 100)
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenDoi() {
        return tenDoi;
    }

    public void setTenDoi(String tenDoi) {
        this.tenDoi = tenDoi;
    }

    public String getSanNha() {
        return sanNha;
    }

    public void setSanNha(String sanNha) {
        this.sanNha = sanNha;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}