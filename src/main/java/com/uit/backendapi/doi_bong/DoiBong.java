package com.uit.backendapi.doi_bong;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uit.backendapi.bxh.BangXepHang;
import com.uit.backendapi.cau_thu.CauThu;
import com.uit.backendapi.models.DoiHinhRaSan;
import com.uit.backendapi.lich.LichThiDau;
import com.uit.backendapi.mua_giai.MuaGiai;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @OneToMany(mappedBy = "maDoi")
    private Set<CauThu> cauThus = new LinkedHashSet<>();

//    @OneToMany(mappedBy = "maDoi")
//    private Set<BangXepHang> bangXepHangs = new LinkedHashSet<>();

//    @OneToMany(mappedBy = "maDoi")
//    private Set<DoiHinhRaSan> doiHinhRaSans = new LinkedHashSet<>();

    @OneToMany(mappedBy = "doiVoDich")
    private Set<MuaGiai> muaGiaiVoDichs = new LinkedHashSet<>();

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