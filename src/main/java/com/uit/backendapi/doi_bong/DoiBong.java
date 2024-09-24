package com.uit.backendapi.doi_bong;

import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "DoiBong", schema = "dbo")
public class DoiBong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDoi", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "TenDoi", nullable = false, length = 100)
    private String tenDoi;

    @Nationalized
    @Column(name = "TenSanNha", nullable = false, length = 100)
    private String tenSanNha;

    @Nationalized
    @Column(name = "DiaChiSanNha", nullable = false, length = 200)
    private String diaChiSanNha;

    @Nationalized
    @Column(name = "DienThoai", nullable = false, length = 20)
    private String dienThoai;

    @Nationalized
    @Column(name = "Email", nullable = false, length = 100)
    private String email;

    @Nationalized
    @Column(name = "ToChucQuanLy", nullable = false, length = 100)
    private String toChucQuanLy;

    @Nationalized
    @Column(name = "ThanhPhoTrucThuoc", nullable = false, length = 100)
    private String thanhPhoTrucThuoc;

    @Nationalized
    @Column(name = "AoChinhThuc", nullable = false, length = 500)
    private String aoChinhThuc;

    @Nationalized
    @Column(name = "AoDuBi", nullable = false, length = 500)
    private String aoDuBi;

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

    public String getTenSanNha() {
        return tenSanNha;
    }

    public void setTenSanNha(String tenSanNha) {
        this.tenSanNha = tenSanNha;
    }

    public String getDiaChiSanNha() {
        return diaChiSanNha;
    }

    public void setDiaChiSanNha(String diaChiSanNha) {
        this.diaChiSanNha = diaChiSanNha;
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

    public String getToChucQuanLy() {
        return toChucQuanLy;
    }

    public void setToChucQuanLy(String toChucQuanLy) {
        this.toChucQuanLy = toChucQuanLy;
    }

    public String getThanhPhoTrucThuoc() {
        return thanhPhoTrucThuoc;
    }

    public void setThanhPhoTrucThuoc(String thanhPhoTrucThuoc) {
        this.thanhPhoTrucThuoc = thanhPhoTrucThuoc;
    }

    public String getAoChinhThuc() {
        return aoChinhThuc;
    }

    public void setAoChinhThuc(String aoChinhThuc) {
        this.aoChinhThuc = aoChinhThuc;
    }

    public String getAoDuBi() {
        return aoDuBi;
    }

    public void setAoDuBi(String aoDuBi) {
        this.aoDuBi = aoDuBi;
    }

}