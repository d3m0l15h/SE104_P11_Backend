package com.uit.backendapi.ban_thang;

import com.uit.backendapi.ket_qua.KetQuaThiDau;
import com.uit.backendapi.mua_giai.MuaGiai;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BanThangRepository extends JpaRepository<BanThang, Long> {
    List<BanThang> findBanThangByMaKetQua(KetQuaThiDau maKetQua);
    Optional<BanThang> findBanThangByMaKetQuaAndId(KetQuaThiDau maKetQua, Integer id);
}
