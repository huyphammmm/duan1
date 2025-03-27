/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Entity.PhieuMuon;
import Entity.ThanhVien;
import java.util.*;
import java.sql.*;
import java.time.LocalDate;
import utils.JDBCHELPER;
import view_2.ThanhVienJPanel;

public class ThanhVienDAO {
    String INSERT_SQL = "INSERT INTO THANHVIEN ( ten_thanh_vien, nam_sinh, dia_chi, so_dien_thoai, email) VALUES (?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE THANHVIEN SET ten_thanh_vien = ?, nam_sinh = ?, dia_chi = ?, so_dien_thoai = ?, email = ? WHERE ID = ?";
    String DELETE_SQL = "DELETE FROM THANHVIEN WHERE ID = ?";
    String SELECT_ALL_SQL = "SELECT * FROM THANHVIEN";
    String SELECT_BY_ID_SQL = "SELECT * FROM THANHVIEN WHERE ID = ?";

    public void insert(ThanhVien entity) {
        JDBCHELPER.update(INSERT_SQL, entity.getTen_thanh_vien(), entity.getNam_sinh(), entity.getDia_chi(), entity.getSo_dien_thoai(), entity.getEmail());
    }

    
    public void update(ThanhVien entity) {
        JDBCHELPER.update(UPDATE_SQL, entity.getTen_thanh_vien(), entity.getNam_sinh(), entity.getDia_chi(), entity.getSo_dien_thoai(), entity.getEmail(), entity.getId());
    }

    
    public void delete(Integer id) {
        JDBCHELPER.update(DELETE_SQL, id);
    }

    
    public ThanhVien selectById(Integer id) {
        List<ThanhVien> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<ThanhVien> selectByKeyword(String keyword) {
    String sql = "SELECT * FROM THANHVIEN WHERE ID LIKE ?";
    return this.selectBySql(sql, "%" + keyword + "%");
    }

    
    public List<ThanhVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    
    protected List<ThanhVien> selectBySql(String sql, Object... args) {
        List<ThanhVien> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHELPER.query(sql, args);
            while (rs.next()) {
                ThanhVien entity = new ThanhVien();
                entity.setId(rs.getInt("ID"));
                entity.setTen_thanh_vien(rs.getString("ten_thanh_vien"));
                entity.setNam_sinh(rs.getString("nam_sinh"));
                entity.setDia_chi(rs.getString("dia_chi"));
                entity.setSo_dien_thoai(rs.getString("so_dien_thoai"));
                entity.setEmail(rs.getString("email"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public ThanhVien getThanhVienByName(String ten_thanh_vien) {
        String sql = "SELECT * FROM THANHVIEN WHERE ten_thanh_vien = ?";
        try (Connection con = JDBCHELPER.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, ten_thanh_vien);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                ThanhVien thanhVien = new ThanhVien();
                thanhVien.setId(rs.getInt("ID"));
                thanhVien.setTen_thanh_vien(rs.getString("ten_thanh_vien"));
                // Thiết lập các thuộc tính khác của thành viên
                return thanhVien;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<ThanhVien> selectByThanhVien(String name) {
    String sql = "SELECT * FROM THANHVIEN WHERE ten_thanh_vien LIKE ?";
    return this.selectBySql(sql, "%" + name + "%");
}
    
    public ThanhVien selectByName(String name) {
        String sql = "SELECT * FROM THANHVIEN WHERE ten_thanh_vien = ?";
        List<ThanhVien> list = this.selectBySql(sql, name);
        return list.isEmpty() ? null : list.get(0);
    }
    
    public List<ThanhVien> selectByID(int id) {
        String sql = "SELECT * FROM THANHVIEN WHERE ID = ?";
         return this.selectBySql(sql, id); 
    }
    public List<ThanhVien> selectBySDT(String sdt) {
    String sql = "SELECT * FROM THANHVIEN WHERE so_dien_thoai LIKE ?";
    return this.selectBySql(sql,"%" + sdt + "%");
}
    public List<ThanhVien> selectByID2(String id) {
    String sql = "SELECT * FROM THANHVIEN WHERE ID LIKE ?";
    return this.selectBySql(sql,"%" + id + "%");
}
}
