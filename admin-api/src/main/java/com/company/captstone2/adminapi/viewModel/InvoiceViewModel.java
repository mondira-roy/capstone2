package com.company.captstone2.adminapi.viewModel;

import com.company.captstone2.adminapi.model.InvoiceItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class InvoiceViewModel {
    private int invoiceId;
    private int customerId;
    private LocalDate purchaseDate;
    // an order (invoice) has many items
    private List<InvoiceItem> invoiceItems;
    // calculated point from the total order
    //private int point;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceViewModel that = (InvoiceViewModel) o;
        return invoiceId == that.invoiceId &&
                customerId == that.customerId &&
                //  point == that.point &&
                purchaseDate.equals(that.purchaseDate) &&
                invoiceItems.equals(that.invoiceItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, customerId, purchaseDate, invoiceItems
                // point
        );
    }

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

}
