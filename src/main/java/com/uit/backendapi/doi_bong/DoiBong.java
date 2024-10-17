package com.uit.backendapi.doi_bong;

import com.uit.backendapi.bxh.BangXepHang;
import com.uit.backendapi.cau_thu.CauThu;
import com.uit.backendapi.models.DoiHinhRaSan;
import com.uit.backendapi.lich.LichThiDau;
import com.uit.backendapi.mua_giai.MuaGiai;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "DoiBong", schema = "QuanLyGiaiVoDichBongDa")
public class DoiBong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDoi", nullable = false)
    private Integer id;

    @Column(name = "Logo", nullable = false, length = 500)
    private String logo;

    @Column(name = "AoChinhThuc", nullable = false, length = 500)
    private String aoChinhThuc;

    @Column(name = "AoDuBi", nullable = false, length = 500)
    private String aoDuBi;

    @Column(name = "DiaChiSanNha", nullable = false, length = 200)
    private String diaChiSanNha;

    @Column(name = "DienThoai", nullable = false, length = 20)
    private String dienThoai;

    @Column(name = "Email", nullable = false, length = 100)
    private String email;

    @Column(name = "TenDoi", nullable = false, length = 100)
    private String tenDoi;

    @Column(name = "TenSanNha", nullable = false, length = 100)
    private String tenSanNha;

    @Column(name = "ThanhPhoTrucThuoc", nullable = false, length = 100)
    private String thanhPhoTrucThuoc;

    @Column(name = "ToChucQuanLy", nullable = false, length = 100)
    private String toChucQuanLy;

    @OneToMany(mappedBy = "maDoi")
    private Set<BangXepHang> bangXepHangs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "maDoi")
    private Set<CauThu> cauThus = new LinkedHashSet<>();

    @OneToMany(mappedBy = "maDoi")
    private Set<DoiHinhRaSan> doiHinhRaSans = new LinkedHashSet<>();

    @OneToMany(mappedBy = "maDoiKhach")
    private Set<LichThiDau> lichThiDaus_DoiKhach = new LinkedHashSet<>();

    @OneToMany(mappedBy = "maDoiNha")
    private Set<LichThiDau> lichThiDaus_DoiNha = new LinkedHashSet<>();

    @OneToMany(mappedBy = "doiVoDich")
    private Set<MuaGiai> muaGiais = new LinkedHashSet<>();

    public DoiBong(String tenDoi, String tenSanNha, String diaChiSanNha, String dienThoai, String email, String toChucQuanLy, String thanhPhoTrucThuoc, String aoChinhThuc, String aoDuBi) {
        this.tenDoi = tenDoi;
        this.tenSanNha = tenSanNha;
        this.diaChiSanNha = diaChiSanNha;
        this.dienThoai = dienThoai;
        this.email = email;
        this.toChucQuanLy = toChucQuanLy;
        this.thanhPhoTrucThuoc = thanhPhoTrucThuoc;
        this.aoChinhThuc = aoChinhThuc;
        this.aoDuBi = aoDuBi;
    }
}