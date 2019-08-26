package com.company.capstone2.retailapi.viewModel;


import com.company.capstone2.retailapi.model.InvoiceItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Order {
    private int invoiceId;
    private int customerId;
    private LocalDate purchaseDate;
    private List<InvoiceItem> invoiceItems;
    private int point;

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order that = (Order) o;
        return invoiceId == that.invoiceId &&
                customerId == that.customerId &&
                point == that.point &&
                purchaseDate.equals(that.purchaseDate) &&
                invoiceItems.equals(that.invoiceItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, customerId, purchaseDate, invoiceItems, point);
    }
}
