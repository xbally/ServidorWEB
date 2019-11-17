/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.android.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Gabriel
 */
public class ConnectionFactory {
    
  public static Connection getConnection() {
        
        try {
            Class.forName("org.postgresql.Driver");
            //Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/oscar_app", "postgres", "root");
            //return DriverManager.getConnection("jdbc:mysql://localhost:3306/tcc","root","root");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException (e);
        }
    }
}

