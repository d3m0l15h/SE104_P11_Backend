package com.uit.backendapi.ban_thang;

import com.uit.backendapi.Utils;
import com.uit.backendapi.ban_thang.dto.CreateBanThangDto;
import com.uit.backendapi.ban_thang.dto.UpdateBanThangDto;
import com.uit.backendapi.bxh.BangXepHang;
import com.uit.backendapi.bxh.BangXepHangRepository;
import com.uit.backendapi.bxh.BangXepHangService;
import com.uit.backendapi.cau_thu.CauThu;
import com.uit.backendapi.cau_thu.CauThuRepository;
import com.uit.backendapi.exceptions.ResourceNotFoundException;
import com.uit.backendapi.ket_qua.KetQuaThiDau;
import com.uit.backendapi.ket_qua.KetQuaThiDauRepository;
import com.uit.backendapi.qui_dinh.QuiDinhRepository;
import com.uit.backendapi.thay_nguoi.ThayNguoiRepository;
import com.uit.backendapi.the_phat.ThePhat;
import com.uit.backendapi.the_phat.ThePhatRepository;
import com.uit.backendapi.utils.KetQuaUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BanThangService implements IBanThangService {
    private final KetQuaThiDauRepository ketQuaThiDauRepository;
    private final BanThangRepository banThangRepository;
    private final CauThuRepository cauThuRepository;
    private final BangXepHangService bangXepHangService;
    private final ThayNguoiRepository thayNguoiRepository;
    private final ThePhatRepository thePhatRepository;
    private final QuiDinhRepository quiDinhRepository;
    private final BangXepHangRepository bangXepHangRepository;

    public boolean isCauThuDoiNha(CauThu cauThu, KetQuaThiDau ketQuaThiDau) {
        return ketQuaThiDau.getLichThiDau().getMaDoiNha().getCauThus().contains(cauThu);
    }

    public boolean isCauThuDoiKhach(CauThu cauThu, KetQuaThiDau ketQuaThiDau) {
        return ketQuaThiDau.getLichThiDau().getMaDoiKhach().getCauThus().contains(cauThu);
    }

    @Override
    public List<BanThang> getBanThangByKetQua(Long id) {
        KetQuaThiDau ketQuaThiDau = ketQuaThiDauRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ket qua thi dau not found with id: " + id));
        return banThangRepository.findBanThangByMaKetQua(ketQuaThiDau);
    }

    @Override
    public BanThang createBanThangByKetQua(KetQuaThiDau ketQuaThiDau, CreateBanThangDto createBanThangDto) {
        //----------------------------------------VALIDATION----------------------------------------
        CauThu cauThu = cauThuRepository.findById(createBanThangDto.getMaCauThu())
                .orElseThrow(() -> new ResourceNotFoundException("Cau thu not found with id: " + createBanThangDto.getMaCauThu()));

        KetQuaUtils.thoiDiemValidation(createBanThangDto.getThoiDiem(), quiDinhRepository);

        KetQuaUtils.cauThuValidation(ketQuaThiDau, cauThu, createBanThangDto.getThoiDiem(), thayNguoiRepository);

        KetQuaUtils.thePhatValidation(ketQuaThiDau, cauThu,
                createBanThangDto.getThoiDiem(), thePhatRepository, "ban thang");

        //--------------------------------------------------------------------------------

        BangXepHang bangXepHang = bangXepHangRepository.findByMaDoiAndMaMuaGiai(
                        cauThu.getMaDoi(),
                        ketQuaThiDau.getLichThiDau().getMaMuaGiai())
                .orElseThrow(() -> new ResourceNotFoundException("Bang xep hang not found with doi: " + ketQuaThiDau.getLichThiDau().getMaDoiNha().getTenDoi()));
        // Rollback bang xep hang

        bangXepHangService.rollbackBangXepHang(
                ketQuaThiDau.getLichThiDau().getMaDoiNha(),
                ketQuaThiDau.getLichThiDau().getMaDoiKhach(),
                ketQuaThiDau.getLichThiDau().getMaMuaGiai(),
                ketQuaThiDau.getSoBanDoiNha(),
                ketQuaThiDau.getSoBanDoiKhach()
        );


        // Handle calculate score
        if (isCauThuDoiNha(cauThu, ketQuaThiDau) && !createBanThangDto.getLoaiBanThang().equals("C") ||
                (isCauThuDoiKhach(cauThu, ketQuaThiDau) && createBanThangDto.getLoaiBanThang().equals("C"))
        ) {
            ketQuaThiDau.setSoBanDoiNha(ketQuaThiDau.getSoBanDoiNha() + 1);
        }

        // Handle calculate score
        if (isCauThuDoiKhach(cauThu, ketQuaThiDau) && !createBanThangDto.getLoaiBanThang().equals("C")
                || (isCauThuDoiNha(cauThu, ketQuaThiDau) && createBanThangDto.getLoaiBanThang().equals("C"))
        ) {
            ketQuaThiDau.setSoBanDoiKhach(ketQuaThiDau.getSoBanDoiKhach() + 1);
        }

        //----------------------------------------CREATE----------------------------------------
        BanThang banThang = new BanThang(
                ketQuaThiDau,
                cauThu,
                createBanThangDto.getLoaiBanThang(),
                createBanThangDto.getThoiDiem());

        // Update bang xep hang
        bangXepHangService.updateBangXepHang(
                ketQuaThiDau.getLichThiDau().getMaDoiNha(),
                ketQuaThiDau.getLichThiDau().getMaDoiKhach(),
                ketQuaThiDau.getLichThiDau().getMaMuaGiai(),
                ketQuaThiDau.getSoBanDoiNha(),
                ketQuaThiDau.getSoBanDoiKhach()
        );

        ketQuaThiDauRepository.save(ketQuaThiDau);

        return banThangRepository.save(banThang);
    }

    @Override
    public BanThang updateBanThangByKetQuaAndId(KetQuaThiDau ketQuaThiDau, Long id, UpdateBanThangDto updateBanThangDto) {
        ketQuaThiDau.getBanThangs().stream()
                .filter(banThang -> banThang.getId() == id.intValue())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Ban thang not found in ket qua with id: " + id));

        return banThangRepository.findBanThangByMaKetQuaAndId(ketQuaThiDau, Math.toIntExact(id))
                .map(existingBanThang -> updateExistingBanThang(existingBanThang, updateBanThangDto))
                .map(banThangRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Ban thang not found with id: " + id));
    }

    private BanThang updateExistingBanThang(BanThang existingBanThang, UpdateBanThangDto updateBanThangDto) {
        Utils.copyNonNullProperties(updateBanThangDto, existingBanThang, "id", "maKetQua", "maCauThu");

        if (updateBanThangDto.getMaCauThu() != null) {
            //Find new CauThu
            CauThu cauThu = cauThuRepository.findById(updateBanThangDto.getMaCauThu())
                    .orElseThrow(() -> new ResourceNotFoundException("Cau thu not found with id: " + updateBanThangDto.getMaCauThu()));

            if (!existingBanThang.getMaKetQua().getLichThiDau().getMaDoiNha().getCauThus().contains(cauThu)
                    && !existingBanThang.getMaKetQua().getLichThiDau().getMaDoiKhach().getCauThus().contains(cauThu)
            ) {
                throw new ResourceNotFoundException("Cau thu " + cauThu.getTenCauThu() + " khong thuoc doi bong nao trong tran dau");
            }

            //Rollback bang xep hang
            bangXepHangService.rollbackBangXepHang(
                    existingBanThang.getMaKetQua().getLichThiDau().getMaDoiNha(),
                    existingBanThang.getMaKetQua().getLichThiDau().getMaDoiKhach(),
                    existingBanThang.getMaKetQua().getLichThiDau().getMaMuaGiai(),
                    existingBanThang.getMaKetQua().getSoBanDoiNha(),
                    existingBanThang.getMaKetQua().getSoBanDoiKhach()
            );

            assert existingBanThang.getMaKetQua() != null;
            //Check if the old cau thu and new cau thu are in the different team
            if (
                    isCauThuDoiNha(cauThu, existingBanThang.getMaKetQua()) //new cau thu is from doi nha
                            && isCauThuDoiKhach(existingBanThang.getMaCauThu(), existingBanThang.getMaKetQua()) //old cau thu is from doi khach
            ) {
                existingBanThang.getMaKetQua().setSoBanDoiNha(existingBanThang.getMaKetQua().getSoBanDoiNha() + 1);
                existingBanThang.getMaKetQua().setSoBanDoiKhach(existingBanThang.getMaKetQua().getSoBanDoiKhach() - 1);
            }

            //Check if the old cau thu and new cau thu are in the different team
            if (isCauThuDoiKhach(cauThu, existingBanThang.getMaKetQua()) // new cau thu is from doi khach
                    && isCauThuDoiNha(existingBanThang.getMaCauThu(), existingBanThang.getMaKetQua()) // old cau thu is from doi nha
            ) {
                existingBanThang.getMaKetQua().setSoBanDoiKhach(existingBanThang.getMaKetQua().getSoBanDoiKhach() + 1);
                existingBanThang.getMaKetQua().setSoBanDoiNha(existingBanThang.getMaKetQua().getSoBanDoiNha() - 1);
            }

            // Update bang xep hang
            bangXepHangService.updateBangXepHang(
                    existingBanThang.getMaKetQua().getLichThiDau().getMaDoiNha(),
                    existingBanThang.getMaKetQua().getLichThiDau().getMaDoiKhach(),
                    existingBanThang.getMaKetQua().getLichThiDau().getMaMuaGiai(),
                    existingBanThang.getMaKetQua().getSoBanDoiNha(),
                    existingBanThang.getMaKetQua().getSoBanDoiKhach()
            );

            ketQuaThiDauRepository.save(existingBanThang.getMaKetQua());
            existingBanThang.setMaCauThu(cauThu);
        }

        return existingBanThang;
    }

    @Override
    public void deleteBanThangByKetQuaAndId(KetQuaThiDau ketQuaThiDau, Long id) {
        //Rollback bang xep hang
        bangXepHangService.rollbackBangXepHang(
                ketQuaThiDau.getLichThiDau().getMaDoiNha(),
                ketQuaThiDau.getLichThiDau().getMaDoiKhach(),
                ketQuaThiDau.getLichThiDau().getMaMuaGiai(),
                ketQuaThiDau.getSoBanDoiNha(),
                ketQuaThiDau.getSoBanDoiKhach()
        );

        BanThang banThang = banThangRepository.findBanThangByMaKetQuaAndId(ketQuaThiDau, Math.toIntExact(id))
                .orElseThrow(() -> new ResourceNotFoundException("Ban thang not found with id: " + id));

        if (isCauThuDoiNha(banThang.getMaCauThu(), ketQuaThiDau) && !banThang.getLoaiBanThang().equals("C")
                || (isCauThuDoiKhach(banThang.getMaCauThu(), ketQuaThiDau) && banThang.getLoaiBanThang().equals("C"))
        ) {
            ketQuaThiDau.setSoBanDoiNha(ketQuaThiDau.getSoBanDoiNha() - 1);
        }

        if (isCauThuDoiKhach(banThang.getMaCauThu(), ketQuaThiDau) && !banThang.getLoaiBanThang().equals("C")
                || (isCauThuDoiNha(banThang.getMaCauThu(), ketQuaThiDau) && banThang.getLoaiBanThang().equals("C"))
        ) {
            ketQuaThiDau.setSoBanDoiKhach(ketQuaThiDau.getSoBanDoiKhach() - 1);
        }

        bangXepHangService.updateBangXepHang(
                ketQuaThiDau.getLichThiDau().getMaDoiNha(),
                ketQuaThiDau.getLichThiDau().getMaDoiKhach(),
                ketQuaThiDau.getLichThiDau().getMaMuaGiai(),
                ketQuaThiDau.getSoBanDoiNha(),
                ketQuaThiDau.getSoBanDoiKhach()
        );

        ketQuaThiDauRepository.save(ketQuaThiDau);

        banThangRepository.findBanThangByMaKetQuaAndId(ketQuaThiDau, Math.toIntExact(id))
                .ifPresentOrElse(banThangRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException("Ban thang not found with id: " + id);
                        });
    }
}
