package com.uit.backendapi.ket_qua;

import com.uit.backendapi.ban_thang.BanThang;
import com.uit.backendapi.cau_thu.CauThu;
import com.uit.backendapi.ket_qua.dto.CreateKetQuaThiDauDto;
import com.uit.backendapi.lich.LichThiDau;
import com.uit.backendapi.thay_nguoi.ThayNguoi;
import com.uit.backendapi.the_phat.ThePhat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "KetQuaThiDau", schema = "QuanLyGiaiVoDichBongDa")
public class KetQuaThiDau {
    @Id
    @Column(name = "MaKetQua", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaKetQua", nullable = false)
    private LichThiDau lichThiDau;

    @ColumnDefault("0")
    @Column(name = "SoBanDoiNha", nullable = false)
    private Integer soBanDoiNha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CauThuXuatSac")
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

    public KetQuaThiDau(CreateKetQuaThiDauDto createKetQuaThiDauDto, LichThiDau lichThiDau) {
        this.lichThiDau = lichThiDau;
        this.soBanDoiNha = 0;
        this.ghiChu = createKetQuaThiDauDto.getGhiChu();
        this.soBanDoiKhach = 0;
    }

    public KetQuaThiDau(LichThiDau lichThiDau, Integer soBanDoiNha, CauThu cauThuXuatSac, String ghiChu, Integer soBanDoiKhach) {
        this.lichThiDau = lichThiDau;
        this.soBanDoiNha = 0;
        this.cauThuXuatSac = cauThuXuatSac;
        this.ghiChu = ghiChu;
        this.soBanDoiKhach = 0;
    }

}