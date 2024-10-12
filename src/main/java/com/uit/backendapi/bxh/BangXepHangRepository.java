package com.uit.backendapi.bxh;

import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.mua_giai.MuaGiai;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BangXepHangRepository extends JpaRepository<BangXepHang, Long> {
    Optional<BangXepHang> findByMaDoiAndMaMuaGiai(DoiBong maDoi, MuaGiai maMuaGiai);
    List<BangXepHang> findByMaMuaGiaiOrderByDiemDescSoTranThangDescHieuSoDescSoBanThangDesc(MuaGiai maMuaGiai);
}
