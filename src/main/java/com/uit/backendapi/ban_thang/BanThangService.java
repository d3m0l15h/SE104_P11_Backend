package com.uit.backendapi.ban_thang;

import com.uit.backendapi.ban_thang.dto.CreateBanThangDto;
import com.uit.backendapi.ban_thang.dto.UpdateBanThangDto;
import com.uit.backendapi.cau_thu.CauThu;
import com.uit.backendapi.cau_thu.CauThuRepository;
import com.uit.backendapi.exceptions.ResourceNotFoundException;
import com.uit.backendapi.ket_qua.KetQuaThiDau;
import com.uit.backendapi.ket_qua.KetQuaThiDauRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BanThangService implements IBanThangService {
    private final KetQuaThiDauRepository ketQuaThiDauRepository;
    private final BanThangRepository banThangRepository;
    private final CauThuRepository cauThuRepository;

    @Override
    public List<BanThang> getBanThangByKetQua(Long id) {
        KetQuaThiDau ketQuaThiDau = ketQuaThiDauRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ket qua thi dau not found with id: " + id));
        return banThangRepository.findBanThangByMaKetQua(ketQuaThiDau);
    }

    @Override
    public BanThang createBanThangByKetQua(KetQuaThiDau ketQuaThiDau, CreateBanThangDto createBanThangDto) {
        CauThu cauThu = cauThuRepository.findById(createBanThangDto.getMaCauThu())
                .orElseThrow(() -> new ResourceNotFoundException("Cau thu not found with id: " + createBanThangDto.getMaCauThu()));

        BanThang banThang = new BanThang(
                ketQuaThiDau,
                cauThu,
                createBanThangDto.getLoaiBanThang(),
                createBanThangDto.getThoiDiem());

        return banThangRepository.save(banThang);
    }

    @Override
    public BanThang updateBanThangByKetQuaAndId(KetQuaThiDau ketQuaThiDau, Long id, UpdateBanThangDto updateBanThangDto) {
        return  banThangRepository.findBanThangByMaKetQuaAndId(ketQuaThiDau, Math.toIntExact(id))
                .map(existingBanThang -> updateExistingBanThang(existingBanThang, updateBanThangDto))
                .map(banThangRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Ban thang not found with id: " + id));
    }

    private BanThang updateExistingBanThang(BanThang existingBanThang, UpdateBanThangDto updateBanThangDto) {
        BeanUtils.copyProperties(updateBanThangDto, existingBanThang,  "id", "maKetQua", "maCauThu");

        if(updateBanThangDto.getMaCauThu() != null) {
            CauThu cauThu = cauThuRepository.findById(updateBanThangDto.getMaCauThu())
                    .orElseThrow(() -> new ResourceNotFoundException("Cau thu not found with id: " + updateBanThangDto.getMaCauThu()));
            existingBanThang.setMaCauThu(cauThu);
        }

        return existingBanThang;
    }

    @Override
    public void deleteBanThangByKetQuaAndId(KetQuaThiDau ketQuaThiDau, Long id) {
        banThangRepository.findBanThangByMaKetQuaAndId(ketQuaThiDau, Math.toIntExact(id))
                .ifPresentOrElse(banThangRepository::delete,
                        () -> {throw new ResourceNotFoundException("Ban thang not found with id: " + id);});
    }
}
