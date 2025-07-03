package ui;

import dao.UserDAO;
import dao.AdminDAO; // 导入AdminDAO
import model.User;
import model.Admin; // 导入Admin类

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame {
    public RegisterFrame() {
        setTitle("线上药店注册");
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

        JButton registerButton = new JButton("注册");
        JButton loginButton = new JButton("登录");
        JLabel message = new JLabel("");

        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(typeLabel);
        panel.add(typePanel);
        panel.add(registerButton);
        panel.add(loginButton);
        panel.add(message);

        add(panel);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());

                // 根据选择的用户类型创建不同的对象并调用不同的方法
                boolean result;
                if (userRadio.isSelected()) {
                    User user = new User();
                    user.setName(username);
                    user.setPassword(password);
                    result = new UserDAO().addUser(user);
                } else {
                    Admin admin = new Admin();
                    admin.setName(username);
                    admin.setPassword(password);
                    result = new AdminDAO().addAdmin(admin);
                }

                message.setText(result ? "注册成功" : "注册失败");
            }
        });

        loginButton.addActionListener(e -> new LoginFrame().setVisible(true));
    }
}
