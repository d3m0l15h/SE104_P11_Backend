package com.uit.backendapi.ban_thang;

import com.uit.backendapi.ban_thang.dto.CreateBanThangDto;
import com.uit.backendapi.ban_thang.dto.UpdateBanThangDto;
import com.uit.backendapi.ket_qua.KetQuaThiDau;

import java.util.List;

public interface IBanThangService {
    List<BanThang> getBanThangByKetQua(Long id);
    BanThang createBanThangByKetQua(KetQuaThiDau ketQuaThiDau, CreateBanThangDto createBanThangDto);
    BanThang updateBanThangByKetQuaAndId(KetQuaThiDau ketQuaThiDau, Long id, UpdateBanThangDto updateBanThangDto);
    void deleteBanThangByKetQuaAndId(KetQuaThiDau ketQuaThiDau, Long id);
}
