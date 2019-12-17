/**
 * @author Rohit Thorawade
 * @Purpose Creating label Controller class 
 *
 */
package com.bridgelabz.fundoo.label.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.label.dto.Labeldto;
import com.bridgelabz.fundoo.label.model.Label;
import com.bridgelabz.fundoo.label.service.LabelImpl;
import com.bridgelabz.fundoo.response.Response;

@RestController
@RequestMapping("/label")

public class LabelController {

	@Autowired
	LabelImpl labelimpl;
	
	@Autowired
	Environment labelenv;
	

	

	/**
	 * @param labeldto- dto of label class
	 * @param token     of the user
	 * @return creating label
	 */
	@PostMapping("/create")
	public ResponseEntity<Response> createlabel(@Valid @RequestBody Labeldto labeldto, @RequestHeader String token) {
		
		return new ResponseEntity<Response>(labelimpl.createLabel(labeldto, token), HttpStatus.OK);
		
	}

	/**
	 * @param id-labelid
	 * @param token -token of the user
	 * @return deleting specific label
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<Response> deletelabel(@RequestHeader String noteId,@RequestHeader String id,@RequestHeader String token) {
		
		return new ResponseEntity<Response>(labelimpl.deleteLabel(noteId,id,token), HttpStatus.OK);

	}

	/**
	 * @param labeldto -dto class of label
	 * @param token -token of the user
	 * @return Updated label
	 */
	@PutMapping("/update")
	public ResponseEntity<Response> updatelabel(@RequestHeader String id,@Valid @RequestBody Labeldto labeldto,@RequestHeader String token) {

		return new ResponseEntity<Response>( labelimpl.updateLabel(id,labeldto, token), HttpStatus.OK);

	}

	/**
	 * @return show all the label
	 */
	@GetMapping("/show")
	public Response show() {
		List<Label> list = labelimpl.show();
		return new Response(200,  labelenv.getProperty("SHOW_ALL"), list);

	}

	/**
	 * @param id of the label
	 * @param token of the user
	 * @return find specific label
	 */
	@GetMapping("/findlabel")
	public Response findlabel(@RequestHeader String id,@RequestHeader String token) {
		return new Response(200, labelenv.getProperty("LABEL_FOUND") , labelimpl.findlabel(id,token));

	}
	
	/**
	 * @param noteId     -Id of the Note
	 * @param labelId-   Id of the label
	 * @param token-     Token of the User
	 * @return Add label
	 */
	@PostMapping("/addlabeltonote")
	public Response addLabelToNote(@RequestHeader String noteId, @RequestHeader String labelId,
			@RequestHeader String token) {

		return new Response(200,  labelenv.getProperty("LABEL_ADDED_TO_NOTE"), labelimpl.addLabelToNote(noteId, labelId, token));
	
	}
	
	

}
