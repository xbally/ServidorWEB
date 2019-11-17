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
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
@WebServlet(name = "UserServlet", urlPatterns = {"/UserServlet"})
public class UserServlet extends HttpServlet {

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
         response.setContentType("application/json");
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

                String login = (String) jsonObject.get("login");
                String password = (String) jsonObject.get("password");
                
                User us = new User();

                try {
                    us = UserFacade.searchUserByLogin(login);
                } catch (SQLException | ClassNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
                HashMap<String, String> hm = new HashMap<String, String>();

                if (us != null && us.getLogin().equals(login) && us.getPassword().equals(password)) {
                    hm.put("login", "true");
                    hm.put("name", us.getName());
                } else {
                    String message = "Login ou senha incorretos";
                    hm.put("login", "false");
                    hm.put("message", message);
                }
                JSONObject json = JSONObject.fromObject(hm);
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.print(json);
                out.flush();
            } catch (JSONException ex) {
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
