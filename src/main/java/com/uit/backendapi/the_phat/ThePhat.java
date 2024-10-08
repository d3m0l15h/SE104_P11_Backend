package com.uit.backendapi.the_phat;

import com.uit.backendapi.cau_thu.CauThu;
import com.uit.backendapi.ket_qua.KetQuaThiDau;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "ThePhat", schema = "dbo")
public class ThePhat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaThePhat", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaCauThu", nullable = false)
    private CauThu maCauThu;

    @Nationalized
    @Column(name = "LoaiThe", nullable = false, length = 100)
    private String loaiThe;

    @Column(name = "ThoiDiem", nullable = false)
    private Integer thoiDiem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaKetQua", nullable = false)
    private KetQuaThiDau maKetQua;

    public ThePhat(CauThu maCauThu, String loaiThe, Integer thoiDiem, KetQuaThiDau maKetQua) {
        this.maCauThu = maCauThu;
        this.loaiThe = loaiThe;
        this.thoiDiem = thoiDiem;
        this.maKetQua = maKetQua;
    }
}