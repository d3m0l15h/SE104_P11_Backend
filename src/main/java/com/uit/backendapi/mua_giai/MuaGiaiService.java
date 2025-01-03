package com.uit.backendapi.mua_giai;

import com.uit.backendapi.Utils;
import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.doi_bong.DoiBongRepository;
import com.uit.backendapi.exceptions.ResourceNotFoundException;
import com.uit.backendapi.lich.LichThiDau;
import com.uit.backendapi.lich.LichThiDauRepository;
import com.uit.backendapi.mua_giai.dto.CreateMuaGiaiDto;
import com.uit.backendapi.mua_giai.dto.FilterMuaGiaiDto;
import com.uit.backendapi.mua_giai.dto.UpdateMuaGiaiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MuaGiaiService implements IMuaGiaiService {

    private final MuaGiaiRepository muaGiaiRepository;
    private final DoiBongRepository doiBongRepository;

    @Override
    public Page<MuaGiai> getAllMuaGiai(Pageable pageable) {
        return muaGiaiRepository.findAll(pageable);
    }

    @Override
    public Page<MuaGiai> filter(FilterMuaGiaiDto filterMuaGiaiDto, Pageable pageable) {
        List<MuaGiai> filteredList = muaGiaiRepository.findAll(pageable).stream()
                .filter(muaGiai -> filterMuaGiaiDto.getNam() == null
                        || muaGiai.getNam().equals(filterMuaGiaiDto.getNam())
                )
                .filter(muaGiai -> filterMuaGiaiDto.getDoiVoDich() == null
                        || muaGiai.getDoiVoDich().getId().equals(filterMuaGiaiDto.getDoiVoDich())
                )
                .filter(muaGiai -> filterMuaGiaiDto.getNgayBatDau() == null
                        || muaGiai.getNgayBatDau().equals(filterMuaGiaiDto.getNgayBatDau())
                )
                .filter(muaGiai -> filterMuaGiaiDto.getNgayKetThuc() == null
                        || muaGiai.getNgayKetThuc().equals(filterMuaGiaiDto.getNgayKetThuc())
                ).toList();

        return new PageImpl<>(filteredList, pageable, filteredList.size());
    }

    @Override
    public MuaGiai createMuaGiai(CreateMuaGiaiDto createMuaGiaiDto) {
        MuaGiai muaGiai = new MuaGiai(createMuaGiaiDto);
        return muaGiaiRepository.save(muaGiai);
    }

    @Override
    public MuaGiai updateMuaGiai(Long id, UpdateMuaGiaiDto updateMuaGiaiDto) {
        return muaGiaiRepository.findById(id)
                .map(existingMuaGiai -> updateExistingMuaGiai(existingMuaGiai, updateMuaGiaiDto))
                .map(muaGiaiRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Mua giai not found with id: " + id));
    }

    private MuaGiai updateExistingMuaGiai(MuaGiai existingMuaGiai, UpdateMuaGiaiDto updateMuaGiaiDto) {
        Utils.copyNonNullProperties(updateMuaGiaiDto, existingMuaGiai, "id", "maDoiVoDich");

        if(updateMuaGiaiDto.getDoiVoDich() != null) {
            DoiBong doiVoDich = doiBongRepository.findById(updateMuaGiaiDto.getDoiVoDich()).orElseThrow(
                    () -> new ResourceNotFoundException("Doi bong not found with id: " + updateMuaGiaiDto.getDoiVoDich())
            );
            existingMuaGiai.setDoiVoDich(doiVoDich);
        }

        return existingMuaGiai;
    }

    @Override
    public MuaGiai getMuaGiaiById(Long id) {
        return muaGiaiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mua giai not found with id: " + id));
    }

    @Override
    public void deleteMuaGiai(Long id) {
        muaGiaiRepository.findById(id).ifPresentOrElse(
                muaGiaiRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("Mua giai not found with id: " + id);
                }
        );
    }
}
