package com.uit.backendapi.models;

import com.uit.backendapi.cau_thu.CauThu;
import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.lich.LichThiDau;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "DoiHinhRaSan", schema = "dbo")
public class DoiHinhRaSan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDoiHinh", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaDoi", nullable = false)
    private DoiBong maDoi;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaLichThiDau", nullable = false)
    private LichThiDau maLichThiDau;

    @Column(name = "DoiHinh", nullable = false, length = 50)
    private String doiHinh;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaDoiTruong", nullable = false)
    private CauThu maDoiTruong;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaThuMon", nullable = false)
    private CauThu maThuMon;

    @OneToMany(mappedBy = "maDoiHinh")
    private Set<ChiTietDoiHinh> chiTietDoiHinhs = new LinkedHashSet<>();

}