package com.uit.backendapi.qui_dinh;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuiDinhRepository extends JpaRepository<QuiDinh, Integer> {
    List<QuiDinh> findQuiDinhByTenQuiDinhLikeIgnoreCase(String tenQuiDinh);
    List<QuiDinh> findQuiDinhByTenQuiDinhContainsIgnoreCase(String tenQuiDinh);

}
