package com.uit.backendapi.bxh;

import com.uit.backendapi.bxh.dto.CreateBxhDto;
import com.uit.backendapi.bxh.dto.UpdateBxhDto;
import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.mua_giai.MuaGiai;

import java.util.List;

public interface IBangXepHangService {
    List<BangXepHang> getBangXepHangByMuaGiai(Long muaGiai);
    List<BangXepHang> getBangXepHangByMuaGiai(MuaGiai muaGiai);
    BangXepHang getBangXepHangByMaDoiAndMuaGiai(Long maDoi, Long muaGiai);
    BangXepHang getBangXepHangByMaDoiAndMuaGiai(DoiBong doiBong, MuaGiai muaGiai);
    BangXepHang createBangXepHang(CreateBxhDto createBxhDto);
    void createBangXepHang(DoiBong doiBong, MuaGiai muaGiai);
    BangXepHang updateBangXepHang(Long id, UpdateBxhDto updateBxhDto);
    void deleteBangXepHang(Long id);
}
