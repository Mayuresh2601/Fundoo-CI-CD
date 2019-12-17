/**
 * @author Rohit Thorawade
 * @Purpose Performing register ,login,forget password,reset password for user 
 *
 */
package com.bridgelabz.fundoo.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.dto.Logindto;
import com.bridgelabz.fundoo.dto.Registerdto;
import com.bridgelabz.fundoo.dto.Resetdto;
import com.bridgelabz.fundoo.exception.FileException;
import com.bridgelabz.fundoo.exception.LoginException;
import com.bridgelabz.fundoo.jwt.utility.Jms;
import com.bridgelabz.fundoo.jwt.utility.Jwt;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.UserRepo;
import com.bridgelabz.fundoo.response.Response;

@Service
@PropertySource("classpath:MessageReference.properties")
public class ServiceImp implements Operations {

	@Autowired
	Jwt jwt;

	@Autowired
	JavaMailSender javamailsender;

	@Autowired
	Jms jms;

	@Autowired
	UserRepo repo; // creating user repository

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	Environment userenv;

	/**
	 * Adding User into database
	 * 
	 */ 
	
	
	@Override
	public Response addNewUser( Registerdto regDto) {
		ModelMapper mapper = new ModelMapper();

		User user = mapper.map(regDto, User.class);
			boolean passwordStatus = regDto.getPassWord().equals(regDto.getConfirmpassWord()); 
			
			if(passwordStatus)
			{
				user.setPassWord(bCryptPasswordEncoder.encode(regDto.getPassWord()));
	
				String token = jwt.createToken(user.getEmailId());
				jms.sendMail(user.getEmailId(), token);
				repo.save(user);
				return new Response(200,userenv.getProperty("USER_REGISTER_SUCCESSFULLY"),HttpStatus.OK);
			
			}	
			
			return new Response(400,userenv.getProperty("PASSWORD_NOT_MATCH"),HttpStatus.BAD_REQUEST);
			
		}


	/**
	 * Finding user by id
	 */
	@Override
	public Response findByUser(String id) {

		repo.findById(id);
		return new Response(200, userenv.getProperty("USER_FOUND"), HttpStatus.OK);
	}

	/**
	 * Display all the record present
	 */
	@Override
	public Response Show() {
	
		return new Response(200, userenv.getProperty("USER_DATA"), 	repo.findAll());
	}

	/**
	 * Delete Record by using Id
	 */
	@Override
	public Response deleteUser(String token) {
		String emailId =jwt.getUserToken(token);
		
			if(emailId!=null)
			{
			repo.deleteById(emailId);
	
			return new Response(200,(userenv.getProperty("RECORD_DELETED")),HttpStatus.OK);
			}
		
		return new Response(404,userenv.getProperty("UNAUTHORIZED_USER"),HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Update Record by using Id
	 */
	@Override
	public Response updateuser(Registerdto regdto, String token) {
		String emailId = jwt.getUserToken(token);
		if (emailId != null) {
			User userupdate = repo.findByEmailId(emailId);
			userupdate.setMobileNumber(regdto.getMobileNumber());
			repo.save(userupdate);
			return new Response(200,userenv.getProperty("RECORD_UPDATED"),HttpStatus.OK);
		}
		return new Response(404,userenv.getProperty("UNAUTHORIZED_USER"),HttpStatus.BAD_REQUEST);
	}

	/**
	 * Login Method for user
	 */
	@Override
	public Response login(String token, Logindto logindto) {

		String emailId = jwt.getUserToken(token);
		if (emailId != null) {
			ModelMapper mapper = new ModelMapper();
			User user = mapper.map(logindto, User.class);
			User user1 = repo.findByEmailId(user.getEmailId());
			boolean check = bCryptPasswordEncoder.matches(logindto.getPassWord(), user1.getPassWord());
			if (check) {
				user1.setValidate(true);

				return new Response( 200,(userenv.getProperty("USER_LOGIN_SUCCESSFULLY")),HttpStatus.OK);
			}
		}
		return new Response(404,(userenv.getProperty(MessageReference.NULL_VALUE)),HttpStatus.BAD_REQUEST);

	}

	/**
	 * Forget Password method
	 */
	public Response forgetPassword(String emailId) {
		
		String token = jwt.createToken(emailId);

		jms.sendMail("rohit.thorawade51@gmail.com", token);

		return new Response(200,userenv.getProperty("TOKEN_SEND_TO_MAIL"),HttpStatus.OK);

	}

	/**
	 * Reset Password for user
	 */
	public Response resetpassWord(Resetdto resetdto, String token) {
		String email = jwt.getUserToken(token);
		User user = repo.findByEmailId(email);
		if (email != null && user!=null) {

			
			user.setPassWord(resetdto.getNewpassWord());

			if (resetdto.getNewpassWord().contentEquals(resetdto.getConfirmpassWord())) {

				user.setPassWord(bCryptPasswordEncoder.encode(resetdto.getNewpassWord()));
				user.setConfirmpassWord(resetdto.getConfirmpassWord());
				repo.save(user);
				return new Response(200,userenv.getProperty("PASSWORD_UPDATED"),HttpStatus.OK);
			}

		}
		return  new Response(400,userenv.getProperty("PASSWORD_NOT_MATCHED"),HttpStatus.BAD_REQUEST);

	}

	/**
	 * @param token of the user
	 * @return verify if the user is verified
	 */
	public Response verify(String token) {
		String email = jwt.getUserToken(token);
		User user = repo.findByEmailId(email);
		if (email != null && user!=null) {
			
			if (user != null) {
				user.setValidate(true);
				repo.save(user);
				return new Response(200,(userenv.getProperty("VERIFY")),HttpStatus.OK);
			}
		}
		throw new LoginException(userenv.getProperty("UNAUTHORIZED_USER"));
	}

	/**
	 * Uploading profile photo for the user
	 */
	@Override
	public Response uploadProfilePic(String token, MultipartFile file) throws IOException {

		
		String emailId = jwt.getUserToken(token);
		User user = repo.findByEmailId(emailId);
		
	
			if (emailId != null && user!=null)
			{
				if(file.getOriginalFilename().contains(".jpg")||(file.getOriginalFilename().contains(".png"))||(file.getOriginalFilename().contains(".jpeg")))
				{
			
						File convertfile = new File("/home/admin-1/Desktop/" + file.getOriginalFilename());
						convertfile.createNewFile();
						FileOutputStream fout = new FileOutputStream(convertfile);
						String pic = "/home/admin-1/Desktop/" + file.getOriginalFilename();
						user.setProfilePic(pic);
						repo.save(user);
						fout.write(file.getBytes());
						fout.close();
		
						return  new Response(200,userenv.getProperty("UPLOAD_PIC"),HttpStatus.OK);
		
					
					
					
					
				}
				return new Response(400,userenv.getProperty("FILE_NOT_SUPPORTED"),HttpStatus.BAD_REQUEST);
				
			}
			return new Response(404,userenv.getProperty("UNAUTHORIZED_USER"),HttpStatus.BAD_REQUEST);
		
	}
		

	/**
	 * Removing profile picture  of the user
	 */
	@Override
	public Response removeProfilePic(String token)
	{
		String emailId = jwt.getUserToken(token);
		User user = repo.findByEmailId(emailId);

		if (emailId != null && user!=null ) 
		{
			
			user.setProfilePic(null);
			repo.save(user);
			return  new Response(200,(userenv.getProperty("DELETE_PROFILE_PIC")),HttpStatus.OK);
		}
		return new Response(404,userenv.getProperty("UNAUTHORIZED_USER"),HttpStatus.BAD_REQUEST);
	}

	/**
	 *Updating profile of the user
	 */
	@SuppressWarnings("resource")
	@Override
	public Response updateProfilePic(String token, MultipartFile file) throws FileNotFoundException, IOException {

	
		String emailId = jwt.getUserToken(token);
		User user = repo.findByEmailId(emailId);
	
		if(file.getOriginalFilename().contains(".jpg")||(file.getOriginalFilename().contains(".png"))||(file.getOriginalFilename().contains(".jpeg")))
		{
			
			if (emailId != null && user!=null)
			{
		
				String userpicdata = user.getProfilePic();
					
				
					if (!file.isEmpty() && userpicdata != null)
					{
						File convertfile = new File("/home/admin-1/Desktop/" + file.getOriginalFilename());
						convertfile.createNewFile();
						FileOutputStream fout = new FileOutputStream(convertfile);
		
						String pic = "/home/admin-1/Desktop/" + file.getOriginalFilename();
						
							if(userpicdata.equals(pic))
							{
								throw new FileException(userenv.getProperty("SAME_FILE"));
							}
						user.setProfilePic(pic);
		
						repo.save(user);
						fout.write(file.getBytes());
		
						fout.close();
		
						return  new Response(200,userenv.getProperty("UPDATED_PIC"),HttpStatus.OK);
		
				}
				return new Response(202,userenv.getProperty("SAME_FILE"),HttpStatus.OK);
		
			}
			return new Response(404,userenv.getProperty("UNAUTHORIZED_USER"),HttpStatus.BAD_REQUEST);
		}
		
		return new Response(404,userenv.getProperty("FILE_NOT_FOUND"),HttpStatus.BAD_REQUEST);

		

	}

}
