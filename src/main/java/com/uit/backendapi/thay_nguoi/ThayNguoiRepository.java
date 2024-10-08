package com.uit.backendapi.thay_nguoi;

import com.uit.backendapi.ket_qua.KetQuaThiDau;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ThayNguoiRepository extends JpaRepository<ThayNguoi, Long> {
    List<ThayNguoi> findByMaKetQua(KetQuaThiDau maKetQua);
    Optional<ThayNguoi> findByMaKetQuaAndId(KetQuaThiDau maKetQua, Integer id);
}
