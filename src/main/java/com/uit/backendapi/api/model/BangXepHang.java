package com.uit.backendapi.api.model;

import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.mua_giai.MuaGiai;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Table(name = "BangXepHang", schema = "dbo")
public class BangXepHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaBangXepHang", nullable = false)
    private Integer id;

    @Column(name = "NgayXepHang", nullable = false)
    private LocalDate ngayXepHang;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaDoi", nullable = false)
    private DoiBong maDoi;

    @Column(name = "SoTranThang", nullable = false)
    private Integer soTranThang;

    @Column(name = "SoTranHoa", nullable = false)
    private Integer soTranHoa;

    @Column(name = "SoTranThua", nullable = false)
    private Integer soTranThua;

    @ColumnDefault("0")
    @Column(name = "HieuSo", nullable = false)
    private Integer hieuSo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaMuaGiai", nullable = false)
    private MuaGiai maMuaGiai;

    @ColumnDefault("0")
    @Column(name = "Diem")
    private Integer diem;

    @ColumnDefault("0")
    @Column(name = "SoBanThang")
    private Integer soBanThang;

    @ColumnDefault("0")
    @Column(name = "SoBanThua")
    private Integer soBanThua;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getNgayXepHang() {
        return ngayXepHang;
    }

    public void setNgayXepHang(LocalDate ngayXepHang) {
        this.ngayXepHang = ngayXepHang;
    }

    public DoiBong getMaDoi() {
        return maDoi;
    }

    public void setMaDoi(DoiBong maDoi) {
        this.maDoi = maDoi;
    }

    public Integer getSoTranThang() {
        return soTranThang;
    }

    public void setSoTranThang(Integer soTranThang) {
        this.soTranThang = soTranThang;
    }

    public Integer getSoTranHoa() {
        return soTranHoa;
    }

    public void setSoTranHoa(Integer soTranHoa) {
        this.soTranHoa = soTranHoa;
    }

    public Integer getSoTranThua() {
        return soTranThua;
    }

    public void setSoTranThua(Integer soTranThua) {
        this.soTranThua = soTranThua;
    }

    public Integer getHieuSo() {
        return hieuSo;
    }

    public void setHieuSo(Integer hieuSo) {
        this.hieuSo = hieuSo;
    }

    public MuaGiai getMaMuaGiai() {
        return maMuaGiai;
    }

    public void setMaMuaGiai(MuaGiai maMuaGiai) {
        this.maMuaGiai = maMuaGiai;
    }

    public Integer getDiem() {
        return diem;
    }

    public void setDiem(Integer diem) {
        this.diem = diem;
    }

    public Integer getSoBanThang() {
        return soBanThang;
    }

    public void setSoBanThang(Integer soBanThang) {
        this.soBanThang = soBanThang;
    }

    public Integer getSoBanThua() {
        return soBanThua;
    }

    public void setSoBanThua(Integer soBanThua) {
        this.soBanThua = soBanThua;
    }

}