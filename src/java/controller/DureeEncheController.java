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
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Duree;
import model.Duree_Enchere;
import services.Connexion;

/**
 *
 * @author lucky
 */
public class DureeEncheController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DureeEncheController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DureeEncheController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        if(request.getSession().getAttribute("token") == null){
            response.sendRedirect("index.jsp");
        }else{
            try {
            Connection conn = Connexion.getConnection();
            Duree_Enchere duree_Enchere = Duree_Enchere.getDuree(conn);            
            request.setAttribute("duree", duree_Enchere);
            conn.close();
            RequestDispatcher req = request.getRequestDispatcher("duree_enchere.jsp");
            req.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            String min_heure = request.getParameter("min_heure");
            String min_min = request.getParameter("min_min");
            String max_heure = request.getParameter("max_heure");
            String max_min = request.getParameter("max_min");
            long a = Long.parseLong(min_min);
            long b = Long.parseLong(max_min);
            if(a>60 || b>60 ){
                request.getSession().setAttribute("error", "erreur : les donner inserer ne sont pas compatible ");                
                doGet(request, response);
                return;
            }
            long min =  TimeUnit.HOURS.toSeconds(Long.parseLong(min_heure)) + 
                     TimeUnit.MINUTES.toSeconds(Long.parseLong(min_min)) ; 
            long max =  TimeUnit.HOURS.toSeconds(Long.parseLong(max_heure)) + 
                     TimeUnit.MINUTES.toSeconds(Long.parseLong(max_min));
            if(min>max){
                request.getSession().setAttribute("error", "la valeur du duree minimal ne doit pas etre superieur a celle du duree maximal ");
                doGet(request, response);
                return;
            }
            Duree_Enchere duree_Enchere = new Duree_Enchere();
            duree_Enchere.setMin(new Duree(min));
            duree_Enchere.setMax(new Duree(max));
             conn = Connexion.getConnection();
            duree_Enchere.update(conn);
            conn.close();
            response.sendRedirect("DureeEncheController");
        } catch (Exception e) {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) {
                    
                }
            }
            try (PrintWriter out = response.getWriter()) {
                e.printStackTrace(out);
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
