package com.uit.backendapi.qui_dinh;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Setter
@Getter
@Entity
@NoArgsConstructor
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

    public QuiDinh(String tenQuiDinh, String noiDung) {
        this.tenQuiDinh = tenQuiDinh;
        this.noiDung = noiDung;
    }
}