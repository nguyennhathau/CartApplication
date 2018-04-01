/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;


import bussiness.Invoice;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Admin
 */
public class InvoiceDB {
    public static boolean insertInvoice(Invoice invoice){
        boolean t=false;
        //List<Product> products=new ArrayList<>();
        ConnectionPool pool=ConnectionPool.getInstance();
        Connection con= pool.getConnection();
        String sqlString = "Insert into invoice values(?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(sqlString);
            DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String invoiceDateString=dtf.format(invoice.getInvoiceDate());
            ps.setString(1, invoiceDateString);
            ps.setString(2, invoice.getCustomerName());
            ps.executeUpdate();
            int invoiceID= getLastInvoiceID();
            
            if(!LineItemDB.insertItems(invoiceID, invoice.getCart().getItems())){
                return false;
            }
            t=true;
            
        } catch (SQLException e) {
            System.out.println(e);
        }finally{
            pool.freeConnection(con);
        }
        return t;
    }
    
    public static int getLastInvoiceID(){
        int i=0;
        ConnectionPool pool=ConnectionPool.getInstance();
        Connection con =pool.getConnection();
        
        String sqlString="select Max(invoiceID) as 'LastInvoice' from invoice";
        
        try{
            PreparedStatement ps = con.prepareStatement(sqlString);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            i=rs.getInt("LastInvoice");
        }catch(SQLException e){
            System.out.println(e);
        }
        return i;
    }
    
    public static List<Invoice> getInvoices(){    
         List<Invoice> invoices= new ArrayList<>();
        ConnectionPool pool=ConnectionPool.getInstance();
        Connection con=pool.getConnection();
        
        String sqlString="select * from invoice";
        try{
            PreparedStatement ps=con.prepareStatement(sqlString);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                int invoiceID=rs.getInt("invoiceID");
                Date invoiceDatedate=rs.getDate("invoiceDate");
                LocalDate invoiceDate = invoiceDatedate.toLocalDate();
                String customerName=rs.getString("customerName");
                Invoice invoice = new Invoice(invoiceID, invoiceDate, customerName, null);
                invoices.add(invoice);
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        return invoices;
    }
    public static Invoice getInvoice(int invoiceID){
        Invoice invoice=null;
        ConnectionPool pool=ConnectionPool.getInstance();
        Connection con=pool.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("select * from invoice where invoiceID=?");
            //ps.setString(1, invoiceID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String description = rs.getString("description");
                double price = rs.getDouble("price");
              //  p= new Product(code, description, price);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }finally{
            pool.freeConnection(con);
        }
        return invoice;
    }
}
