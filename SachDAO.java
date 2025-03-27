/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Entity.Sach;
import java.sql.*;
import java.util.*;
import utils.JDBCHELPER;
/**
 *
 * @author ADMIN
 */
   public class SachDAO extends ThuVienDao<Sach, Integer> {
    String INSERT_SQL = "INSERT INTO SACH (ID_the_loai,ID_tac_gia,  ten_sach, the_loai, tac_gia, nam_xuat_ban, nha_xuat_ban, so_luong, trang_thai, hinh_anh) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE SACH SET ID_the_loai = ?, ID_tac_gia = ?, ten_sach = ?, the_loai = ?, tac_gia = ?, nam_xuat_ban = ?, nha_xuat_ban = ?, so_luong = ?, trang_thai = ?, hinh_anh = ? WHERE ID = ?";
    String DELETE_SQL = "DELETE FROM SACH WHERE ID = ?";
    String SELECT_ALL_SQL = "SELECT * FROM SACH";
    String SELECT_BY_ID_SQL = "SELECT * FROM SACH WHERE ID = ?";
    String SELECT_BY_KEYWORD_SQL = "SELECT * FROM SACH WHERE ten_sach LIKE ?";
    String UPDATE_QUANTITY_SQL = "UPDATE SACH SET so_luong = ? WHERE ID = ?";

    
    @Override
    public void insert(Sach entity) {
        JDBCHELPER.update(INSERT_SQL, entity.getIDTL(), entity.getIDTG(),  entity.getTenSach(), entity.getTheLoai(), entity.getTacGia(), 
                entity.getNamXuatBan(), entity.getNhaXuatBan(), entity.getSoLuong(), entity.isTrangThai(), entity.getHinhAnh());
    }

    @Override
public void update(Sach entity) {
    if (entity.getSoLuong() == 0) {
        entity.setTrangThai(false);
    } 
    JDBCHELPER.update(UPDATE_SQL,entity.getIDTL(), entity.getIDTG(), entity.getTenSach(), entity.getTheLoai(), entity.getTacGia(), 
                entity.getNamXuatBan(), entity.getNhaXuatBan(), entity.getSoLuong(), entity.isTrangThai(), entity.getHinhAnh(), entity.getID());
}

    
    @Override
    public void delete(Integer id) {
        JDBCHELPER.update(DELETE_SQL, id);
    }

    @Override
    public Sach selectById(Integer id) {
        List<Sach> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Sach> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

     protected List<Sach> selectBySql(String sql, Object... args) {
        List<Sach> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHELPER.query(sql, args);
            while (rs.next()) {
                Sach entity = new Sach();
                entity.setID(rs.getInt("ID"));
                entity.setIDTL(rs.getInt("ID_the_loai"));
                entity.setIDTG(rs.getInt("ID_tac_gia"));
                entity.setTenSach(rs.getString("ten_sach"));
                entity.setTheLoai(rs.getString("the_loai"));
                entity.setTacGia(rs.getString("tac_gia"));
                entity.setNamXuatBan(rs.getString("nam_xuat_ban"));
                entity.setNhaXuatBan(rs.getString("nha_xuat_ban"));
                entity.setSoLuong(rs.getInt("so_luong"));
                entity.setTrangThai(rs.getBoolean("trang_thai"));
                entity.setHinhAnh(rs.getString("hinh_anh"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    public List<Sach> selectByKeyword(String keyword) {
        return this.selectBySql(SELECT_BY_KEYWORD_SQL, "%" + keyword + "%");
    }
    
   public Sach selectByName(String name) {
        String sql = "SELECT * FROM SACH WHERE ten_sach = ?";
        List<Sach> list = this.selectBySql(sql, name);
        return list.isEmpty() ? null : list.get(0);
    }
        
   public void updateQuantity(int bookId, int newQuantity) {
        JDBCHELPER.update(UPDATE_QUANTITY_SQL, newQuantity, bookId);
    }
   
   public List<Sach> selectByTenSach(String name) {
    String sql = "SELECT * FROM SACH WHERE ten_sach LIKE ?";
    return this.selectBySql(sql, "%" + name + "%");
}

   public List<Sach> selectByTenTG(String TG) {
    String sql = "SELECT * FROM SACH WHERE tac_gia LIKE ?";
    return this.selectBySql(sql, "%" + TG + "%");
}
   
   public List<Sach> selectByTenTL(String TL) {
    String sql = "SELECT * FROM SACH WHERE the_loai LIKE ?";
    return this.selectBySql(sql, "%" + TL + "%");
}
}




