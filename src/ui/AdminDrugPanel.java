package ui;

import dao.DrugDAO;
import model.Drug;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDrugPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private DrugDAO dao = new DrugDAO();

    public AdminDrugPanel() {
        setLayout(new BorderLayout());

        // 表格列名
        String[] columnNames = {"ID", "名称", "库存", "类型", "有效期", "成本", "售价", "供应商"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        // 加载数据
        loadData();

        // 放在滚动面板中
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 按钮区域
        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("添加");
        JButton deleteBtn = new JButton("删除");
        JButton updateBtn = new JButton("修改");

        btnPanel.add(addBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(updateBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // 按钮功能
        addBtn.addActionListener(e -> addDrug());
        deleteBtn.addActionListener(e -> deleteDrug());
        updateBtn.addActionListener(e -> updateDrug());
    }

    // 加载药品数据
    private void loadData() {
        tableModel.setRowCount(0);
        List<Drug> list = dao.getAllDrugs();
        for (Drug d : list) {
            tableModel.addRow(new Object[]{
                    d.getDrugID(),
                    d.getName(),
                    d.getInventory(),
                    d.getType(),
                    d.getExpiry(),
                    d.getCost(),
                    d.getPrice(),
                    d.getSupplier()
            });
        }
    }

    // 添加药品
    private void addDrug() {
        DrugFormDialog dialog = new DrugFormDialog(null);
        dialog.setVisible(true);
        if (dialog.isSucceeded()) {
            dao.addDrug(dialog.getDrug());
            loadData();
        }
    }

    // 删除选中药品
    private void deleteDrug() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String id = tableModel.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "确认删除药品ID " + id + " 吗？", "删除确认", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dao.deleteDrug(id);
                loadData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "请先选择一行");
        }
    }

    // 修改选中药品
    private void updateDrug() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            Drug drug = new Drug();
            drug.setDrugID(Integer.parseInt(tableModel.getValueAt(row, 0).toString()));
            drug.setName(tableModel.getValueAt(row, 1).toString());
            drug.setInventory(Integer.parseInt(String.valueOf(Integer.parseInt(tableModel.getValueAt(row, 2).toString()))));
            drug.setType(tableModel.getValueAt(row, 3).toString());
            drug.setExpiry(Integer.parseInt(tableModel.getValueAt(row, 4).toString()));
            drug.setCost(Double.parseDouble(tableModel.getValueAt(row, 5).toString()));
            drug.setPrice(Double.parseDouble(tableModel.getValueAt(row, 6).toString()));
            drug.setSupplier(tableModel.getValueAt(row, 7).toString());

            DrugFormDialog dialog = new DrugFormDialog(drug);
            dialog.setVisible(true);
            if (dialog.isSucceeded()) {
                boolean result = dao.updateDrug(dialog.getDrug());
                if (result) {
                    JOptionPane.showMessageDialog(this,"药品修改成功");
                } else {
                    JOptionPane.showMessageDialog(this,"药品修改失败");
                }
                loadData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "请先选择一行");
        }
    }
}