/**
 * @author Rohit Thorawade
 * @Purpose Creating label model class
 *
 */
package com.bridgelabz.fundoo.label.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.bridgelabz.fundoo.notes.model.Note;

import lombok.Data;

@Data
public class Label {

	@Id
	private String labelId;

	private String labeltitle;

	
	private String emailId;
	
	private LocalDateTime createdate;
	private LocalDateTime editdate;

	@DBRef
	List<Note> notelist=new ArrayList<>();

}
