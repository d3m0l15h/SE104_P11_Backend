package com.uit.backendapi.doi_bong;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.uit.backendapi.Utils;
import com.uit.backendapi.bxh.BangXepHang;
import com.uit.backendapi.bxh.BangXepHangRepository;
import com.uit.backendapi.bxh.BangXepHangService;
import com.uit.backendapi.doi_bong.dto.CreateDoiBongDto;
import com.uit.backendapi.doi_bong.dto.FilterDoiBongDto;
import com.uit.backendapi.doi_bong.dto.UpdateDoiBongDto;
import com.uit.backendapi.exceptions.ResourceNotFoundException;
import com.uit.backendapi.mua_giai.MuaGiai;
import com.uit.backendapi.mua_giai.MuaGiaiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DoiBongService implements IDoiBongService {
    private final DoiBongRepository doiBongRepository;
    private final BangXepHangRepository bangXepHangRepository;
    private final MuaGiaiRepository muaGiaiRepository;
    private final Cloudinary cloudinary;

    @Override
    public Page<DoiBong> getAllDoiBong(Pageable pageable) {
        return doiBongRepository.findAll(pageable);
    }

    @Override
    public DoiBong getDoiBongById(Long id) {
        return doiBongRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Doi bong not found")
        );
    }

    @Override
    public List<DoiBong> getDoiBongByMuaGiaiId(Long id) {
        MuaGiai muaGiai = muaGiaiRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Mua giai not found")
        );

        return bangXepHangRepository.findByMaMuaGiai(muaGiai).stream()
                .map(BangXepHang::getMaDoi)
                .toList();
    }


    @Override
    public Page<DoiBong> filter(FilterDoiBongDto filterDoiBongDto, Pageable pageable) {
        List<DoiBong> doiBongs = doiBongRepository.findAll(pageable).stream()
                .filter(doiBong -> filterDoiBongDto.getTenDoi() == null
                        || doiBong.getTenDoi().equals(filterDoiBongDto.getTenDoi()))
                .filter(doiBong -> filterDoiBongDto.getTenSanNha() == null
                        || doiBong.getTenSanNha().equals(filterDoiBongDto.getTenSanNha()))
                .filter(doiBong -> filterDoiBongDto.getDiaChiSanNha() == null
                        || doiBong.getDiaChiSanNha().equals(filterDoiBongDto.getDiaChiSanNha()))
                .filter(doiBong -> filterDoiBongDto.getDienThoai() == null
                        || doiBong.getDienThoai().equals(filterDoiBongDto.getDienThoai()))
                .filter(doiBong -> filterDoiBongDto.getEmail() == null
                        || doiBong.getEmail().equals(filterDoiBongDto.getEmail()))
                .filter(doiBong -> filterDoiBongDto.getToChucQuanLy() == null
                        || doiBong.getToChucQuanLy().equals(filterDoiBongDto.getToChucQuanLy()))
                .filter(doiBong -> filterDoiBongDto.getThanhPhoTrucThuoc() == null
                        || doiBong.getThanhPhoTrucThuoc().equals(filterDoiBongDto.getThanhPhoTrucThuoc()))
                .toList();

        return new PageImpl<>(doiBongs, pageable, doiBongs.size());
    }

    @Override
    public DoiBong createDoiBong(CreateDoiBongDto createDoiBongDto) throws IOException {
        DoiBong doiBong = new DoiBong();
        doiBong.setLogo("");
        doiBong.setAoChinhThuc("");
        doiBong.setAoDuBi("");
        BeanUtils.copyProperties(createDoiBongDto, doiBong);
        doiBong = doiBongRepository.save(doiBong);

        String folder = "doi-bong/" + doiBong.getId();

        if (createDoiBongDto.getLogo() != null) {
            String logoUrl = uploadToCloudinary(createDoiBongDto.getLogo().getBytes(), folder, doiBong.getId() + "_logo");
            doiBong.setLogo(logoUrl);
        }

        if (createDoiBongDto.getAoChinhThuc() != null) {
            String aoChinhThucUrl = uploadToCloudinary(createDoiBongDto.getAoChinhThuc().getBytes(), folder, doiBong.getId() + "_ao_chinh_thuc");
            doiBong.setAoChinhThuc(aoChinhThucUrl);
        }

        if (createDoiBongDto.getAoDuBi() != null) {
            String aoDuBiUrl = uploadToCloudinary(createDoiBongDto.getAoDuBi().getBytes(), folder, doiBong.getId() + "_ao_du_bi");
            doiBong.setAoDuBi(aoDuBiUrl);
        }

        return doiBongRepository.save(doiBong);
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
    public DoiBong updateDoiBong(Long id, UpdateDoiBongDto updateDoiBongDto) {
        return doiBongRepository.findById(id)
                .map(existingDoiBong -> {
                    try {
                        return updateExistingDoiBong(existingDoiBong, updateDoiBongDto);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(doiBongRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Doi bong not found"));
    }

    private DoiBong updateExistingDoiBong(DoiBong existingDoiBong, UpdateDoiBongDto updateDoiBongDto) throws IOException {

        Utils.copyNonNullProperties(updateDoiBongDto, existingDoiBong, "id", "aoChinhThuc", "aoDuBi", "logo");

        String folder = "doi-bong/" + existingDoiBong.getId();

        if (updateDoiBongDto.getLogo() != null) {
            String logoUrl = uploadToCloudinary(updateDoiBongDto.getLogo().getBytes(), folder, existingDoiBong.getId() + "_logo");
            existingDoiBong.setLogo(logoUrl);
        }

        if (updateDoiBongDto.getAoChinhThuc() != null) {
            String aoChinhThucUrl = uploadToCloudinary(updateDoiBongDto.getAoChinhThuc().getBytes(), folder, existingDoiBong.getId() + "_ao_chinh_thuc");
            existingDoiBong.setAoChinhThuc(aoChinhThucUrl);
        }

        if (updateDoiBongDto.getAoDuBi() != null) {
            String aoDuBiUrl = uploadToCloudinary(updateDoiBongDto.getAoDuBi().getBytes(), folder, existingDoiBong.getId() + "_ao_du_bi");
            existingDoiBong.setAoDuBi(aoDuBiUrl);
        }

        return existingDoiBong;
    }

    @Override
    public void deleteDoiBong(Long id) {
        doiBongRepository.findById(id).ifPresentOrElse(
                doiBong -> {
                    try {
                        deleteCloudinaryFolder("doi-bong/" + doiBong.getId());
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to delete Cloudinary folder", e);
                    }
                    doiBongRepository.delete(doiBong);
                },
                () -> {
                    throw new ResourceNotFoundException("Doi bong not found");
                }
        );
    }

    private void deleteCloudinaryFolder(String folderPath) throws Exception {
        Map deleteParams = ObjectUtils.asMap(
                "resource_type", "image",
                "type", "upload",
                "prefix", folderPath
        );
        cloudinary.api().deleteResourcesByPrefix(folderPath, deleteParams);
        cloudinary.api().deleteFolder(folderPath, ObjectUtils.emptyMap());
    }
}
