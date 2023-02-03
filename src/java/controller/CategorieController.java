/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Categorie;
import services.Connexion;

/**
 *
 * @author lucky
 */
public class CategorieController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession().getAttribute("token") == null){
            response.sendRedirect("index.jsp");
        }else{
            Connection conn = null;
        try {
             conn = Connexion.getConnection();
            liste(request, response, conn);
        } catch (Exception e) {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) {}
            }
            try (PrintWriter out = response.getWriter()) {
                e.printStackTrace(out);
            }
        }
        }
        
    }
    
    public static void liste(HttpServletRequest request, HttpServletResponse response,Connection conn) throws Exception {
        if(request.getSession().getAttribute("token") == null){
            response.sendRedirect("index.jsp");
        }else{
            Categorie [] donner = Categorie.getAll(conn);
        request.setAttribute("value", donner);
        conn.close();
        RequestDispatcher dist = request.getRequestDispatcher("categorie.jsp");
        dist.forward(request, response);
        }
        
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
        if(request.getSession().getAttribute("token") == null){
            response.sendRedirect("index.jsp");
        }else{
            Connection conn = null;
        try {
            String nom = request.getParameter("nom");
            if(nom!=null){
                Categorie categorie = new Categorie();
                categorie.setNom(nom);
                conn = Connexion.getConnection();
                categorie.insert(conn);
                liste(request, response, conn);                
            }
        } catch (Exception e) {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) { }
            }
        }
        }
        
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
