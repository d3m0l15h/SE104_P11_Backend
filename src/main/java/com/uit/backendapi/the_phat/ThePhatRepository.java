package com.uit.backendapi.the_phat;

import com.uit.backendapi.ket_qua.KetQuaThiDau;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ThePhatRepository extends JpaRepository<ThePhat, Long> {
    List<ThePhat> findByMaKetQua(KetQuaThiDau maKetQua);
    Optional<ThePhat> findByIdAndMaKetQua(Integer id, KetQuaThiDau maKetQua);
}
