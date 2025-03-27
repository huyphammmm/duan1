/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.ChiTietPhieuMuon;
import Entity.Sach;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import utils.JDBCHELPER;
import utils.MsgBox;

/**
 *
 * @author ADMIN
 */
public class ChiTietPhieuMuonDAO extends ThuVienDao<ChiTietPhieuMuon, Integer> {

    String INSERT_SQL = "INSERT INTO CHITIETPHIEUMUON ( ID_phieu_muon, ID_sach ,ten_sach, so_luong, ngay_muon, ngay_tra, ngay_tra_thuc_te, trang_thai) values (?, ?, ?, ?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE CHITIETPHIEUMUON SET ID_phieu_muon = ?, ID_sach  = ?,ten_sach = ?, so_luong = ?, ngay_muon = ?, ngay_tra = ?, ngay_tra_thuc_te = ?, trang_thai = ? WHERE ID = ?";
    String DELETE_SQL = "DELETE FROM CHITIETPHIEUMUON WHERE ID = ?";
    String SELECT_ALL_SQL = "SELECT * FROM CHITIETPHIEUMUON";
    String SELECT_BY_ID_SQL = "SELECT * FROM CHITIETPHIEUMUON WHERE ID = ?";
    String SELECT_BY_PHIEU_ID_SQL = "SELECT * FROM CHITIETPHIEUMUON WHERE ID_phieu_muon = ?";
    String EXTEND_LOAN_SQL = "UPDATE CHITIETPHIEUMUON SET ngay_tra = ? WHERE ID = ?";
    SachDAO sachDAO = new SachDAO();

    @Override
    public void insert(ChiTietPhieuMuon entity) {
    Sach sach = sachDAO.selectById(entity.getIdSach());
    if (sach == null) {
        MsgBox.alert(null, "Không tìm thấy sách");
        return;
    }

    int newQuantity = sach.getSoLuong() - entity.getSoLuong();
    if (sach.getSoLuong() <= 0) {
        MsgBox.alert(null, "Sách đã hết, không thể mượn thêm");
        return;
    }
    if (newQuantity < 0) {
        MsgBox.alert(null, "Không đủ sách để mượn");
        return;
    }

    try {
        LocalDate ngayMuon = LocalDate.now();
        LocalDate ngayTra = ngayMuon.plusDays(7);
        Date sqlNgayMuon = Date.valueOf(ngayMuon);
        Date sqlNgayTra = Date.valueOf(ngayTra);
        JDBCHELPER.update(INSERT_SQL, entity.getIdPhieu(), entity.getIdSach(), entity.getTenSach(), entity.getSoLuong(), sqlNgayMuon, sqlNgayTra, null, entity.isTrangThai());
        sachDAO.updateQuantity(sach.getID(), newQuantity);
        MsgBox.alert(null, "Thêm mới thành công!");
    } catch (Exception e) {
        MsgBox.alert(null, "Thêm mới thất bại: " + e.getMessage());
        e.printStackTrace();
    }
}

    
    @Override
    public void update(ChiTietPhieuMuon entity) {
        Date NgayMuon = Date.valueOf(entity.getNgayMuon());
        ChiTietPhieuMuon existingEntity = selectById(entity.getID());
        if (!existingEntity.isTrangThai() && entity.isTrangThai()) {
            LocalDate ngayTraThucTe = LocalDate.now();
            entity.setNgay_tra_thuc_te(ngayTraThucTe.toString());
            Sach sach = sachDAO.selectById(entity.getIdSach());
            if (sach != null) {
                int newQuantity = sach.getSoLuong() + entity.getSoLuong();
                sachDAO.updateQuantity(sach.getID(), newQuantity);
            }
        }

        JDBCHELPER.update(UPDATE_SQL, entity.getIdPhieu(), entity.getIdSach(), entity.getTenSach(), entity.getSoLuong(), NgayMuon, entity.getNgayTra(), entity.getNgay_tra_thuc_te(), entity.isTrangThai(), entity.getID());
    }

    @Override
    public void delete(Integer id) {
        JDBCHELPER.update(DELETE_SQL, id);
    }
    

    @Override
    public ChiTietPhieuMuon selectById(Integer id) {
        List<ChiTietPhieuMuon> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
}

    @Override
    public List<ChiTietPhieuMuon> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<ChiTietPhieuMuon> selectBySql(String sql, Object... args) {
        List<ChiTietPhieuMuon> list = new ArrayList<>();
        try (ResultSet rs = JDBCHELPER.query(sql, args)) {
            while (rs.next()) {
                ChiTietPhieuMuon entity = new ChiTietPhieuMuon();
                entity.setID(rs.getInt("ID"));
                entity.setIdPhieu(rs.getInt("ID_phieu_muon"));
                entity.setIdSach(rs.getInt("ID_sach"));
                entity.setTenSach(rs.getString("ten_sach"));
                entity.setSoLuong(rs.getInt("so_luong"));
                Date sqlDate = rs.getDate("ngay_muon");
                if (sqlDate != null) {
                    entity.setNgayMuon(sqlDate.toLocalDate());
                } else {
                    entity.setNgayMuon(null); 
                }

                entity.setNgayTra(rs.getString("ngay_tra"));
                entity.setNgay_tra_thuc_te(rs.getString("ngay_tra_thuc_te"));
                entity.setTrangThai(rs.getBoolean("trang_thai"));
                list.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<ChiTietPhieuMuon> selectByPhieuMuonId(int idPM) {
        return selectBySql(SELECT_BY_PHIEU_ID_SQL, idPM);
    }
    public void extendLoan(int chiTietPhieuMuonId, int extraDays) {
        ChiTietPhieuMuon chiTietPhieuMuon = selectById(chiTietPhieuMuonId);
        if (chiTietPhieuMuon == null) {
            MsgBox.alert(null, "Không tìm thấy chi tiết phiếu mượn");
            return;
        }
        try {
            LocalDate ngayTra = LocalDate.parse(chiTietPhieuMuon.getNgayTra()).plusDays(extraDays);
            Date sqlNgayTra = Date.valueOf(ngayTra);
            JDBCHELPER.update(EXTEND_LOAN_SQL, sqlNgayTra, chiTietPhieuMuonId);
            MsgBox.alert(null, "Gia hạn mượn sách thành công!");
        } catch (Exception e) {
            MsgBox.alert(null, "Gia hạn mượn sách thất bại: " + e.getMessage());
            e.printStackTrace();
        }
    }
      public ChiTietPhieuMuon searchById(Integer id) {
        return selectById(id);
    }
}

