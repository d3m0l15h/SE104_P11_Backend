package com.uit.backendapi.lich;

import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.mua_giai.MuaGiai;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface LichThiDauRepository extends JpaRepository<LichThiDau, Long> {
    List<LichThiDau> findByMaMuaGiai(MuaGiai maMuaGiai);

}
