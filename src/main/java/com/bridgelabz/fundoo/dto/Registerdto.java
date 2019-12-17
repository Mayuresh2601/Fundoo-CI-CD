/**
 * @author Rohit Thorawade
 * @Purpose Creating Registerdto
 *
 */
package com.bridgelabz.fundoo.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class Registerdto {
	//Blank Message
	@NotBlank(message="firstName is mandatory")
	private String firstName;
	@NotBlank(message="lastName is mandatory")
	private String lastName;
	@NotBlank(message="userName is mandatory")
	private String userName;
	
	@NotBlank(message="EmailId is mandatory")
	@Pattern(regexp ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	private String emailId;
	
	private long mobileNumber;
	
	@NotBlank(message="password is mandatory")
	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")
	private String passWord;

	@NotBlank(message="confirmpassWord is mandatory")
	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")
	private String confirmpassWord;
	
	private boolean validate;

}
