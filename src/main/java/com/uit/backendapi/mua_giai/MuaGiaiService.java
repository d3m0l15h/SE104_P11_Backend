package com.uit.backendapi.mua_giai;

import com.uit.backendapi.Utils;
import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.doi_bong.DoiBongRepository;
import com.uit.backendapi.exceptions.ResourceNotFoundException;
import com.uit.backendapi.mua_giai.dto.CreateMuaGiaiDto;
import com.uit.backendapi.mua_giai.dto.UpdateMuaGiaiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MuaGiaiService implements IMuaGiaiService {

    private final MuaGiaiRepository muaGiaiRepository;
    private final DoiBongRepository doiBongRepository;

    @Override
    public List<MuaGiai> getAllMuaGiai() {
        return muaGiaiRepository.findAll();
    }

    @Override
    public MuaGiai getMuaGiaiById(Long id) {
        return muaGiaiRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Mua giai not found with id: " + id)
        );
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
        muaGiaiRepository.deleteById(id);
    }
}
