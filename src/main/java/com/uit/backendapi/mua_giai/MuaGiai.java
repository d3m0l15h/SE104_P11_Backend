package com.uit.backendapi.mua_giai;

import com.uit.backendapi.bxh.BangXepHang;
import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.lich.LichThiDau;
import com.uit.backendapi.mua_giai.dto.CreateMuaGiaiDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "MuaGiai", schema = "QuanLyGiaiVoDichBongDa", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_Nam", columnNames = {"Nam"})
})
public class MuaGiai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaMuaGiai", nullable = false)
    private Integer id;

    @Column(name = "Nam", nullable = false, length = 50)
    private String nam;

    @Column(name = "NgayBatDau", nullable = false)
    private LocalDate ngayBatDau;

    @Column(name = "NgayKetThuc", nullable = false)
    private LocalDate ngayKetThuc;

    @ManyToOne(fetch = FetchType.LAZY)
    @ColumnDefault("NULL")
    @JoinColumn(name = "DoiVoDich")
    private DoiBong doiVoDich;

    @OneToMany(mappedBy = "maMuaGiai")
    private Set<BangXepHang> bangXepHangs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "maMuaGiai")
    private Set<LichThiDau> lichThiDaus = new LinkedHashSet<>();

    public MuaGiai(CreateMuaGiaiDto createMuaGiaiDto) {
        this.nam = createMuaGiaiDto.getNam();
        this.ngayBatDau = createMuaGiaiDto.getNgayBatDau();
        this.ngayKetThuc = createMuaGiaiDto.getNgayKetThuc();
    }
}