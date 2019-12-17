/**
 * @author Rohit Thorawade
 * @purpose Creating resetdto 
 *
 */
package com.bridgelabz.fundoo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Resetdto {
	
	@NotBlank(message="password is mandatory")
	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")
	private String newpassWord;
	
	@NotBlank(message="password is mandatory")
	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")
	private String confirmpassWord;

}
