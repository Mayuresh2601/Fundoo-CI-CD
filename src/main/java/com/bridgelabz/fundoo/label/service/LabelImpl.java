/**
 * @author Rohit Thorawade
 * @purpose Performing CURD Operations
 *
 */
package com.bridgelabz.fundoo.label.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.jwt.utility.Jwt;
import com.bridgelabz.fundoo.label.dto.Labeldto;
import com.bridgelabz.fundoo.label.model.Label;
import com.bridgelabz.fundoo.label.repository.LabelRepo;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.notes.repository.NoteRepo;
import com.bridgelabz.fundoo.notes.service.NoteImpl;
import com.bridgelabz.fundoo.repository.UserRepo;
import com.bridgelabz.fundoo.response.Response;

@Service

public class LabelImpl implements LabelOperations {

	@Autowired
	LabelRepo labelrepo;

	@Autowired
	Jwt jwt;

	@Autowired
	NoteRepo noterepo;

	@Autowired
	NoteImpl noteimpl;

	@Autowired
	UserRepo userrepo;

	@Autowired
	Environment labelenv;

	/**
	 * Creating label for the user
	 */
	@Override
	public Response createLabel(Labeldto labeldto, String token) {

		String email = jwt.getUserToken(token);
		User user = userrepo.findByEmailId(email);
		if (email!=null) {

			ModelMapper mapper = new ModelMapper();

			Label label = mapper.map(labeldto, Label.class);
			label.setEmailId(email);
			label.setLabeltitle(labeldto.getLabeltitle());

			LocalDateTime now = LocalDateTime.now();
			label.setCreatedate(now);
			labelrepo.save(label);

			return new Response(200,labelenv.getProperty("LABEL_CREATED"),HttpStatus.OK);

		}
		return new Response(400,labelenv.getProperty("UNAUTHORIZED_USER"),HttpStatus.BAD_REQUEST);

	}

	/**
	 * Deleting label for the user
	 */
	@Override
	public Response deleteLabel(String noteId, String id, String token) {

		String emailId = jwt.getUserToken(token);
		User user = userrepo.findByEmailId(emailId);
		if (emailId!=null) {
			labelrepo.deleteById(id);
			Note note = (Note) noterepo.findByIdAndEmailId(noteId, id);
			//note.getLabellist().removeIf(i -> i.getLabelId().equals(id));
			noterepo.save(note);

			return new Response(200,labelenv.getProperty("LABEL_DELETED"),HttpStatus.OK);
		}
		return new Response(400,labelenv.getProperty("UNAUTHORIZED_USER"),HttpStatus.BAD_REQUEST);

	}

	/**
	 * updating label for the user
	 */
	@Override
	public Response updateLabel(String id, Labeldto labeldto, String token) {
		String emailId = jwt.getUserToken(token);
		User user = userrepo.findByEmailId(emailId);

		if (emailId!=null) {
			
			Label label = labelrepo.findById(id).get();
			label.setLabeltitle(labeldto.getLabeltitle());
			LocalDateTime now = LocalDateTime.now();
			label.setEditdate(now);
			labelrepo.save(label);

			return new Response(200, (labelenv.getProperty("LABEL_UPDATED")), HttpStatus.OK);
		}
		return new Response(400,labelenv.getProperty("UNAUTHORIZED_USER"),HttpStatus.BAD_REQUEST);
	}

	/**
	 * Show all the label
	 */
	@Override
	public List<Label> show() {
		return labelrepo.findAll();

	}

	/**
	 * Find the specific label
	 */
	public Response findlabel(String id, String token) {
		String emailId = jwt.getUserToken(token);
		User user = userrepo.findByEmailId(emailId);
		if (emailId!=null) {
			
			Optional<Label> label = labelrepo.findById(id);
			return new Response(200, labelenv.getProperty("LABEL_DATA"), label);
		}
		
		return new Response(400,labelenv.getProperty("UNAUTHORIZED_USER"),HttpStatus.BAD_REQUEST);

	}

	/**
	 * Adding label into notes and notes into labellist
	 */
	@Override
	public Response addLabelToNote(String noteId, String labelId, String token) {
		String emailId = jwt.getUserToken(token);
		//User user = userrepo.findByEmailId(emailId);

		if (emailId!=null)
		{
			Note note = (Note) noterepo.findByIdAndEmailId(noteId, emailId);
			
			Label label = labelrepo.findById(labelId).get();
			note.getLabellist().add(label);
			System.out.println(note);
			label.getNotelist().add(note);
			noterepo.save(note);
//				user.getNotelist().add(note);
//				userrepo.save(user);
			labelrepo.save(label);
			return new Response(200,labelenv.getProperty("LABEL_ADDED_TO_NOTE"),HttpStatus.OK);
			

		}
		return new Response(400,labelenv.getProperty("UNAUTHORIZED_USER"),HttpStatus.BAD_REQUEST);

	}

}
