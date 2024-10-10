package com.uit.backendapi.doi_bong;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.uit.backendapi.Utils;
import com.uit.backendapi.doi_bong.dto.CreateDoiBongDto;
import com.uit.backendapi.doi_bong.dto.UpdateDoiBongDto;
import com.uit.backendapi.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class DoiBongService implements IDoiBongService {
    private final DoiBongRepository doiBongRepository;
    private final Cloudinary cloudinary;

    @Override
    public List<DoiBong> getAllDoiBong() {
        return doiBongRepository.findAll();
    }

    @Override
    public DoiBong getDoiBongById(Long id) {
        return doiBongRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Doi bong not found")
        );
    }

    @Override
    public DoiBong createDoiBong(CreateDoiBongDto createDoiBongDto) throws IOException {
        DoiBong doiBong = new DoiBong();
        doiBong.setAoChinhThuc("");
        doiBong.setAoDuBi("");
        BeanUtils.copyProperties(createDoiBongDto, doiBong);
        doiBong = doiBongRepository.save(doiBong);

        String folder = "doi-bong/" + doiBong.getId();
        String aoChinhThucUrl = uploadToCloudinary(createDoiBongDto.getAoChinhThuc().getBytes(), folder, doiBong.getId() + "_ao_chinh_thuc");
        String aoDuBiUrl = uploadToCloudinary(createDoiBongDto.getAoDuBi().getBytes(), folder, doiBong.getId() + "_ao_du_bi");

        doiBong.setAoChinhThuc(aoChinhThucUrl);
        doiBong.setAoDuBi(aoDuBiUrl);

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

        Utils.copyNonNullProperties(updateDoiBongDto, existingDoiBong, "id", "aoChinhThuc", "aoDuBi");

        String folder = "doi-bong/" + existingDoiBong.getId();

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
                doiBongRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("Doi bong not found");
                }
        );
    }
}
