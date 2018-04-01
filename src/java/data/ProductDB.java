/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import bussiness.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ProductDB {
    public static List<Product> getProducts(){
        List<Product> products= new ArrayList<>();
        
        ConnectionPool pool=ConnectionPool.getInstance();
        Connection con= pool.getConnection();
        String sqlString ="Select * from Product";
        try{
            PreparedStatement ps=con.prepareStatement(sqlString);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                String code=rs.getString("code");
                String description=rs.getString("description");
                double price=rs.getDouble("price");
                
                Product p=new Product(code, description, price);
                products.add(p);
            }
        }catch(SQLException e){
            System.out.println(e);
        }finally{
            pool.freeConnection(con);
        }
        return products;
    }
    public static Product getProduct(String code){
        Product p=null;
         ConnectionPool pool=ConnectionPool.getInstance();
        Connection con= pool.getConnection();
        String sqlString = "select * from Product where code=?";

        try {

            PreparedStatement ps = con.prepareStatement(sqlString);
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                p= new Product(code, description, price);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }finally{
            pool.freeConnection(con);
        }
        return p;
    }
    
}
