/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.android.servlets;

import com.ufpr.android.beans.User;
import com.ufpr.tads.android.facade.UserFacade;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 *
 * @author Gabriel
 */
@WebServlet(name = "CadastroServlet", urlPatterns = {"/CadastroServlet"})
public class CadastroServlet extends HttpServlet {

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
      if (request.getMethod().equals("POST")) {
            StringBuffer jb = new StringBuffer();
            String line = null;
            try {
              BufferedReader reader = request.getReader();
              while ((line = reader.readLine()) != null)
                jb.append(line);
            } catch (Exception ex) { 
                System.out.println(ex.getMessage());
            }

            try {
                JSONObject jsonObject =  JSONObject.fromObject(jb.toString());

                String email = (String) jsonObject.get("email");
                String name = (String) jsonObject.get("name");
                String password = (String) jsonObject.get("senha");
               
                int idFound = UserFacade.getUserByEmail(email);
                HashMap<String, String> hm = new HashMap<String, String>();
                
                if ( idFound == 0 ) {
                    User user = new User();
                    user.setLogin(email);
                    user.setName(name);
                    user.setPassword(password);
            
                    
                    UserFacade.insert(user);
                    hm.put("insert", "true");
                    hm.put("message", "Usuario Inserido");
                } else {
                    hm.put("insert", "false");
                    hm.put("message", "Email com este nome já existente");
                }
                JSONObject json = JSONObject.fromObject(hm);
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.print(json);
                out.flush();
            } catch (SQLException | ClassNotFoundException | JSONException ex) {
              System.out.println(ex.getMessage());
            }
        
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
