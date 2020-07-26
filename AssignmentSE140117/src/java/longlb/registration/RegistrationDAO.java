/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longlb.registration;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import longlb.util.DBHelpers;

/**
 *
 * @author Long Le
 */
public class RegistrationDAO implements Serializable {

    public RegistrationDTO checkLogin(String username, String password)
	    throws SQLException, NamingException {
	Connection con = null;
	PreparedStatement stm = null;
	ResultSet rs = null;

	try {
	    //1. Make connection
	    con = DBHelpers.makeConnection();

	    if (con != null) {
		//2. Create SQL String 
		String sql = "SELECT fullname, isAdmin "
			+ "FROM Registration "
			+ "WHERE username=? AND password=?";

		//3. Create query statement
		stm = con.prepareStatement(sql);
		stm.setString(1, username);
		stm.setString(2, password);

		//4. query data
		rs = stm.executeQuery();

		//5. Process data
		if (rs.next()) {
		    String fullname = rs.getString("fullname");
		    boolean role = rs.getBoolean("isAdmin");
		    
		    RegistrationDTO dto = new RegistrationDTO(username, password, fullname, role);
		    return dto;
		}//end if rs

	    }//end if con is not null
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (stm != null) {
		stm.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
	
	return null;
    }
    
    private List<RegistrationDTO> listAccount;

    public List<RegistrationDTO> getListAccount() {
	return listAccount;
    }
    
    public void searchFullname(String searchValue) 
	    throws SQLException, NamingException{
	Connection con = null;
	PreparedStatement stm = null;
	ResultSet rs = null;
	
	try {
	    //1. make connection
	    con = DBHelpers.makeConnection();
	    
	    if (con != null){
		//2. Create sql string
		String sql = "SELECT username, password, fullname, isAdmin "
			+ "FROM Registration "
			+ "WHERE fullname LIKE ?";
		
		//3. Create query statement
		stm = con.prepareStatement(sql);
		stm.setString(1, "%" + searchValue + "%");
		
		//4. query data
		rs = stm.executeQuery();
		
		//5. process data
		while (rs.next()){
		    String username = rs.getString("username");
		    String password = rs.getString("password");
		    String fullname = rs.getString("fullname");
		    boolean role = rs.getBoolean("isAdmin");
		    
		    RegistrationDTO dto = new RegistrationDTO(username, password, fullname, role);
		    
		    if (listAccount == null){
			listAccount = new ArrayList<>();
		    }
		    
		    listAccount.add(dto);
		}//end while rs
	    }//end if con is not null
	} finally {
	    if (rs != null){
		rs.close();
	    }
	    if (stm != null){
		stm.close();
	    }
	    if (con != null){
		con.close();
	    }
	}
    }
    
    public boolean deleteAccount(String username) 
	    throws SQLException, NamingException{
	Connection con = null;
	PreparedStatement stm = null;
	
	try {
	    //1. Make connection
	    con = DBHelpers.makeConnection();
	    
	    if (con != null){
		//2. Create SQl string
		String sql = "DELETE FROM Registration "
			+ "WHERE username=?";
		
		//3. Create query statement
		stm = con.prepareStatement(sql);
		stm.setString(1, username);
		
		//4. Query data
		int row = stm.executeUpdate();
		
		//5. Process data
		if (row > 0){
		    return true;
		}//end if row
		
	    }//end if con is not null
	} finally {
	    if (stm != null){
		stm.close();
	    }
	    if (con != null){
		con.close();
	    }
	}
	return false;
    }
    
    public boolean updateAccount (String username, String password, String fullname, boolean role)
	    throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement stm = null;
	
	try {
	    //1.Make connection
	    con = DBHelpers.makeConnection();
	    if (con != null){
		//2. Create sql string
		String sql = "UPDATE Registration "
			+ "SET password=?, fullname=?, isAdmin=? "
			+ "WHERE username=?";
		
		//3. Create query statement
		stm = con.prepareStatement(sql);
		stm.setString(1, password);
		stm.setString(2, fullname);
		stm.setBoolean(3, role);
		stm.setString(4, username);
		
		//4. Query data
		int row = stm.executeUpdate();
		
		//5. Process data
		if (row > 0){
		    return true;
		}//end if row
	    }//end if con is not null
	} finally {
	    if (stm != null){
		stm.close();
	    }
	    if (con != null){
		con.close();
	    }
	}
	return false;
    }
    
    public boolean createAccount (String username, String password, String fullname, boolean role) 
	    throws NamingException, SQLException{
	Connection con = null;
	PreparedStatement stm = null;
	
	try {
	    //1. Make connection
	    con = DBHelpers.makeConnection();
	    
	    if (con != null){
		//2. Create sql string
		String sql = "INSERT INTO Registration(username, password, fullname, isAdmin) "
			+ "VALUES(?,?,?,?)";
		
		//3. Create query string
		stm = con.prepareStatement(sql);
		stm.setString(1, username);
		stm.setString(2, password);
		stm.setString(3, fullname);
		stm.setBoolean(4, role);
		
		//4. Query
		int row = stm.executeUpdate();
		
		//5. Process data
		if (row > 0){
		    return true;
		}
		
	    }//end if con is not null
	} finally {
	    if (stm != null){
		stm.close();
	    }
	    if (con != null){
		con.close();
	    }
	}
	return false;
    }
}
