package com.uit.controller;

import com.uit.common.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;



@ManagedBean (name ="userss")
@SessionScoped
public class Users {
    private int id;
    private String fullname;
    private String email;
    private String phone;
    private String username;
    private String password;
    

    public Users() {
    }

    public Users(String fullname, String email, String phone, String username, String password) {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public List<Users> getAll() {
        try {
            List<Users> users = new ArrayList<>();
            
            String sql = "select * from users";
            
            PreparedStatement ps = DBConnection.getConnect().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Users user = new Users();
                user.setId(rs.getInt("id"));
                user.setFullname(rs.getString("fullname"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
	

    public void createOne() throws SQLException{
        try {

            String sql = "insert into USERS (FULLNAME, EMAIL, PHONE, USERNAME, PASSWORD) "
                + "VALUES(?,?,?,?,?)";
            
            
            PreparedStatement ps = DBConnection.getConnect().prepareStatement(sql);
            
            ps.setString(1,this.getFullname());
            ps.setString(2,this.getEmail());
            ps.setString(3,this.getPhone());
            ps.setString(4,this.getUsername());
            ps.setString(5,this.getPassword());
            
            ps.execute();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
    public void delete(String Username) {
        try {
            String sql = "delete from USERS where USERNAME = ?";
            PreparedStatement ps = DBConnection.getConnect().prepareStatement(sql);
            ps.setString(1, Username);
            ps.execute();
            //return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //return false;
        }
    }
    
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap(); 
    
    public String editUser() throws SQLException{
        FacesContext fc = FacesContext.getCurrentInstance();

        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();

	String Username = params.get("username");
        try {
            
            String sql = "select * from USERS where USERNAME = ?";
            
            PreparedStatement ps = DBConnection.getConnect().prepareStatement(sql);
            ps.setString(1, Username);
            System.out.println(Username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Users user =new Users();
                user.setId(rs.getInt("id"));
                user.setFullname(rs.getString("fullname"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            
            sessionMap.put("edituser", user);  
            
            
            return "/edit.xhtml?faces-redirect=true"; 
        }
        catch (Exception e) {
            System.out.println(e);
            return "";
        }
    }

    public String update() {
        try {
            String sql = "update USERS "
                    + "set FULLNAME = ? , EMAIL = ?, PHONE = ? "
                    + "where USERNAME = ?;";//Code lạ phải nghiên cứu
            PreparedStatement ps = DBConnection.getConnect().prepareStatement(sql);
            ps.setString(1, this.fullname);
            ps.setString(2, this.email);
            ps.setString(3, this.phone);
            
            Map<String, String> params =FacesContext.getCurrentInstance().
                   getExternalContext().getRequestParameterMap();
            String parameterUsername = params.get("username");
            ps.setString(4, parameterUsername);
            
            System.out.println(parameterUsername);
            ps.execute();
            return "/index.xhtml?faces-redirect=true";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "/index.xhtml?faces-redirect=true";
        }
    }
    
}
