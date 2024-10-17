package com.uit.backendapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Setter
@Getter
@Entity
@Table(name = "QuiDinh", schema = "QuanLyGiaiVoDichBongDa")
public class QuiDinh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaQuiDinh", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "TenQuiDinh", nullable = false, length = 100)
    private String tenQuiDinh;

    @Nationalized
    @Column(name = "NoiDung", nullable = false, length = 1000)
    private String noiDung;

}