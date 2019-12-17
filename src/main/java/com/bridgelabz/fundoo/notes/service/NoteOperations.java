/**
 * @author Rohit Thorawade
 * @Purpose Declare method that to be performed
 *
 */
package com.bridgelabz.fundoo.notes.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.notes.dto.Collaboratorsdto;
import com.bridgelabz.fundoo.notes.dto.Notedto;
import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.response.Response;

@Repository
public interface NoteOperations {

	public Response createNote(Notedto notedto, String token);// creating notes for user

	public Response updateNote(Notedto notedto, String id,String token);// update note for user

	public Response findNote(String id,String token); // find note method for user

	public Response deleteNote(String id,String token); // delete Note method for user

	public Response pin(String noteId, String token); // pin or unpinned method for user

	public Response archive(String noteId, String token);// archive or unarchive method for user

	public Response trash(String noteId, String token); // trash and restore method for user

	public List<Note> showNotes();// show notes of a particular user

	

	public List<?> sortNotesByTitle();// sort notes by title name in ascending order

	public List<?> sortNotesByDate(); // sort notes by date in ascending order
	
	public List<?> sortNotesByTitleInDesc();// sort notes by title name in descending order

	public List<?> sortNotesByDateInDesc(); // sort notes by date in descending order
	
	public Response addCollaborator(String noteId, Collaboratorsdto collaboratorsdto, String token); // add collaborator

	public Response removeCollaborator(String noteId,Collaboratorsdto collaboratorsdto, String token); // remove collaborator

	public Response addRemainder(String noteId,String token,int year,int month,int day,int hour,int minute,int second); //add remainder method for user
	
	public Response deleteRemainder(String noteId,String token);//delete remainder for user
	
	public Response editRemainder(String noteId,String token,int year,int month,int day,int hour,int minute,int second); //add remainder method for user


}
