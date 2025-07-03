package ui;

import dao.DrugDAO;
import dao.OrderDAO;
import model.Drug;
import model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DrugPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Drug> drugList;
    private String userID;

    public DrugPanel(String userID) {
        this.userID = userID;
        setLayout(new BorderLayout());

        initTable();

        JButton orderButton = new JButton("提交订单");
        orderButton.addActionListener(e -> submitOrder());
        add(orderButton, BorderLayout.SOUTH);

        loadDrugData();
    }
    private void initTable(){
        String[] columns = {"ID", "名称", "价格", "库存", "购买数量"};
        tableModel = new DefaultTableModel(columns,0) {
            public boolean isCellEditable(int row,int col) {
                return col == 4;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadDrugData() {

        DrugDAO dao = new DrugDAO();
        drugList = dao.getAllDrugs();
        tableModel.setRowCount(0);

        for (Drug d : drugList) {
            tableModel.addRow(new Object[]{
                    d.getDrugID(),
                    d.getName(),
                    d.getPrice(),
                    d.getInventory(),
                    0
            });
        }

    }
        private void submitOrder() {
            List<Drug> selectedDrugs = new ArrayList<>();
            List<Integer> quantities = new ArrayList<>();
            DrugDAO drugDAO = new DrugDAO();

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                int qty = 0;
                try {
                    qty = Integer.parseInt(tableModel.getValueAt(i, 4).toString());
                } catch (Exception ignored) {}

                if (qty > 0) {
                    Drug d = drugList.get(i);
                    if (qty > d.getInventory()) {
                        JOptionPane.showMessageDialog(this, d.getName() + " 库存不足！");
                        return;
                    }
                    selectedDrugs.add(d);
                    quantities.add(qty);
                }
            }

            if (selectedDrugs.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请至少选择一种商品并输入正确数量");
                return;
            }

            // 创建订单
            Order order = new Order();
            String orderID = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + String.valueOf((int)(Math.random() * 90 + 10));
            order.setDdno(orderID);
            order.setHno(userID);
            order.setPayment("微信");
            order.setDate(new Date());

            OrderDAO orderDAO = new OrderDAO();
            boolean success = orderDAO.addOrder(order);

            if (success) {
                // 减少库存
                for (int i = 0; i < selectedDrugs.size(); i++) {
                    Drug d = selectedDrugs.get(i);
                    int newInventory = d.getInventory() - quantities.get(i);
                    d.setInventory(newInventory);
                    drugDAO.updateDrug(d); // 你需要实现 updateDrug 方法
                }

                JOptionPane.showMessageDialog(this, "订单提交成功！");
                loadDrugData(); // 重新加载表格数据
            } else {
                JOptionPane.showMessageDialog(this, "订单提交失败！");
            }}
}