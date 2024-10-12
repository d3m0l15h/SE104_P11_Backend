package com.uit.backendapi.models;

import com.uit.backendapi.cau_thu.CauThu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

@Setter
@Getter
@Entity
@Table(name = "ChiTietDoiHinh", schema = "dbo")
public class ChiTietDoiHinh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaChiTietDoiHinh", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaDoiHinh", nullable = false)
    private DoiHinhRaSan maDoiHinh;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaCauThu", nullable = false)
    private CauThu maCauThu;

    @Nationalized
    @Column(name = "SoAo", nullable = false, length = 10)
    private String soAo;

    @Nationalized
    @Column(name = "ViTri", nullable = false, length = 50)
    private String viTri;

    @ColumnDefault("0")
    @Column(name = "LaDuBi", nullable = false)
    private Integer laDuBi;

}