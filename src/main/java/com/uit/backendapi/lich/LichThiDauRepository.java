package com.uit.backendapi.lich;

import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.mua_giai.MuaGiai;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface LichThiDauRepository extends JpaRepository<LichThiDau, Long> {
    List<LichThiDau> findByMaDoiNhaOrMaDoiKhachOrMaMuaGiai(DoiBong doiNha, DoiBong doiKhach, MuaGiai muaGiai);

    List<LichThiDau> findByMaMuaGiai(MuaGiai muaGiai);

    List<LichThiDau> findByVongThiDauAndMaMuaGiai(String vongThiDau, MuaGiai muaGiai);

    List<LichThiDau> findByNgayThiDauBetween(LocalDate ngayThiDauStart, LocalDate ngayThiDauEnd);

    List<LichThiDau> findByNgayThiDauAndGioThiDau(LocalDate ngayThiDau, LocalTime gioThiDau);
}
