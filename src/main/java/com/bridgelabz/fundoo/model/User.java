/******************************************************************************
. *  Purpose: create user model 
 *
 *  @author  Rohit Thorawade
 *  @version 1.0
 ******************************************************************************/

package com.bridgelabz.fundoo.model;


import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.bridgelabz.fundoo.notes.model.Note;

import lombok.Data;

//Automatically generate getter and setter

@Document()
@Data
public class User {

	@Id
	private String id;

	@NotBlank(message = "First Name is mandatory")
	private String firstName;

	@NotBlank(message = "Last Name is mandatory")
	private String lastName;

	@NotBlank(message = "User Name is mandatory")
	private String userName;

	@NotBlank(message = "description is mandatory")
	private String emailId;

	//Mobile Number in Long Data Type
	private long mobileNumber;

	@NotBlank(message = "Password is mandatory")
	private String passWord;

	@NotBlank(message = "confirmPassword is mandatory")
	private String confirmpassWord;

	public boolean validate;
	
	
	private String profilePic;
	
	
	private List<Note> notelist = new ArrayList<>();
	
	
	

}
