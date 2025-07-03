package ui;

import dao.OrderDAO;
import model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminOrderPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public AdminOrderPanel() {
        setLayout(new BorderLayout());

        String[] columns = {"订单号", "用户编号", "日期", "支付方式"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        loadOrders();

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton deleteBtn = new JButton("删除选中订单");
        deleteBtn.addActionListener(e -> deleteOrder());
        bottomPanel.add(deleteBtn);

        JButton refreshBtn = new JButton("刷新");
        refreshBtn.addActionListener(e -> loadOrders());
        bottomPanel.add(refreshBtn);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadOrders() {
        tableModel.setRowCount(0);
        List<Order> list = new OrderDAO().getAllOrders();
        for (Order o : list) {
            tableModel.addRow(new Object[]{
                    o.getDdno(), o.getHno(), o.getDate(), o.getPayment()
            });
        }
    }

    private void deleteOrder() {
        int selected = table.getSelectedRow();
        if (selected >= 0) {
            String orderId = tableModel.getValueAt(selected, 0).toString();
            int result = JOptionPane.showConfirmDialog(this, "确认删除订单：" + orderId + "？", "提示", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                boolean success = new OrderDAO().deleteOrderByDdno(orderId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "删除成功！");
                    loadOrders();
                } else {
                    JOptionPane.showMessageDialog(this, "删除失败！");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "请先选择一条订单！");
        }
    }
}