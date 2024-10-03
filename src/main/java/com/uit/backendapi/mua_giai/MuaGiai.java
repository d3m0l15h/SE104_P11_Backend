package com.uit.backendapi.mua_giai;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uit.backendapi.bxh.BangXepHang;
import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.lich.LichThiDau;
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
@Table(name = "MuaGiai", schema = "dbo", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_Nam", columnNames = {"Nam"})
})
public class MuaGiai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaMuaGiai", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "Nam", nullable = false, length = 50)
    private String nam;

    @ManyToOne(fetch = FetchType.LAZY)
    @ColumnDefault("NULL")
    @JoinColumn(name = "DoiVoDich")
    private DoiBong doiVoDich;

    @OneToMany(mappedBy = "maMuaGiai")
    private Set<BangXepHang> bangXepHangs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "maMuaGiai")
    private Set<LichThiDau> lichThiDaus = new LinkedHashSet<>();

    public MuaGiai(String nam) {
        this.nam = nam;
    }
}