package com.uit.backendapi.bxh;

import com.uit.backendapi.Utils;
import com.uit.backendapi.ban_thang.BanThang;
import com.uit.backendapi.bxh.dto.CreateBxhDto;
import com.uit.backendapi.bxh.dto.FilterBangXepHangDto;
import com.uit.backendapi.bxh.dto.UpdateBxhDto;
import com.uit.backendapi.cau_thu.CauThu;
import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.doi_bong.DoiBongRepository;
import com.uit.backendapi.exceptions.ResourceNotFoundException;
import com.uit.backendapi.ket_qua.KetQuaThiDau;
import com.uit.backendapi.ket_qua.KetQuaThiDauRepository;
import com.uit.backendapi.lich.LichThiDau;
import com.uit.backendapi.lich.LichThiDauRepository;
import com.uit.backendapi.mua_giai.MuaGiai;
import com.uit.backendapi.mua_giai.MuaGiaiRepository;
import com.uit.backendapi.qui_dinh.QuiDinh;
import com.uit.backendapi.qui_dinh.QuiDinhRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BangXepHangService implements IBangXepHangService {
    private final BangXepHangRepository bangXepHangRepository;
    private final DoiBongRepository doiBongRepository;
    private final MuaGiaiRepository muaGiaiRepository;
    private final QuiDinhRepository quiDinhRepository;

    @Override
    public List<BangXepHang> getBangXepHangByMuaGiai(String namOrId) {
        MuaGiai muaGiai = muaGiaiRepository.findByNamOrId(namOrId, Integer.valueOf(namOrId)).orElseThrow(
                () -> new ResourceNotFoundException("Mua giai not found with id: " + namOrId)
        );

        return bangXepHangRepository.findByMaMuaGiaiOrderByDiemDescSoTranThangDescHieuSoDescSoBanThangDesc(muaGiai);
    }

    @Override
    public List<BangXepHang> getBangXepHangByMuaGiai(MuaGiai muaGiai) {
        return bangXepHangRepository.findByMaMuaGiaiOrderByDiemDescSoTranThangDescHieuSoDescSoBanThangDesc(muaGiai);
    }

    @Override
    public BangXepHang getBangXepHangByMaDoiAndMuaGiai(Long maDoi, Long maMuaGiai) {
        DoiBong doiBong = doiBongRepository.findById(maDoi).orElse(null);

        MuaGiai muaGiai = muaGiaiRepository.findById(maMuaGiai).orElse(null);

        return bangXepHangRepository.findByMaDoiAndMaMuaGiai(doiBong, muaGiai).orElse(null);
    }

    @Override
    public BangXepHang getBangXepHangByMaDoiAndMuaGiai(DoiBong doiBong, MuaGiai muaGiai) {
        return bangXepHangRepository.findByMaDoiAndMaMuaGiai(doiBong, muaGiai).orElse(null);
    }

    @Override
    public Page<BangXepHang> filter(FilterBangXepHangDto filterBangXepHangDto, Pageable pageable) {
        Long maMuaGiai = Long.valueOf(filterBangXepHangDto.getMaMuaGiai());

        MuaGiai muaGiai = muaGiaiRepository.findById(maMuaGiai).orElseThrow(
                () -> new ResourceNotFoundException("Mua giai not found with id: " + maMuaGiai)
        );

        List<BangXepHang> bangXepHangs = bangXepHangRepository.findByMaMuaGiai(muaGiai).stream()
                .sorted(Comparator.comparing(BangXepHang::getDiem).reversed()).toList();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), bangXepHangs.size());
        return new PageImpl<>(bangXepHangs.subList(start, end), pageable, bangXepHangs.size());
    }

    @Override
    public BangXepHang createBangXepHang(CreateBxhDto createBxhDto) {
        if (createBxhDto.getMaDoi() == null) {
            throw new IllegalArgumentException("The given maDoi must not be null");
        }
        if (createBxhDto.getMaMuaGiai() == null) {
            throw new IllegalArgumentException("The given maMuaGiai must not be null");
        }

        DoiBong maDoiBong = doiBongRepository.findById(createBxhDto.getMaDoi()).orElseThrow(
                () -> new ResourceNotFoundException("Doi bong not found with id: " + createBxhDto.getMaDoi())
        );

        MuaGiai maMuaGiai = muaGiaiRepository.findById(createBxhDto.getMaMuaGiai()).orElseThrow(
                () -> new ResourceNotFoundException("Mua giai not found with id: " + createBxhDto.getMaMuaGiai())
        );

        BangXepHang bangXepHang = new BangXepHang(
                maDoiBong,
                0,
                0,
                0,
                0,
                maMuaGiai,
                0,
                0,
                0
        );

        return bangXepHangRepository.save(bangXepHang);
    }

    @Override
    public void createBangXepHang(DoiBong doiBong, MuaGiai muaGiai) {
        BangXepHang bangXepHang = new BangXepHang(
                doiBong,
                0,
                0,
                0,
                0,
                muaGiai,
                0,
                0,
                0
        );

        bangXepHangRepository.save(bangXepHang);
    }

    @Override
    public BangXepHang updateBangXepHang(Long id, UpdateBxhDto updateBxhDto) {
        return bangXepHangRepository.findById(id)
                .map(existingBangXepHang -> updateExistingBangXepHang(existingBangXepHang, updateBxhDto))
                .map(bangXepHangRepository::save)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Bang xep hang not found with id: " + id)
                );
    }

    private BangXepHang updateExistingBangXepHang(BangXepHang existingBangXepHang, UpdateBxhDto updateBxhDto) {
        Utils.copyNonNullProperties(updateBxhDto, existingBangXepHang, "id", "maDoi", "maMuaGiai");

        if (updateBxhDto.getMaDoi() != null) {
            DoiBong maDoiBong = doiBongRepository.findById(updateBxhDto.getMaDoi()).orElseThrow(
                    () -> new ResourceNotFoundException("Doi bong not found with id: " + updateBxhDto.getMaDoi())
            );
            existingBangXepHang.setMaDoi(maDoiBong);
        }

        if (updateBxhDto.getMaMuaGiai() != null) {
            MuaGiai maMuaGiai = muaGiaiRepository.findById(updateBxhDto.getMaMuaGiai()).orElseThrow(
                    () -> new ResourceNotFoundException("Mua giai not found with id: " + updateBxhDto.getMaMuaGiai())
            );
            existingBangXepHang.setMaMuaGiai(maMuaGiai);
        }

        return existingBangXepHang;
    }

    /**
     * Reverts the standings of the home and away teams based on the match result.
     *
     * @param doiNha             the home team
     * @param doiKhach           the away team
     * @param muaGiai            the season
     * @param soBanThangDoiNha   the number of goals scored by the home team
     * @param soBanThangDoiKhach the number of goals scored by the away team
     */
    public void rollbackBangXepHang(DoiBong doiNha, DoiBong doiKhach, MuaGiai muaGiai, int soBanThangDoiNha, int soBanThangDoiKhach) {
        int diemThang = quiDinhRepository.findQuiDinhByTenQuiDinhLikeIgnoreCase("%DiemThang%")
                .stream()
                .findFirst()
                .map(QuiDinh::getNoiDung)
                .map(Integer::parseInt)
                .orElse(3);
        int diemHoa = quiDinhRepository.findQuiDinhByTenQuiDinhLikeIgnoreCase("%DiemHoa%")
                .stream()
                .findFirst()
                .map(QuiDinh::getNoiDung)
                .map(Integer::parseInt)
                .orElse(1);
        int diemThua = quiDinhRepository.findQuiDinhByTenQuiDinhLikeIgnoreCase("%DiemThua%")
                .stream()
                .findFirst()
                .map(QuiDinh::getNoiDung)
                .map(Integer::parseInt)
                .orElse(0);

        BangXepHang bxhDoiNha = getBangXepHangByMaDoiAndMuaGiai(doiNha, muaGiai);
        BangXepHang bxhDoiKhach = getBangXepHangByMaDoiAndMuaGiai(doiKhach, muaGiai);

        if (soBanThangDoiNha > soBanThangDoiKhach) {
            //Diem doi nha
            bxhDoiNha.setDiem(bxhDoiNha.getDiem() - diemThang);
            //Diem doi khach
            bxhDoiKhach.setDiem(bxhDoiKhach.getDiem() - diemThua);
            //So tran thang doi nha
            bxhDoiNha.setSoTranThang(bxhDoiNha.getSoTranThang() - 1);
            //So tran thua doi khach
            bxhDoiKhach.setSoTranThua(bxhDoiKhach.getSoTranThua() - 1);
        } else if (soBanThangDoiNha == soBanThangDoiKhach) {
            //Diem
            bxhDoiNha.setDiem(bxhDoiNha.getDiem() - diemHoa);
            bxhDoiKhach.setDiem(bxhDoiKhach.getDiem() - diemHoa);
            //So tran hoa
            bxhDoiNha.setSoTranHoa(bxhDoiNha.getSoTranHoa() - 1);
            bxhDoiKhach.setSoTranHoa(bxhDoiKhach.getSoTranHoa() - 1);
        } else {
            //Diem doi khach
            bxhDoiKhach.setDiem(bxhDoiKhach.getDiem() - diemThang);
            //Diem doi nha
            bxhDoiNha.setDiem(bxhDoiNha.getDiem() - diemThua);
            //So tran thang doi khach
            bxhDoiKhach.setSoTranThang(bxhDoiKhach.getSoTranThang() - 1);
            //So tran thua doi nha
            bxhDoiNha.setSoTranThua(bxhDoiNha.getSoTranThua() - 1);
        }
        //So ban thang va so ban thua
        bxhDoiNha.setSoBanThang(bxhDoiNha.getSoBanThang() - soBanThangDoiNha);
        bxhDoiNha.setSoBanThua(bxhDoiNha.getSoBanThua() - soBanThangDoiKhach);

        bxhDoiKhach.setSoBanThang(bxhDoiKhach.getSoBanThang() - soBanThangDoiKhach);
        bxhDoiKhach.setSoBanThua(bxhDoiKhach.getSoBanThua() - soBanThangDoiNha);

        //Hieu so
        bxhDoiNha.setHieuSo(bxhDoiNha.getSoBanThang() - bxhDoiNha.getSoBanThua());
        bxhDoiKhach.setHieuSo(bxhDoiKhach.getSoBanThang() - bxhDoiKhach.getSoBanThua());

        bangXepHangRepository.save(bxhDoiNha);
        bangXepHangRepository.save(bxhDoiKhach);
    }

    @Override
    public void updateBangXepHang(DoiBong doiNha, DoiBong doiKhach, MuaGiai muaGiai, int soBanThangDoiNha, int soBanThangDoiKhach) {
        int diemThang = quiDinhRepository.findQuiDinhByTenQuiDinhLikeIgnoreCase("%DiemThang%")
                .stream()
                .findFirst()
                .map(QuiDinh::getNoiDung)
                .map(Integer::parseInt)
                .orElse(3);
        int diemHoa = quiDinhRepository.findQuiDinhByTenQuiDinhLikeIgnoreCase("%DiemHoa%")
                .stream()
                .findFirst()
                .map(QuiDinh::getNoiDung)
                .map(Integer::parseInt)
                .orElse(1);
        int diemThua = quiDinhRepository.findQuiDinhByTenQuiDinhLikeIgnoreCase("%DiemThua%")
                .stream()
                .findFirst()
                .map(QuiDinh::getNoiDung)
                .map(Integer::parseInt)
                .orElse(0);

        BangXepHang bxhDoiNha = getBangXepHangByMaDoiAndMuaGiai(doiNha, muaGiai);
        BangXepHang bxhDoiKhach = getBangXepHangByMaDoiAndMuaGiai(doiKhach, muaGiai);

        if (soBanThangDoiNha > soBanThangDoiKhach) {
            //Diem doi nha
            bxhDoiNha.setDiem(bxhDoiNha.getDiem() + diemThang);
            bxhDoiKhach.setDiem(bxhDoiKhach.getDiem() + diemThua);
            //So tran thang doi nha
            bxhDoiNha.setSoTranThang(bxhDoiNha.getSoTranThang() + 1);
            //So tran thua doi khach
            bxhDoiKhach.setSoTranThua(bxhDoiKhach.getSoTranThua() + 1);
        } else if (soBanThangDoiNha == soBanThangDoiKhach) {
            //Diem
            bxhDoiNha.setDiem(bxhDoiNha.getDiem() + diemHoa);
            bxhDoiKhach.setDiem(bxhDoiKhach.getDiem() + diemHoa);
            //So tran hoa
            bxhDoiNha.setSoTranHoa(bxhDoiNha.getSoTranHoa() + 1);
            bxhDoiKhach.setSoTranHoa(bxhDoiKhach.getSoTranHoa() + 1);
        } else {
            //Diem doi khach
            bxhDoiKhach.setDiem(bxhDoiKhach.getDiem() + diemThang);
            bxhDoiNha.setDiem(bxhDoiNha.getDiem() + diemThua);
            //So tran thang doi khach
            bxhDoiKhach.setSoTranThang(bxhDoiKhach.getSoTranThang() + 1);
            //So tran thua doi nha
            bxhDoiNha.setSoTranThua(bxhDoiNha.getSoTranThua() + 1);
        }
        //So ban thang va so ban thua
        bxhDoiNha.setSoBanThang(bxhDoiNha.getSoBanThang() + soBanThangDoiNha);
        bxhDoiNha.setSoBanThua(bxhDoiNha.getSoBanThua() + soBanThangDoiKhach);

        bxhDoiKhach.setSoBanThang(bxhDoiKhach.getSoBanThang() + soBanThangDoiKhach);
        bxhDoiKhach.setSoBanThua(bxhDoiKhach.getSoBanThua() + soBanThangDoiNha);

        //Hieu so
        bxhDoiNha.setHieuSo(bxhDoiNha.getSoBanThang() - bxhDoiNha.getSoBanThua());
        bxhDoiKhach.setHieuSo(bxhDoiKhach.getSoBanThang() - bxhDoiKhach.getSoBanThua());

        bangXepHangRepository.save(bxhDoiNha);
        bangXepHangRepository.save(bxhDoiKhach);
    }

    @Override
    public void deleteBangXepHang(Long id) {
        BangXepHang bxh = bangXepHangRepository.findById(id).orElse(null);

        if(Objects.isNull(bxh)) {
            throw new ResourceNotFoundException("Bang xep hang not found with id: " + id);
        } else if (bxh.getDiem() != 0) {
            throw new IllegalArgumentException("Cannot delete a standing that has points");
        } else {
            bangXepHangRepository.deleteById(id);
        }
    }
}
