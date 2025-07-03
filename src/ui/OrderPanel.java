package ui;

import dao.OrderDAO;
import model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class OrderPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private String userId;
    private OrderDAO dao = new OrderDAO();

    public OrderPanel(String userId) {
        this.userId = userId;
        setLayout(new BorderLayout());

        // 添加选择列
        String[] columns = {"选择", "订单编号", "日期", "支付方式", "操作"};
        tableModel = new DefaultTableModel(columns, 0) {
            public Class<?> getColumnClass(int column) {
                return column == 0 ? Boolean.class : super.getColumnClass(column);
            }

            public boolean isCellEditable(int row, int col) {
                return col == 0 || col == 4; // 选择列和操作列可编辑
            }
        };

        table = new JTable(tableModel);
        table.getColumn("操作").setCellRenderer(new ButtonRenderer());
        table.getColumn("操作").setCellEditor(new ButtonEditor(new JCheckBox()));

        loadOrders();

        // 添加批量删除按钮
        JButton batchDeleteButton = new JButton("批量删除");
        batchDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                batchDeleteOrders();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(batchDeleteButton);
        add(buttonPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void loadOrders() {
        tableModel.setRowCount(0);
        List<Order> orders = dao.getOrdersByUser(userId);
        for (Order order : orders) {
            Object[] row = {
                    false, // 默认不选择
                    order.getDdno(),
                    order.getDate(),
                    order.getPayment(),
                    "删除"
            };
            tableModel.addRow(row);
        }
    }

    // 批量删除订单
    private void batchDeleteOrders() {
        List<String> selectedDdnos = new ArrayList<>();

        // 收集选中的订单编号
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Boolean isSelected = (Boolean) tableModel.getValueAt(i, 0);
            if (isSelected) {
                String ddno = (String) tableModel.getValueAt(i, 1);
                selectedDdnos.add(ddno);
            }
        }

        if (selectedDdnos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请选择要删除的订单！");
            return;
        }

        // 确认删除
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "确定删除选中的 " + selectedDdnos.size() + " 个订单？",
                "确认批量删除",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // 调用批量删除方法
            if (dao.batchDeleteOrders(selectedDdnos)) {
                JOptionPane.showMessageDialog(this, "批量删除成功！");
                loadOrders();
            } else {
                JOptionPane.showMessageDialog(this, "批量删除失败！");
            }
        }
    }

    // 删除按钮渲染器
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setText("删除");
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // 删除按钮编辑器
    class ButtonEditor extends DefaultCellEditor {
        private JButton button = new JButton("删除");
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button.addActionListener(e -> {
                if (currentRow >= 0) {
                    String ddno = (String) tableModel.getValueAt(currentRow, 1);
                    int confirm = JOptionPane.showConfirmDialog(button, "确定删除订单：" + ddno + "？", "确认", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        if (dao.deleteOrderByDdno(ddno)) {
                            JOptionPane.showMessageDialog(button, "删除成功！");
                            loadOrders();
                        } else {
                            JOptionPane.showMessageDialog(button, "删除失败！");
                        }
                    }
                }
                fireEditingStopped();
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            currentRow = row;
            return button;
        }
    }
}
