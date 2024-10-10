package com.uit.backendapi.the_phat;

import com.uit.backendapi.Utils;
import com.uit.backendapi.cau_thu.CauThu;
import com.uit.backendapi.cau_thu.CauThuRepository;
import com.uit.backendapi.exceptions.ResourceNotFoundException;
import com.uit.backendapi.ket_qua.KetQuaThiDau;
import com.uit.backendapi.the_phat.dto.CreateThePhatDto;
import com.uit.backendapi.the_phat.dto.UpdateThePhatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThePhatService implements IThePhatService {
    private final ThePhatRepository thePhatRepository;
    private final CauThuRepository cauThuRepository;

    @Override
    public List<ThePhat> getThePhatByMaKetQua(KetQuaThiDau maKetQua) {
        return thePhatRepository.findByMaKetQua(maKetQua);
    }

    @Override
    public ThePhat createThePhatByMaKetQua(KetQuaThiDau ketQuaThiDau, CreateThePhatDto createThePhatDto) {
        CauThu cauThu = cauThuRepository.findById(createThePhatDto.getMaCauThu())
                .orElseThrow(() -> new ResourceNotFoundException("Cau thu not found with id: " + createThePhatDto.getMaCauThu()));

        ThePhat thePhat = new ThePhat(
                cauThu,
                createThePhatDto.getLoaiThe(),
                createThePhatDto.getThoiDiem(),
                ketQuaThiDau
        );

        return thePhatRepository.save(thePhat);
    }

    @Override
    public ThePhat updateThePhatByMaKetQuaAndId(KetQuaThiDau ketQuaThiDau, Long id, UpdateThePhatDto updateThePhatDto) {
        return thePhatRepository.findByIdAndMaKetQua(Math.toIntExact(id), ketQuaThiDau)
                .map(existingThePhat -> updateExistingThePhat(existingThePhat, updateThePhatDto))
                .map(thePhatRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("The phat not found with id: " + id));
    }

    private ThePhat updateExistingThePhat(ThePhat existingThePhat, UpdateThePhatDto updateThePhatDto) {
        Utils.copyNonNullProperties(updateThePhatDto, existingThePhat, "id", "maCauThu", "maKetQua");

        if (updateThePhatDto.getMaCauThu() != null) {
            CauThu cauThu = cauThuRepository.findById(updateThePhatDto.getMaCauThu())
                    .orElseThrow(() -> new ResourceNotFoundException("Cau thu not found with id: " + updateThePhatDto.getMaCauThu()));
            existingThePhat.setMaCauThu(cauThu);
        }

        return existingThePhat;
    }

    @Override
    public void deleteThePhatByMaKetQuaAndId(KetQuaThiDau ketQuaThiDau, Long id) {
        thePhatRepository.findByIdAndMaKetQua(Math.toIntExact(id), ketQuaThiDau).ifPresentOrElse(
                thePhatRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("The phat not found with id: " + id);
                }
        );
    }
}
