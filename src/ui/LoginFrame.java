package ui;

import dao.AdminDAO;
import dao.UserDAO;
import model.Admin;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("线上药店登录");
        setSize(350, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        JLabel userLabel = new JLabel("用户名：");
        JTextField userText = new JTextField();
        JLabel passwordLabel = new JLabel("密码：");
        JPasswordField passwordText = new JPasswordField();

        // 添加用户类型选择
        JLabel typeLabel = new JLabel("用户类型：");
        JPanel typePanel = new JPanel();
        JRadioButton userRadio = new JRadioButton("用户", true);
        JRadioButton adminRadio = new JRadioButton("管理员");
        ButtonGroup group = new ButtonGroup();
        group.add(userRadio);
        group.add(adminRadio);
        typePanel.add(userRadio);
        typePanel.add(adminRadio);

        JButton loginButton = new JButton("登录");
        JButton registerButton = new JButton("注册");
        JLabel message = new JLabel("");

        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(typeLabel);
        panel.add(typePanel);
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(message);

        add(panel);

        // 登录逻辑
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText().trim();
                String password = new String(passwordText.getPassword()).trim();

                // 根据选择的用户类型进行登录
                if (userRadio.isSelected()) {
                    // 用户登录
                    User user = UserDAO.login(username, password);
                    if (user != null) {
                        message.setText("用户登录成功！");
                        dispose();
                        new UserMainFrame(user).setVisible(true);
                    } else {
                        message.setText("用户名或密码错误！");
                    }
                } else {
                    // 管理员登录
                    Admin admin = AdminDAO.login(username, password);
                    if (admin != null) {
                        message.setText("管理员登录成功！");
                        dispose();
                        new AdminMainFrame().setVisible(true);
                    } else {
                        message.setText("用户名或密码错误！");
                    }
                }
            }
        });

        registerButton.addActionListener(e -> new RegisterFrame().setVisible(true));
    }
}
