package com.uit.backendapi.bxh;

import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.mua_giai.MuaGiai;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "BangXepHang", schema = "dbo")
public class BangXepHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaBangXepHang", nullable = false)
    private Integer id;

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

    public BangXepHang( DoiBong maDoi, Integer soTranThang, Integer soTranHoa, Integer soTranThua, Integer hieuSo, MuaGiai maMuaGiai, Integer diem, Integer soBanThang, Integer soBanThua) {
        this.maDoi = maDoi;
        this.soTranThang = soTranThang;
        this.soTranHoa = soTranHoa;
        this.soTranThua = soTranThua;
        this.hieuSo = hieuSo;
        this.maMuaGiai = maMuaGiai;
        this.diem = diem;
        this.soBanThang = soBanThang;
        this.soBanThua = soBanThua;
    }
}