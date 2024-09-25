package com.uit.backendapi.mua_giai;

import com.uit.backendapi.bxh.BangXepHang;
import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.lich.LichThiDau;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "MuaGiai", schema = "dbo")
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public DoiBong getDoiVoDich() {
        return doiVoDich;
    }

    public void setDoiVoDich(DoiBong doiVoDich) {
        this.doiVoDich = doiVoDich;
    }

    public Set<BangXepHang> getBangXepHangs() {
        return bangXepHangs;
    }

    public void setBangXepHangs(Set<BangXepHang> bangXepHangs) {
        this.bangXepHangs = bangXepHangs;
    }

    public Set<LichThiDau> getLichThiDaus() {
        return lichThiDaus;
    }

    public void setLichThiDaus(Set<LichThiDau> lichThiDaus) {
        this.lichThiDaus = lichThiDaus;
    }

}