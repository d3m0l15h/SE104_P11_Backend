package com.uit.backendapi.ban_thang;

import com.uit.backendapi.cau_thu.CauThu;
import com.uit.backendapi.ket_qua.KetQuaThiDau;
import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;

import java.time.LocalTime;

@Entity
@Table(name = "BanThang", schema = "dbo")
public class BanThang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaBanThang", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaKetQua", nullable = false)
    private KetQuaThiDau maKetQua;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaCauThu", nullable = false)
    private CauThu maCauThu;

    @Nationalized
    @Column(name = "LoaiBanThang", nullable = false, length = 50)
    private String loaiBanThang;

    @Column(name = "ThoiDiem", nullable = false)
    private LocalTime thoiDiem;

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

    public CauThu getMaCauThu() {
        return maCauThu;
    }

    public void setMaCauThu(CauThu maCauThu) {
        this.maCauThu = maCauThu;
    }

    public String getLoaiBanThang() {
        return loaiBanThang;
    }

    public void setLoaiBanThang(String loaiBanThang) {
        this.loaiBanThang = loaiBanThang;
    }

    public LocalTime getThoiDiem() {
        return thoiDiem;
    }

    public void setThoiDiem(LocalTime thoiDiem) {
        this.thoiDiem = thoiDiem;
    }

}