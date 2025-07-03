package dao;

import model.Drug;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DrugDAO {

        public boolean reduceInventory(int drugID, int quantity) {
            String sql = "UPDATE Drug SET Inventory = Inventory - ? WHERE DrugID = ? AND Inventory >= ?";
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, quantity);
                stmt.setInt(2, drugID);
                stmt.setInt(3, quantity);
                int affected = stmt.executeUpdate();
                return affected > 0;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }


    public boolean addDrug(Drug drug) {
        String sql = "INSERT INTO Drug(DrugID, Name, Inventory, Type, Expiry, Cost, Price, Supplier) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, drug.getDrugID());
            ps.setString(2, drug.getName());
            ps.setInt(3, drug.getInventory());
            ps.setString(4, drug.getType());
            ps.setInt(5, drug.getExpiry());
            ps.setDouble(6, drug.getCost());
            ps.setDouble(7, drug.getPrice());
            ps.setString(8, drug.getSupplier());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Drug getDrugById(String id) {
        String sql = "SELECT * FROM Drug WHERE DrugID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Drug drug = new Drug();
                drug.setDrugID(rs.getInt("DrugID"));
                drug.setName(rs.getString("Name"));
                drug.setInventory(Integer.parseInt(rs.getString("Inventory")));
                drug.setType(rs.getString("Type"));
                drug.setExpiry(rs.getInt("Expiry"));
                drug.setCost(rs.getDouble("Cost"));
                drug.setPrice(rs.getDouble("Price"));
                drug.setSupplier(rs.getString("Supplier"));
                return drug;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Drug> getAllDrugs() {
        List<Drug> list = new ArrayList<>();
        String sql = "SELECT * FROM Drug";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Drug drug = new Drug();
                drug.setDrugID(rs.getInt("DrugID"));
                drug.setName(rs.getString("Name"));
                drug.setInventory(Integer.parseInt(rs.getString("Inventory")));
                drug.setType(rs.getString("Type"));
                drug.setExpiry(rs.getInt("Expiry"));
                drug.setCost(rs.getDouble("Cost"));
                drug.setPrice(rs.getDouble("Price"));
                drug.setSupplier(rs.getString("Supplier"));
                list.add(drug);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateDrug(Drug drug) {
        String sql = "UPDATE Drug SET Name=?, Inventory=?, Type=?, Expiry=?, Cost=?, Price=?, Supplier=? WHERE DrugID=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, drug.getName());
            ps.setInt(2, drug.getInventory());
            ps.setString(3, drug.getType());
            ps.setInt(4, drug.getExpiry());
            ps.setDouble(5, drug.getCost());
            ps.setDouble(6, drug.getPrice());
            ps.setString(7, drug.getSupplier());
            ps.setInt(8, drug.getDrugID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteDrug(String id) {
        String sql = "DELETE FROM Drug WHERE DrugID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}