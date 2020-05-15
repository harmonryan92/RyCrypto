package com.jdbc;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private UserDbUtil userDbUtil;
	
	@Resource(name="jdbc/rycrypto_site")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException 
	{
		super.init();
		
		try 
		{
			userDbUtil = new UserDbUtil(dataSource);			//create user db util and pass in the connection pool / datasource
		}
		catch (Exception exc) 
		{
			throw new ServletException(exc);
		}
	}
	
	//doGet for loading, listing, deleting users and loading the index page
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		try 
		{
			String theCommand = request.getParameter("command");		//read the "command" parameter
			
			if(theCommand == null) 					//if command is missing, default to index
			{
				theCommand = "INDEX";
			}
			
			switch (theCommand) 					//switch to route to the appropriate method
			{
				case "LIST":
					listUsers(request, response);
					break;	
				case "LOAD":
					loadUser(request, response);
					break;			
				case "DELETE":
					deleteUser(request, response);
					break;				
				case "INDEX":
					index(request, response);
					break;				
				default:
					index(request, response);
			}
		}
		catch (Exception exc) 
		{
			throw new ServletException(exc);
		}
	}
	
	private void listUsers(HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		List<User> users = userDbUtil.getUsers();												//get users from dbUtil
		request.setAttribute("USER_LIST", users);												//add users to the request
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-users.jsp");			//send to JSP page (view)
		dispatcher.forward(request, response);
	}
	
	private void loadUser(HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		String theUserId = request.getParameter("id");		//read user id from form data
		User theUser = userDbUtil.getUser(theUserId);		//get user from database
		request.setAttribute("THE_USER", theUser);			//place user in the request attribute
		RequestDispatcher dispatcher = 						//send to jsp page: update-user-form.jsp
				request.getRequestDispatcher("/update-user-form.jsp");
		dispatcher.forward(request, response);
	}
	
	private void deleteUser(HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		String theUserId = request.getParameter("id");		//read User id from form data
		userDbUtil.deleteUser(theUserId);					//delete user from database
		listUsers(request, response);						//send back to list users page
	}
	
	//function to navigate to index page
	private void index (HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request,  response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		try 
		{
			String theCommand = request.getParameter("command"); 		//read the "command" parameter
			
			if(theCommand == null) 					//if command is missing, default to listing users
			{
				theCommand = "INDEX";
			}
			
			switch (theCommand) 		//switch to route to the appropriate command
			{
				case "LOGIN":
					login(request, response);
					break;
					
				case "ENCRYPT":
					encrypt(request, response);
					break;
					
				case "DECRYPT":
					decrypt(request, response);
					break;
				
				case "ADD":
					addUser(request, response);
					break;
					
				case "UPDATE":
					updateUser(request, response);
					break;
			}
		}
		catch (Exception exc) 
		{
			throw new ServletException(exc);
		}
	}	
		
	private void login(HttpServletRequest request, HttpServletResponse response)
		throws Exception 
	{
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		UserDbUtil userDbUtil = new UserDbUtil(dataSource);
		try
		{
			User user = userDbUtil.checkLogin(email, password);			//call the checkLogin function to make sure it matches w/ a record in the DB
			String destPage = "login-error.jsp";						//dest page is set to the error page for failures
			
			if (user != null)											//ensure the user isn't null so it will work
			{
				HttpSession session = request.getSession();
				session.setAttribute("email", email);					//set email attribute to the inputted email
				destPage = "home.jsp";									//set destPage to home for successes
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);	//setup a request dispatcher using the dest page based off the if statement
	        dispatcher.forward(request, response);									//forward to dest page
		}
		catch (Exception exc) 
		{
			throw new ServletException(exc);
		}
	}
	
	private static final String ALGORITHM = "AES";                          //sets up AES as the package to use for our ALGORITHM, set to a constant as it won't ever change
	private static final String TRANSFORMATION = "AES";  
	
	//encrypt a file
	private void encrypt(HttpServletRequest request, HttpServletResponse response) 
            throws CryptoException, IOException, Exception                                     
    {
        String key = request.getParameter("key");
        String key2 = request.getParameter("key2");
        if (key != null && key2 != null)
        {
        	String destPage = "encrypt-error.jsp";
        	if (key.equals(key2))
            {
        		String filePath = request.getParameter("filePath");
        		File inputFile = new File (filePath);
        		
        		cryptoFunction(Cipher.ENCRYPT_MODE, key, inputFile);    //call the crypto function (below) and use the ENCRYPT_MODE, passing the key, original file, and encrypted file (to be) into it
        		destPage = "encrypt-successful.jsp";
//        		response.sendRedirect("encrypt-successful.jsp");
            }
        	RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);	//setup a request dispatcher using the dest page based off the if statement
	        dispatcher.forward(request, response);									//forward to dest page
        }	
	}
	
	//decrypt a file
	private void decrypt(HttpServletRequest request, HttpServletResponse response) 
            throws CryptoException, IOException 				//use custom exception to eliminate throw clause
	{                                      
		String key = request.getParameter("key");
		String filePath = request.getParameter("filePath");
		File inputFile = new File (filePath);

        cryptoFunction(Cipher.DECRYPT_MODE, key, inputFile);    //call the crypto function (below) and use the DECRYPT_MODE method, passing the key, original file, and encrypted file (to be) into it
        response.sendRedirect("decrypt-successful.jsp");
	}
	
	//function for encryption/decryption algorithm
	private static void cryptoFunction(int cipherMode, String key, File inputFile) //the method that will be doing all of the work
            throws CryptoException 
	{
        try 
        {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);   //generate a secure key using the AES algorithm
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);             //create a cipher using the AES package. TRANSFORMATION will call the package and have it do the operations needed
            cipher.init(cipherMode, secretKey);                             //initialize a new cipher that takes in the cipherMode (Encrypt or Decrypt) and the secretKey generated above

            FileInputStream input = new FileInputStream(inputFile);         //initialize a new inputStream for the input file
            byte[] inputBytes = new byte[(int) inputFile.length()];         //convert it to bytes based on the length of the file
            input.read(inputBytes);                                         //read the bytes once we have them

            byte[] outputBytes = cipher.doFinal(inputBytes);                //finish the operation(doFinal) based on the input bytes and add them to the output bytes array

            FileOutputStream output = new FileOutputStream(inputFile);     //initialize a new outputStream for the output(encrypted/decrypted) file
            output.write(outputBytes);                                      //write the new file using the newly encrypted/decrypted bytes

            input.close();                                                  //close streams
            output.close();
        } 
        catch (NoSuchPaddingException | NoSuchAlgorithmException          //exceptions to catch errors (so if the TRY fails, we know why it failed)
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) 
        {
            throw new CryptoException("Error encrypting/decrypting file", ex);  //throw our custom exception again
        }
    }
	
	private void updateUser(HttpServletRequest request, HttpServletResponse response)
		throws Exception 
	{	
		int id = Integer.parseInt(request.getParameter("id"));			//read user info from form data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		User theUser = new User (id, firstName, lastName, email);		//create new user object
		userDbUtil.updateUser(theUser);								//perform update on the database
		listUsers(request, response);							//send  back to the home page
	}
	
	private void addUser(HttpServletRequest request, HttpServletResponse response)
		throws Exception 
	{	
		String firstName = request.getParameter("firstName"); 		//read user info from form data
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String salt = PasswordUtil.getSalt(30);
		
		if (password != null && password2 != null)
		{
			String destPage = "add-user-error.jsp";
			if (password.equals(password2))
			{
				//encode password here
				String securePassword = PasswordUtil.generateSecurePassword(password, salt);
				User theUser = new User(firstName, lastName, email, securePassword, salt);		//create a new user object
				userDbUtil.addUser(theUser);								//add user to database
				destPage = "login-new-user.jsp";					//set destPage to the login page for a new user
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);	//setup a request dispatcher using the dest page based off the if statement
	        dispatcher.forward(request, response);									//forward to dest page
		}	
	}
}
