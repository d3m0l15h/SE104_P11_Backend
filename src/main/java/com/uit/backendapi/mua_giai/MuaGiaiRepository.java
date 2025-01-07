package com.uit.backendapi.mua_giai;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MuaGiaiRepository extends JpaRepository<MuaGiai, Long> {
    Optional<MuaGiai> findByNamOrId(String nam, Integer id);
}
