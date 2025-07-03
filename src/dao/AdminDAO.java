package dao;
import model.Admin;
import model.User;
import util.DBUtil;
import java.sql.*;

public class AdminDAO {
        public static Admin login(String name, String pwd) {
            String sql = "SELECT * FROM Administrator WHERE Name=? AND Password=?";
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, pwd);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Admin admin = new Admin();
                    admin.setName(rs.getString("Name"));
                    return admin;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    public boolean addAdmin(Admin admin) {
        String sql = "INSERT INTO administrator(Name, Password, rzrq) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, admin.getName());
            ps.setString(2, admin.getPassword());

            // 设置注册时间
            if (admin.getRzrq() != null) {
                ps.setDate(3, new java.sql.Date(admin.getRzrq().getTime()));
            } else {
                // 如果注册时间为空，使用当前时间
                ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            }

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}