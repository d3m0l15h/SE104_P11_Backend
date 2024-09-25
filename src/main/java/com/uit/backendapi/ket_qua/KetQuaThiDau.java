package com.uit.backendapi.ket_qua;

import com.uit.backendapi.ban_thang.BanThang;
import com.uit.backendapi.cau_thu.CauThu;
import com.uit.backendapi.lich.LichThiDau;
import com.uit.backendapi.thay_nguoi.ThayNguoi;
import com.uit.backendapi.the_phat.ThePhat;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class KetQuaThiDau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaKetQua", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaLichThiDau", nullable = false)
    private LichThiDau maLichThiDau;

    @ColumnDefault("0")
    @Column(name = "SoBanDoiNha", nullable = false)
    private Integer soBanDoiNha;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CauThuXuatSac", nullable = false)
    private CauThu cauThuXuatSac;

    @Nationalized
    @Column(name = "GhiChu", length = 500)
    private String ghiChu;

    @ColumnDefault("0")
    @Column(name = "SoBanDoiKhach", nullable = false)
    private Integer soBanDoiKhach;

    @OneToMany(mappedBy = "maKetQua")
    private Set<BanThang> banThangs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "maKetQua")
    private Set<ThayNguoi> thayNguois = new LinkedHashSet<>();

    @OneToMany(mappedBy = "maKetQua")
    private Set<ThePhat> thePhats = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LichThiDau getMaLichThiDau() {
        return maLichThiDau;
    }

    public void setMaLichThiDau(LichThiDau maLichThiDau) {
        this.maLichThiDau = maLichThiDau;
    }

    public Integer getSoBanDoiNha() {
        return soBanDoiNha;
    }

    public void setSoBanDoiNha(Integer soBanDoiNha) {
        this.soBanDoiNha = soBanDoiNha;
    }

    public CauThu getCauThuXuatSac() {
        return cauThuXuatSac;
    }

    public void setCauThuXuatSac(CauThu cauThuXuatSac) {
        this.cauThuXuatSac = cauThuXuatSac;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Integer getSoBanDoiKhach() {
        return soBanDoiKhach;
    }

    public void setSoBanDoiKhach(Integer soBanDoiKhach) {
        this.soBanDoiKhach = soBanDoiKhach;
    }

    public Set<BanThang> getBanThangs() {
        return banThangs;
    }

    public void setBanThangs(Set<BanThang> banThangs) {
        this.banThangs = banThangs;
    }

    public Set<ThayNguoi> getThayNguois() {
        return thayNguois;
    }

    public void setThayNguois(Set<ThayNguoi> thayNguois) {
        this.thayNguois = thayNguois;
    }

    public Set<ThePhat> getThePhats() {
        return thePhats;
    }

    public void setThePhats(Set<ThePhat> thePhats) {
        this.thePhats = thePhats;
    }

}