package com.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.jdbc.User;

public class UserDbUtil 
{	
	private DataSource dataSource;
	
	public UserDbUtil(DataSource theDataSource) 
	{
		dataSource = theDataSource;
	}

	//method for listing all users
	public List<User> getUsers() throws Exception 
	{	
		List<User> users = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myResults = null;
		
		try 
		{
			myConn = dataSource.getConnection();					//get a connection
			String sql = "SELECT * FROM users order by last_name";	//create sql statement
			myStmt = myConn.createStatement();
			myResults = myStmt.executeQuery(sql);					//execute query
			
			while (myResults.next()) 								//process result set
			{		
				int id = myResults.getInt("user_id");				//retrieve data from result set row
				String firstName = myResults.getString("first_name");
				String lastName = myResults.getString("last_name");
				String email = myResults.getString("email");
				String password = myResults.getString("password");
				User tempUser = new User(id, firstName, lastName, email, password);		//create new user object
				users.add(tempUser);								//add new user to list of users
			}
			return users;
		}
		finally 
		{
			close(myConn, myStmt, myResults);					//close JDBC objects
		}
	}
	
	//method for adding a new user (commented out lines are for when the db is split into more tables than just the users one)
	public void addUser (User user) throws Exception 
	{	
		Connection myConn = null;
		PreparedStatement myStmt = null;
//		PreparedStatement myStmt2 = null;
		try 
		{
			myConn = dataSource.getConnection();						//get db connection
			String sql = "INSERT into users "							//create sql for the insert
						+ "(first_name, last_name, email, password, salt) "
						+ "values (?, ?, ?, ?, ?)";
//			String sql2 = "insert into user_passwords "					//sql for inserting the password into the passwords table (once that feature is in)
//						+ "(user_password) "
//						+ "values (?)";
			
			myStmt = myConn.prepareStatement(sql);						//prepare statement with the sql inserts
//			myStmt2 = myConn.prepareStatement(sql2);
			
			myStmt.setString(1,  user.getFirstName());					//set param values
			myStmt.setString(2,  user.getLastName());
			myStmt.setString(3,  user.getEmail());
			myStmt.setString(4,  user.getPassword());
			myStmt.setString(5,  user.getSalt());
//			myStmt2.setString(1,  user.getPassword());
			
			myStmt.execute();											//execute the statements containing the sql inserts
//			myStmt2.execute();
		}
		finally 
		{
			close(myConn, myStmt, null);								//clean up JDBC objects
		}
	}
	
	//method for deleting user
	public void deleteUser(String theUserId) throws Exception 
	{	
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try 
		{
			int userId = Integer.parseInt(theUserId);				//convert user ID into int
			myConn = dataSource.getConnection();					//connect to db
			String sql = "DELETE FROM users WHERE user_id=?";		//sql for deleting student
			myStmt = myConn.prepareStatement(sql);					//prepare statement
			myStmt.setInt(1, userId);								//set params		
			myStmt.execute();										//execute sql statement
		}
		finally 
		{
			close(myConn, myStmt, null);							//clean up JDBC code
		}
	}
	
	//method for updating a user
	public void updateUser(User theUser) throws Exception 
	{	
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try 
		{
			myConn = dataSource.getConnection();				//get db connection
			String sql = "UPDATE users "						//create sql statement for updating
						+ "SET first_name=?, last_name=?, email=? "
						+ "WHERE user_id=?";
			myStmt = myConn.prepareStatement(sql);				//prepare statement
			myStmt.setString(1,  theUser.getFirstName());		//set params
			myStmt.setString(2,  theUser.getLastName());
			myStmt.setString(3,  theUser.getEmail());
			myStmt.setInt(4,  theUser.getId());
			myStmt.execute();									//execute sql statement
		}
		finally 
		{
			close(myConn, myStmt, null);						//clean up JDBC objects
		}
	}
	
	//method for getting a user by ID
	public User getUser(String theUserId) throws Exception 
	{	
		User theUser = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myResults = null;
		int userId;
		
		try 
		{
			userId = Integer.parseInt(theUserId);				//convert user ID to int
			myConn = dataSource.getConnection();				//get connection to database
			String sql = "SELECT * FROM users WHERE user_id = ?";	//create sql statement to get selected user
			myStmt = myConn.prepareStatement(sql);				//create prepared statement
			myStmt.setInt(1,  userId);							//set params
			myResults = myStmt.executeQuery();					//execute statement
			
			if (myResults.next()) 								//retrieve data from result set row
			{
				String firstName = myResults.getString("first_name");
				String lastName = myResults.getString("last_name");
				String email = myResults.getString("email");
				theUser = new User(userId, firstName, lastName, email);		//use the userId during construction
			}
			else 
			{
				throw new Exception("Could not find the user id: " + userId);
			}
			return theUser;
		}
		finally 
		{
			close(myConn, myStmt, myResults);				//clean up JDBC objects
		}
	}

	//method for getting user by email
	public int getUserId(String theUserEmail) throws Exception 
	{	
		User theUser = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myResults = null;
		String userEmail = null;
		
		try 
		{
			myConn = dataSource.getConnection();				//get connection to database
			String sql = "SELECT * FROM users WHERE email=?";	//create sql statement to get selected user
			myStmt = myConn.prepareStatement(sql);				//create prepared statement
			myStmt.setString(1,  userEmail);					//set params
			myResults = myStmt.executeQuery();					//execute statement
			
			if (myResults.next()) 								//retrieve data from result set row
			{
				String email = myResults.getString("email");
				int userId = myResults.getInt("user_id");
				theUser = new User(userId, email);				//use the userId during construction
			}
			else 
			{
				throw new Exception("Could not find the user id");
			}
			return theUser.getId();
		}
		finally 
		{
			close(myConn, myStmt, myResults);				//clean up JDBC objects
		}
	}
	
	//method for checking login
	public User checkLogin(String submittedEmail, String submittedPassword) throws SQLException
	{
		User theUser = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myResults = null;
		
		try															//see if the email address entered is in the database
		{
			myConn = dataSource.getConnection();
			String sql = "SELECT * FROM users WHERE email = ?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, submittedEmail);
			myResults = myStmt.executeQuery();
			
			while (myResults.next())							//pull the salt and stored password and run the verifypassword function using the submitted password to validate
			{
				String salt = myResults.getString("salt");
				String storedPassword = myResults.getString("password");
				boolean verified = PasswordUtil.verifyPassword(submittedPassword, storedPassword, salt);
				
				if(verified)									//add user information to the user object and return it if it passes
				{
					theUser = new User(submittedPassword, submittedEmail);
				}
			}
			return theUser;
		}
		finally
		{
			close(myConn, myStmt, myResults);
		}
	}

	//method for close
	private void close(Connection myConn, Statement myStmt, ResultSet myResults) 
	{
		try 
		{
			if (myResults != null) 
			{
				myResults.close();
			}
			
			if (myStmt != null) 
			{
				myStmt.close();
			}
			
			if (myConn != null) 
			{
				myConn.close();   			// doesn't really close it ... just puts back in connection pool
			}
		}
		catch (Exception exc) 
		{
			exc.printStackTrace();
		}
	}	
}

