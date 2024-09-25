package com.uit.backendapi.cau_thu;

import com.uit.backendapi.ban_thang.BanThang;
import com.uit.backendapi.ket_qua.KetQuaThiDau;
import com.uit.backendapi.models.*;
import com.uit.backendapi.thay_nguoi.ThayNguoi;
import com.uit.backendapi.the_phat.ThePhat;
import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "maCauThuVao")
    private Set<ThayNguoi> thayNguois_Vao = new LinkedHashSet<>();

    @OneToMany(mappedBy = "maCauThuRa")
    private Set<ThayNguoi> thayNguois_Ra = new LinkedHashSet<>();

    @OneToMany(mappedBy = "maCauThu")
    private Set<ThePhat> thePhats = new LinkedHashSet<>();

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

    public Set<BanThang> getBanThangs() {
        return banThangs;
    }

    public void setBanThangs(Set<BanThang> banThangs) {
        this.banThangs = banThangs;
    }

    public Set<ChiTietDoiHinh> getChiTietDoiHinhs() {
        return chiTietDoiHinhs;
    }

    public void setChiTietDoiHinhs(Set<ChiTietDoiHinh> chiTietDoiHinhs) {
        this.chiTietDoiHinhs = chiTietDoiHinhs;
    }

    public Set<DoiHinhRaSan> getDoiHinhRaSans_DoiTruong() {
        return doiHinhRaSans_DoiTruong;
    }

    public void setDoiHinhRaSans_DoiTruong(Set<DoiHinhRaSan> doiHinhRaSans_DoiTruong) {
        this.doiHinhRaSans_DoiTruong = doiHinhRaSans_DoiTruong;
    }

    public Set<DoiHinhRaSan> getDoiHinhRaSans_ThuMon() {
        return doiHinhRaSans_ThuMon;
    }

    public void setDoiHinhRaSans_ThuMon(Set<DoiHinhRaSan> doiHinhRaSans_ThuMon) {
        this.doiHinhRaSans_ThuMon = doiHinhRaSans_ThuMon;
    }

    public Set<KetQuaThiDau> getKetQuaThiDaus() {
        return ketQuaThiDaus;
    }

    public void setKetQuaThiDaus(Set<KetQuaThiDau> ketQuaThiDaus) {
        this.ketQuaThiDaus = ketQuaThiDaus;
    }

    public Set<ThayNguoi> getThayNguois_Vao() {
        return thayNguois_Vao;
    }

    public void setThayNguois_Vao(Set<ThayNguoi> thayNguois_Vao) {
        this.thayNguois_Vao = thayNguois_Vao;
    }

    public Set<ThayNguoi> getThayNguois_Ra() {
        return thayNguois_Ra;
    }

    public void setThayNguois_Ra(Set<ThayNguoi> thayNguois_Ra) {
        this.thayNguois_Ra = thayNguois_Ra;
    }

    public Set<ThePhat> getThePhats() {
        return thePhats;
    }

    public void setThePhats(Set<ThePhat> thePhats) {
        this.thePhats = thePhats;
    }

}