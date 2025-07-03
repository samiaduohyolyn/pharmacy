package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public UserPanel() {
        setLayout(new BorderLayout());

        String[] columns = {"用户编号", "姓名", "性别", "生日", "电话", "备注"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        loadUsers();

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton refreshBtn = new JButton("刷新");
        refreshBtn.addActionListener(e -> loadUsers());
        add(refreshBtn, BorderLayout.SOUTH);
    }

    private void loadUsers() {
        tableModel.setRowCount(0);
        List<User> list = new UserDAO().getAllUsers();
        for (User u : list) {
            tableModel.addRow(new Object[]{
                    u.getUserID(), u.getName(), u.getGender(),
                    u.getBirthday(), u.getPhone(), u.getNote()
            });
        }
    }
}