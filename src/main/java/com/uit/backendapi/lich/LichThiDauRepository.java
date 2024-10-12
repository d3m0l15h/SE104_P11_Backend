package com.uit.backendapi.lich;

import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.mua_giai.MuaGiai;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface LichThiDauRepository extends JpaRepository<LichThiDau, Long> {
    List<LichThiDau> findByMaDoiNhaOrMaDoiKhachOrVongThiDauOrMaMuaGiai(DoiBong maDoiNha, DoiBong maDoiKhach, String vongThiDau, MuaGiai maMuaGiai);

    List<LichThiDau> findByNgayThiDauBetween(LocalDate ngayThiDauStart, LocalDate ngayThiDauEnd);

    Integer countByMaDoiKhachAndMaMuaGiai(DoiBong maDoiKhach, MuaGiai maMuaGiai);

    Integer countByMaDoiNhaAndMaMuaGiai(DoiBong maDoiNha, MuaGiai maMuaGiai);
}
