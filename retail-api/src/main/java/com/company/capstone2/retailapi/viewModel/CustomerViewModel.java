package com.company.capstone2.retailapi.viewModel;

import com.company.capstone2.retailapi.model.Levelup;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * not a part of requirement
 * may need to excluded
 */
public class CustomerViewModel {
    private int customerId;
    private String fistName;
    private String lastName;
    private String street;
    private String city;
    private String zip;
    private String email;
    private String phone;
    // A customer has many invoices
    private List<InvoiceViewModel> invoices;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerViewModel that = (CustomerViewModel) o;
        return customerId == that.customerId &&
                fistName.equals(that.fistName) &&
                lastName.equals(that.lastName) &&
                street.equals(that.street) &&
                city.equals(that.city) &&
                zip.equals(that.zip) &&
                email.equals(that.email) &&
                phone.equals(that.phone) &&
                invoices.equals(that.invoices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, fistName, lastName, street, city, zip, email, phone, invoices);
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<InvoiceViewModel> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoiceViewModel> invoices) {
        this.invoices = invoices;
    }
}
