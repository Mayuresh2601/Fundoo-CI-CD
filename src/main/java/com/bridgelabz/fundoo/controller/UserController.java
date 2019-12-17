/**
 * @author Rohit Thorawade
 * @Purpose Creating Registeration,login Controller for User
 *
 */
package com.bridgelabz.fundoo.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.dto.Logindto;
import com.bridgelabz.fundoo.dto.Registerdto;
import com.bridgelabz.fundoo.dto.Resetdto;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.service.ServiceImp;

@RestController
@RequestMapping("/api")

public class UserController {

	@Autowired
	private ServiceImp serviceimp;
	
	@Autowired
	Environment userenv;
	/**
	 * @param Registerdto
	 * @return if added successfully print message
	 */
	@PostMapping("/adduser")
	public ResponseEntity<Response> addNewUser(@Valid @RequestBody Registerdto regdto) {


		return new ResponseEntity<Response>( serviceimp.addNewUser(regdto) , HttpStatus.OK);

	}

	/**
	 * @param Find User by using Id
	 * @return user data if present
	 */
	@GetMapping("/finduser")
	public ResponseEntity<Response> findByuser(@RequestHeader String id) {

		return new ResponseEntity<Response>(serviceimp.findByUser(id),HttpStatus.OK);

	}

	/**
	 * @param person
	 * @param Object id
	 * @return updated information at particular id
	 */
	@PutMapping("/updateuser")
	public ResponseEntity<Response> updateuser(@Valid @RequestBody Registerdto regdto, @RequestHeader String token) {

	
		return new ResponseEntity<Response>(serviceimp.updateuser(regdto, token), HttpStatus.OK);
	}

	/**
	 * @param Object id
	 * @return Delete the record by using particular id
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteUser(@RequestHeader String token) {
	
		return new ResponseEntity<Response>(serviceimp.deleteUser(token), HttpStatus.OK);

	}

	/**
	 * @return show all users information
	 */
	@GetMapping("/show")
	public ResponseEntity<Response> Show() {

		return new ResponseEntity<Response>(serviceimp.Show(),HttpStatus.OK);
	}

	/**
	 * @param logindto
	 * @return User Login option for user
	 */
	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestHeader String token, @Valid @RequestBody Logindto logindto) {
		
		return new ResponseEntity<Response>(serviceimp.login(token, logindto),HttpStatus.OK);

	}

	/**
	 * @param logindto
	 * @return Forget password for user
	 */
	@PostMapping("/forgetpassword")
	public ResponseEntity<Response> forgetPassword(@Valid @RequestHeader String emailId) {
	
		return new ResponseEntity<Response>(serviceimp.forgetPassword(emailId), HttpStatus.OK);

	}

	/**
	 * @param resetdto
	 * @param token
	 * @return Reset password option for user
	 */
	@PostMapping("/reset")
	public ResponseEntity<Response> resetPassword(@Valid @RequestBody Resetdto resetdto, @RequestHeader String token) {

	
		return new ResponseEntity<Response>(serviceimp.resetpassWord(resetdto, token),HttpStatus.OK);

	}

	/**
	 * @param token of the user
	 * @return if validate properly then its true or else false
	 */
	@PostMapping("/verify")
	public ResponseEntity<Response> verifyUser(@RequestHeader String token) {
		return new ResponseEntity<Response>(serviceimp.verify(token),HttpStatus.OK);
		// return serviceimp.verify(token);
	}
	
	/**
	 * @param token of the user
	 * @param file 
	 * @return profile upload successfully
	 * @throws IOException
	 */
	@PostMapping("/uploadpic")
	public ResponseEntity<Response> uploadProfilePic(@RequestHeader String token,@RequestParam ("file") MultipartFile file) throws IOException
	{
		
		return new ResponseEntity<Response>(serviceimp.uploadProfilePic(token,file), HttpStatus.OK);
	}
	
	/**
	 * @param token of the user
	 * @return remove profile photo
	 */
	@DeleteMapping("/removepic")
	public ResponseEntity<Response> removeProfilePic(@RequestHeader String token)
	{
	
		return new ResponseEntity<Response>(serviceimp.removeProfilePic(token),HttpStatus.OK );
	}
	
	
	/**
	 * @param token of the user
	 * @param file -file to be uploaded as profile photo
	 * @return uploading photo
	 * @throws IOException
	 */
	@PutMapping("/updatepic")
	public ResponseEntity<Response> updateProfilePic(@RequestHeader String token,@RequestParam ("file") MultipartFile file) throws IOException
	{
	
		return new ResponseEntity<Response>(serviceimp.updateProfilePic(token,file),HttpStatus.OK);
	}
	

}
