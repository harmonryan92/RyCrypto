package com.jdbc;

public class User 
{
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String salt;

	public User(String firstName, String lastName, String email, String password, String salt) 
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.salt = salt;
	}
	
	public User(int id, String firstName, String lastName, String email, String password) 
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
	
	public User(int id, String firstName, String lastName, String email) 
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		
	}
	
	public User(int id, String email, String password) 
	{
		this.id = id;
		this.email = email;
		this.password = password;
	}
	
	public User(int id, String email) 
	{
		this.id = id;
		this.email = email;
	}
	
	public User(String email, String password) 
	{
		this.email = email;
		this.password = password;
	}
	
	public User(String email)
	{
		this.email = email;
	}
	
	public int getId() 
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}
	public String getFirstName() 
	{
		return firstName;
	}
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}
	public String getLastName() 
	{
		return lastName;
	}
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}
	public String getEmail() 
	{
		return email;
	}
	public void setEmail(String email) 
	{
		this.email = email;
	}
	public String getPassword() 
	{
		return password;
	}
	public void setPassword(String email) 
	{
		this.email = password;
	}
	public void setSalt(String salt)
	{
		this.salt = salt;
	}
	public String getSalt()
	{
		return salt;
	}

	@Override
	public String toString() 
	{
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}
}
