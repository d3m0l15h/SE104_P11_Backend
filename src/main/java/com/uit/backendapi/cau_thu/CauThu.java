package com.uit.backendapi.cau_thu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uit.backendapi.ban_thang.BanThang;
import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.ket_qua.KetQuaThiDau;
import com.uit.backendapi.the_phat.ThePhat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaDoi", nullable = false)
    private DoiBong maDoi;

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

//    @OneToMany(mappedBy = "maCauThu")
//    private Set<BanThang> banThangs = new LinkedHashSet<>();
//
//    @OneToMany(mappedBy = "cauThuXuatSac")
//    private Set<KetQuaThiDau> ketQuaThiDaus = new LinkedHashSet<>();
//
//    @OneToMany(mappedBy = "maCauThu")
//    private Set<ThePhat> thePhats = new LinkedHashSet<>();

    public CauThu(String tenCauThu, LocalDate ngaySinh, String loaiCauThu, DoiBong maDoi, Integer soAo, String viTri, String noiSinh, String quocTich, String tieuSu, Double chieuCao, Double canNang) {
        this.tenCauThu = tenCauThu;
        this.ngaySinh = ngaySinh;
        this.loaiCauThu = loaiCauThu;
        this.maDoi = maDoi;
        this.soAo = soAo;
        this.viTri = viTri;
        this.noiSinh = noiSinh;
        this.quocTich = quocTich;
        this.tieuSu = tieuSu;
        this.chieuCao = chieuCao;
        this.canNang = canNang;
    }
}