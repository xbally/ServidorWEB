/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.android.dao;

import com.ufpr.android.beans.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Gabriel
 */
public class UserDAO {
    private Connection conn;
    
    public UserDAO() throws SQLException, ClassNotFoundException {
        this.conn = ConnectionFactory.getConnection();
    }
    
    public User getUserByLogin(String login) throws SQLException {
        String sql = "SELECT * from users where login=(?);";
		
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1,login);

        ResultSet res = stmt.executeQuery();
        User user = new User();

        while (res.next())
        { 
            user.setId(res.getInt("id"));
            user.setLogin(res.getString("login"));
            user.setPassword(res.getString("password"));
            user.setName(res.getString("name"));
            return user;
        }
        return null;
    }
    
    public int getUserByEmail(String email) throws SQLException {
        ResultSet rs;
        int id = 0;
        String query = "select * from Users "
                + "where login=(?);";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, email);
        rs = stmt.executeQuery();
        while (rs.next()) {
            id = rs.getInt("id");
        }
        return id;
    }

    public void insertUser(User user)  throws SQLException {
        String sql = "insert into users(login, name, password) values((?), (?), (?));";

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, user.getLogin());
        stmt.setString(2, user.getName());
        stmt.setString(3, user.getPassword());

        stmt.executeUpdate();
    }
}
