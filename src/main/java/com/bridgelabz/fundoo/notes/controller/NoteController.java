/******************************************************************************
 *  Purpose: created Note controller for performing curd operations 
 *
 *  @author  Rohit Thorawade
 *  @version 1.0
 ******************************************************************************/

package com.bridgelabz.fundoo.notes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.notes.dto.Collaboratorsdto;
import com.bridgelabz.fundoo.notes.dto.Notedto;
import com.bridgelabz.fundoo.notes.service.NoteImpl;
import com.bridgelabz.fundoo.response.Response;

@RestController
@RequestMapping("/fundoonotes")
public class NoteController {

	@Autowired
	NoteImpl noteimpl;
	
	@Autowired
	Environment noteenv;

	/**
	 * @param notedto
	 * @param token
	 * @return response if note created successfully or not
	 */
	@PostMapping("/createnote")
	public ResponseEntity<Response> createNote(@Valid @RequestBody Notedto notedto, @RequestHeader String token) {
	
		return new ResponseEntity<Response>(noteimpl.createNote(notedto, token),HttpStatus.OK);
	}

	/**
	 * @param note
	 * @param id
	 * @return response if note updated successfully or not
	 */
	@PutMapping("/updatenote")
	public ResponseEntity<Response> updateNote(@Valid @RequestBody Notedto notedto, @RequestHeader String id,@RequestHeader String token) {
		
		
		return new ResponseEntity<Response>(noteimpl.updateNote(notedto, id,token), HttpStatus.OK);
	}

	/**
	 * @param id
	 * @return response of searched note
	 */
	@GetMapping("/findnote")
	public ResponseEntity<Response> findNote(@RequestHeader String id,@RequestHeader String token) {
		return new ResponseEntity<Response>( noteimpl.findNote(id, token),HttpStatus.OK);

	}

	/**
	 * @param id
	 * @return response regarding deleted note
	 */
	@DeleteMapping("/deletenote")
	public ResponseEntity<Response> deletenote(@RequestHeader String id,@RequestHeader String token) {
	
		return new ResponseEntity<Response>(noteimpl.deleteNote(id,token), HttpStatus.OK);
	}

	/**
	 * @param id
	 * @param token
	 * @return response regarding pin/unpinned note
	 */
	@PostMapping("/pin")
	public ResponseEntity<Response>  pin(@RequestHeader String noteId, @RequestHeader String token) {
	
		
		return new ResponseEntity<Response>(  noteimpl.pin(noteId, token), HttpStatus.OK);
	}

	/**
	 * @param id
	 * @param token
	 * @return response regarding archive/unarchive note
	 */
	@PostMapping("/archive")
	public ResponseEntity<Response> archive(@RequestHeader String noteId, @RequestHeader String token) {
		
		return new ResponseEntity<Response>(noteimpl.archive(noteId, token), HttpStatus.OK);
	}

	/**
	 * @param id
	 * @param token
	 * @return response regarding trash/untrash note
	 */
	@PostMapping("/trash")
	public ResponseEntity<Response> trash(@RequestHeader String noteId, @RequestHeader String token) {
	
		
		return new ResponseEntity<Response>(noteimpl.trash(noteId, token), HttpStatus.OK);
	
	}

	/**
	 * @return display all the notes present in database
	 */
	@GetMapping("/show")
	public Response showNotes() {
		List<?> list = noteimpl.showNotes();
		return new Response(200, "note list", list);
	}



	/**
	 * @return Sort Note BY title
	 */
	@GetMapping("/sortnotesbytitle")
	public Response sortNotesByTitle() {

		List<?> list = noteimpl.sortNotesByTitle();
		return new Response(200, noteenv.getProperty("SORTED_TITLE"), list);
	}

	/**
	 * @return Sort Notes by date
	 */
	@GetMapping("/sortnotesbydate")
	public Response sortNotesByDate() {

		List<?> list = noteimpl.sortNotesByDate();
		return new Response(200, noteenv.getProperty("SORTED_DATE"), list);
	}
	
	
	/**
	 * @return Sort Note BY title in descending order
	 */
	@GetMapping("/sortnotesbytitleindesc")
	public Response sortNotesByTitleInDesc() {

		List<?> list = noteimpl.sortNotesByTitleInDesc();
		return new Response(200, noteenv.getProperty("SORTED_TITLE_DESC"), list);
	}

	/**
	 * @return Sort Notes by date in descending order
	 */
	@GetMapping("/sortnotesbydateindesc")
	public Response sortNotesByDateInDesc() {

		List<?> list = noteimpl.sortNotesByDateInDesc();
		return new Response(200, noteenv.getProperty("SORTED_DATE_DESC"), list);
	}

	/**
	 * @param noteId-             Id of the Note
	 * @param CollaboratorsEmail -Id of the Collaborators
	 * @param token              -Token of the user
	 * @return Adding Collaborators
	 */
	@PostMapping("/addCollaborator")
	public ResponseEntity<Response> addCollaborator(@RequestHeader String noteId, @Valid @RequestBody Collaboratorsdto collaboratorsdto,
			@RequestHeader String token) {
		
		return new ResponseEntity<Response>( noteimpl.addCollaborator(noteId, collaboratorsdto, token), HttpStatus.OK);
	}

	/**
	 * @param noteId                -Id of The Note
	 * @param CollaboratorsEmail-    Id of the Collaborators
	 * @param token-                 Token of the user
	 * @return removing Collaborators
	 */
	@DeleteMapping("/removeCollaborator")
	public ResponseEntity<Response> removeCollaborator(@RequestHeader String noteId,@Valid @RequestBody Collaboratorsdto collaboratorsdto,
			@RequestHeader String token) {

		return new ResponseEntity<Response>(noteimpl.removeCollaborator(noteId, collaboratorsdto, token), HttpStatus.OK);
	}
	
	/**
	 * @param token -token of the user
	 * @param year- year to be set
	 * @param month- month to be set
	 * @param day- day to be set
	 * @param hour- hour to be set
	 * @param minute- minute to be set
	 * @param second- second to be set
	 * @return add remainder
	 */
	@PostMapping("/addRemainder")
	public ResponseEntity<Response> addRemainder(@RequestHeader String noteId,@RequestHeader String token ,@RequestParam("year") int year ,@RequestParam("month") int month ,@RequestParam("day") int day,@RequestParam("hour") int hour,@RequestParam("minute")int minute, @RequestParam("second") int second) 
	{
		
		
		
		return new ResponseEntity<Response>(noteimpl.addRemainder(noteId,token,year,month,day,hour,minute,second), HttpStatus.OK);
	}
	
	/**
	 * @param token of the user
	 * @param noteId - noteId of the user
	 * @return remainder deleted successfully
	 */
	@DeleteMapping("/deleteRemainder")
	public ResponseEntity<Response>deleteRemainder(@RequestHeader String noteId ,@RequestHeader String token ) 
	{
		
		return new ResponseEntity<Response>(noteimpl.deleteRemainder(noteId,token), HttpStatus.OK);

	}
	/**
	 * @param token -token of the user
	 * @param year- year to be set
	 * @param month- month to be set
	 * @param day- day to be set
	 * @param hour- hour to be set
	 * @param minute- minute to be set
	 * @param second- second to be set
	 * @return edit remainder
	 */
	@PutMapping("/editRemainder")
	public ResponseEntity<Response>editRemainder(@RequestHeader String noteId,@RequestHeader String token ,@RequestParam("year") int year ,@RequestParam("month") int month ,@RequestParam("day") int day,@RequestParam("hour") int hour,@RequestParam("minute")int minute, @RequestParam("second") int second) 
	{
		
		
		return new ResponseEntity<Response>(noteimpl.editRemainder(noteId,token,year,month,day,hour,minute,second), HttpStatus.OK);

	}


}
