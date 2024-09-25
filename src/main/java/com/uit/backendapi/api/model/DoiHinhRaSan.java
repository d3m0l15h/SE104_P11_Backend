package com.uit.backendapi.api.model;

import com.uit.backendapi.cau_thu.CauThu;
import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.lich.LichThiDau;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "DoiHinhRaSan")
public class DoiHinhRaSan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDoiHinh", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaDoi", nullable = false)
    private DoiBong maDoi;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaLichThiDau", nullable = false)
    private LichThiDau maLichThiDau;

    @Column(name = "DoiHinh", nullable = false, length = 50)
    private String doiHinh;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaDoiTruong", nullable = false)
    private CauThu maDoiTruong;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaThuMon", nullable = false)
    private CauThu maThuMon;

    @OneToMany(mappedBy = "maDoiHinh")
    private Set<ChiTietDoiHinh> chiTietDoiHinhs = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DoiBong getMaDoi() {
        return maDoi;
    }

    public void setMaDoi(DoiBong maDoi) {
        this.maDoi = maDoi;
    }

    public LichThiDau getMaLichThiDau() {
        return maLichThiDau;
    }

    public void setMaLichThiDau(LichThiDau maLichThiDau) {
        this.maLichThiDau = maLichThiDau;
    }

    public String getDoiHinh() {
        return doiHinh;
    }

    public void setDoiHinh(String doiHinh) {
        this.doiHinh = doiHinh;
    }

    public CauThu getMaDoiTruong() {
        return maDoiTruong;
    }

    public void setMaDoiTruong(CauThu maDoiTruong) {
        this.maDoiTruong = maDoiTruong;
    }

    public CauThu getMaThuMon() {
        return maThuMon;
    }

    public void setMaThuMon(CauThu maThuMon) {
        this.maThuMon = maThuMon;
    }

    public Set<ChiTietDoiHinh> getChiTietDoiHinhs() {
        return chiTietDoiHinhs;
    }

    public void setChiTietDoiHinhs(Set<ChiTietDoiHinh> chiTietDoiHinhs) {
        this.chiTietDoiHinhs = chiTietDoiHinhs;
    }

}