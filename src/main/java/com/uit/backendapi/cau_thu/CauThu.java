package com.uit.backendapi.cau_thu;

import com.uit.backendapi.ban_thang.BanThang;
import com.uit.backendapi.cau_thu.dto.CreateCauThuDto;
import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.ket_qua.KetQuaThiDau;
import com.uit.backendapi.models.ChiTietDoiHinh;
import com.uit.backendapi.models.DoiHinhRaSan;
import com.uit.backendapi.thay_nguoi.ThayNguoi;
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
@Table(name = "CauThu", schema = "QuanLyGiaiVoDichBongDa")
public class CauThu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCauThu", nullable = false)
    private Integer id;

    @Column(name = "CanNang", nullable = false)
    private Double canNang;

    @Column(name = "ChieuCao", nullable = false)
    private Double chieuCao;

    @Column(name = "LoaiCauThu", nullable = false, length = 50)
    private String loaiCauThu;

    @Column(name = "NgaySinh", nullable = false)
    private LocalDate ngaySinh;

    @Column(name = "NoiSinh", nullable = false, length = 100)
    private String noiSinh;

    @Column(name = "QuocTich", nullable = false, length = 100)
    private String quocTich;

    @Column(name = "SoAo", nullable = false)
    private Integer soAo;

    @Column(name = "TenCauThu", nullable = false, length = 100)
    private String tenCauThu;

    @Column(name = "TieuSu", length = 500)
    private String tieuSu;

    @Column(name = "ViTri", nullable = false, length = 50)
    private String viTri;

    @Column(name = "Avatar", length = 500)
    private String avatar;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaDoi", nullable = false)
    private DoiBong maDoi;

    @OneToMany(mappedBy = "maCauThu")
    private Set<BanThang> banThangs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "maCauThu")
    private Set<ChiTietDoiHinh> chiTietDoiHinhs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "maDoiTruong")
    private Set<DoiHinhRaSan> doiHinhRaSans_DoiTruong = new LinkedHashSet<>();

    @OneToMany(mappedBy = "maThuMon")
    private Set<DoiHinhRaSan> doiHinhRaSans_ThuMon = new LinkedHashSet<>();

    @OneToMany(mappedBy = "cauThuXuatSac")
    private Set<KetQuaThiDau> ketQuaThiDaus = new LinkedHashSet<>();

    @OneToMany(mappedBy = "maCauThuRa")
    private Set<ThayNguoi> thayNguois_Ra = new LinkedHashSet<>();

    @OneToMany(mappedBy = "maCauThuVao")
    private Set<ThayNguoi> thayNguois_Vao = new LinkedHashSet<>();

    @OneToMany(mappedBy = "maCauThu")
    private Set<ThePhat> thePhats = new LinkedHashSet<>();

    public CauThu(CreateCauThuDto createCauThuDto) {
        this.tenCauThu = createCauThuDto.getTenCauThu();
        this.ngaySinh = createCauThuDto.getNgaySinh();
        this.loaiCauThu = createCauThuDto.getLoaiCauThu();
        this.soAo = createCauThuDto.getSoAo();
        this.viTri = createCauThuDto.getViTri();
        this.noiSinh = createCauThuDto.getNoiSinh();
        this.quocTich = createCauThuDto.getQuocTich();
        this.tieuSu = createCauThuDto.getTieuSu();
        this.chieuCao = createCauThuDto.getChieuCao();
        this.canNang = createCauThuDto.getCanNang();
    }
}