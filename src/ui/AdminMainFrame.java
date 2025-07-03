package ui;

import model.Admin;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMainFrame extends JFrame {

    public AdminMainFrame() {
        setTitle("线上药店系统 - 主界面");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu systemMenu = new JMenu("系统");
        JMenuItem logoutItem = new JMenuItem("退出登录");

        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        systemMenu.add(logoutItem);
        menuBar.add(systemMenu);
        setJMenuBar(menuBar);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("药品管理", new AdminDrugPanel());
        tabbedPane.add("订单管理", new AdminOrderPanel());
        tabbedPane.add("用户管理", new UserPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void logout() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "确定要退出登录吗？",
                "确认退出",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            dispose(); // 关闭当前窗口
            new LoginFrame().setVisible(true); // 显示登录窗口
        }
    }

}