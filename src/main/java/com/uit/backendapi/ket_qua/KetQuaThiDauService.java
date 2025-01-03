package com.uit.backendapi.ket_qua;

import com.uit.backendapi.Utils;
import com.uit.backendapi.ban_thang.BanThangService;
import com.uit.backendapi.ban_thang.dto.CreateBanThangDto;
import com.uit.backendapi.ban_thang.dto.UpdateBanThangDto;
import com.uit.backendapi.bxh.BangXepHangService;
import com.uit.backendapi.cau_thu.CauThu;
import com.uit.backendapi.cau_thu.CauThuRepository;
import com.uit.backendapi.exceptions.ResourceNotFoundException;
import com.uit.backendapi.ket_qua.dto.CreateKetQuaThiDauDto;
import com.uit.backendapi.ket_qua.dto.UpdateKetQuaThiDauDto;
import com.uit.backendapi.lich.LichThiDau;
import com.uit.backendapi.lich.LichThiDauRepository;
import com.uit.backendapi.thay_nguoi.ThayNguoiService;
import com.uit.backendapi.thay_nguoi.dto.CreateThayNguoiDto;
import com.uit.backendapi.thay_nguoi.dto.UpdateThayNguoiDto;
import com.uit.backendapi.the_phat.ThePhatService;
import com.uit.backendapi.the_phat.dto.CreateThePhatDto;
import com.uit.backendapi.the_phat.dto.UpdateThePhatDto;
import com.uit.backendapi.utils.KetQuaUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class KetQuaThiDauService implements IKetQuaThiDauService {
    private final KetQuaThiDauRepository ketQuaThiDauRepository;
    private final CauThuRepository cauThuRepository;
    private final ThePhatService thePhatService;
    private final ThayNguoiService thayNguoiService;
    private final BanThangService banThangService;
    private final BangXepHangService bangXepHangService;
    private final LichThiDauRepository lichThiDauRepository;

    @Override
    public Page<KetQuaThiDau> getAllKetQuaThiDau(Pageable pageable) {
        List<KetQuaThiDau> ketQuaThiDaus = ketQuaThiDauRepository.findAll();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), ketQuaThiDaus.size());
        return new PageImpl<>(ketQuaThiDaus.subList(start, end), pageable, ketQuaThiDaus.size());
    }

    @Override
    public KetQuaThiDau getKetQuaThiDauById(Long id) {
        return ketQuaThiDauRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Ket qua thi dau not found")
        );
    }

    @Override
    public KetQuaThiDau createKetQuaThiDau(CreateKetQuaThiDauDto createKetQuaThiDauDto) {
        LichThiDau lichThiDau = lichThiDauRepository.findById(createKetQuaThiDauDto.getMaLichThiDau()).orElseThrow(
                () -> new ResourceNotFoundException("Lich thi dau not found")
        );
        KetQuaThiDau ketQuaThiDau = new KetQuaThiDau(createKetQuaThiDauDto, lichThiDau);

        if (createKetQuaThiDauDto.getCauThuXuatSac() != null) {
            CauThu cauThuXuatSac = cauThuRepository.findById(createKetQuaThiDauDto.getCauThuXuatSac()).orElseThrow(
                    () -> new ResourceNotFoundException("Cau thu not found")
            );

            if (!ketQuaThiDau.getLichThiDau().getMaDoiNha().getCauThus().contains(cauThuXuatSac)
                    && !ketQuaThiDau.getLichThiDau().getMaDoiKhach().getCauThus().contains(cauThuXuatSac)) {
                throw new ResourceNotFoundException("Cau thu khong thuoc doi bong nao trong tran dau");
            }

            ketQuaThiDau.setCauThuXuatSac(cauThuXuatSac);
        }

        ketQuaThiDau = ketQuaThiDauRepository.save(ketQuaThiDau);

        // Create the phat
        if (createKetQuaThiDauDto.getThePhats() != null) {
            for (CreateThePhatDto thePhatDto : createKetQuaThiDauDto.getThePhats()) {
                thePhatService.createThePhatByMaKetQua(ketQuaThiDau, thePhatDto);
            }
        }

        // Create thay nguoi
        if (createKetQuaThiDauDto.getThayNguois() != null) {
            for (CreateThayNguoiDto thayNguoiDto : createKetQuaThiDauDto.getThayNguois()) {
                thayNguoiService.createThayNguoiByKetQua(ketQuaThiDau, thayNguoiDto);
            }
        }

        // Create ban thang
        if (createKetQuaThiDauDto.getBanThangs() != null && createKetQuaThiDauDto.getBanThangs().length > 0) {
            for (CreateBanThangDto banThangDto : createKetQuaThiDauDto.getBanThangs()) {
                banThangService.createBanThangByKetQua(ketQuaThiDau, banThangDto);
            }
        } else {
            bangXepHangService.updateBangXepHang(
                    ketQuaThiDau.getLichThiDau().getMaDoiNha(),
                    ketQuaThiDau.getLichThiDau().getMaDoiKhach(),
                    ketQuaThiDau.getLichThiDau().getMaMuaGiai(),
                    0,
                    0
            );
        }

        return ketQuaThiDauRepository.findById(ketQuaThiDau.getId().longValue()).orElseThrow(
                () -> new ResourceNotFoundException("Ket qua thi dau not found")
        );
    }

    @Override
    public KetQuaThiDau updateKetQuaThiDau(Long id, UpdateKetQuaThiDauDto updateKetQuaThiDauDto) {
        return ketQuaThiDauRepository.findById(id)
                .map(existingKetQuaThiDau -> updateExistingKetQuaThiDau(existingKetQuaThiDau, updateKetQuaThiDauDto))
                .map(ketQuaThiDauRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Ket qua thi dau not found")
                );
    }

    private KetQuaThiDau updateExistingKetQuaThiDau(KetQuaThiDau existingKetQuaThiDau, UpdateKetQuaThiDauDto updateKetQuaThiDauDto) {
        Utils.copyNonNullProperties(updateKetQuaThiDauDto, existingKetQuaThiDau, "id", "maLichThiDau", "cauThuXuatSac");

        if (updateKetQuaThiDauDto.getCauThuXuatSac() != null) {
            CauThu cauThuXuatSac = cauThuRepository.findById(updateKetQuaThiDauDto.getCauThuXuatSac()).orElseThrow(
                    () -> new ResourceNotFoundException("Cau thu not found")
            );

            if (!existingKetQuaThiDau.getLichThiDau().getMaDoiNha().getCauThus().contains(cauThuXuatSac)
                    && !existingKetQuaThiDau.getLichThiDau().getMaDoiKhach().getCauThus().contains(cauThuXuatSac)) {
                throw new ResourceNotFoundException("Cau thu khong thuoc doi bong nao trong tran dau");
            }

            existingKetQuaThiDau.setCauThuXuatSac(cauThuXuatSac);
        }

        return existingKetQuaThiDau;
    }

    @Override
    public void deleteKetQuaThiDau(Long id) {
        ketQuaThiDauRepository.findById(id).ifPresentOrElse(
                ketQuaThiDauRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("Ket qua thi dau not found");
                }
        );
    }


}
