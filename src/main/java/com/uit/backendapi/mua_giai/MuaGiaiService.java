package com.uit.backendapi.mua_giai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MuaGiaiService {

    private final MuaGiaiRepository muaGiaiRepository;

    @Autowired
    public MuaGiaiService(MuaGiaiRepository muaGiaiRepository) {
        this.muaGiaiRepository = muaGiaiRepository;
    }

    public List<MuaGiai> getAllMuaGiai() {
        return muaGiaiRepository.findAll();
    }

    public MuaGiai getMuaGiaiById(Long id) {
        return muaGiaiRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Mua giai not found with id: " + id)
        );
    }

    public MuaGiai createMuaGiai(MuaGiai muaGiai) {
        return muaGiaiRepository.save(muaGiai);
    }

    public MuaGiai updateMuaGiai(Long id, MuaGiai muaGiaiDto) {
        return muaGiaiRepository.findById(id)
                .map(muaGiai -> {
                    muaGiai.setNam(muaGiaiDto.getNam());
                    muaGiai.setDoiVoDich(muaGiaiDto.getDoiVoDich());
                    return muaGiaiRepository.save(muaGiai);
                })
                .orElseGet(() -> muaGiaiRepository.save(muaGiaiDto));
    }

    public void deleteMuaGiai(Long id) {
        muaGiaiRepository.deleteById(id);
    }
}
