package DAO;

import Entity.PhieuMuon;
import Entity.ThanhVien;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import utils.JDBCHELPER;

public class PhieuMuonDAO extends ThuVienDao<PhieuMuon, Integer> {

    String INSERT_SQL = "INSERT INTO PHIEUMUON (ID_nhan_vien, ID_thanh_vien, ten_nhan_vien, ten_thanh_vien, ngay_tao_phieu) values (?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE PHIEUMUON SET ten_nhan_vien = ?, ten_thanh_vien = ?, ngay_tao_phieu = ? WHERE ID = ?";
    String DELETE_SQL = "DELETE FROM PHIEUMUON WHERE ID = ?";
    String SELECT_ALL_SQL = "SELECT * FROM PHIEUMUON";
    String SELECT_BY_ID_SQL = "SELECT * FROM PHIEUMUON WHERE ID = ?";

    @Override
    public void insert(PhieuMuon entity) {
        Date ngayTao = Date.valueOf(entity.getNgayTaoPhieu());
        JDBCHELPER.update(INSERT_SQL, entity.getIDNV(), entity.getIDTV(), entity.getTenNhanVien(), entity.getTenThanhVien(), ngayTao);
    }

    @Override
    public void update(PhieuMuon entity) {
        Date ngayTao = Date.valueOf(entity.getNgayTaoPhieu());
        JDBCHELPER.update(UPDATE_SQL, entity.getTenNhanVien(), entity.getTenThanhVien(), ngayTao, entity.getID());
    }

    @Override
    public void delete(Integer id) {
        JDBCHELPER.update(DELETE_SQL, id);
    }

    @Override
    public PhieuMuon selectById(Integer id) {
        List<PhieuMuon> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<PhieuMuon> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<PhieuMuon> selectBySql(String sql, Object... args) {
        List<PhieuMuon> list = new ArrayList<>();
        try (var rs = JDBCHELPER.query(sql, args)) {
            while (rs.next()) {
                PhieuMuon entity = new PhieuMuon();
                entity.setID(rs.getInt("ID"));
                entity.setIDTV(rs.getInt("ID_thanh_vien"));
                entity.setIDNV(rs.getInt("ID_nhan_vien"));
                entity.setTenNhanVien(rs.getString("ten_nhan_vien"));
                entity.setTenThanhVien(rs.getString("ten_thanh_vien"));
                Date sqlDate = rs.getDate("ngay_tao_phieu");
                if (sqlDate != null) {
                    entity.setNgayTaoPhieu(sqlDate.toLocalDate());
                } else {
                    entity.setNgayTaoPhieu(null);
                }
                list.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<PhieuMuon> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM PHIEUMUON WHERE ID LIKE ?";
        return this.selectBySql(sql, "%" + keyword + "%");
    }

    public List<PhieuMuon> selectByDate(LocalDate date) {
        String sql = "SELECT * FROM PHIEUMUON WHERE ngay_tao_phieu = ?";
        return this.selectBySql(sql, Date.valueOf(date));
    }
    
    

    
}
