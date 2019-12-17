/**
 * @author Rohit Thorawade
 * @purpose Creating logindto 
 *
 */
package com.bridgelabz.fundoo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class Logindto
{
	
	@NotBlank(message="EmailId is mandatory")
	@Pattern(regexp ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	private String emailId;
	
	

	@NotBlank(message="password is mandatory")
	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")
	private String passWord;

	
}
