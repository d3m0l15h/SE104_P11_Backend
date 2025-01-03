package com.uit.backendapi.thay_nguoi;

import com.uit.backendapi.cau_thu.CauThu;
import com.uit.backendapi.ket_qua.KetQuaThiDau;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "ThayNguoi", schema = "QuanLyGiaiVoDichBongDa")
public class ThayNguoi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaThayNguoi", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaKetQua", nullable = false)
    private KetQuaThiDau maKetQua;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaCauThuVao", nullable = false)
    private CauThu maCauThuVao;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaCauThuRa", nullable = false)
    private CauThu maCauThuRa;

    @NotNull
    @Column(name = "ThoiDiem", nullable = false)
    private Integer thoiDiem;

    public ThayNguoi(KetQuaThiDau maKetQua, CauThu maCauThuVao, CauThu maCauThuRa, Integer thoiDiem) {
        this.maKetQua = maKetQua;
        this.maCauThuVao = maCauThuVao;
        this.maCauThuRa = maCauThuRa;
        this.thoiDiem = thoiDiem;
    }
}