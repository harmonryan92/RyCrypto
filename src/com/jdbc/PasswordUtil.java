package com.jdbc;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

class PasswordUtil 
{
	private static final Random RANDOM = new SecureRandom();													//initialize a new random class
	private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";	//set your alphabet for numbers and upper/lower case
	private static final int ITERATIONS = 10000;																//set iterations
	private static final int KEY_LENGTH = 256;																	//set key length as 256 for security
	
	public static String getSalt(int length) 						//function for getting a salt value to hash with the password
	{
		StringBuilder salt = new StringBuilder(length);
		
		for(int i = 0; i < length; i++)
		{
			salt.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));	//append the randomized chars to the salt based on the length of the pw. 
			
		}
		return new String(salt);
	}
	
	public static byte[] hash(char[] password, byte[] salt)							//function to hash the password using the salt
	{
		PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);	//set up a new PBEKeySpec using the password, salt, iterations, and key_length as parameters
		Arrays.fill(password, Character.MIN_VALUE);									//fill the arrays with the password and the min value of characters
		try
		{
			SecretKeyFactory key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");		//initialize a secret key factory and set appropriate algorithm as argument
			return key.generateSecret(spec).getEncoded();									//return the key and run the getEncoded function on it
		}
		catch (NoSuchAlgorithmException | InvalidKeySpecException e)
		{
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);	//error thrown if something goes wrong
		}
		finally
		{
			spec.clearPassword();
		}
	}
	
	public static String generateSecurePassword(String password, String salt)
	{
		String finalValue = null;
		byte[] securePassword = hash(password.toCharArray(), salt.getBytes());		//hash function will run using the password converted to a char array and the salt converted to bytes
		
		finalValue = Base64.getEncoder().encodeToString(securePassword);			//use base64 encoder to encode the salted and hashed password to a string
		return finalValue;
	}
	
	public static boolean verifyPassword(String providedPassword, String securePassword, String salt)
	{
		boolean verified = false;
		
		String newSecurePassword = generateSecurePassword(providedPassword, salt);	//generate a secure password using the provided password and salt to ensure it matches
		verified = newSecurePassword.equalsIgnoreCase(securePassword);				//make sure the passwords match
		
		return verified;
	}
}
