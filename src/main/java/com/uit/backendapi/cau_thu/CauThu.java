package com.uit.backendapi.cau_thu;

import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;

@Entity
@Table(name = "CauThu", schema = "dbo")
public class CauThu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCauThu", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "TenCauThu", nullable = false, length = 100)
    private String tenCauThu;

    @Column(name = "NgaySinh", nullable = false)
    private LocalDate ngaySinh;

    @Nationalized
    @Column(name = "LoaiCauThu", nullable = false, length = 50)
    private String loaiCauThu;

    @Column(name = "MaDoi", nullable = false)
    private Integer maDoi;

    @Column(name = "SoAo", nullable = false)
    private Integer soAo;

    @Nationalized
    @Column(name = "ViTri", nullable = false, length = 50)
    private String viTri;

    @Nationalized
    @Column(name = "NoiSinh", nullable = false, length = 100)
    private String noiSinh;

    @Nationalized
    @Column(name = "QuocTich", nullable = false, length = 100)
    private String quocTich;

    @Nationalized
    @Column(name = "TieuSu", length = 500)
    private String tieuSu;

    @Column(name = "ChieuCao", nullable = false)
    private Double chieuCao;

    @Column(name = "CanNang", nullable = false)
    private Double canNang;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenCauThu() {
        return tenCauThu;
    }

    public void setTenCauThu(String tenCauThu) {
        this.tenCauThu = tenCauThu;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getLoaiCauThu() {
        return loaiCauThu;
    }

    public void setLoaiCauThu(String loaiCauThu) {
        this.loaiCauThu = loaiCauThu;
    }

    public Integer getMaDoi() {
        return maDoi;
    }

    public void setMaDoi(Integer maDoi) {
        this.maDoi = maDoi;
    }

    public Integer getSoAo() {
        return soAo;
    }

    public void setSoAo(Integer soAo) {
        this.soAo = soAo;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public String getNoiSinh() {
        return noiSinh;
    }

    public void setNoiSinh(String noiSinh) {
        this.noiSinh = noiSinh;
    }

    public String getQuocTich() {
        return quocTich;
    }

    public void setQuocTich(String quocTich) {
        this.quocTich = quocTich;
    }

    public String getTieuSu() {
        return tieuSu;
    }

    public void setTieuSu(String tieuSu) {
        this.tieuSu = tieuSu;
    }

    public Double getChieuCao() {
        return chieuCao;
    }

    public void setChieuCao(Double chieuCao) {
        this.chieuCao = chieuCao;
    }

    public Double getCanNang() {
        return canNang;
    }

    public void setCanNang(Double canNang) {
        this.canNang = canNang;
    }

}