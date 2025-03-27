/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.TacGia;
import java.sql.*;
import java.util.*;
import utils.JDBCHELPER;

/**
 *
 * @author ADMIN
 */

public class TacGiaDAO extends ThuVienDao<TacGia, Integer> {
    String INSERT_SQL = "INSERT INTO TACGIA (tac_gia, nam_sinh, quoc_tich) values (?,?,?)";
    String UPDATE_SQL = "UPDATE TACGIA SET tac_gia = ?, nam_sinh = ?, quoc_tich = ? WHERE ID = ?";
    String DELETE_SQL = "DELETE FROM TACGIA WHERE ID = ?";
    String SELECT_ALL_SQL = "SELECT * FROM TACGIA";
    String SELECT_BY_ID_SQL = "SELECT * FROM TACGIA WHERE ID = ?";

    @Override
    public void insert(TacGia entity) {
        JDBCHELPER.update(INSERT_SQL, entity.getTenTacGia(), entity.getNamSinh(), entity.getQuocTich());
    }

    @Override
    public void update(TacGia entity) {
        JDBCHELPER.update(UPDATE_SQL, entity.getTenTacGia(), entity.getNamSinh(), entity.getQuocTich(), entity.getID());
    }

    @Override
    public void delete(Integer id) {
        JDBCHELPER.update(DELETE_SQL, id);
    }

    @Override
    public TacGia selectById(Integer id) {
        List<TacGia> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<TacGia> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<TacGia> selectBySql(String sql, Object... args) {
        List<TacGia> list = new ArrayList<>();
        try (ResultSet rs = JDBCHELPER.query(sql, args)) {
            while (rs.next()) {
                TacGia entity = new TacGia();
                entity.setID(rs.getInt("ID"));
                entity.setTenTacGia(rs.getString("tac_gia"));
                entity.setNamSinh(rs.getString("nam_sinh"));
                entity.setQuocTich(rs.getString("quoc_tich"));
                list.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

//    public TacGia selectByName(String name) {
//        String sql = "SELECT * FROM TACGIA WHERE tac_gia = ?";
//        List<TacGia> list = this.selectBySql(sql, name);
//        return list.isEmpty() ? null : list.get(0);
//    }
    
    public List<TacGia> selecByID(String ID){
       String sql = "SELECT * FROM TACGIA WHERE ID LIKE ?";
       return this.selectBySql(sql, "%" + ID + "%");
    }
    
    public List<TacGia> selectByTenTG(String TenTG){
        String sql = "SELECT * FROM TACGIA WHERE tac_gia like ?";
        return this.selectBySql(sql, "%" + TenTG + "%");
    }
    public List<TacGia> selectByQuocTich(String QT){
        String sql = "SELECT * FROM TACGIA WHERE quoc_tich like ?";
        return this.selectBySql(sql, "%" + QT + "%");
    }
    
    public TacGia selectByName(String name) {
    System.out.println("Đang tìm tác giả với tên: " + name);
    String sql = "SELECT * FROM TACGIA WHERE tac_gia = ?";
    List<TacGia> list = this.selectBySql(sql, name);
    if (list.isEmpty()) {
        System.out.println("Không tìm thấy tác giả với tên: " + name);
    }
    return list.isEmpty() ? null : list.get(0);
}


}
