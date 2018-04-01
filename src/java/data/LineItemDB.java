
package data;

import bussiness.Cart;
import bussiness.Invoice;
import bussiness.LineItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LineItemDB {
    public static boolean InsertLineItem(int invoiceID, LineItem item){
        boolean t=false;
        
        ConnectionPool pool=ConnectionPool.getInstance();
        Connection con=pool.getConnection();
        
        String sqlString ="Insert into LineItem values(?,?,?)";
        try{
            PreparedStatement ps=con.prepareStatement(sqlString);
            ps.setInt(1, invoiceID);
            ps.setString(2, item.getProduct().getCode());
            ps.setInt(3, item.getQuantity());
            ps.executeUpdate();
            t=true;
        }catch(SQLException e){
            System.out.println(e);
        }finally{pool.freeConnection(con);}
        return t;
    }
    public static boolean insertItems(int invoiceID, List<LineItem> items){
        boolean t=false;
        
        ConnectionPool pool=ConnectionPool.getInstance();
        Connection con=pool.getConnection();
        
        String sqlString ="Insert into LineItem values(?,?,?)";
        try{
            PreparedStatement ps=con.prepareStatement(sqlString);
            for(LineItem item: items){
            ps.setInt(1, invoiceID);
            ps.setString(2, item.getProduct().getCode());
            ps.setInt(3, item.getQuantity());
            ps.executeUpdate();
            }
            t=true;
        }catch(SQLException e){
            System.out.println(e);
        }finally{pool.freeConnection(con);}
        return t;
    }
    public static List<Invoice> getItemOfInvoice(Invoice invoiceID){
        List<Invoice> items=new ArrayList<>();        
        ConnectionPool pool=ConnectionPool.getInstance();
        Connection con=pool.getConnection();
        
        String sqlString="select * from invoice";
        try{
            PreparedStatement ps=con.prepareStatement(sqlString);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
               // String invoiceID=rs.getString("invoiceID");
                String invoiceDate=rs.getString("invoiceDate");
                double customerName=rs.getDouble("customerName");
                Cart cart=null;
                //Invoice invoice = new Invoice(0, invoiceDate, invoiceDate, cart);
                //items.add(invoice);
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        return items;
    }
}
