package ui;

import model.Drug;

import javax.swing.*;
import java.awt.*;

public class DrugFormDialog extends JDialog {
    private JTextField idField, nameField, inventoryField, typeField, expiryField, costField, priceField, supplierField;
    private boolean succeeded = false;
    private Drug drug;

    public DrugFormDialog(Drug existing) {
        setTitle(existing == null ? "添加药品" : "修改药品");
        setModal(true);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2, 5, 5));


        add(new JLabel("名称:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("库存:"));
        inventoryField = new JTextField();
        add(inventoryField);

        add(new JLabel("类型:"));
        typeField = new JTextField();
        add(typeField);

        add(new JLabel("有效期:"));
        expiryField = new JTextField();
        add(expiryField);

        add(new JLabel("成本:"));
        costField = new JTextField();
        add(costField);

        add(new JLabel("售价:"));
        priceField = new JTextField();
        add(priceField);

        add(new JLabel("供应商:"));
        supplierField = new JTextField();
        add(supplierField);

        JButton confirmBtn = new JButton("确认");
        confirmBtn.addActionListener(e -> {
            drug = new Drug(
                    nameField.getText(),
                    Integer.parseInt(inventoryField.getText()),
                    typeField.getText(),
                    Integer.parseInt(expiryField.getText()),
                    Double.parseDouble(costField.getText()),
                    Double.parseDouble(priceField.getText()),
                    supplierField.getText()
            );
            if (existing != null) {
                drug.setDrugID(existing.getDrugID());
            }
            succeeded = true;
            dispose();
        });
        add(confirmBtn);

        JButton cancelBtn = new JButton("取消");
        cancelBtn.addActionListener(e -> dispose());
        add(cancelBtn);

        // 若是编辑模式，填充原数据
        if (existing != null) {
            nameField.setText(existing.getName());
            inventoryField.setText(String.valueOf(existing.getInventory()));
            typeField.setText(existing.getType());
            expiryField.setText(String.valueOf(existing.getExpiry()));
            costField.setText(String.valueOf(existing.getCost()));
            priceField.setText(String.valueOf(existing.getPrice()));
            supplierField.setText(existing.getSupplier());
        }
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public Drug getDrug() {
        return drug;
    }
}