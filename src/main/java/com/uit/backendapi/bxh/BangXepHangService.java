package com.uit.backendapi.bxh;

import com.uit.backendapi.Utils;
import com.uit.backendapi.bxh.dto.CreateBxhDto;
import com.uit.backendapi.bxh.dto.UpdateBxhDto;
import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.doi_bong.DoiBongRepository;
import com.uit.backendapi.exceptions.ResourceNotFoundException;
import com.uit.backendapi.mua_giai.MuaGiai;
import com.uit.backendapi.mua_giai.MuaGiaiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BangXepHangService implements IBangXepHangService {
    private final BangXepHangRepository bangXepHangRepository;
    private final DoiBongRepository doiBongRepository;
    private final MuaGiaiRepository muaGiaiRepository;

    @Override
    public List<BangXepHang> getBangXepHangByMuaGiai(Long maMuaGiai) {
        MuaGiai muaGiai = muaGiaiRepository.findById(maMuaGiai).orElseThrow(
                () -> new ResourceNotFoundException("Mua giai not found with id: " + maMuaGiai)
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
    public BangXepHang createBangXepHang(CreateBxhDto createBxhDto) {
        DoiBong maDoiBong = doiBongRepository.findById(createBxhDto.getMaDoi()).orElseThrow(
                () -> new ResourceNotFoundException("Doi bong not found with id: " + createBxhDto.getMaDoi())
        );

        MuaGiai maMuaGiai = muaGiaiRepository.findById(createBxhDto.getMaMuaGiai()).orElseThrow(
                () -> new ResourceNotFoundException("Mua giai not found with id: " + createBxhDto.getMaMuaGiai())
        );

        BangXepHang bangXepHang = new BangXepHang(
                maDoiBong,
                Objects.requireNonNullElse(createBxhDto.getSoTranThang(), 0),
                Objects.requireNonNullElse(createBxhDto.getSoTranHoa(), 0),
                Objects.requireNonNullElse(createBxhDto.getSoTranThua(), 0),
                Objects.requireNonNullElse(createBxhDto.getHieuSo(), 0),
                maMuaGiai,
                Objects.requireNonNullElse(createBxhDto.getDiem(), 0),
                Objects.requireNonNullElse(createBxhDto.getSoBanThang(), 0),
                Objects.requireNonNullElse(createBxhDto.getSoBanThua(), 0)
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

    @Override
    public void updateBangXepHang(DoiBong doiNha, DoiBong doiKhach, MuaGiai muaGiai, int soBanThangDoiNha, int soBanThangDoiKhach){
        BangXepHang bxhDoiNha = getBangXepHangByMaDoiAndMuaGiai(doiNha, muaGiai);
        BangXepHang bxhDoiKhach = getBangXepHangByMaDoiAndMuaGiai(doiKhach, muaGiai);

        if(soBanThangDoiNha > soBanThangDoiKhach) {
            //Diem doi nha
            bxhDoiNha.setDiem(bxhDoiNha.getDiem() + 3);
            //So tran thang doi nha
            bxhDoiNha.setSoTranThang(bxhDoiNha.getSoTranThang() + 1);
            //So tran thua doi khach
            bxhDoiKhach.setSoTranThua(bxhDoiKhach.getSoTranThua() + 1);
        } else if(soBanThangDoiNha == soBanThangDoiKhach) {
            //Diem
            bxhDoiNha.setDiem(bxhDoiNha.getDiem() + 1);
            bxhDoiKhach.setDiem(bxhDoiKhach.getDiem() + 1);
            //So tran hoa
            bxhDoiNha.setSoTranHoa(bxhDoiNha.getSoTranHoa() + 1);
            bxhDoiKhach.setSoTranHoa(bxhDoiKhach.getSoTranHoa() + 1);
        } else {
            //Diem doi khach
            bxhDoiKhach.setDiem(bxhDoiKhach.getDiem() + 3);
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
        bangXepHangRepository.findById(id).ifPresentOrElse(
                bangXepHangRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("Bang xep hang not found with id: " + id);
                }
        );
    }
}
