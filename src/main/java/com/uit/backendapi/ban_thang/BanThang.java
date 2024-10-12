package com.uit.backendapi.ban_thang;

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
@Table(name = "BanThang", schema = "dbo")
public class BanThang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaBanThang", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaKetQua", nullable = false)
    private KetQuaThiDau maKetQua;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MaCauThu", nullable = false)
    private CauThu maCauThu;

    @Nationalized
    @Column(name = "LoaiBanThang", nullable = false, length = 50)
    private String loaiBanThang;

    @Column(name = "ThoiDiem", nullable = false)
    private Integer thoiDiem;

    public BanThang(KetQuaThiDau maKetQua, CauThu maCauThu, String loaiBanThang, Integer thoiDiem) {
        this.maKetQua = maKetQua;
        this.maCauThu = maCauThu;
        this.loaiBanThang = loaiBanThang;
        this.thoiDiem = thoiDiem;
    }
}