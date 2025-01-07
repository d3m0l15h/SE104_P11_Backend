package com.uit.backendapi.cau_thu;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.uit.backendapi.Utils;
import com.uit.backendapi.cau_thu.dto.CauThuDto;
import com.uit.backendapi.cau_thu.dto.CreateCauThuDto;
import com.uit.backendapi.cau_thu.dto.FilterCauThuDto;
import com.uit.backendapi.cau_thu.dto.UpdateCauThuDto;
import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.doi_bong.DoiBongRepository;
import com.uit.backendapi.exceptions.ResourceNotFoundException;
import com.uit.backendapi.lich.LichThiDau;
import com.uit.backendapi.lich.LichThiDauRepository;
import com.uit.backendapi.qui_dinh.QuiDinh;
import com.uit.backendapi.qui_dinh.QuiDinhRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CauThuService implements ICauThuService {
    private final CauThuRepository cauThuRepository;
    private final DoiBongRepository doiBongRepository;
    private final Cloudinary cloudinary;
    private final QuiDinhRepository quiDinhRepository;
    private final LichThiDauRepository lichThiDauRepository;

    @Override
    public Page<CauThu> getAllCauThu(Pageable pageable) {
        return cauThuRepository.findAll(pageable);
    }

    @Override
    public Page<CauThu> filter(FilterCauThuDto filterCauThuDto, Pageable pageable) {
        List<CauThu> cauThus = cauThuRepository.findAll(pageable).stream()
                .filter(cauThu -> filterCauThuDto.getTenCauThu() == null
                        || cauThu.getTenCauThu().equals(filterCauThuDto.getTenCauThu()))
                .filter(cauThu -> filterCauThuDto.getNgaySinh() == null
                        || cauThu.getNgaySinh().equals(filterCauThuDto.getNgaySinh()))
                .filter(cauThu -> filterCauThuDto.getLoaiCauThu() == null
                        || cauThu.getLoaiCauThu().equals(filterCauThuDto.getLoaiCauThu()))
                .filter(cauThu -> filterCauThuDto.getMaDoi() == null
                        || cauThu.getMaDoi().getId().equals(filterCauThuDto.getMaDoi()))
                .filter(cauThu -> filterCauThuDto.getSoAo() == null
                        || cauThu.getSoAo().equals(filterCauThuDto.getSoAo()))
                .filter(cauThu -> filterCauThuDto.getViTri() == null
                        || cauThu.getViTri().equals(filterCauThuDto.getViTri()))
                .filter(cauThu -> filterCauThuDto.getNoiSinh() == null
                        || cauThu.getNoiSinh().equals(filterCauThuDto.getNoiSinh()))
                .filter(cauThu -> filterCauThuDto.getQuocTich() == null
                        || cauThu.getQuocTich().equals(filterCauThuDto.getQuocTich()))
                .filter(cauThu -> filterCauThuDto.getCanNang() == null
                        || cauThu.getCanNang().equals(filterCauThuDto.getCanNang()))
                .filter(cauThu -> filterCauThuDto.getChieuCao() == null
                        || cauThu.getChieuCao().equals(filterCauThuDto.getChieuCao()))
                .toList();

        return new PageImpl<>(cauThus, pageable, cauThus.size());
    }

    @Override
    public CauThu getCauThuById(Long id) {
        return cauThuRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cau thu not found")
        );
    }

    public List<CauThu> getCauThuByLichThiDauId(Long id) {
        LichThiDau lichThiDau = lichThiDauRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Lich thi dau not found")
        );

        List<CauThu> cauThuDoiNha = new java.util.ArrayList<>(lichThiDau.getMaDoiNha().getCauThus().stream().toList());

        List<CauThu> cauThuDoiKhach = lichThiDau.getMaDoiKhach().getCauThus().stream().toList();

        cauThuDoiNha.addAll(cauThuDoiKhach);

        return cauThuDoiNha;
    }

    @Override
    public CauThu createCauThu(CreateCauThuDto createCauThuDto) throws IOException {
        DoiBong doiBong = doiBongRepository.findById(createCauThuDto.getMaDoi()).orElseThrow(
                () -> new ResourceNotFoundException("Doi bong not found")
        );

        CauThu cauThu = new CauThu(createCauThuDto);
        cauThu.setAvatar("");
        cauThu.setMaDoi(doiBong);
        cauThuException(doiBong, cauThu);
        cauThu = cauThuRepository.save(cauThu);


        cauThuException(doiBong, cauThu);

        if (createCauThuDto.getAvatar() != null) {
            String folder = "cau-thu/" + cauThu.getId();

            String avatarUrl = uploadToCloudinary(createCauThuDto.getAvatar().getBytes(), folder, cauThu.getId() + "_avatar");

            cauThu.setAvatar(avatarUrl);
        }

        return cauThuRepository.save(cauThu);
    }

    private String uploadToCloudinary(byte[] fileBytes, String folder, String publicId) throws IOException {
        Map uploadParams = ObjectUtils.asMap(
                "folder", folder,
                "public_id", publicId,
                "use_filename", false,
                "unique_filename", false,
                "overwrite", true
        );

        Map uploadResult = cloudinary.uploader().upload(fileBytes, uploadParams);
        return (String) uploadResult.get("secure_url");
    }

    @Override
    public CauThu updateCauThu(Long id, UpdateCauThuDto updateCauThuDto) {
        return cauThuRepository.findById(id)
                .map(existingCauThu ->
                        updateExistingCauThu(existingCauThu, updateCauThuDto)
                )
                .map(cauThuRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Doi bong not found"));
    }

    private CauThu updateExistingCauThu(CauThu existingCauThu, UpdateCauThuDto updateCauThuDto) {
        Utils.copyNonNullProperties(updateCauThuDto, existingCauThu, "id", "maDoi", "avatar");
        String folder = "cau-thu/" + existingCauThu.getId();

        if (updateCauThuDto.getAvatar() != null) {
            try {
                String avatarUrl = uploadToCloudinary(updateCauThuDto.getAvatar().getBytes(), folder, existingCauThu.getId() + "_avatar");
                existingCauThu.setAvatar(avatarUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (updateCauThuDto.getMaDoi() != null) {
            DoiBong doiBong = doiBongRepository.findById(updateCauThuDto.getMaDoi()).orElseThrow(
                    () -> new ResourceNotFoundException("Doi bong not found")
            );

            // Check if the number of players in the team is less than the minimum number of players
            int soCauThuToiThieu = quiDinhRepository.findQuiDinhByTenQuiDinhLikeIgnoreCase("%CauThuToiThieu%")
                    .stream()
                    .findFirst()
                    .map(QuiDinh::getNoiDung)
                    .map(Integer::parseInt)
                    .orElse(19);

            if (existingCauThu.getMaDoi().getCauThus().size() <= soCauThuToiThieu) {
                throw new IllegalArgumentException("Doi bong phai co it nhat " + soCauThuToiThieu + " cau thu");
            }

            existingCauThu.setMaDoi(doiBong);
        }

        cauThuException(existingCauThu.getMaDoi(), existingCauThu);

        return existingCauThu;
    }

    private void cauThuException(DoiBong doiBong, CauThu cauThu) {
        int soCauThuToiDa = quiDinhRepository.findQuiDinhByTenQuiDinhLikeIgnoreCase("%CauThuToiDa%")
                .stream()
                .findFirst()
                .map(QuiDinh::getNoiDung)
                .map(Integer::parseInt)
                .orElse(22);

        int soCauThuNuocNgoaiToiDa = quiDinhRepository.findQuiDinhByTenQuiDinhLikeIgnoreCase("%CauThuNuocNgoaiToiDa%")
                .stream()
                .findFirst()
                .map(QuiDinh::getNoiDung)
                .map(Integer::parseInt)
                .orElse(3);

        int tuoiCauThuToiThieu = quiDinhRepository.findQuiDinhByTenQuiDinhLikeIgnoreCase("%TuoiToiThieu%")
                .stream()
                .findFirst()
                .map(QuiDinh::getNoiDung)
                .map(Integer::parseInt)
                .orElse(16);

        int tuoiCauThuToiDa = quiDinhRepository.findQuiDinhByTenQuiDinhLikeIgnoreCase("%TuoiToiDa%")
                .stream()
                .findFirst()
                .map(QuiDinh::getNoiDung)
                .map(Integer::parseInt)
                .orElse(40);

        if (doiBong.getCauThus().size() >= soCauThuToiDa) {
            throw new IllegalArgumentException("Doi bong da dat toi da so cau thu");
        }

        if (doiBong.getCauThus().stream().filter(ct -> ct.getLoaiCauThu().equalsIgnoreCase("Ngoai Nuoc")).count() >= soCauThuNuocNgoaiToiDa) {
            throw new IllegalArgumentException("Doi bong da dat toi da so cau thu Nuoc ngoai");
        }

        LocalDate today = LocalDate.now();
        int age = Period.between(cauThu.getNgaySinh(), today).getYears();

        if (age < tuoiCauThuToiThieu || age > tuoiCauThuToiDa) {
            throw new IllegalArgumentException("Tuoi cau thu khong hop le");
        }


    }

    @Override
    public void deleteCauThu(Long id) {
        CauThu cauThu = cauThuRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cau thu not found")
        );

        int soCauThuToiThieu = quiDinhRepository.findQuiDinhByTenQuiDinhLikeIgnoreCase("%CauThuToiThieu%")
                .stream()
                .findFirst()
                .map(QuiDinh::getNoiDung)
                .map(Integer::parseInt)
                .orElse(19);

        if (cauThu.getMaDoi().getCauThus().size() <= soCauThuToiThieu) {
            throw new IllegalArgumentException("Doi bong phai co it nhat " + soCauThuToiThieu + " cau thu");
        }
        cauThuRepository.deleteById(id);
    }
}
