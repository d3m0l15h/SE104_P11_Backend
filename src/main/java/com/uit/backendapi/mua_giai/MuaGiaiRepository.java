package com.uit.backendapi.mua_giai;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MuaGiaiRepository extends JpaRepository<MuaGiai, Long> {
    List<MuaGiai> findByTenMuaGiaiContaining(String tenMuaGiai);
}
