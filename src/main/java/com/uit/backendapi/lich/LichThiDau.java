package com.uit.backendapi.lich;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.ket_qua.KetQuaThiDau;
import com.uit.backendapi.models.DoiHinhRaSan;
import com.uit.backendapi.mua_giai.MuaGiai;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    public LichThiDau( String vongThiDau, LocalDate ngayThiDau, LocalTime gioThiDau, String sanThiDau, DoiBong maDoiNha, DoiBong maDoiKhach, MuaGiai maMuaGiai) {
        this.vongThiDau = vongThiDau;
        this.ngayThiDau = ngayThiDau;
        this.gioThiDau = gioThiDau;
        this.sanThiDau = sanThiDau;
        this.maDoiNha = maDoiNha;
        this.maDoiKhach = maDoiKhach;
        this.maMuaGiai = maMuaGiai;
    }
}