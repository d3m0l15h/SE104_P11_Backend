package com.uit.backendapi.the_phat;

import com.uit.backendapi.ket_qua.KetQuaThiDau;
import com.uit.backendapi.the_phat.dto.CreateThePhatDto;
import com.uit.backendapi.the_phat.dto.UpdateThePhatDto;

import java.util.List;

public interface IThePhatService {
    List<ThePhat> getThePhatByMaKetQua(KetQuaThiDau maKetQua);
    ThePhat createThePhatByMaKetQua(KetQuaThiDau ketQuaThiDau, CreateThePhatDto createThePhatDto);
    ThePhat updateThePhatByMaKetQuaAndId(KetQuaThiDau ketQuaThiDau, Long id, UpdateThePhatDto updateThePhatDto);
    void deleteThePhatByMaKetQuaAndId(KetQuaThiDau ketQuaThiDau, Long id);
}
