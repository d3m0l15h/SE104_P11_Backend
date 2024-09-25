package com.uit.backendapi.models;

import com.uit.backendapi.cau_thu.CauThu;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "ChiTietDoiHinh", schema = "dbo")
public class ChiTietDoiHinh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaChiTietDoiHinh", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaDoiHinh", nullable = false)
    private DoiHinhRaSan maDoiHinh;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaCauThu", nullable = false)
    private CauThu maCauThu;

    @Nationalized
    @Column(name = "SoAo", nullable = false, length = 10)
    private String soAo;

    @Nationalized
    @Column(name = "ViTri", nullable = false, length = 50)
    private String viTri;

    @ColumnDefault("0")
    @Column(name = "LaDuBi", nullable = false)
    private Integer laDuBi;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DoiHinhRaSan getMaDoiHinh() {
        return maDoiHinh;
    }

    public void setMaDoiHinh(DoiHinhRaSan maDoiHinh) {
        this.maDoiHinh = maDoiHinh;
    }

    public CauThu getMaCauThu() {
        return maCauThu;
    }

    public void setMaCauThu(CauThu maCauThu) {
        this.maCauThu = maCauThu;
    }

    public String getSoAo() {
        return soAo;
    }

    public void setSoAo(String soAo) {
        this.soAo = soAo;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public Integer getLaDuBi() {
        return laDuBi;
    }

    public void setLaDuBi(Integer laDuBi) {
        this.laDuBi = laDuBi;
    }

}