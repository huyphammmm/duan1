/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Entity.TheLoai;
import java.util.*;
import java.sql.*;
import utils.JDBCHELPER;

public class TheLoaiDao extends ThuVienDao<TheLoai, Integer> {
    String INSERT_SQL = "INSERT INTO THELOAI(the_loai) VALUES (?)";
    String UPDATE_SQL = "UPDATE THELOAI SET the_loai = ? WHERE ID = ?";
    String DELETE_SQL = "DELETE FROM THELOAI WHERE ID = ?";
    String SELECT_ALL_SQL = "SELECT * FROM THELOAI";
    String SELECT_BY_ID_SQL = "SELECT * FROM THELOAI WHERE ID = ?";

    @Override
    public void insert(TheLoai entity) {
        JDBCHELPER.update(INSERT_SQL, entity.getTheLoai());
    }

    @Override
    public void update(TheLoai entity) {
        JDBCHELPER.update(UPDATE_SQL, entity.getTheLoai(), entity.getID());
    }

    @Override
    public void delete(Integer id) {
        JDBCHELPER.update(DELETE_SQL, id);
    }

    @Override
    public TheLoai selectById(Integer id) {
        List<TheLoai> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<TheLoai> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<TheLoai> selectBySql(String sql, Object... args) {
        List<TheLoai> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHELPER.query(sql, args);
            while (rs.next()) {
                TheLoai entity = new TheLoai();
                entity.setID(rs.getInt("ID"));
                entity.setTheLoai(rs.getString("the_loai"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public TheLoai selectByName(String name) {
    String sql = "SELECT * FROM THELOAI WHERE the_loai = ?";
    List<TheLoai> list = this.selectBySql(sql, name);
    return list.isEmpty() ? null : list.get(0);
}
}