package com.company.capstone2.invoiceservice.viewModel;

import com.company.capstone2.invoiceservice.model.InvoiceItem;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class InvoiceViewModel {
    private int invoiceId;
    @Min(value = 1,message = "invalid customer id")
    private int customerId;
    @NotNull(message = "Please supply a member date")
    private LocalDate purchaseDate;
    // an order (invoice) has many items
    @Size(min = 1,message = "do not open an invoice with no item")
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

//    public int getPoint() {
//        return point;
//    }

//    public void setPoint(int point) {
//        this.point = point;
//    }
}
