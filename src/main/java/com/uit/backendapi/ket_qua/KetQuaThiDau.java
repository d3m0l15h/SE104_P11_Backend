package com.uit.backendapi.ket_qua;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uit.backendapi.ban_thang.BanThang;
import com.uit.backendapi.cau_thu.CauThu;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "KetQuaThiDau", schema = "dbo", uniqueConstraints = {
        @UniqueConstraint(name = "KetQuaThiDau_pk", columnNames = {"MaLichThiDau"})
})
public class KetQuaThiDau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaKetQua", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaLichThiDau", nullable = false)
    private LichThiDau maLichThiDau;

    @ColumnDefault("0")
    @Column(name = "SoBanDoiNha", nullable = false)
    private Integer soBanDoiNha;

    @ManyToOne(fetch = FetchType.LAZY)
    @ColumnDefault("NULL")
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

    public KetQuaThiDau( LichThiDau maLichThiDau, Integer soBanDoiNha, String ghiChu, Integer soBanDoiKhach) {
        this.maLichThiDau = maLichThiDau;
        this.soBanDoiNha = soBanDoiNha;
        this.ghiChu = ghiChu;
        this.soBanDoiKhach = soBanDoiKhach;
    }

}