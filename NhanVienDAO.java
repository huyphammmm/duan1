/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import utils.JDBCHELPER;
import Entity.NhanVien;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
/**
 *
 * @author ADMIN
 */
public class NhanVienDAO extends ThuVienDao<NhanVien, Integer> {
    String INSERT_SQL = "INSERT INTO NHANVIEN(ten_nhan_vien, mat_khau, vai_tro) VALUES(?,?,?)";
    String UPDATE_SQL = "UPDATE NHANVIEN SET ten_nhan_vien=?, mat_khau=?, vai_tro=? WHERE ID=?";
    String DELETE_SQL = "DELETE FROM NHANVIEN WHERE ID=?";
    String SELECT_ALL_SQL = "SELECT * FROM NHANVIEN";
    String SELECT_BY_ID_SQL = "SELECT * FROM NHANVIEN WHERE ID=?";

    public void insert(NhanVien entity) {
        JDBCHELPER.update(INSERT_SQL, entity.getTenNV(), entity.getMatKhau(), entity.isVaiTro());
    }

    @Override
    public void update(NhanVien entity) {
        JDBCHELPER.update(UPDATE_SQL, entity.getTenNV(), entity.getMatKhau(), entity.isVaiTro(), entity.getID());
    }

    @Override
    public void delete(Integer id) {
        JDBCHELPER.update(DELETE_SQL, id);
    }

    @Override
    public NhanVien selectById(Integer id) {
        List<NhanVien> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try (ResultSet rs = JDBCHELPER.query(sql, args)) {
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setID(rs.getInt("ID"));
                nv.setTenNV(rs.getString("ten_nhan_vien"));
                nv.setMatKhau(rs.getString("mat_khau"));
                nv.setVaiTro(rs.getBoolean("vai_tro"));
                list.add(nv);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}

