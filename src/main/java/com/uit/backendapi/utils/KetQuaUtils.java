package com.uit.backendapi.utils;

import com.uit.backendapi.cau_thu.CauThu;
import com.uit.backendapi.exceptions.ResourceNotFoundException;
import com.uit.backendapi.ket_qua.KetQuaThiDau;
import com.uit.backendapi.qui_dinh.QuiDinhRepository;
import com.uit.backendapi.thay_nguoi.ThayNguoi;
import com.uit.backendapi.thay_nguoi.ThayNguoiRepository;
import com.uit.backendapi.the_phat.ThePhat;
import com.uit.backendapi.the_phat.ThePhatRepository;

import java.util.Comparator;
import java.util.List;

public class KetQuaUtils {
    public static void thoiDiemValidation(int thoiDiem, QuiDinhRepository quiDinhRepository) {
        int thoiGianToiDa = quiDinhRepository.findQuiDinhByTenQuiDinhLikeIgnoreCase("%ThoiGianToiDa%").isEmpty()
                ? 90
                : Integer.parseInt(quiDinhRepository.findQuiDinhByTenQuiDinhLikeIgnoreCase("%ThoiGianToiDa%").getFirst().getNoiDung());

        if (thoiDiem < 0 || thoiDiem > thoiGianToiDa) {
            throw new IllegalArgumentException("Thoi diem khong hop le (0-90)");
        }
    }

    public static void cauThuValidation(
            KetQuaThiDau ketQuaThiDau,
            CauThu cauThu, int thoiDiem,
            ThayNguoiRepository thayNguoiRepository
    ) {
        if (!ketQuaThiDau.getLichThiDau().getMaDoiNha().getCauThus().contains(cauThu)
                && !ketQuaThiDau.getLichThiDau().getMaDoiKhach().getCauThus().contains(cauThu)) {
            throw new ResourceNotFoundException("Cau thu khong thuoc doi bong nao trong tran dau");
        }

        ThayNguoi thayNguoiRa = thayNguoiRepository.findByMaKetQuaAndMaCauThuRa(ketQuaThiDau, cauThu)
                .stream()
                .findFirst()
                .orElse(null);
        if (thayNguoiRa != null && thayNguoiRa.getThoiDiem() < thoiDiem) {
            throw new ResourceNotFoundException("Cau thu " + cauThu.getTenCauThu() + " da bi thay ra ngoai");
        }

        ThayNguoi thayNguoiVao = thayNguoiRepository.findByMaKetQuaAndMaCauThuVao(ketQuaThiDau, cauThu)
                .stream()
                .findFirst()
                .orElse(null);
        if (thayNguoiVao != null && thayNguoiVao.getThoiDiem() > thoiDiem) {
            throw new ResourceNotFoundException("Cau thu " + cauThu.getTenCauThu() + " chua vao san");
        }
    }

    public static void thayNguoiValidation(
            KetQuaThiDau ketQuaThiDau,
            CauThu cauThuRa,
            CauThu cauThuVao,
            int thoiDiem,
            ThayNguoiRepository thayNguoiRepository,
            ThePhatRepository thePhatRepository
    ) {
        if (cauThuRa.getMaDoi() != cauThuVao.getMaDoi()) {
            throw new IllegalArgumentException("Cau thu ra and cau thu vao must be in same team");
        }

        if (!ketQuaThiDau.getLichThiDau().getMaDoiNha().getCauThus().contains(cauThuRa)
                && !ketQuaThiDau.getLichThiDau().getMaDoiKhach().getCauThus().contains(cauThuRa)
        ) {
            throw new ResourceNotFoundException("Cau thu khong thuoc doi bong nao trong tran dau");
        }

        ThayNguoi thayNguoi = thayNguoiRepository.findByMaKetQuaAndMaCauThuRaAndMaCauThuVao(ketQuaThiDau, cauThuRa, cauThuVao)
                .stream()
                .findFirst()
                .orElse(null);

        if (thayNguoi != null) {
            throw new IllegalArgumentException("Thay nguoi da ton tai");
        }
        //VAO san DA
        //RA ngoai DU BI

        // CauThu da ra ko the vao nua
        thayNguoi= thayNguoiRepository.findByMaKetQuaAndMaCauThuRa(ketQuaThiDau, cauThuVao)
                .stream()
                .findFirst()
                .orElse(null);
        if(thayNguoi != null){
            throw new IllegalArgumentException("Cau thu " + cauThuVao.getTenCauThu() + "da ra ngoai");
        }

        // CauThu da ra ko the ra nua
        thayNguoi = thayNguoiRepository.findByMaKetQuaAndMaCauThuRa(ketQuaThiDau, cauThuVao)
                .stream()
                .findFirst()
                .orElse(null);
        if(thayNguoi != null){
            throw new IllegalArgumentException("Cau thu " + cauThuVao.getTenCauThu() + "da ra ngoai");
        }

        // Cau thu da vao ko the vao nua
        thayNguoi = thayNguoiRepository.findByMaKetQuaAndMaCauThuVao(ketQuaThiDau, cauThuVao)
                .stream()
                .findFirst()
                .orElse(null);
        if(thayNguoi != null){
            throw new IllegalArgumentException("Cau thu " + cauThuVao.getTenCauThu() + "da vao san");
        }

        // Thoi gian vao san phai nho hon thoi gian ra san
        thayNguoi = thayNguoiRepository.findByMaKetQuaAndMaCauThuVao(ketQuaThiDau, cauThuRa)
                .stream()
                .findFirst()
                .orElse(null);
        if(thayNguoi != null && thayNguoi.getThoiDiem() > thoiDiem){
            throw new IllegalArgumentException("Thoi diem cau thu ra ngoai phai lon hon thoi diem vao san");
        }

        // Validate that the substitution time is greater than the yellow card time
        List<ThePhat> theVangs = thePhatRepository.findByMaKetQua(ketQuaThiDau).stream()
                .filter(thePhat -> thePhat.getMaCauThu().equals(cauThuRa))
                .filter(thePhat -> thePhat.getLoaiThe().equalsIgnoreCase("the vang"))
                .sorted(Comparator.comparing(ThePhat::getThoiDiem).reversed())
                .toList();

        if (!theVangs.isEmpty() && theVangs.getFirst().getThoiDiem() > thoiDiem) {
            throw new IllegalArgumentException("Cau thu " + cauThuRa.getTenCauThu() + " da co the vang tai thoi diem lon hon thoi diem thay nguoi");
        }

        thePhatValidation(ketQuaThiDau, cauThuRa, thoiDiem, thePhatRepository,"thay nguoi");
        thePhatValidation(ketQuaThiDau, cauThuVao, thoiDiem, thePhatRepository, "thay nguoi");
    }

    public static void thePhatValidation(
            KetQuaThiDau ketQuaThiDau,
            CauThu cauThu,
            int thoiDiem,
            ThePhatRepository thePhatRepository,
            String type) {
        ThePhat theDo = thePhatRepository.findByMaKetQua(ketQuaThiDau).stream()
                .filter(thePhat -> thePhat.getMaCauThu().equals(cauThu))
                .filter(thePhat -> thePhat.getLoaiThe().equalsIgnoreCase("the do"))
                .findFirst()
                .orElse(null);

        if (theDo != null) {
            if(type.equals("the phat"))
                throw new IllegalArgumentException("Cau thu " + cauThu.getTenCauThu() + " da co the do");
            else if ((type.equals("ban thang") || type.equals("thay nguoi")) && theDo.getThoiDiem() < thoiDiem)
                throw new IllegalArgumentException("Cau thu " + cauThu.getTenCauThu() + " da co the do");
        }

        List<ThePhat> theVangs = thePhatRepository.findByMaKetQua(ketQuaThiDau).stream()
                .filter(thePhat -> thePhat.getMaCauThu().equals(cauThu))
                .filter(thePhat -> thePhat.getLoaiThe().equalsIgnoreCase("the vang"))
                .sorted(Comparator.comparing(ThePhat::getThoiDiem).reversed())
                .toList();

        if (theVangs.size() == 2) {
            if (type.equals("the phat"))
                throw new IllegalArgumentException("Cau thu " + cauThu.getTenCauThu() + " da co 2 the vang");
            else if ((type.equals("ban thang") || type.equals("thay nguoi")) && theVangs.getFirst().getThoiDiem() < thoiDiem)
                throw new IllegalArgumentException("Cau thu " + cauThu.getTenCauThu() + " da co 2 the vang");
        }
    }

}
