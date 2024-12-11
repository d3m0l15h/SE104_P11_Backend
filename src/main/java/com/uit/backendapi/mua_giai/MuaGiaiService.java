package com.uit.backendapi.mua_giai;

import com.uit.backendapi.Utils;
import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.doi_bong.DoiBongRepository;
import com.uit.backendapi.exceptions.ResourceNotFoundException;
import com.uit.backendapi.mua_giai.dto.CreateMuaGiaiDto;
import com.uit.backendapi.mua_giai.dto.FilterMuaGiaiDto;
import com.uit.backendapi.mua_giai.dto.UpdateMuaGiaiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MuaGiaiService implements IMuaGiaiService {

    private final MuaGiaiRepository muaGiaiRepository;
    private final DoiBongRepository doiBongRepository;

    @Override
    public Page<MuaGiai> getAllMuaGiai(Pageable pageable) {
        List<MuaGiai> muaGiais = muaGiaiRepository.findAll();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), muaGiais.size());
        return new PageImpl<>(muaGiais.subList(start, end), pageable, muaGiais.size());
    }

    @Override
    public Page<MuaGiai> filter(FilterMuaGiaiDto filterMuaGiaiDto, Pageable pageable) {
        List<MuaGiai> filteredList = muaGiaiRepository.findAll().stream()
                .filter(muaGiai -> filterMuaGiaiDto.getNam() == null
                        || muaGiai.getNam().equals(filterMuaGiaiDto.getNam())
                )
                .filter(muaGiai -> filterMuaGiaiDto.getMaDoiVoDich() == null
                        || muaGiai.getDoiVoDich().getId().equals(filterMuaGiaiDto.getMaDoiVoDich())
                )
                .toList();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredList.size());
        return new PageImpl<>(filteredList.subList(start, end), pageable, filteredList.size());
    }

    @Override
    public MuaGiai createMuaGiai(CreateMuaGiaiDto createMuaGiaiDto) {
        MuaGiai muaGiai = new MuaGiai(
                createMuaGiaiDto.getNam()
        );

        createMuaGiaiDto.getDoiVoDich().ifPresent(
                doiVoDichId -> {
                    DoiBong doiVoDich = doiBongRepository.findById(doiVoDichId).orElseThrow(
                            () -> new ResourceNotFoundException("Doi bong not found with id: " + doiVoDichId)
                    );
                    muaGiai.setDoiVoDich(doiVoDich);
                }
        );

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
        Utils.copyNonNullProperties(updateMuaGiaiDto, existingMuaGiai, "id");

        updateMuaGiaiDto.getDoiVoDich().ifPresent(doiVoDichId -> {
            DoiBong doiVoDich = doiBongRepository.findById(doiVoDichId).orElseThrow(
                    () -> new ResourceNotFoundException("Doi bong not found with id: " + doiVoDichId)
            );
            existingMuaGiai.setDoiVoDich(doiVoDich);
        });

        return existingMuaGiai;
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
