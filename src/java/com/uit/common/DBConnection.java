/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uit.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author hp
 */

//Tạo ra một cái class kết nối tới database
public class DBConnection {
    //Cái URL là Database URL
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=WEB_TRUYEN_TRANH";
    //User password là cái của mình kết nối tới database
    private static final String USER = "sa";
    private static final String PASSWORD = "123";
        
    
    public static Connection getConnect() {
        try {
            //Cái forname là driver class
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //M cứ xem như nó gọi tới cái hàm khác đi
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    //Làm một cái hàm main để kiểm tra kết nối
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnect();
//        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        try {
            if(conn != null && !conn.isClosed()) {
                System.out.println("Kết nối tới database web_truyen_tranh thành công");
            } else {
                System.out.println("No");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
