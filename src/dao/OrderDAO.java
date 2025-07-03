package dao;

import model.Order;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public boolean addOrder(Order order) {
        String sql = "INSERT INTO `Order`(Ddno, Date, Hno, Payment) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, order.getDdno());
            ps.setDate(2, new Date(order.getDate().getTime()));
            ps.setString(3, order.getHno());
            ps.setString(4, order.getPayment());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Order> getOrdersByUser(String userId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM `Order` WHERE Hno = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setDdno(rs.getString("Ddno"));
                    order.setDate(rs.getDate("Date"));
                    order.setHno(rs.getString("Hno"));
                    order.setPayment(rs.getString("Payment"));
                    list.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteOrderByDdno(String ddno) {
        String sql = "DELETE FROM `Order` WHERE Ddno = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ddno);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM `Order`";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order order = new Order();
                order.setDdno(rs.getString("Ddno"));
                order.setDate(rs.getDate("Date"));
                order.setHno(rs.getString("Hno"));
                order.setPayment(rs.getString("Payment"));
                list.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 批量删除订单方法
    public boolean batchDeleteOrders(List<String> ddnos) {
        if (ddnos == null || ddnos.isEmpty()) {
            return false;
        }

        // 构建SQL语句
        StringBuilder sqlBuilder = new StringBuilder("DELETE FROM `Order` WHERE Ddno IN (");
        for (int i = 0; i < ddnos.size(); i++) {
            sqlBuilder.append("?");
            if (i < ddnos.size() - 1) {
                sqlBuilder.append(",");
            }
        }
        sqlBuilder.append(")");

        String sql = sqlBuilder.toString();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 设置参数
            for (int i = 0; i < ddnos.size(); i++) {
                ps.setString(i + 1, ddnos.get(i));
            }

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}