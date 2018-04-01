
package bussiness;

import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDate;

public class Invoice implements Serializable{
    private int invoiceID;
    private LocalDate invoiceDate;
    private String customerName;
    private Cart cart;

    public Invoice() {
    }

    public Invoice(int invoiceID, LocalDate invoiceDate, String customerName, Cart cart) {
        this.invoiceID = invoiceID;
        this.invoiceDate = invoiceDate;
        this.customerName = customerName;
        this.cart = cart;
    }


    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
    
    public double getInvoiceTotal(){
        double sum=0;
        for(LineItem item:cart.getItems()){
            sum+=item.getTotal();
        }
        return sum;
    }
    public String getInvoiceFormatted(){
        NumberFormat nf=NumberFormat.getCurrencyInstance();
        return nf.format(getInvoiceTotal());
    }
}
