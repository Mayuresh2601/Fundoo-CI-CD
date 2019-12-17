/**
 * @author Rohit Thorawade
 * @Purpose Note model class
 *
 */
package com.bridgelabz.fundoo.notes.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bridgelabz.fundoo.label.model.Label;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Document("Notes")
@Data
public class Note {

	

	@Id
	private String id;
	
	private String title;
	private String description;

	private String emailId;

	private boolean pin;
	private boolean trash;
	private boolean archive;

	private LocalDateTime createdate;
	private LocalDateTime editdate;

	List<String> collaboratorsList = new ArrayList<>();
	
	private String reminder;

	@JsonIgnore
	@DBRef(lazy = true)
	List<Label> labellist=new ArrayList<>();


	

	

}
