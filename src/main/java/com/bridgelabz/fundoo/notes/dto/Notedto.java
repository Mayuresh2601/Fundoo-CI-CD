/******************************************************************************
 *  Purpose: created Notedto 
 *
 *  @author  Rohit Thorawade
 *  @version 1.0
 ******************************************************************************/
package com.bridgelabz.fundoo.notes.dto;



import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Notedto {
	
	@NotBlank(message = "title is mandatory")
	private String title;
	@NotBlank(message = "description is mandatory")
	private String description;
	
	
	private String emailId;

}
