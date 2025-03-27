/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Entity.*;
import java.sql.*;
import java.util.*;
import utils.*;
/**
 *
 * @author ADMIN
 */
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.JDBCHELPER;

public class ThongKeDAO {
    private List<Object[]> getListOfArray(String sql, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = JDBCHELPER.query(sql, args);
            while (rs.next()) {
                int cols = rs.getMetaData().getColumnCount();
                Object[] row = new Object[cols];
                for (int i = 0; i < cols; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                list.add(row);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> getTotalBooks() {
    String sql = "EXEC sp_TotalBooks";
    return this.getListOfArray(sql);
}

public List<Object[]> getTotalAvailableBooks() {
    String sql = "EXEC sp_TotalAvailableBooks";
    return this.getListOfArray(sql);
}

public List<Object[]> getTotalBorrowedBooks() {
    String sql = "EXEC sp_TotalBorrowedBooks";
    return this.getListOfArray(sql);
}

public List<Object[]> getListOverdueBooks() {
    String sql = "EXEC sp_ListOverdueBooks";
    return this.getListOfArray(sql);
}

public List<Object[]> getTotalMembers() {
    String sql = "EXEC sp_TotalMembers";
    return this.getListOfArray(sql);
}

public List<Object[]> getListOverdueMembers() {
    String sql = "EXEC sp_OverdueMembers";
    return this.getListOfArray(sql);
}

public List<Object[]> getNumberOfMembersWhoBorrowedBooks() {
    String sql = "EXEC sp_NumberOfMembersWhoBorrowedBooks";
    return this.getListOfArray(sql);
}

public List<Object[]> getNumberOfMembersWhoHaveNotBorrowedBooks() {
    String sql = "EXEC sp_NumberOfMembersWhoHaveNotBorrowedBooks";
    return this.getListOfArray(sql);
}

public List<Object[]> getTotalLoanSlips() {
    String sql = "EXEC sp_TotalLoanSlips";
    return this.getListOfArray(sql);
}

public List<Object[]> getMostActiveMonthForLoanSlips() {
    String sql = "EXEC sp_MostActiveMonthsForLoanSlips;";
    return this.getListOfArray(sql);
}

public List<Object[]> getTotalQuantityOfLoanDetails() {
    String sql = "EXEC sp_TotalQuantityOfLoanDetails";
    return this.getListOfArray(sql);
}

public List<Object[]> getDetailedLoanSlipStatistics() {
    String sql = "EXEC sp_DetailedLoanSlipStatistics";
    return this.getListOfArray(sql);
}

public List<Object[]> getBookCategoryBorrowStatistics() {
    String sql = "EXEC sp_BookCategoryBorrowStatistics";
    return this.getListOfArray(sql);
}

public List<Object[]> getTop10MostBorrowedBooks() {
    String sql = "EXEC sp_Top10MostBorrowedBooks";
    return this.getListOfArray(sql);
}

public List<Object[]> getTotalEmployees() {
    String sql = "EXEC sp_TotalEmployees";
    return this.getListOfArray(sql);
}

public List<Object[]> getEmployeesWithMostLoanSlips() {
    String sql = "EXEC sp_EmployeesWithMostLoanSlips";
    return this.getListOfArray(sql);
}

}
