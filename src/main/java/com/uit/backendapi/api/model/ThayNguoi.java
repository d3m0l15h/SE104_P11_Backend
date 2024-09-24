package com.uit.backendapi.api.model;

import com.uit.backendapi.cau_thu.CauThu;
import com.uit.backendapi.ket_qua.KetQuaThiDau;
import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "ThayNguoi", schema = "dbo")
public class ThayNguoi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaThayNguoi", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaKetQua", nullable = false)
    private KetQuaThiDau maKetQua;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaCauThuVao", nullable = false)
    private CauThu maCauThuVao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaCauThuRa", nullable = false)
    private CauThu maCauThuRa;

    @Nationalized
    @Column(name = "ThoiDiem", nullable = false, length = 100)
    private String thoiDiem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public KetQuaThiDau getMaKetQua() {
        return maKetQua;
    }

    public void setMaKetQua(KetQuaThiDau maKetQua) {
        this.maKetQua = maKetQua;
    }

    public CauThu getMaCauThuVao() {
        return maCauThuVao;
    }

    public void setMaCauThuVao(CauThu maCauThuVao) {
        this.maCauThuVao = maCauThuVao;
    }

    public CauThu getMaCauThuRa() {
        return maCauThuRa;
    }

    public void setMaCauThuRa(CauThu maCauThuRa) {
        this.maCauThuRa = maCauThuRa;
    }

    public String getThoiDiem() {
        return thoiDiem;
    }

    public void setThoiDiem(String thoiDiem) {
        this.thoiDiem = thoiDiem;
    }

}