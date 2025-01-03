package com.uit.backendapi.qui_dinh;

import com.uit.backendapi.qui_dinh.dto.UpsertQuiDinhDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuiDinhService implements IQuiDinhService {
    private final QuiDinhRepository quiDinhRepository;

    @Override
    public List<QuiDinh> getQuiDinh() {
        return quiDinhRepository.findAll();
    }

    @Override
    public List<QuiDinh> upsertQuiDinh(UpsertQuiDinhDto upsertQuiDinhDto) {
        return Arrays.stream(upsertQuiDinhDto.getQuiDinhDtos())
                .map(quiDinhDto -> {
                    QuiDinh quiDinh = quiDinhRepository.findQuiDinhByTenQuiDinhLikeIgnoreCase(quiDinhDto.getTenQuiDinh()).stream()
                            .findFirst()
                            .map(quiDinh1 -> {
                                quiDinh1.setNoiDung(quiDinhDto.getNoiDung());
                                return quiDinh1;
                            })
                            .orElseGet(() -> {
                                QuiDinh quiDinh1 = new QuiDinh();
                                quiDinh1.setNoiDung(quiDinhDto.getNoiDung());
                                quiDinh1.setTenQuiDinh(quiDinhDto.getTenQuiDinh());
                                return quiDinh1;
                            });
                    return quiDinhRepository.save(quiDinh);
                })
                .toList();
    }


    @Override
    public void deleteQuiDinh(Long id) {
        quiDinhRepository.deleteById(Math.toIntExact(id));
    }


}
