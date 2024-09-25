package com.uit.backendapi.lich;

import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.ket_qua.KetQuaThiDau;
import com.uit.backendapi.models.DoiHinhRaSan;
import com.uit.backendapi.mua_giai.MuaGiai;
import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "LichThiDau", schema = "dbo")
public class LichThiDau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaLichThiDau", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "VongThiDau", nullable = false, length = 50)
    private String vongThiDau;

    @Column(name = "NgayThiDau", nullable = false)
    private LocalDate ngayThiDau;

    @Column(name = "GioThiDau", nullable = false)
    private LocalTime gioThiDau;

    @Nationalized
    @Column(name = "SanThiDau", nullable = false, length = 100)
    private String sanThiDau;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaDoiNha", nullable = false)
    private DoiBong maDoiNha;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaDoiKhach", nullable = false)
    private DoiBong maDoiKhach;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaMuaGiai", nullable = false)
    private MuaGiai maMuaGiai;

    @OneToMany(mappedBy = "maLichThiDau")
    private Set<DoiHinhRaSan> doiHinhRaSans = new LinkedHashSet<>();

    @OneToMany(mappedBy = "maLichThiDau")
    private Set<KetQuaThiDau> ketQuaThiDaus = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVongThiDau() {
        return vongThiDau;
    }

    public void setVongThiDau(String vongThiDau) {
        this.vongThiDau = vongThiDau;
    }

    public LocalDate getNgayThiDau() {
        return ngayThiDau;
    }

    public void setNgayThiDau(LocalDate ngayThiDau) {
        this.ngayThiDau = ngayThiDau;
    }

    public LocalTime getGioThiDau() {
        return gioThiDau;
    }

    public void setGioThiDau(LocalTime gioThiDau) {
        this.gioThiDau = gioThiDau;
    }

    public String getSanThiDau() {
        return sanThiDau;
    }

    public void setSanThiDau(String sanThiDau) {
        this.sanThiDau = sanThiDau;
    }

    public DoiBong getMaDoiNha() {
        return maDoiNha;
    }

    public void setMaDoiNha(DoiBong maDoiNha) {
        this.maDoiNha = maDoiNha;
    }

    public DoiBong getMaDoiKhach() {
        return maDoiKhach;
    }

    public void setMaDoiKhach(DoiBong maDoiKhach) {
        this.maDoiKhach = maDoiKhach;
    }

    public MuaGiai getMaMuaGiai() {
        return maMuaGiai;
    }

    public void setMaMuaGiai(MuaGiai maMuaGiai) {
        this.maMuaGiai = maMuaGiai;
    }

    public Set<DoiHinhRaSan> getDoiHinhRaSans() {
        return doiHinhRaSans;
    }

    public void setDoiHinhRaSans(Set<DoiHinhRaSan> doiHinhRaSans) {
        this.doiHinhRaSans = doiHinhRaSans;
    }

    public Set<KetQuaThiDau> getKetQuaThiDaus() {
        return ketQuaThiDaus;
    }

    public void setKetQuaThiDaus(Set<KetQuaThiDau> ketQuaThiDaus) {
        this.ketQuaThiDaus = ketQuaThiDaus;
    }

}