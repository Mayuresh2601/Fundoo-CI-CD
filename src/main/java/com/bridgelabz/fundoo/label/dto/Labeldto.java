/**
 * @author Rohit Thorawade
 * @Purpose Creating label dto class 
 *
 */
package com.bridgelabz.fundoo.label.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Labeldto {
	
	@NotBlank(message = "title is mandatory")
	private String labeltitle;

}
