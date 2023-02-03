/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
public class UpdateCategorie extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

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
        if(request.getSession().getAttribute("token") == null){
            response.sendRedirect("index.jsp");
        }else{
            try {
            
            String id = request.getParameter("id");
            Categorie categorie = new Categorie();
            categorie.setId(Integer.parseInt(id));
            
            Connection conn  = Connexion.getConnection();
            categorie.delete(conn);
            
            CategorieController.liste(request, response, conn);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        
    }
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession().getAttribute("token") == null){
            response.sendRedirect("index.jsp");
        }else{
            try {

            Connection conn  = Connexion.getConnection();

            String id = request.getParameter("id");
            String nom = request.getParameter("nom");
            
            Categorie categorie = new Categorie();
            categorie.setId(Integer.parseInt(id));
            categorie.setNom(nom);
            
            categorie.update(conn);            
            CategorieController.liste(request, response, conn);            
        } catch (Exception e) {
            e.printStackTrace();
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
