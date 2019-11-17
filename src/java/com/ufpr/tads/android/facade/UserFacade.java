/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.tads.android.facade;

import com.ufpr.android.beans.User;
import com.ufpr.tads.android.dao.UserDAO;
import java.sql.SQLException;

/**
 *
 * @author Gabriel
 */
public class UserFacade {
    public static User searchUserByLogin(String login) throws SQLException, ClassNotFoundException {
        UserDAO dao = new UserDAO();
        return dao.getUserByLogin(login);
    }
    
public static int getUserByEmail(String email) throws SQLException, ClassNotFoundException {
        UserDAO dao = new UserDAO();
        return dao.getUserByEmail(email);
}

public static void insert(User user) throws ClassNotFoundException, SQLException {
        UserDAO dao = new UserDAO();
        dao.insertUser(user);
    }

}
