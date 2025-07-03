package model;

public class Drug {
    private int drugID;
    private String name;
    private int inventory;
    private String type;
    private int expiry;
    private double cost;
    private double price;
    private String supplier;

    // 无参构造函数
    public Drug() {
    }

    // 全参构造函数（用于表单对话框）
    public Drug(String name, int inventory, String type, int expiry, double cost, double price, String supplier) {
        this.drugID = drugID;
        this.name = name;
        this.inventory = inventory;
        this.type = type;
        this.expiry = expiry;
        this.cost = cost;
        this.price = price;
        this.supplier = supplier;
    }

    // Getter 和 Setter 方法
    public int getDrugID() {
        return drugID;
    }

    public void setDrugID(int drugID) {
        this.drugID = drugID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getExpiry() {
        return expiry;
    }

    public void setExpiry(int expiry) {
        this.expiry = expiry;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}