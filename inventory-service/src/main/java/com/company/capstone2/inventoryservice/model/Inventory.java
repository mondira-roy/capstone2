package com.company.capstone2.inventoryservice.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Inventory {
    private int inventoryId;
    @Min(value = 1,message = "must have a valid product id")
    private int productId;
    @Min(value = 1,message = "must have a valid quantity")
    private int quantity;

    public Inventory() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return getInventoryId() == inventory.getInventoryId() &&
                getProductId() == inventory.getProductId() &&
                getQuantity() == inventory.getQuantity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInventoryId(), getProductId(), getQuantity());
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
