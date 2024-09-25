package com.uit.backendapi.the_phat;

import com.uit.backendapi.ket_qua.KetQuaThiDau;
import com.uit.backendapi.cau_thu.CauThu;
import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;

@Entity
public class ThePhat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaThePhat", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaCauThu", nullable = false)
    private CauThu maCauThu;

    @Nationalized
    @Column(name = "LoaiThe", nullable = false, length = 100)
    private String loaiThe;

    @Nationalized
    @Column(name = "ThoiDiem", nullable = false, length = 100)
    private String thoiDiem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaKetQua", nullable = false)
    private KetQuaThiDau maKetQua;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CauThu getMaCauThu() {
        return maCauThu;
    }

    public void setMaCauThu(CauThu maCauThu) {
        this.maCauThu = maCauThu;
    }

    public String getLoaiThe() {
        return loaiThe;
    }

    public void setLoaiThe(String loaiThe) {
        this.loaiThe = loaiThe;
    }

    public String getThoiDiem() {
        return thoiDiem;
    }

    public void setThoiDiem(String thoiDiem) {
        this.thoiDiem = thoiDiem;
    }

    public KetQuaThiDau getMaKetQua() {
        return maKetQua;
    }

    public void setMaKetQua(KetQuaThiDau maKetQua) {
        this.maKetQua = maKetQua;
    }

}