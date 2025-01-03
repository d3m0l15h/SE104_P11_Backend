package com.uit.backendapi.thay_nguoi;

import com.uit.backendapi.cau_thu.CauThu;
import com.uit.backendapi.ket_qua.KetQuaThiDau;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ThayNguoiRepository extends JpaRepository<ThayNguoi, Long> {
    List<ThayNguoi> findByMaKetQua(KetQuaThiDau maKetQua);
    Optional<ThayNguoi> findByMaKetQuaAndId(KetQuaThiDau maKetQua, Integer id);
    List<ThayNguoi> findByMaKetQuaAndMaCauThuRa(KetQuaThiDau maKetQua, CauThu maCauThuRa);
    List<ThayNguoi> findByMaKetQuaAndMaCauThuVao(KetQuaThiDau maKetQua, CauThu maCauThuVao);
    List<ThayNguoi> findByMaKetQuaAndMaCauThuRaAndMaCauThuVao(KetQuaThiDau maKetQua, CauThu maCauThuRa, CauThu maCauThuVao);
    List<ThayNguoi> findByMaKetQuaAndMaCauThuRaOrMaKetQuaAndMaCauThuVao(KetQuaThiDau maKetQua, CauThu maCauThuRa, KetQuaThiDau maKetQua2, CauThu maCauThuVao);

}
