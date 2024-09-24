package com.uit.backendapi.mua_giai;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MuaGiaiService {
    private final MuaGiaiRepository muaGiaiRepository;

    public MuaGiaiService(MuaGiaiRepository muaGiaiRepository) {
        this.muaGiaiRepository = muaGiaiRepository;
    }

    public List<MuaGiai> getAllMuaGiai() {
        return muaGiaiRepository.findAll();
    }

    public MuaGiai getMuaGiaiById(Long id) {
        return muaGiaiRepository.findById(id).orElse(null);
    }

    public MuaGiai createMuaGiai(MuaGiai muaGiai) {
        return muaGiaiRepository.save(muaGiai);
    }
}
