/**
 * @author Rohit Thorawade
 * @Purpose Creating Interface so as declare all the methods needs to be performed
 *
 */
package com.bridgelabz.fundoo.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.dto.Logindto;
import com.bridgelabz.fundoo.dto.Registerdto;
import com.bridgelabz.fundoo.dto.Resetdto;
import com.bridgelabz.fundoo.response.Response;

public interface Operations {

	public Response addNewUser(Registerdto regdto); // create addNewUser() method for add new user

	public Response findByUser(String id); // create findByUser() method for check user detail present or not

	public Response Show(); // create Show() method for display all user details

	public Response deleteUser(String token); // create deleteUser() method for remove particular user

	public Response updateuser(Registerdto regdto, String token); // create UpdateUser() method for update particular user

	public Response login(String token, Logindto logindto);// creating login for user

	public Response forgetPassword(String emailId);// creating forget password() method for user

	public Response resetpassWord(Resetdto resetdto, String token);// creating reset method for user
	
	public Response uploadProfilePic(String token,MultipartFile file) throws FileNotFoundException, IOException;// add  profile Image

	public Response removeProfilePic(String token);//remove profile photos

	public Response updateProfilePic(String token,MultipartFile file) throws FileNotFoundException, IOException;// updated  profile Image

	public Response verify(String token);
	

	
}
