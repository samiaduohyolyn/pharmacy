package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserInfoPanel extends JPanel {
    private JTextField nameField;
    private JComboBox<String> genderBox;
    private JTextField birthdayField;
    private JTextField phoneField;
    private JPasswordField passwordField;
    private JTextArea noteArea;
    private JButton saveButton;

    private User currentUser;

    public UserInfoPanel(User user) {
        this.currentUser = user;
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(7, 2, 50, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        form.add(new JLabel("用户编号："));
        form.add(new JLabel(user.getUserID()));

        form.add(new JLabel("姓名："));
        nameField = new JTextField(user.getName());
        form.add(nameField);

        form.add(new JLabel("性别："));
        genderBox = new JComboBox<>(new String[]{"男", "女"});
        genderBox.setSelectedItem(user.getGender());
        form.add(genderBox);

        form.add(new JLabel("生日（yyyy-MM-dd）："));
        String birthdayText = user.getBirthday() != null ? sdf.format(user.getBirthday()) : "";
        birthdayField = new JTextField(birthdayText);
        form.add(birthdayField);


        form.add(new JLabel("电话："));
        phoneField = new JTextField(user.getPhone());
        form.add(phoneField);

        form.add(new JLabel("密码："));
        passwordField = new JPasswordField(user.getPassword());
        form.add(passwordField);

        form.add(new JLabel("备注："));
        noteArea = new JTextArea(user.getNote(), 3, 20);
        form.add(new JScrollPane(noteArea));

        add(form, BorderLayout.CENTER);

        saveButton = new JButton("保存修改");
        add(saveButton, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> saveUserInfo());
    }

    private void saveUserInfo() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            currentUser.setName(nameField.getText().trim());
            currentUser.setGender(genderBox.getSelectedItem().toString());
            currentUser.setBirthday(sdf.parse(birthdayField.getText().trim()));
            currentUser.setPhone(phoneField.getText().trim());
            currentUser.setPassword(new String(passwordField.getPassword()).trim());
            currentUser.setNote(noteArea.getText().trim());

            UserDAO dao = new UserDAO();
            if (dao.updateUser(currentUser)) {
                JOptionPane.showMessageDialog(this, "修改成功！");
            } else {
                JOptionPane.showMessageDialog(this, "修改失败！");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "日期格式错误，请使用 yyyy-MM-dd");
        }
    }
}