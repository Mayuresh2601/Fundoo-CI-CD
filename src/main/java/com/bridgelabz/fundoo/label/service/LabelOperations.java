/**
 * @author Rohit Thorawade
 * @Purpose Declaring method to be performed
 *
 */
package com.bridgelabz.fundoo.label.service;

import java.util.List;

import com.bridgelabz.fundoo.label.dto.Labeldto;
import com.bridgelabz.fundoo.label.model.Label;
import com.bridgelabz.fundoo.response.Response;

public interface LabelOperations {

	public Response createLabel(Labeldto labeldto, String token);// creating label for user

	public Response deleteLabel(String noteId,String id,String token); // delete label for user

	public Response updateLabel(String id,Labeldto labeldto, String token);// update label for user

	public List<Label> show();// show all the record

	public Response findlabel(String id,String token);// find label using id
	
	public Response addLabelToNote(String noteId, String labelId, String token);// add label to note
}
