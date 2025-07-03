package ui;

import model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UserMainFrame extends JFrame {
    private User user;

    public UserMainFrame(User user) {
        this.user = user;
        setTitle("线上药店系统 - 主界面");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建菜单栏
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

        // 创建子面板并保留引用
        DrugPanel drugPanel = new DrugPanel(user.getUserID());
        OrderPanel orderPanel = new OrderPanel(user.getUserID());
        UserInfoPanel userInfoPanel = new UserInfoPanel(user);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("药品购买", drugPanel);
        tabbedPane.add("订单管理", orderPanel);
        tabbedPane.add("个人信息", userInfoPanel);

        // 添加选项卡切换监听器
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int index = tabbedPane.getSelectedIndex();
                String title = tabbedPane.getTitleAt(index);

                if ("订单管理".equals(title)) {
                    // 每次切换到"订单管理"都刷新订单数据
                    orderPanel.loadOrders();
                }
            }
        });

        add(tabbedPane, BorderLayout.CENTER);
    }

    // 退出登录方法
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
