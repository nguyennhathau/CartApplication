/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bussiness.Cart;
import bussiness.Invoice;
import bussiness.LineItem;
import bussiness.Product;
import data.InvoiceDB;
import data.ProductDB;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/cartServlet"})
public class CartServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");        
        String action=request.getParameter("action");        
        if(action==null){
            action="cart";
        }
        if(action.equals("cart")||action.equals("Update")){
            doAddToCart(request,response);
        }else if(action.equals("shop")){
            doShop(request, response);
        }else if(action.equals("checkout")){
            doCheckout(request, response);
        }
    }
    private void doAddToCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action=request.getParameter("action");
        String code=request.getParameter("code");
        String quantityString = request.getParameter("quantity");       
        Product p= ProductDB.getProduct(code);        
        int quantity=0;        
        try{
            quantity=Integer.parseInt(quantityString);
            
        }catch(NumberFormatException e){
            quantity=1;
        }
        
        LineItem item=new LineItem(p, quantity);
        HttpSession session=request.getSession();
        Cart cart=(Cart) session.getAttribute("cart");
        
        if(cart==null){
            cart= new Cart();
        }       
        if(quantity==0){
            cart.removeItem(item);   
        }else if(action==null)
            cart.addItem(item);
        else
            cart.update(item);
        session.setAttribute("cart", cart);
        
        String url="/Cart.jsp";
        getServletContext().getRequestDispatcher(url).forward(request, response);      
    }
     private void doCheckout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         
         String customerName = request.getParameter("customerName");
         LocalDate invoiceDate=LocalDate.now();
         Cart cart=(Cart) request.getSession().getAttribute("cart");
         
         Invoice invoice= new Invoice(0, invoiceDate, customerName, cart);
         String url="/thanks.jsp";
         String message="";
         if(InvoiceDB.insertInvoice(invoice)){
             message="success";
         }else
             message="fail";
         request.setAttribute("message", message);
         getServletContext().getRequestDispatcher(url).forward(request, response);
     }
    private void doShop(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String url="/index.jsp";
        
        List<Product> products= ProductDB.getProducts();
        
        request.setAttribute("products", products);
        
        getServletContext()
                .getRequestDispatcher(url).forward(request, response);
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
