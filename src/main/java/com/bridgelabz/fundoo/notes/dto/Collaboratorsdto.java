
/**
 * @author Rohit Thorawade
 * @Purpose Creating Collaboratorsdto 
 *
 */
package com.bridgelabz.fundoo.notes.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class Collaboratorsdto {

	@NotBlank(message = "CollaboratorsEmail is mandatory")
	@Pattern(regexp ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	private String collaboratorsEmail;

}
